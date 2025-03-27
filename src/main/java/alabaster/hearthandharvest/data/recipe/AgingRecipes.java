package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.ModItems;
import alabaster.hearthandharvest.data.builder.CaskRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import umpaz.brewinandchewin.common.registry.BnCItems;

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
    }

    private static void ageCheese(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(ModItems.CHEDDAR_CHEESE_WHEEL.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .unlockedByAnyIngredient(ModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.GOAT_CHEESE_WHEEL.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.UNRIPE_GOAT_CHEESE_WHEEL.get())
                .unlockedByAnyIngredient(ModItems.UNRIPE_GOAT_CHEESE_WHEEL.get())
                .build(output);
    }

    private static void ageDrinks(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(ModItems.RED_GRAPE_WINE.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(ModItems.GRAPE_JUICE.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.GRAPE_JUICE.get())
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.BLUEBERRY_WINE.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(ModItems.BLUEBERRY_JUICE.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.BLUEBERRY_JUICE.get())
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.RASPBERRY_WINE.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(ModItems.RASPBERRY_JUICE.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.RASPBERRY_JUICE.get())
                .build(output);

        CaskRecipeBuilder.caskRecipe(ModItems.MEAD.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(Items.HONEY_BOTTLE)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.HONEY_BOTTLE)
                .build(output);
    }

}
