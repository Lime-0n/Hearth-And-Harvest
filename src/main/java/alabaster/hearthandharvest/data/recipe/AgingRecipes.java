package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.client.recipebook.CaskRecipeBookTab;
import alabaster.hearthandharvest.common.registry.ModItems;
import alabaster.hearthandharvest.data.builder.CaskRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

public class AgingRecipes {
    public static final int FAST_AGING = 300;      // 1.25 minutes
    public static final int NORMAL_AGING = 600;    // 2.5 minutes
    public static final int SLOW_AGING = 1200;     // 5 minutes

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(RecipeOutput output) {
        ageCheese(output);
        ageDrinks(output);
        agePickles(output);
    }

    private static void ageCheese(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(ModItems.CHEDDAR_CHEESE_WHEEL.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .unlockedByAnyIngredient(ModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.GOAT_CHEESE_WHEEL.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.UNRIPE_GOAT_CHEESE_WHEEL.get())
                .unlockedByAnyIngredient(ModItems.UNRIPE_GOAT_CHEESE_WHEEL.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);
    }

    private static void ageDrinks(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(ModItems.RED_GRAPE_WINE.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(ModItems.GRAPE_JUICE.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.GRAPE_JUICE.get())
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.BLUEBERRY_WINE.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(ModItems.BLUEBERRY_JUICE.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.BLUEBERRY_JUICE.get())
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.RASPBERRY_WINE.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(ModItems.RASPBERRY_JUICE.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.RASPBERRY_JUICE.get())
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.MEAD.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(Items.HONEY_BOTTLE)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.HONEY_BOTTLE)
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);
    }

    private static void agePickles(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(ModItems.PICKLED_CARROTS.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(ModItems.JAR.get())
                .unlockedByAnyIngredient(Items.CARROT)
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.PICKLED_POTATOES.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(ModItems.JAR.get())
                .unlockedByAnyIngredient(Items.POTATO)
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.PICKLED_ONIONS.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(ModItems.JAR.get())
                .unlockedByAnyIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.PICKLED_CABBAGE.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE.get())
                .addIngredient(ModItems.JAR.get())
                .unlockedByAnyIngredient(vectorwing.farmersdelight.common.registry.ModItems.CABBAGE.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.PICKLED_BEETROOTS.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.SALT.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(ModItems.JAR.get())
                .unlockedByAnyIngredient(Items.BEETROOT)
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);
    }
}
