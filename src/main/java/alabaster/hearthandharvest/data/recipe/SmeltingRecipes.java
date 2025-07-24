package alabaster.hearthandharvest.data.recipe;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class SmeltingRecipes
{

    public static void register(RecipeOutput output) {
        foodSmeltingRecipes("cooked_sausage", HHModItems.RAW_SAUSAGE.get(), HHModItems.COOKED_SAUSAGE.get(), 0.35F, output);
        foodSmeltingRecipes("raisins_from_red_grapes", HHModItems.RED_GRAPES.get(), HHModItems.RAISINS.get(), 0.35F, output);
        foodSmeltingRecipes("raisins_from_green_grapes", HHModItems.GREEN_GRAPES.get(), HHModItems.RAISINS.get(), 0.35F, output);
        foodSmeltingRecipes("roasted_peanuts", HHModItems.PEANUT.get(), HHModItems.ROASTED_PEANUTS.get(), 0.35F, output);

        // Marshmallows have only a campfire recipe
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(HHModItems.MARSHMALLOW_STICK.get()), RecipeCategory.FOOD,
                        HHModItems.ROASTED_MARSHMALLOW_STICK.get(), 0.35F, 200)
                .unlockedBy("has_marshmallow", InventoryChangeTrigger.TriggerInstance.hasItems(HHModItems.MARSHMALLOW_STICK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "roasted_marshmallow").toString() + "_from_campfire");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(HHModItems.ROASTED_MARSHMALLOW_STICK.get()), RecipeCategory.FOOD,
                        HHModItems.CHARRED_MARSHMALLOW_STICK.get(), 0.35F, 200)
                .unlockedBy("has_roasted_marshmallow", InventoryChangeTrigger.TriggerInstance.hasItems(HHModItems.ROASTED_MARSHMALLOW_STICK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "charred_marshmallow").toString() + "_from_campfire");
    }

    private static void foodSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, RecipeOutput output) {
        String namePrefix = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, name).toString();
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, namePrefix + "_from_smelting");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, namePrefix + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, namePrefix + "_from_smoking");
    }
}