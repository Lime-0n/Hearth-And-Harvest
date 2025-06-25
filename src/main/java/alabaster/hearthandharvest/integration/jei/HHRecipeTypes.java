package alabaster.hearthandharvest.integration.jei;

import alabaster.hearthandharvest.common.crafting.BottleCrateRecipe;
import alabaster.hearthandharvest.common.crafting.CaskRecipe;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.RecipeHolder;

public final class HHRecipeTypes
{
    public static final RecipeType<RecipeHolder<CaskRecipe>> AGING = RecipeType.createFromVanilla(HHModRecipeTypes.AGING.get());
}
