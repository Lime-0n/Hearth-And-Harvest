package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class SmeltingRecipes
{

    public static void register(Consumer<FinishedRecipe> consumer) {
        foodSmeltingRecipes("cooked_sausage", HHModItems.RAW_SAUSAGE.get(), HHModItems.COOKED_SAUSAGE.get(), 0.35F, consumer);
        foodSmeltingRecipes("red_grape_raisins", HHModItems.RED_GRAPES.get(), HHModItems.RAISINS.get(), 0.2F, consumer);
        foodSmeltingRecipes("green_grape_raisins", HHModItems.GREEN_GRAPES.get(), HHModItems.RAISINS.get(), 0.2F, consumer);
        foodSmeltingRecipes("roasted_peanuts", HHModItems.PEANUT.get(), HHModItems.ROASTED_PEANUTS.get(), 0.35F, consumer);

        // Marshmallows have only a campfire recipe
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(HHModItems.MARSHMALLOW_STICK.get()), RecipeCategory.FOOD,
                        HHModItems.ROASTED_MARSHMALLOW_STICK.get(), 0.35F, 200)
                .unlockedBy("has_marshmallow", InventoryChangeTrigger.TriggerInstance.hasItems(HHModItems.MARSHMALLOW_STICK.get()))
                .save(consumer, new ResourceLocation(HearthAndHarvest.MODID, "roasted_marshmallow") + "_from_campfire");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(HHModItems.ROASTED_MARSHMALLOW_STICK.get()), RecipeCategory.FOOD,
                        HHModItems.CHARRED_MARSHMALLOW_STICK.get(), 0.35F, 200)
                .unlockedBy("has_roasted_marshmallow", InventoryChangeTrigger.TriggerInstance.hasItems(HHModItems.ROASTED_MARSHMALLOW_STICK.get()))
                .save(consumer, new ResourceLocation(HearthAndHarvest.MODID, "charred_marshmallow") + "_from_campfire");
    }

    private static void foodSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, Consumer<FinishedRecipe> consumer) {
        String namePrefix = new ResourceLocation(HearthAndHarvest.MODID, name).toString();
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_smoking");
    }
}