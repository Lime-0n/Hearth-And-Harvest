package alabaster.hearthandharvest.common.crafting;

import alabaster.hearthandharvest.common.registry.HHModRecipeSerializers;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
import alabaster.hearthandharvest.common.tag.HHModTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class BottleCrateRecipe implements CraftingRecipe {
    public final ShapedRecipePattern pattern;
    final ItemStack result;
    final String group;
    final CraftingBookCategory category;
    final boolean showNotification;

    public BottleCrateRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
        this.group = group;
        this.category = category;
        this.pattern = pattern;
        this.result = result;
        this.showNotification = showNotification;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HHModRecipeSerializers.BOTTLE_CRATE.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeType<?> getType() {
        return HHModRecipeTypes.BOTTLE_CRATE.get();
    }

    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.pattern.matches(input);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput container) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(container.size(), ItemStack.EMPTY);

        for (int index = 0; index < remainders.size(); ++index) {
            ItemStack selectedStack = container.getItem(index);
            if (selectedStack.is(HHModTags.BOTTLES)) {
                remainders.set(index, selectedStack.copy());
            }
        }

        return remainders;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.pattern.ingredients();
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.pattern.width() && height >= this.pattern.height();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return new ItemStack(this.result.getItem());
    }

    public static class Serializer implements RecipeSerializer<BottleCrateRecipe> {
        public static final MapCodec<BottleCrateRecipe> CODEC = RecordCodecBuilder.mapCodec((p_340778_) -> p_340778_.group(Codec.STRING.optionalFieldOf("group", "").forGetter((p_311729_) -> p_311729_.group), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((p_311732_) -> p_311732_.category), ShapedRecipePattern.MAP_CODEC.forGetter((p_311733_) -> p_311733_.pattern), ItemStack.STRICT_CODEC.fieldOf("result").forGetter((p_311730_) -> p_311730_.result), Codec.BOOL.optionalFieldOf("show_notification", true).forGetter((p_311731_) -> p_311731_.showNotification)).apply(p_340778_, BottleCrateRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BottleCrateRecipe> STREAM_CODEC = StreamCodec.of(BottleCrateRecipe.Serializer::toNetwork, BottleCrateRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        public MapCodec<BottleCrateRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, BottleCrateRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BottleCrateRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            CraftingBookCategory craftingbookcategory = (CraftingBookCategory)buffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = (ShapedRecipePattern)ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            ItemStack itemstack = (ItemStack)ItemStack.STREAM_CODEC.decode(buffer);
            boolean flag = buffer.readBoolean();
            return new BottleCrateRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, flag);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, BottleCrateRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeBoolean(recipe.showNotification);
        }
    }
}
