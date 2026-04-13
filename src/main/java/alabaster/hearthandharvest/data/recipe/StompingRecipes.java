package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.data.builder.StompingBasinRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

public class StompingRecipes {

    public static final int HALF_BOTTLE = 125;
    public static final int BOTTLE = 250;
    public static final int HALF_BUCKET = 500;
    public static final int BUCKET = 1000;

    public static void register(RecipeOutput output) {
        stompJuices(output);
        stompMaterials(output);
    }

    private static void stompJuices(RecipeOutput output) {
        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.BLUEBERRY_JUICE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.BLUEBERRIES.get(), 4)
                .unlockedByAnyIngredient(HHModItems.BLUEBERRIES.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.CHERRY_JUICE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.CHERRY.get(), 4)
                .unlockedByAnyIngredient(HHModItems.CHERRY.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.RASPBERRY_JUICE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.RASPBERRY.get(), 4)
                .unlockedByAnyIngredient(HHModItems.RASPBERRY.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.RED_GRAPE_JUICE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.RED_GRAPES.get(), 4)
                .unlockedByAnyIngredient(HHModItems.RED_GRAPES.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.GREEN_GRAPE_JUICE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.GREEN_GRAPES.get(), 4)
                .unlockedByAnyIngredient(HHModItems.GREEN_GRAPES.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.SWEET_BERRY_JUICE.get(), HALF_BUCKET))
                .addIngredient(Items.SWEET_BERRIES, 4)
                .unlockedByAnyIngredient(Items.SWEET_BERRIES)
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.MELON_JUICE.get(), HALF_BUCKET))
                .addIngredient(Items.MELON_SLICE, 4)
                .unlockedByAnyIngredient(Items.MELON_SLICE)
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.GLOW_BERRY_JUICE.get(), HALF_BUCKET))
                .addIngredient(Items.GLOW_BERRIES, 4)
                .unlockedByAnyIngredient(Items.GLOW_BERRIES)
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.COOKING_OIL.get(), HALF_BUCKET))
                .addIngredient(HHModItems.PEANUT.get(), 4)
                .unlockedByAnyIngredient(HHModItems.PEANUT.get())
                .build(output, "cooking_oil_from_peanuts");

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.COOKING_OIL.get(), HALF_BOTTLE))
                .addIngredient(Tags.Items.SEEDS, 8)
                .unlockedByAnyIngredient()
                .build(output, "cooking_oil_from_seeds");

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.COOKING_OIL.get(), BUCKET))
                .addIngredient(HHModItems.CORN.get(), 4)
                .unlockedByAnyIngredient(HHModItems.CORN.get())
                .build(output, "cooking_oil_from_corn");
    }

    private static void stompMaterials(RecipeOutput output) {
        StompingBasinRecipeBuilder.stomping(Items.SLIME_BALL, 9)
                .addIngredient(Items.SLIME_BLOCK, 1)
                .unlockedByAnyIngredient(Items.SLIME_BLOCK)
                .build(output);
        StompingBasinRecipeBuilder.stomping(Items.SUGAR, 1)
                .addIngredient(Items.BEETROOT, 1)
                .unlockedByAnyIngredient(Items.BEETROOT)
                .build(output);
        StompingBasinRecipeBuilder.stomping(new FluidStack(Fluids.WATER, BUCKET), Items.SPONGE, 1)
                .addIngredient(Items.WET_SPONGE, 1)
                .unlockedByAnyIngredient(Items.WET_SPONGE)
                .build(output);
    }
}