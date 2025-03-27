package alabaster.hearthandharvest.common.crafting;

import alabaster.hearthandharvest.client.recipebook.CaskRecipeBookTab;
import alabaster.hearthandharvest.common.registry.ModItems;
import alabaster.hearthandharvest.common.registry.ModRecipeSerializers;
import alabaster.hearthandharvest.common.registry.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Optional;

public class CaskRecipe implements Recipe<RecipeWrapper>
{
    public static final int INPUT_SLOTS = 4;

    private final CaskRecipeBookTab tab;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final float experience;
    private final int cookTime;

    public CaskRecipe(@Nullable CaskRecipeBookTab tab, NonNullList<Ingredient> inputItems, ItemStack output, float experience, int cookTime) {
        this.tab = tab;
        this.inputItems = inputItems;
        this.output = output;
        this.experience = experience;
        this.cookTime = cookTime;
    }

    @Nullable
    public CaskRecipeBookTab getRecipeBookTab() {
        return this.tab;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, HolderLookup.Provider provider) {
        return this.output.copy();
    }

    public float getExperience() {
        return this.experience;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for (int j = 0; j < INPUT_SLOTS; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.inputItems.size();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.AGING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.AGING.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModItems.CASK.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaskRecipe that = (CaskRecipe) o;

        if (Float.compare(that.getExperience(), getExperience()) != 0) return false;
        if (getCookTime() != that.getCookTime()) return false;
        if (tab != that.tab) return false;
        if (!inputItems.equals(that.inputItems)) return false;
        if (!output.equals(that.output)) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        int result = getGroup().hashCode();
        result = 31 * result + (getRecipeBookTab() != null ? getRecipeBookTab().hashCode() : 0);
        result = 31 * result + inputItems.hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + (getExperience() != 0.0f ? Float.floatToIntBits(getExperience()) : 0);
        result = 31 * result + getCookTime();
        return result;
    }

    public static class Serializer implements RecipeSerializer<CaskRecipe>
    {
        private static final MapCodec<CaskRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                CaskRecipeBookTab.CODEC.optionalFieldOf("recipe_book_tab").xmap(optional -> optional.orElse(null), Optional::of).forGetter(CaskRecipe::getRecipeBookTab),
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients").xmap(ingredients -> {
                    NonNullList<Ingredient> nonNullList = NonNullList.create();
                    nonNullList.addAll(ingredients);
                    return nonNullList;
                }, ingredients -> ingredients).forGetter(CaskRecipe::getIngredients),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.output),
                Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(CaskRecipe::getExperience),
                Codec.INT.optionalFieldOf("cookingtime", 200).forGetter(CaskRecipe::getCookTime)
        ).apply(inst, CaskRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CaskRecipe> STREAM_CODEC = StreamCodec.of(CaskRecipe.Serializer::toNetwork, CaskRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        @Override
        public MapCodec<CaskRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CaskRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CaskRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            CaskRecipeBookTab tabIn = CaskRecipeBookTab.findByName(buffer.readUtf());
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);
            inputItemsIn.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            ItemStack outputIn = ItemStack.STREAM_CODEC.decode(buffer);
            float experienceIn = buffer.readFloat();
            int cookTimeIn = buffer.readVarInt();
            return new CaskRecipe(tabIn, inputItemsIn, outputIn, experienceIn, cookTimeIn);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CaskRecipe recipe) {
            buffer.writeUtf(recipe.tab != null ? recipe.tab.toString() : "");
            buffer.writeVarInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.cookTime);
        }
    }
}