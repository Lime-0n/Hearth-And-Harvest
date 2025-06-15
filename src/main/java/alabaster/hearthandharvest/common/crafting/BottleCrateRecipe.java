package alabaster.hearthandharvest.common.crafting;

import alabaster.hearthandharvest.common.registry.HHModRecipeSerializers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class BottleCrateRecipe extends CustomRecipe {
    private final Ingredient input;
    private final ItemStack result;

    public BottleCrateRecipe(CraftingBookCategory category, Ingredient input, ItemStack result) {
        super(category);
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput container, Level level) {
        int count = 0;

        for (int i = 0; i < container.size(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (input.test(stack)) {
                    count++;
                } else {
                    return false;
                }
            }
        }

        return count == 9;
    }

    @Override
    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
        return result.copy();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput container) {
        return NonNullList.withSize(container.size(), ItemStack.EMPTY); // fully consume
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HHModRecipeSerializers.BOTTLE_CRATE_RECIPE.get();
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    public static class Serializer implements RecipeSerializer<BottleCrateRecipe> {
        private static final MapCodec<BottleCrateRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(r -> r.category()),
                Ingredient.CODEC.fieldOf("input").forGetter(r -> r.input),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result)
        ).apply(instance, BottleCrateRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, BottleCrateRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork,
                Serializer::fromNetwork
        );

        @Override
        public MapCodec<BottleCrateRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BottleCrateRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BottleCrateRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new BottleCrateRecipe(category, input, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, BottleCrateRecipe recipe) {
            buf.writeEnum(recipe.category());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.input);
            ItemStack.STREAM_CODEC.encode(buf, recipe.result);
        }
    }

}
