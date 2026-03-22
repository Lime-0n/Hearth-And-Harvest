package alabaster.hearthandharvest.common.crafting;

import alabaster.hearthandharvest.common.block.entity.StompingBasinBlockEntity;
import alabaster.hearthandharvest.common.registry.HHModRecipeSerializers;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;

public class StompingBasinRecipe implements Recipe<RecipeWrapper> {

    private final NonNullList<Ingredient> ingredients;
    private final FluidStack resultFluid;
    private final ItemStack  resultItem;

    public StompingBasinRecipe(NonNullList<Ingredient> ingredients, FluidStack resultFluid, ItemStack resultItem) {
        this.ingredients = ingredients;
        this.resultFluid = resultFluid;
        this.resultItem  = resultItem;
    }

    @Override
    public boolean matches(RecipeWrapper wrapper, Level level) {
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < StompingBasinBlockEntity.ITEM_SLOTS; i++) {
            ItemStack stack = wrapper.getItem(i);
            for (int j = 0; j < stack.getCount(); j++) {
                inputs.add(stack.copyWithCount(1));
                // Stop expanding once we have enough entries for the bijection.
                if (inputs.size() == this.ingredients.size()) break;
            }
            if (inputs.size() == this.ingredients.size()) break;
        }
        return inputs.size() == this.ingredients.size()
                && RecipeMatcher.findMatches(inputs, this.ingredients) != null;
    }

    @Override
    public ItemStack assemble(RecipeWrapper wrapper, HolderLookup.Provider registries) {
        return resultItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return resultItem;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HHModRecipeSerializers.STOMPING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return HHModRecipeTypes.STOMPING.get();
    }

    public FluidStack getResultFluid() {
        return resultFluid;
    }

    public ItemStack getResultItem() {
        return resultItem;
    }

    // Serializer
    public static class Serializer implements RecipeSerializer<StompingBasinRecipe> {

        private static final MapCodec<StompingBasinRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC_NONEMPTY
                                .listOf()
                                .fieldOf("ingredients")
                                .xmap(list -> {
                                    NonNullList<Ingredient> nnList = NonNullList.create();
                                    nnList.addAll(list);
                                    return nnList;
                                }, nnList -> nnList)
                                .forGetter(StompingBasinRecipe::getIngredients),

                        FluidStack.CODEC
                                .optionalFieldOf("result_fluid", FluidStack.EMPTY)
                                .forGetter(StompingBasinRecipe::getResultFluid),

                        ItemStack.OPTIONAL_CODEC
                                .optionalFieldOf("result_item", ItemStack.EMPTY)
                                .forGetter(StompingBasinRecipe::getResultItem)

                ).apply(instance, StompingBasinRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, StompingBasinRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public Serializer() {}

        @Override
        public MapCodec<StompingBasinRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, StompingBasinRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static StompingBasinRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            int count = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(count, Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));

            FluidStack fluid = FluidStack.STREAM_CODEC.decode(buf);
            ItemStack  item  = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);

            return new StompingBasinRecipe(ingredients, fluid, item);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, StompingBasinRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            FluidStack.STREAM_CODEC.encode(buf, recipe.resultFluid);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, recipe.resultItem);
        }
    }
}