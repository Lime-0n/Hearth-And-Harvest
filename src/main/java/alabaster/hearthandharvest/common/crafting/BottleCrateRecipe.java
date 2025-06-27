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
        if (container.size() != 9) return false;

        for (int index = 0; index < container.size(); ++index) {
            ItemStack stack = container.getItem(index);
            if (stack.isEmpty() || !input.test(stack)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registryAccess) {
        return new ItemStack(result.getItem());
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput container) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(container.size(), ItemStack.EMPTY);
        return remainders;
    }

    public int getWidth() { return 3; }
    public int getHeight() { return 3; }

    @Override public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.withSize(9, input);
        return list;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HHModRecipeSerializers.BOTTLE_CRATE.get();
    }

    @Override
    public boolean isSpecial() {
        return false; // allow recipe book discovery
    }

    // --- Serializer ---

    public static class Serializer implements RecipeSerializer<BottleCrateRecipe> {
        private static final MapCodec<BottleCrateRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(BottleCrateRecipe::category),
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