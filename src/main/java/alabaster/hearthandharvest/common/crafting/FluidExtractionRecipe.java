package alabaster.hearthandharvest.common.crafting;

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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class FluidExtractionRecipe implements Recipe<RecipeWrapper> {

    private final FluidStack fluid;
    private final Ingredient container;
    private final ItemStack result;

    public FluidExtractionRecipe(FluidStack fluid, Ingredient container, ItemStack result) {
        this.fluid = fluid;
        this.container = container;
        this.result = result;
    }

    @Override
    public boolean matches(RecipeWrapper wrapper, Level level) {
        return false;
    }

    public boolean matches(FluidStack tankFluid, ItemStack heldItem) {
        if (!container.test(heldItem)) return false;
        if (tankFluid.isEmpty()) return false;
        if (!tankFluid.is(fluid.getFluid())) return false;
        return tankFluid.getAmount() >= fluid.getAmount();
    }

    @Override
    public ItemStack assemble(RecipeWrapper wrapper, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) { return true; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(container);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HHModRecipeSerializers.FLUID_EXTRACTION.get();
    }

    @Override
    public RecipeType<?> getType() {
        return HHModRecipeTypes.FLUID_EXTRACTION.get();
    }

    public FluidStack getFluid() {
        return fluid;
    }
    public Ingredient getContainer() {
        return container;
    }
    public ItemStack  getResult() {
        return result;
    }

    public static class Serializer implements RecipeSerializer<FluidExtractionRecipe> {

        private static final MapCodec<FluidExtractionRecipe> CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        FluidStack.CODEC
                                .fieldOf("fluid")
                                .forGetter(FluidExtractionRecipe::getFluid),
                        Ingredient.CODEC_NONEMPTY
                                .fieldOf("container")
                                .forGetter(FluidExtractionRecipe::getContainer),
                        ItemStack.STRICT_CODEC
                                .fieldOf("result")
                                .forGetter(FluidExtractionRecipe::getResult)
                ).apply(instance, FluidExtractionRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FluidExtractionRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public Serializer() {}

        @Override
        public MapCodec<FluidExtractionRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FluidExtractionRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static FluidExtractionRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            FluidStack fluid = FluidStack.STREAM_CODEC.decode(buf);
            Ingredient container = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new FluidExtractionRecipe(fluid, container, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, FluidExtractionRecipe recipe) {
            FluidStack.STREAM_CODEC.encode(buf, recipe.fluid);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.container);
            ItemStack.STREAM_CODEC.encode(buf, recipe.result);
        }
    }
}