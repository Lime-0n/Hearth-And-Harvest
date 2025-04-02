package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.function.Consumer;

public class CookingRecipes
{
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(Consumer<FinishedRecipe> consumer) {
        jarFoods(consumer);
        cookMeals(consumer);
    }

    private static void jarFoods(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.RASPBERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(HHModItems.RASPBERRY.get())
                .addIngredient(HHModItems.RASPBERRY.get())
                .addIngredient(HHModItems.RASPBERRY.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_raspberry", HHModItems.RASPBERRY.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.BLUEBERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(HHModItems.BLUEBERRIES.get())
                .addIngredient(HHModItems.BLUEBERRIES.get())
                .addIngredient(HHModItems.BLUEBERRIES.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_blueberries", HHModItems.BLUEBERRIES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.GRAPE_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(HHModItems.GRAPES.get())
                .addIngredient(HHModItems.GRAPES.get())
                .addIngredient(HHModItems.BLUEBERRIES.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_grapes", HHModItems.GRAPES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.APPLE_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(Items.APPLE)
                .addIngredient(Items.APPLE)
                .addIngredient(Items.APPLE)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_apple", Items.APPLE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.SWEET_BERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_sweet_berries", Items.SWEET_BERRIES)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.GLOW_BERRY_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_glow_berries", Items.GLOW_BERRIES)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.MELON_JAM.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(Items.MELON_SLICE)
                .addIngredient(Items.MELON_SLICE)
                .addIngredient(Items.MELON_SLICE)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_melon_slice", Items.MELON_SLICE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.PEANUT_BUTTER.get(), 1, SLOW_COOKING, MEDIUM_EXP, HHModItems.JAR.get())
                .addIngredient(HHModItems.PEANUT.get())
                .addIngredient(HHModItems.PEANUT.get())
                .addIngredient(HHModItems.PEANUT.get())
                .addIngredient(HHModItems.SALT.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_peanut", HHModItems.PEANUT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
    }

    private static void cookMeals(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.CARAMEL.get(), 1, FAST_COOKING, MEDIUM_EXP)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(HHModItems.SALT.get())
                .unlockedByItems("has_sugar", Items.SUGAR)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.CHOCOLATE_BAR.get(), 1, FAST_COOKING, MEDIUM_EXP)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .unlockedByItems("has_cocoa_beans", Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.CARAMEL_APPLE.get(), 1, FAST_COOKING, LARGE_EXP, Items.STICK)
                .addIngredient(HHModItems.CARAMEL.get())
                .addIngredient(HHModItems.CARAMEL.get())
                .addIngredient(HHModItems.CARAMEL.get())
                .addIngredient(Items.APPLE)
                .unlockedByItems("has_caramel", HHModItems.CARAMEL.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.BLUEBERRY_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(HHModItems.BLUEBERRIES.get())
                .addIngredient(HHModItems.BLUEBERRIES.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_blueberries", HHModItems.BLUEBERRIES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.CHERRY_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(HHModItems.CHERRY.get())
                .addIngredient(HHModItems.CHERRY.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_cherries", HHModItems.CHERRY.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.RASPBERRY_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(HHModItems.RASPBERRY.get())
                .addIngredient(HHModItems.RASPBERRY.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_raspberry", HHModItems.RASPBERRY.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.GRAPE_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(HHModItems.GRAPES.get())
                .addIngredient(HHModItems.GRAPES.get())
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_grapes", HHModItems.GRAPES.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.SYRUP_BOTTLE.get(), 3, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(HHModItems.SAP_BUCKET.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByItems("has_sap_bucket", HHModItems.SAP_BUCKET.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get(), 1, SLOW_COOKING, LARGE_EXP)
                .addIngredient(ModItems.MILK_BOTTLE.get())
                .addIngredient(ModItems.MILK_BOTTLE.get())
                .addIngredient(HHModItems.SALT.get())
                .addIngredient(HHModItems.SALT.get())
                .unlockedByItems("has_milk_bottle", ModItems.MILK_BOTTLE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.UNRIPE_GOAT_CHEESE_WHEEL.get(), 1, SLOW_COOKING, LARGE_EXP)
                .addIngredient(HHModItems.GOAT_MILK_BOTTLE.get())
                .addIngredient(HHModItems.GOAT_MILK_BOTTLE.get())
                .addIngredient(HHModItems.SALT.get())
                .addIngredient(HHModItems.SALT.get())
                .unlockedByItems("has_goat_milk_bottle", HHModItems.GOAT_MILK_BOTTLE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.MASHED_POTATOES.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(HHModItems.SALT.get())
                .addIngredient(HHModItems.SALT.get())
                .addIngredient(ForgeTags.MILK_BOTTLE)
                .unlockedByItems("has_potato", Items.POTATO)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.MACARONI_AND_CHEESE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(ModItems.RAW_PASTA.get())
                .addIngredient(HHModItems.SALT.get())
                .addIngredient(ForgeTags.MILK_BOTTLE)
                .addIngredient(HHModTags.CHEESE_SLICES)
                .unlockedByItems("has_pasta", vectorwing.farmersdelight.common.registry.ModItems.RAW_PASTA.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.ONION_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(ForgeTags.MILK_BOTTLE)
                .addIngredient(HHModTags.CHEESE_SLICES)
                .unlockedByItems("has_onion", vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(HHModItems.WAFFLE.get(), 1, FAST_COOKING, SMALL_EXP)
                .addIngredient(HHModItems.BATTER.get())
                .addIngredient(HHModItems.SYRUP_BOTTLE.get())
                .unlockedByItems("has_batter", HHModItems.BATTER.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
    }
}