package alabaster.hearthandharvest.integration.jei;

import alabaster.hearthandharvest.common.crafting.BottleCrateRecipe;
import alabaster.hearthandharvest.common.crafting.CaskRecipe;
import alabaster.hearthandharvest.common.crafting.FluidExtractionRecipe;
import alabaster.hearthandharvest.common.crafting.StompingBasinRecipe;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public class HHRecipes
{
    private final RecipeManager recipeManager;

    public HHRecipes() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }

    public List<RecipeHolder<CaskRecipe>> getCaskRecipes() {
        return recipeManager.getAllRecipesFor(HHModRecipeTypes.AGING.get());
    }

    public List<RecipeHolder<StompingBasinRecipe>> getStompingRecipes() {
        return recipeManager.getAllRecipesFor(HHModRecipeTypes.STOMPING.get());
    }

    public List<RecipeHolder<FluidExtractionRecipe>> getFluidExtractionRecipes() {
        return recipeManager.getAllRecipesFor(HHModRecipeTypes.FLUID_EXTRACTION.get());
    }
}
