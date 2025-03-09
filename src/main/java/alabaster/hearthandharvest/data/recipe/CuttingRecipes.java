package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.tag.ModTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import alabaster.hearthandharvest.common.registry.ModItems;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
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
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.RASPBERRY_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.RASPBERRY_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BLUEBERRY_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.BLUEBERRY_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.GRAPE_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.GRAPE_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.GOAT_CHEESE_WHEEL.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.GOAT_CHEESE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CHEESE_WHEEL.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.CHEESE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CARROT_CAKE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.CARROT_CAKE_SLICE.get(), 7)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CHICKEN_POT_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.CHICKEN_POT_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.SUNFLOWER), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.SUNFLOWER_SEEDS.get(), 6)
                .build(output);
    }

    private static void choppingAnimalItems(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BEEF), Ingredient.of(ModTags.CLEAVERS), ModItems.RAW_SAUSAGE.get(), 3)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_beef"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.PORKCHOP), Ingredient.of(ModTags.CLEAVERS), ModItems.RAW_SAUSAGE.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID,"sausage_from_porkchop"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.MUTTON), Ingredient.of(ModTags.CLEAVERS), ModItems.RAW_SAUSAGE.get(), 3)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_mutton"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.CHICKEN), Ingredient.of(ModTags.CLEAVERS), ModItems.RAW_SAUSAGE.get(), 1)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_chicken"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.RABBIT), Ingredient.of(ModTags.CLEAVERS), ModItems.RAW_SAUSAGE.get(), 1)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sausage_from_rabbit"));
    }
}