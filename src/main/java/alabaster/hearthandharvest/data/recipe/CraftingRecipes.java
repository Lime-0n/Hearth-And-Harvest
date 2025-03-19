package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.ModItems;
import alabaster.hearthandharvest.common.tag.ModTags;
import net.minecraft.ResourceLocationException;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import vectorwing.farmersdelight.common.tag.CommonTags;

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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TREE_TAPPER.get(), 1)
                .pattern(" I ")
                .pattern("SBS")
                .pattern(" S ")
                .define('B', Items.BUCKET)
                .define('I', Items.IRON_INGOT)
                .define('S', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CASK.get(), 1)
                .pattern("SSS")
                .pattern("CCC")
                .pattern("SSS")
                .define('C', Items.COPPER_INGOT)
                .define('S', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_copper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.JAR.get(), 4)
                .pattern("G G")
                .pattern("G G")
                .pattern("GGG")
                .define('G', Items.GLASS_PANE)
                .unlockedBy("has_glass_pane", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_PANE))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.JUG.get(), 1)
                .pattern(" I ")
                .pattern("IBI")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('B', Items.BUCKET)
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNIVERSAL_FEED.get(), 8)
                .pattern("   ")
                .pattern("HCF")
                .pattern("ABS")
                .define('H', Items.HAY_BLOCK)
                .define('C', Items.CARROT)
                .define('A', Items.APPLE)
                .define('B', Items.BONE)
                .define('F', Items.SALMON)
                .define('S', Items.SWEET_BERRIES)
                .unlockedBy("has_hay_bale", InventoryChangeTrigger.TriggerInstance.hasItems(Items.HAY_BLOCK))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WATERING_CAN.get(), 1)
                .pattern("C  ")
                .pattern("CBC")
                .pattern("CC ")
                .define('C', Items.COPPER_INGOT)
                .define('B', Items.BUCKET)
                .unlockedBy("has_copper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
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
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.ROPE_COIL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .unlockedBy("has_rope", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get()))
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
                .pattern("   ")
                .pattern(" # ")
                .pattern("   ")
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
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.FLINT_CLEAVER.get(), 1)
                .pattern("  #")
                .pattern(" # ")
                .pattern("S  ")
                .define('#', Items.FLINT)
                .define('S', Items.STICK)
                .unlockedBy("has_flint", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_CLEAVER.get(), 1)
                .pattern("  #")
                .pattern(" # ")
                .pattern("S  ")
                .define('#', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.GOLDEN_CLEAVER.get(), 1)
                .pattern("  #")
                .pattern(" # ")
                .pattern("S  ")
                .define('#', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_CLEAVER.get(), 1)
                .pattern("  #")
                .pattern(" # ")
                .pattern("S  ")
                .define('#', Items.DIAMOND)
                .define('S', Items.STICK)
                .unlockedBy("has_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(output);
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
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "apple_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GOLDEN_APPLE, 9)
                .requires(ModItems.GOLDEN_APPLE_CRATE.get())
                .unlockedBy("has_golden_apple_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLDEN_APPLE_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "golden_apple_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GOLDEN_CARROT, 9)
                .requires(ModItems.GOLDEN_CARROT_CRATE.get())
                .unlockedBy("has_golden_carrot_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLDEN_CARROT_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "golden_carrot_from_crate"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COTTON.get(), 9)
                .requires(ModItems.COTTON_BALE.get())
                .unlockedBy("has_cotton_bale", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COTTON_BALE.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 9)
                .requires(ModItems.SPOOL.get())
                .unlockedBy("has_spool", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SPOOL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "string_from_spool"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, vectorwing.farmersdelight.common.registry.ModItems.ROPE.get(), 9)
                .requires(ModItems.ROPE_COIL.get())
                .unlockedBy("has_rope_coil", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ROPE_COIL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "rope_from_coil"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SALT.get(), 9)
                .requires(ModItems.SALT_BAG.get())
                .unlockedBy("has_salt_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SALT_BAG.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.SUGAR, 9)
                .requires(ModItems.SUGAR_BAG.get())
                .unlockedBy("has_sugar_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SUGAR_BAG.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "sugar_from_bag"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.COCOA_BEANS, 9)
                .requires(ModItems.COCOA_BEAN_BAG.get())
                .unlockedBy("has_cocoa_bean_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COCOA_BEAN_BAG.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "cocoa_bean_from_bag"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.GUNPOWDER, 9)
                .requires(ModItems.GUNPOWDER_BAG.get())
                .unlockedBy("has_gunpowder_bag", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GUNPOWDER_BAG.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "gunpowder_from_bag"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.EGG, 9)
                .requires(ModItems.EGG_CRATE.get())
                .unlockedBy("has_egg_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EGG_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "egg_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.TURTLE_EGG, 9)
                .requires(ModItems.TURTLE_EGG_CRATE.get())
                .unlockedBy("has_turtle_egg_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TURTLE_EGG_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "turtle_egg_from_crate"));
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
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "honey_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.BROWN_MUSHROOM, 9)
                .requires(ModItems.BROWN_MUSHROOM_CRATE.get())
                .unlockedBy("has_brown_mushroom_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BROWN_MUSHROOM_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "brown_mushroom_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.RED_MUSHROOM, 9)
                .requires(ModItems.RED_MUSHROOM_CRATE.get())
                .unlockedBy("has_red_mushroom_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RED_MUSHROOM_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "red_mushroom_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.CRIMSON_FUNGUS, 9)
                .requires(ModItems.CRIMSON_FUNGUS_CRATE.get())
                .unlockedBy("has_crimson_fungus_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRIMSON_FUNGUS_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "crimson_fungus_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.WARPED_FUNGUS, 9)
                .requires(ModItems.WARPED_FUNGUS_CRATE.get())
                .unlockedBy("has_warped_fungus_crate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WARPED_FUNGUS_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "warped_fungus_from_crate"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 2)
                .requires(ModItems.COTTON.get())
                .requires(ModItems.COTTON.get())
                .requires(ModItems.COTTON.get())
                .unlockedBy("has_cotton", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COTTON.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "string_from_cotton"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHARCOAL, 1)
                .requires(ModItems.CHARRED_MARSHMALLOW_STICK.get())
                .unlockedBy("has_charred_marshmallow", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CHARRED_MARSHMALLOW_STICK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "charcoal_from_charred_marshmallow"));
    }

    private static void recipesFoodstuffs(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.MARSHMALLOW_STICK.get(), 1)
                .pattern(" #")
                .pattern("S ")
                .define('#', Items.SUGAR)
                .define('S', Items.STICK)
                .unlockedBy("has_sugar", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SUGAR))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.COTTON_CANDY.get(), 1)
                .pattern(" W ")
                .pattern("#C#")
                .pattern(" S ")
                .define('#', Items.SUGAR)
                .define('S', Items.STICK)
                .define('W', Items.WIND_CHARGE)
                .define('C', ModItems.COTTON.get())
                .unlockedBy("has_cotton", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COTTON.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.PEANUT_BUTTER_COOKIE.get(), 8)
                .pattern("   ")
                .pattern("WPW")
                .pattern("   ")
                .define('W', Items.WHEAT)
                .define('P', ModItems.PEANUT_BUTTER.get())
                .unlockedBy("has_peanut_butter", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEANUT_BUTTER.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.BLUEBERRY_MUFFIN.get(), 1)
                .pattern(" B ")
                .pattern("BSB")
                .pattern(" W ")
                .define('W', Items.WHEAT)
                .define('S', Items.SUGAR)
                .define('B', ModItems.BLUEBERRIES.get())
                .unlockedBy("has_blueberries", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLUEBERRIES.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.RASPBERRY_PIE.get(), 1)
                .pattern("WWW")
                .pattern("FFF")
                .pattern("SPS")
                .define('W', Items.WHEAT)
                .define('S', Items.SUGAR)
                .define('F', ModItems.RASPBERRY.get())
                .define('P', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy("has_raspberry", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RASPBERRY.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.BLUEBERRY_PIE.get(), 1)
                .pattern("WWW")
                .pattern("FFF")
                .pattern("SPS")
                .define('W', Items.WHEAT)
                .define('S', Items.SUGAR)
                .define('F', ModItems.BLUEBERRIES.get())
                .define('P', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy("has_blueberries", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLUEBERRIES.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.GRAPE_PIE.get(), 1)
                .pattern("WWW")
                .pattern("FFF")
                .pattern("SPS")
                .define('W', Items.WHEAT)
                .define('S', Items.SUGAR)
                .define('F', ModItems.GRAPES.get())
                .define('P', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy("has_grapes", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GRAPES.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.RASPBERRY_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.RASPBERRY_PIE_SLICE.get())
                .unlockedBy("has_raspberry_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RASPBERRY_PIE_SLICE.get()))
                .group("raspberry_pie")
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "raspberry_pie_from_slices"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.BLUEBERRY_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.BLUEBERRY_PIE_SLICE.get())
                .unlockedBy("has_blueberry_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLUEBERRY_PIE_SLICE.get()))
                .group("blueberry_pie")
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "blueberry_pie_from_slices"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.GRAPE_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.GRAPE_PIE_SLICE.get())
                .unlockedBy("has_grape_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GRAPE_PIE_SLICE.get()))
                .group("grape_pie")
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "grape_pie_from_slices"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHICKEN_POT_PIE.get(), 1)
                .pattern(" C ")
                .pattern("TVO")
                .pattern("MPS")
                .define('T', Items.POTATO)
                .define('V', Items.CARROT)
                .define('S', ModItems.SALT.get())
                .define('M', CommonTags.FOODS_MILK)
                .define('C', vectorwing.farmersdelight.common.registry.ModItems.COOKED_CHICKEN_CUTS.get())
                .define('O', vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .define('P', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy("has_chicken_cuts", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.COOKED_CHICKEN_CUTS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHICKEN_POT_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.CHICKEN_POT_PIE_SLICE.get())
                .unlockedBy("has_grape_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GRAPE_PIE_SLICE.get()))
                .group("grape_pie")
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "chicken_pot_pie_from_slices"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CARROT_CAKE.get())
                .pattern("CMC")
                .pattern("SES")
                .pattern("WWW")
                .define('C', Items.CARROT)
                .define('S', Items.SUGAR)
                .define('W', Items.SUGAR)
                .define('E', Items.EGG)
                .define('M', CommonTags.FOODS_MILK)
                .unlockedBy("has_grapes", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GRAPES.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CARROT_CAKE.get())
                .requires(ModItems.CARROT_CAKE_SLICE.get())
                .requires(ModItems.CARROT_CAKE_SLICE.get())
                .requires(ModItems.CARROT_CAKE_SLICE.get())
                .requires(ModItems.CARROT_CAKE_SLICE.get())
                .requires(ModItems.CARROT_CAKE_SLICE.get())
                .requires(ModItems.CARROT_CAKE_SLICE.get())
                .requires(ModItems.CARROT_CAKE_SLICE.get())
                .unlockedBy("has_carrot_cake_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CARROT_CAKE_SLICE.get()))
                .group("carrot_cake")
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "carrot_cake_from_slices"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.JERKY.get(), 3)
                .requires(ModItems.SALT.get())
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.ROTTEN_FLESH)
                .unlockedBy("has_rotten_flesh", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ROTTEN_FLESH))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "jerky"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SKEWERED_SAUSAGE.get(), 1)
                .requires(ModItems.COOKED_SAUSAGE.get())
                .requires(Items.STICK)
                .unlockedBy("has_cooked_sausage", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_SAUSAGE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "skewered_sausage"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.PEANUT_BUTTER_AND_JELLY_SANDWICH.get(), 1)
                .requires(ModTags.JAMS)
                .requires(ModItems.PEANUT_BUTTER.get())
                .requires(Items.BREAD)
                .unlockedBy("has_peanut_butter", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEANUT_BUTTER.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "peanut_butter_and_jelly_sandwich"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.BATTER.get(), 1)
                .requires(CommonTags.FOODS_MILK)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.BOWL)
                .unlockedBy("has_wheat", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "batter"));
    }

    private static void recipesFoodBlocks(RecipeOutput output) {

    }

    private static void recipesCraftedMeals(RecipeOutput output) {

    }
}