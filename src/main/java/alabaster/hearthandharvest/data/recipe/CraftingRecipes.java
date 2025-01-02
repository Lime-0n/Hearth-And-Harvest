package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

public class CraftingRecipes
{
    public static final Ingredient WATER_BOTTLE = new Ingredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION).getCustomIngredient());

    public static void register(RecipeOutput output) {
        recipesBlocks(output);
        recipesTools(output);
        recipesMaterials(output);
        recipesFoodstuffs(output);
        recipesFoodBlocks(output);
        recipesCraftedMeals(output);
    }

    private static void recipesBlocks(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.RASPBERRY_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RASPBERRY.get())
                .unlockedBy("has_raspberry", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RASPBERRY.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BLUEBERRY_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.BLUEBERRIES.get())
                .unlockedBy("has_blueberry", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLUEBERRIES.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GRAPE_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.GRAPES.get())
                .unlockedBy("has_grape", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GRAPES.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PEANUT_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.PEANUT.get())
                .unlockedBy("has_peanut", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEANUT.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.APPLE_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.APPLE)
                .unlockedBy("has_apple", InventoryChangeTrigger.TriggerInstance.hasItems(Items.APPLE))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GOLDEN_APPLE_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.GOLDEN_APPLE)
                .unlockedBy("has_golden_apple", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLDEN_APPLE))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GOLDEN_CARROT_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.GOLDEN_CARROT)
                .unlockedBy("has_golden_carrot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLDEN_CARROT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.COTTON_BALE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.COTTON.get())
                .unlockedBy("has_cotton", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COTTON.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SPOOL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.STRING)
                .unlockedBy("has_string", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STRING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SALT_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.SALT.get())
                .unlockedBy("has_salt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SALT.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SUGAR_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.SUGAR)
                .unlockedBy("has_sugar", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SUGAR))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.COCOA_BEAN_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.COCOA_BEANS)
                .unlockedBy("has_cocoa_bean", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COCOA_BEANS))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GUNPOWDER_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.GUNPOWDER)
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.EGG_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.EGG)
                .unlockedBy("has_egg", InventoryChangeTrigger.TriggerInstance.hasItems(Items.EGG))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.TURTLE_EGG_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.TURTLE_EGG)
                .unlockedBy("has_turtle_egg", InventoryChangeTrigger.TriggerInstance.hasItems(Items.TURTLE_EGG))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.MILK_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .unlockedBy("has_milk_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GOAT_MILK_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.GOAT_MILK_BOTTLE.get())
                .unlockedBy("has_goat_milk_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOAT_MILK_BOTTLE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.MEAD_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.MEAD.get())
                .unlockedBy("has_mead", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MEAD.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WINE_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WINE.get())
                .unlockedBy("has_wine", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WINE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.HONEY_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.HONEY_BOTTLE)
                .unlockedBy("has_honey_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(Items.HONEY_BOTTLE))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WATER_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', WATER_BOTTLE)
                .unlockedBy("has_water_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(Items.POTION))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BROWN_MUSHROOM_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.BROWN_MUSHROOM)
                .unlockedBy("has_brown_mushroom", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BROWN_MUSHROOM))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.RED_MUSHROOM_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.RED_MUSHROOM)
                .unlockedBy("has_red_mushroom", InventoryChangeTrigger.TriggerInstance.hasItems(Items.RED_MUSHROOM))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CRIMSON_FUNGUS_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.CRIMSON_FUNGUS)
                .unlockedBy("has_crimson_fungus", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRIMSON_FUNGUS))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WARPED_FUNGUS_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.WARPED_FUNGUS)
                .unlockedBy("has_warped_fungus", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WARPED_FUNGUS))
                .save(output);
    }

    private static void recipesTools(RecipeOutput output) {

    }

    private static void recipesMaterials(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RASPBERRY.get(), 9)
                .requires(ModItems.RASPBERRY_CRATE.get())
                .unlockedBy("has_raspberry_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RASPBERRY_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BLUEBERRIES.get(), 9)
                .requires(ModItems.BLUEBERRY_CRATE.get())
                .unlockedBy("has_blueberry_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLUEBERRY_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.GRAPES.get(), 9)
                .requires(ModItems.GRAPE_CRATE.get())
                .unlockedBy("has_grape_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GRAPE_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.PEANUT.get(), 9)
                .requires(ModItems.PEANUT_CRATE.get())
                .unlockedBy("has_peanut_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEANUT_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.APPLE, 9)
                .requires(ModItems.APPLE_CRATE.get())
                .unlockedBy("has_apple_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.APPLE_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.GOLDEN_APPLE.getDefaultInstance().getItem(), 9)
                .requires(ModItems.GOLDEN_APPLE_CRATE.get())
                .unlockedBy("has_golden_apple_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLDEN_APPLE_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.GOLDEN_CARROT.getDefaultInstance().getItem(), 9)
                .requires(ModItems.GOLDEN_CARROT_CRATE.get())
                .unlockedBy("has_golden_carrot_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLDEN_CARROT_CRATE.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COTTON.get(), 9)
                .requires(ModItems.COTTON_BALE.get())
                .unlockedBy("has_cotton_bale", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COTTON_BALE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 9)
                .requires(ModItems.SPOOL.get())
                .unlockedBy("has_spool", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SPOOL.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SALT.get(), 9)
                .requires(ModItems.SALT_BAG.get())
                .unlockedBy("has_salt_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SALT_BAG.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.SUGAR, 9)
                .requires(ModItems.SUGAR_BAG.get())
                .unlockedBy("has_sugar_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SUGAR_BAG.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.COCOA_BEANS, 9)
                .requires(ModItems.COCOA_BEAN_BAG.get())
                .unlockedBy("has_cocoa_bean_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COCOA_BEAN_BAG.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.GUNPOWDER, 9)
                .requires(ModItems.GUNPOWDER_BAG.get())
                .unlockedBy("has_gunpowder_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GUNPOWDER_BAG.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.EGG, 9)
                .requires(ModItems.EGG_CRATE.get())
                .unlockedBy("has_egg_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EGG_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.TURTLE_EGG, 9)
                .requires(ModItems.TURTLE_EGG_CRATE.get())
                .unlockedBy("has_turtle_egg_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TURTLE_EGG_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get(), 9)
                .requires(ModItems.MILK_CRATE.get())
                .unlockedBy("has_milk_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MILK_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.GOAT_MILK_BOTTLE.get(), 9)
                .requires(ModItems.GOAT_MILK_CRATE.get())
                .unlockedBy("has_goat_milk_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOAT_MILK_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.MEAD.get(), 9)
                .requires(ModItems.MEAD_CRATE.get())
                .unlockedBy("has_mead_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MEAD_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.WINE.get(), 9)
                .requires(ModItems.WINE_CRATE.get())
                .unlockedBy("has_wine_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WINE_CRATE.get()))
                .save(output);
        //ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,, 9)
        //        .requires(ModItems.WATER_CRATE.get())
        //        .unlockedBy("has_water_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WATER_CRATE.get()))
        //        .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.HONEY_BOTTLE, 9)
                .requires(ModItems.HONEY_CRATE.get())
                .unlockedBy("has_honey_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HONEY_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.BROWN_MUSHROOM, 9)
                .requires(ModItems.BROWN_MUSHROOM_CRATE.get())
                .unlockedBy("has_brown_mushroom_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BROWN_MUSHROOM_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.RED_MUSHROOM, 9)
                .requires(ModItems.RED_MUSHROOM_CRATE.get())
                .unlockedBy("has_red_mushroom_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RED_MUSHROOM_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.CRIMSON_FUNGUS, 9)
                .requires(ModItems.CRIMSON_FUNGUS_CRATE.get())
                .unlockedBy("has_crimson_fungus_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRIMSON_FUNGUS_CRATE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.WARPED_FUNGUS, 9)
                .requires(ModItems.WARPED_FUNGUS_CRATE.get())
                .unlockedBy("has_warped_fungus_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WARPED_FUNGUS_CRATE.get()))
                .save(output);
    }

    private static void recipesFoodstuffs(RecipeOutput output) {

    }

    private static void recipesFoodBlocks(RecipeOutput output) {

    }

    private static void recipesCraftedMeals(RecipeOutput output) {

    }
}