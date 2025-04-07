package alabaster.hearthandharvest.integration.jei;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.crafting.CaskRecipe;
import mezz.jei.api.recipe.RecipeType;

public final class HHRecipeTypes
{
    public static final RecipeType<CaskRecipe> AGING = RecipeType.create(HearthAndHarvest.MODID, "aging", CaskRecipe.class);
}
