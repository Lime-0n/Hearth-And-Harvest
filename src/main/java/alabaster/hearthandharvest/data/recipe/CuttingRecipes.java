package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import alabaster.hearthandharvest.common.registry.HHModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;


public class CuttingRecipes {
    public static void register(RecipeOutput output) {
        // Knife
        cuttingFoods(output);

        // Cleaver
        choppingAnimalItems(output);
    }

    private static void cuttingFoods(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.RASPBERRY_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.RASPBERRY_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.BLUEBERRY_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.BLUEBERRY_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.GRAPE_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.GRAPE_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.GOAT_CHEESE_WHEEL.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.GOAT_CHEESE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CHEDDAR_CHEESE_WHEEL.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.CHEDDAR_CHEESE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CARROT_CAKE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.CARROT_CAKE_SLICE.get(), 7)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CHICKEN_POT_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.CHICKEN_POT_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.SUNFLOWER), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.SUNFLOWER_SEEDS.get(), 2)
                .addResultWithChance(HHModItems.SUNFLOWER_SEEDS.get(), 0.5F, 2)
                .build(output);
    }

    private static void choppingAnimalItems(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BEEF), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 3)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_beef"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.PORKCHOP), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID,"sausage_from_porkchop"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.MUTTON), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 3)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_mutton"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.CHICKEN), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 1)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_chicken"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.RABBIT), Ingredient.of(HHModTags.CLEAVERS), HHModItems.RAW_SAUSAGE.get(), 1)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_rabbit"));
    }
}