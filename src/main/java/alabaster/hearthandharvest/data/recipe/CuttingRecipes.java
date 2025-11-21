package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;


public class CuttingRecipes {
    public static void register(RecipeOutput output) {
        // Knife
        cuttingFoods(output);
        cuttingFlowers(output);

        // Cleaver
        choppingAnimalItems(output);

        // Axe
        halfCabinets(output);
    }

    private static void cuttingFoods(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.RASPBERRY_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.RASPBERRY_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.BLUEBERRY_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.BLUEBERRY_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.GRAPE_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.GRAPE_PIE_SLICE.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.PEANUT_BUTTER_PIE.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.PEANUT_BUTTER_PIE_SLICE.get(), 4)
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
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CORN.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.CORN_KERNELS.get(), 2)
                .addResult(HHModItems.CORN_HUSK.get())
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.CORN_KERNELS.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.CORN_MEAL.get(), 1)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.WHEAT), Ingredient.of(CommonTags.TOOLS_KNIFE), HHModItems.FLOUR.get(), 2)
                .build(output);
    }

    private static void cuttingFlowers(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.YELLOW_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.YELLOW_DYE, 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.ORANGE_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.ORANGE_DYE, 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.RED_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.RED_DYE, 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.BLUE_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.BLUE_DYE, 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.LIGHT_BLUE_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.LIGHT_BLUE_DYE, 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.PURPLE_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.PURPLE_DYE, 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.PINK_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.PINK_DYE, 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(HHModItems.WHITE_MUM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), Items.WHITE_DYE, 2)
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

    private static void halfCabinets(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.OAK_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.OAK_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "oak_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.SPRUCE_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.SPRUCE_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "spruce_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BIRCH_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.BIRCH_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "birch_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.JUNGLE_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.JUNGLE_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "jungle_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.ACACIA_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.ACACIA_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "acacia_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.DARK_OAK_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.DARK_OAK_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "dark_oak_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.MANGROVE_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.MANGROVE_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "mangrove_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CHERRY_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.CHERRY_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "cherry_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.BAMBOO_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.BAMBOO_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "bamboo_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CRIMSON_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.CRIMSON_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "crimson_half_cabinet_from_full"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.WARPED_CABINET.get()), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), HHModItems.WARPED_HALF_CABINET.get(), 2)
                .build(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "warped_half_cabinet_from_full"));
    }
}