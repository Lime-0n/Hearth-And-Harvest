package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.common.registry.ModItems;
import alabaster.hearthandharvest.data.builder.CaskRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;

public class AgingRecipes {
    public static final int FAST_AGING = 100;      // 1 minutes
    public static final int NORMAL_AGING = 200;    // 5 minutes
    public static final int SLOW_AGING = 400;      // 10 minutes

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(RecipeOutput output) {
        ageCheese(output);
    }

    private static void ageCheese(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(ModItems.CHEDDAR_CHEESE_WHEEL.get(), 1, NORMAL_AGING, MEDIUM_EXP)
                .addIngredient(ModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .unlockedByAnyIngredient(ModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .build(output);
    }
}
