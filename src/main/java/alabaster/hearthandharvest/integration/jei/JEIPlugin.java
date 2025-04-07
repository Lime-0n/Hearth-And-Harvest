package alabaster.hearthandharvest.integration.jei;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.client.gui.CaskGUI;
import alabaster.hearthandharvest.common.block.entity.container.CaskMenu;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.registry.HHModMenuTypes;
import alabaster.hearthandharvest.integration.jei.category.AgingRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin
{
    private static final ResourceLocation ID = new ResourceLocation(HearthAndHarvest.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new AgingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        HHRecipes modRecipes = new HHRecipes();
        registration.addRecipes(HHRecipeTypes.AGING, modRecipes.getCaskRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(HHModItems.CASK.get()), HHRecipeTypes.AGING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CaskGUI.class, 89, 25, 24, 17, HHRecipeTypes.AGING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CaskMenu.class, HHModMenuTypes.CASK.get(), HHRecipeTypes.AGING, 0, 6, 9, 36);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}