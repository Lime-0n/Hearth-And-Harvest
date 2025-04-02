package alabaster.hearthandharvest.client.gui;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.utilities.HHTextUtils;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nonnull;
import java.util.List;

public class CaskRecipeBookComponent extends RecipeBookComponent
{
    protected static final ResourceLocation RECIPE_BOOK_BUTTONS = new ResourceLocation(HearthAndHarvest.MODID, "textures/gui/recipe_book_buttons.png");

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(0, 0, 28, 18, RECIPE_BOOK_BUTTONS);
    }

    public void hide() {
        this.setVisible(false);
    }

    @Override
    @Nonnull
    protected Component getRecipeFilterName() {
        return HHTextUtils.getTranslation("container.recipe_book.ageable");
    }

    @Override
    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        ItemStack resultStack = recipe.getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(recipe);

        if (slots.get(4).getItem().isEmpty()) {
            this.ghostRecipe.addIngredient(Ingredient.of(resultStack), (slots.get(4)).x, (slots.get(4)).y);
        }
        this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), recipe, recipe.getIngredients().iterator(), 0);
    }
}
