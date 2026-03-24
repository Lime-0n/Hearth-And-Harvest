package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.client.recipebook.CaskRecipeBookTab;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.tag.HHCommonTags;
import alabaster.hearthandharvest.data.builder.CaskRecipeBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;

public class AgingRecipes {
    public static final int FAST_AGING = 300;      // 1.25 minutes
    public static final int NORMAL_AGING = 600;    // 2.5 minutes
    public static final int SLOW_AGING = 1200;     // 5 minutes

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static final Ingredient WATER_BOTTLE = new Ingredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION).getCustomIngredient());


    public static void register(RecipeOutput output) {
        ageCheese(output);
        ageDrinks(output);
        agePickles(output);
        ageMisc(output);
    }

    private static void ageCheese(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(HHModItems.CHEDDAR_CHEESE_WHEEL.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .unlockedByAnyIngredient(HHModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.GOAT_CHEESE_WHEEL.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHModItems.UNRIPE_GOAT_CHEESE_WHEEL.get())
                .unlockedByAnyIngredient(HHModItems.UNRIPE_GOAT_CHEESE_WHEEL.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);
    }

    private static void ageDrinks(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(HHModItems.HARD_CIDER.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(ModItems.APPLE_CIDER.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.APPLE)
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.MEAD.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(WATER_BOTTLE)
                .addIngredient(Items.HONEY_BLOCK)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.HONEY_BLOCK)
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.ROOT_BEER.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(WATER_BOTTLE)
                .addIngredient(Items.HANGING_ROOTS)
                .addIngredient(Items.HANGING_ROOTS)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.HANGING_ROOTS)
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.MOONSHINE.get(), 1, NORMAL_AGING, SMALL_EXP)
                .addIngredient(WATER_BOTTLE)
                .addIngredient(Items.SUGAR)
                .addIngredient(HHModItems.CORN_MEAL.get())
                .addIngredient(HHModItems.CORN_MEAL.get())
                .unlockedByAnyIngredient(HHModItems.CORN_MEAL.get())
                .setRecipeBookTab(CaskRecipeBookTab.DRINKS)
                .build(output);
    }

    private static void agePickles(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(HHModItems.PICKLED_CARROTS.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(HHModItems.JAR.get())
                .unlockedByAnyIngredient(Items.CARROT)
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.PICKLED_POTATOES.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(HHModItems.JAR.get())
                .unlockedByAnyIngredient(Items.POTATO)
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.PICKLED_ONIONS.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(ModItems.ONION.get())
                .addIngredient(ModItems.ONION.get())
                .addIngredient(HHModItems.JAR.get())
                .unlockedByAnyIngredient(ModItems.ONION.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.PICKLED_CABBAGE.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(ModItems.CABBAGE.get())
                .addIngredient(ModItems.CABBAGE.get())
                .addIngredient(HHModItems.JAR.get())
                .unlockedByAnyIngredient(ModItems.CABBAGE.get())
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);

        CaskRecipeBuilder.caskRecipe(HHModItems.PICKLED_BEETROOTS.get(), 1, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(HHModItems.JAR.get())
                .unlockedByAnyIngredient(Items.BEETROOT)
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output);
    }

    private static void ageMisc(RecipeOutput output) {
        CaskRecipeBuilder.caskRecipe(HHModItems.JERKY.get(), 3, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(Items.ROTTEN_FLESH)
                .addIngredient(Items.ROTTEN_FLESH)
                .addIngredient(Items.ROTTEN_FLESH)
                .unlockedByAnyIngredient(Items.ROTTEN_FLESH)
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output,"jerky_from_rotten_flesh");

        CaskRecipeBuilder.caskRecipe(HHModItems.JERKY.get(), 3, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(Tags.Items.FOODS_COOKED_MEAT)
                .addIngredient(Tags.Items.FOODS_COOKED_MEAT)
                .addIngredient(Tags.Items.FOODS_COOKED_MEAT)
                .unlockedByAnyIngredient()
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output,"jerky_from_cooked_meat");

        CaskRecipeBuilder.caskRecipe(HHModItems.JERKY.get(), 3, SLOW_AGING, MEDIUM_EXP)
                .addIngredient(HHCommonTags.DUSTS_SALT)
                .addIngredient(Tags.Items.FOODS_RAW_MEAT)
                .addIngredient(Tags.Items.FOODS_RAW_MEAT)
                .addIngredient(Tags.Items.FOODS_RAW_MEAT)
                .unlockedByAnyIngredient()
                .setRecipeBookTab(CaskRecipeBookTab.MEALS)
                .build(output,"jerky_from_raw_meat");
    }
}
