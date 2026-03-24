package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.data.builder.FluidExtractionRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.FluidStack;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FluidExtractionRecipes {

    public static final int BOTTLE = 250;
    public static final int HALF_BUCKET = 500;
    public static final int BUCKET = 1000;

    public static void register(RecipeOutput output) {
        bottleJuices(output);
        bucketJuices(output);
    }

    private static void bottleJuices(RecipeOutput output) {
        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.BLUEBERRY_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, HHModItems.BLUEBERRY_JUICE.get())
                .unlockedByAnyIngredient(HHModItems.BLUEBERRIES.get())
                .build(output, "blueberry_juice_bottle");

        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.CHERRY_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, HHModItems.CHERRY_JUICE.get())
                .unlockedByAnyIngredient(HHModItems.CHERRY.get())
                .build(output, "cherry_juice_bottle");

        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.RASPBERRY_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, HHModItems.RASPBERRY_JUICE.get())
                .unlockedByAnyIngredient(HHModItems.RASPBERRY.get())
                .build(output, "raspberry_juice_bottle");

        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.RED_GRAPE_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, HHModItems.RED_GRAPE_JUICE.get())
                .unlockedByAnyIngredient(HHModItems.RED_GRAPES.get())
                .build(output, "red_grape_juice_bottle");

        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.GREEN_GRAPE_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, HHModItems.GREEN_GRAPE_JUICE.get())
                .unlockedByAnyIngredient(HHModItems.GREEN_GRAPES.get())
                .build(output, "green_grape_juice_bottle");

        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.SWEET_BERRY_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, HHModItems.SWEET_BERRY_JUICE.get())
                .unlockedByAnyIngredient(Items.SWEET_BERRIES)
                .build(output, "sweet_berry_juice_bottle");

        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.GLOW_BERRY_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, HHModItems.GLOW_BERRY_JUICE.get())
                .unlockedByAnyIngredient(Items.GLOW_BERRIES)
                .build(output, "glow_berry_juice_bottle");

        FluidExtractionRecipeBuilder
                .extraction(new FluidStack(HHModFluids.MELON_JUICE.get(), BOTTLE),
                        Items.GLASS_BOTTLE, ModItems.MELON_JUICE.get())
                .unlockedByAnyIngredient(Items.MELON_SLICE)
                .build(output, "melon_juice_bottle");
    }

    private static void bucketJuices(RecipeOutput output) {
//        FluidExtractionRecipeBuilder
//                .extraction(new FluidStack(HHModFluids.BLUEBERRY_JUICE.get(), BUCKET),
//                        Items.BUCKET, HHModItems.BLUEBERRY_JUICE_BUCKET.get())
//                .unlockedByAnyIngredient(HHModItems.BLUEBERRIES.get())
//                .build(output, "blueberry_juice_bucket");
//
//        FluidExtractionRecipeBuilder
//                .extraction(new FluidStack(HHModFluids.CHERRY_JUICE.get(), BUCKET),
//                        Items.BUCKET, HHModItems.CHERRY_JUICE_BUCKET.get())
//                .unlockedByAnyIngredient(HHModItems.CHERRY.get())
//                .build(output, "cherry_juice_bucket");
//
//        FluidExtractionRecipeBuilder
//                .extraction(new FluidStack(HHModFluids.RASPBERRY_JUICE.get(), BUCKET),
//                        Items.BUCKET, HHModItems.RASPBERRY_JUICE_BUCKET.get())
//                .unlockedByAnyIngredient(HHModItems.RASPBERRY.get())
//                .build(output, "raspberry_juice_bucket");
//
//        FluidExtractionRecipeBuilder
//                .extraction(new FluidStack(HHModFluids.RED_GRAPE_JUICE.get(), BUCKET),
//                        Items.BUCKET, HHModItems.RED_GRAPE_JUICE_BUCKET.get())
//                .unlockedByAnyIngredient(HHModItems.RED_GRAPES.get())
//                .build(output, "red_grape_juice_bucket");
//
//        FluidExtractionRecipeBuilder
//                .extraction(new FluidStack(HHModFluids.GREEN_GRAPE_JUICE.get(), BUCKET),
//                        Items.BUCKET, HHModItems.GREEN_GRAPE_JUICE_BUCKET.get())
//                .unlockedByAnyIngredient(HHModItems.GREEN_GRAPES.get())
//                .build(output, "green_grape_juice_bucket");
    }
}