package alabaster.hearthandharvest.common.crafting;

import alabaster.hearthandharvest.common.registry.ModRecipeSerializers;
import alabaster.hearthandharvest.common.registry.ModRecipeTypes;
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

public record CaskRecipe(Ingredient inputItem, ItemStack output) implements Recipe<CaskRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(CaskRecipeInput caskRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItem.test(caskRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(CaskRecipeInput caskRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.AGING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.AGING.get();
    }

    public static class Serializer implements RecipeSerializer<CaskRecipe> {
        public static final MapCodec<CaskRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CaskRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(CaskRecipe::output)
        ).apply(inst, CaskRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CaskRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, CaskRecipe::inputItem,
                        ItemStack.STREAM_CODEC, CaskRecipe::output,
                        CaskRecipe::new);

        @Override
        public MapCodec<CaskRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CaskRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}