package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import alabaster.hearthandharvest.common.registry.HHModItems;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

public class CuttingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {

        // Knife
        cuttingFoods(consumer);

        // Cleaver
        choppingAnimalItems(consumer);
    }

    private static void cuttingFoods(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.RASPBERRY_PIE.get()), Ingredient.of(ModTags.KNIVES), HHModItems.RASPBERRY_PIE_SLICE.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.BLUEBERRY_PIE.get()), Ingredient.of(ModTags.KNIVES), HHModItems.BLUEBERRY_PIE_SLICE.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.GRAPE_PIE.get()), Ingredient.of(ModTags.KNIVES), HHModItems.GRAPE_PIE_SLICE.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.GOAT_CHEESE_WHEEL.get()), Ingredient.of(ModTags.KNIVES), HHModItems.GOAT_CHEESE_SLICE.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CHEDDAR_CHEESE_WHEEL.get()), Ingredient.of(ModTags.KNIVES), HHModItems.CHEDDAR_CHEESE_SLICE.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CARROT_CAKE.get()), Ingredient.of(ModTags.KNIVES), HHModItems.CARROT_CAKE_SLICE.get(), 7)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CHICKEN_POT_PIE.get()), Ingredient.of(ModTags.KNIVES), HHModItems.CHICKEN_POT_PIE_SLICE.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.SUNFLOWER), Ingredient.of(ModTags.KNIVES), HHModItems.SUNFLOWER_SEEDS.get(), 2)
                .addResultWithChance(HHModItems.SUNFLOWER_SEEDS.get(), 0.5F, 2)
                .build(consumer);
    }

    private static void choppingAnimalItems(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BEEF), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 3)
                .build(consumer, new ResourceLocation(HearthAndHarvest.MODID,"sausage_from_beef"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.PORKCHOP), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 2)
                .build(consumer, new ResourceLocation(HearthAndHarvest.MODID,"sausage_from_porkchop"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.MUTTON), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 3)
                .build(consumer, new ResourceLocation(HearthAndHarvest.MODID, "sausage_from_mutton"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.CHICKEN), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 1)
                .build(consumer, new ResourceLocation(HearthAndHarvest.MODID, "sausage_from_chicken"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.RABBIT), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 1)
                .build(consumer, new ResourceLocation(HearthAndHarvest.MODID, "sausage_from_rabbit"));
    }
}