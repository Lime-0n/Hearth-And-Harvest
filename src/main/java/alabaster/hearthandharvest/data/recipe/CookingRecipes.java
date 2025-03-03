package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.ModItems;
import alabaster.hearthandharvest.common.tag.ModTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.stream.Stream;

public class CookingRecipes
{
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(RecipeOutput output) {
        jarFoods(output);
        cookMeals(output);
    }

    private static void jarFoods(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.RASPBERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(ModItems.RASPBERRY.get())
                .addIngredient(ModItems.RASPBERRY.get())
                .addIngredient(ModItems.RASPBERRY.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_raspberry", ModItems.RASPBERRY.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BLUEBERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(ModItems.BLUEBERRIES.get())
                .addIngredient(ModItems.BLUEBERRIES.get())
                .addIngredient(ModItems.BLUEBERRIES.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_blueberries", ModItems.BLUEBERRIES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.GRAPE_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(ModItems.GRAPES.get())
                .addIngredient(ModItems.GRAPES.get())
                .addIngredient(ModItems.BLUEBERRIES.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_grapes", ModItems.GRAPES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.APPLE_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(Items.APPLE)
                .addIngredient(Items.APPLE)
                .addIngredient(Items.APPLE)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_apple", Items.APPLE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.SWEET_BERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_sweet_berries", Items.SWEET_BERRIES)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.GLOW_BERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_glow_berries", Items.GLOW_BERRIES)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.MELON_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(Items.MELON_SLICE)
                .addIngredient(Items.MELON_SLICE)
                .addIngredient(Items.MELON_SLICE)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_melon_slice", Items.MELON_SLICE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.PEANUT_BUTTER.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(ModItems.PEANUT.get())
                .addIngredient(ModItems.PEANUT.get())
                .addIngredient(ModItems.PEANUT.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_peanut", ModItems.PEANUT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.PICKLED_BEETROOTS.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_beetroot", Items.BEETROOT)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.PICKLED_CABBAGE.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_cabbage", vectorwing.farmersdelight.common.registry.ModItems.CABBAGE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.PICKLED_CARROTS.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_carrot", Items.CARROT)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.PICKLED_ONIONS.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_onion", vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.PICKLED_POTATOES.get(), 1, SLOW_COOKING, MEDIUM_EXP, ModItems.JAR.get())
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_potato", Items.POTATO)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
    }

    private static void cookMeals(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CARAMEL.get(), 1, FAST_COOKING, MEDIUM_EXP)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_sugar", Items.SUGAR)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CHOCOLATE_BAR.get(), 1, FAST_COOKING, MEDIUM_EXP)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .unlockedByItems("has_cocoa_beans", Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CARAMEL_APPLE.get(), 1, FAST_COOKING, LARGE_EXP, Items.STICK)
                .addIngredient(ModItems.CARAMEL.get())
                .addIngredient(ModItems.CARAMEL.get())
                .addIngredient(ModItems.CARAMEL.get())
                .addIngredient(Items.APPLE)
                .unlockedByItems("has_caramel", ModItems.CARAMEL.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.MEAD.get(), 1, SLOW_COOKING, LARGE_EXP, Items.GLASS_BOTTLE)
                .addIngredient(Items.HONEY_BOTTLE)
                .addIngredient(Items.HONEY_BOTTLE)
                .addIngredient(Items.HONEY_BOTTLE)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_honey", Items.HONEY_BOTTLE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.WINE.get(), 1, SLOW_COOKING, LARGE_EXP, Items.GLASS_BOTTLE)
                .addIngredient(ModItems.GRAPES.get())
                .addIngredient(ModItems.GRAPES.get())
                .addIngredient(ModItems.GRAPES.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_grapes", ModItems.GRAPES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.GRAPE_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(ModItems.GRAPES.get())
                .addIngredient(ModItems.GRAPES.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_grapes", ModItems.GRAPES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BLUEBERRY_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(ModItems.BLUEBERRIES.get())
                .addIngredient(ModItems.BLUEBERRIES.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_blueberries", ModItems.BLUEBERRIES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.RASPBERRY_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(ModItems.RASPBERRY.get())
                .addIngredient(ModItems.RASPBERRY.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_raspberry", ModItems.RASPBERRY.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.SYRUP_BOTTLE.get(), 3, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(ModItems.SAP_BUCKET.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_sap_bucket", ModItems.SAP_BUCKET.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CHEESE_WHEEL.get(), 1, SLOW_COOKING, LARGE_EXP)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_milk_bottle", vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.GOAT_CHEESE_WHEEL.get(), 1, SLOW_COOKING, LARGE_EXP)
                .addIngredient(ModItems.GOAT_MILK_BOTTLE.get())
                .addIngredient(ModItems.GOAT_MILK_BOTTLE.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .unlockedByItems("has_goat_milk_bottle", ModItems.GOAT_MILK_BOTTLE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.MASHED_POTATOES.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(CommonTags.FOODS_MILK)
                .unlockedByItems("has_potato", Items.POTATO)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.MACARONI_AND_CHEESE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.RAW_PASTA.get())
                .addIngredient(ModItems.SALT.get())
                .addIngredient(CommonTags.FOODS_MILK)
                .addIngredient(ModTags.CHEESE_SLICES)
                .unlockedByItems("has_pasta", vectorwing.farmersdelight.common.registry.ModItems.RAW_PASTA.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.ONION_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(CommonTags.FOODS_MILK)
                .addIngredient(ModTags.CHEESE_SLICES)
                .unlockedByItems("has_onion", vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.WAFFLE.get(), 1, FAST_COOKING, SMALL_EXP)
                .addIngredient(ModItems.BATTER.get())
                .addIngredient(ModItems.SYRUP_BOTTLE.get())
                .unlockedByItems("has_batter", ModItems.BATTER.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
    }
}