package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.data.builder.StompingBasinRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.fluids.FluidStack;

public class StompingRecipes {

    public static final int BOTTLE = 250;   // one glass bottle worth
    public static final int HALF_BUCKET = 500;
    public static final int BUCKET = 1000;

    public static void register(RecipeOutput output) {
        stompJuices(output);
    }

    private static void stompJuices(RecipeOutput output) {
        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.BLUEBERRY_WINE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.BLUEBERRIES.get(), 4)
                .unlockedByAnyIngredient(HHModItems.BLUEBERRIES.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.CHERRY_WINE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.CHERRY.get(), 4)
                .unlockedByAnyIngredient(HHModItems.CHERRY.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.RASPBERRY_WINE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.RASPBERRY.get(), 4)
                .unlockedByAnyIngredient(HHModItems.RASPBERRY.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.RED_GRAPE_WINE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.RED_GRAPES.get(), 4)
                .unlockedByAnyIngredient(HHModItems.RED_GRAPES.get())
                .build(output);

        StompingBasinRecipeBuilder.stomping(new FluidStack(HHModFluids.GREEN_GRAPE_WINE.get(), HALF_BUCKET))
                .addIngredient(HHModItems.GREEN_GRAPES.get(), 4)
                .unlockedByAnyIngredient(HHModItems.GREEN_GRAPES.get())
                .build(output);
    }
}