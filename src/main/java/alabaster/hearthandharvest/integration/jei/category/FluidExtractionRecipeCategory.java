package alabaster.hearthandharvest.integration.jei.category;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.crafting.FluidExtractionRecipe;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.utilities.HHTextUtils;
import alabaster.hearthandharvest.integration.jei.HHRecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FluidExtractionRecipeCategory implements IRecipeCategory<RecipeHolder<FluidExtractionRecipe>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            HearthAndHarvest.MODID, "textures/gui/jei/jei_fluid_extraction_gui.png");

    private static final int FLUID_TANK_WIDTH = 26;
    private static final int FLUID_TANK_HEIGHT = 16;
    private static final int FLUID_TANK_CAPACITY = 1000;

    private static final int CONTAINER_X = 28;
    private static final int CONTAINER_Y = 6;

    private static final int FLUID_TANK_X = 23;
    private static final int FLUID_TANK_Y = 28;

    private static final int ITEM_OUTPUT_X = 63;
    private static final int ITEM_OUTPUT_Y = 17;

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    public FluidExtractionRecipeCategory(IGuiHelper helper) {
        title = HHTextUtils.getTranslation("jei.fluid_extraction");
        ResourceLocation backgroundImage = ResourceLocation.fromNamespaceAndPath(
                HearthAndHarvest.MODID, "textures/gui/jei/jei_fluid_extraction_gui.png");
        background = helper.createDrawable(backgroundImage, 33, 16, 102, 50);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(HHModItems.STOMPING_BASIN.get()));
    }

    @Override
    public RecipeType<RecipeHolder<FluidExtractionRecipe>> getRecipeType() {
        return HHRecipeTypes.FLUID_EXTRACTION;
    }

    @Override public Component getTitle() { return title; }
    @Override public IDrawable getBackground() { return background; }
    @Override public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<FluidExtractionRecipe> holder, IFocusGroup focusGroup) {
        FluidExtractionRecipe recipe = holder.value();

        builder.addSlot(RecipeIngredientRole.INPUT, CONTAINER_X, CONTAINER_Y)
                .addIngredients(recipe.getContainer());

        FluidStack fluid = recipe.getFluid();
        if (!fluid.isEmpty()) {
            IDrawable cornerOverlay = new IDrawable() {
                @Override public int getWidth() { return FLUID_TANK_WIDTH; }
                @Override public int getHeight() { return FLUID_TANK_HEIGHT; }

                @Override
                public void draw(GuiGraphics guiGraphics, int mouseX, int mouseY) {
                    int texX = 33 + FLUID_TANK_X;
                    int texY = 16 + FLUID_TANK_Y;
                    int w = FLUID_TANK_WIDTH;
                    int h = FLUID_TANK_HEIGHT;
                    guiGraphics.blit(TEXTURE, FLUID_TANK_X, FLUID_TANK_Y, texX, texY, 1, 1, 256, 256);
                    guiGraphics.blit(TEXTURE, FLUID_TANK_X + w - 1, FLUID_TANK_Y, texX + w-1, texY, 1, 1, 256, 256);
                    guiGraphics.blit(TEXTURE, FLUID_TANK_X, FLUID_TANK_Y + h - 1, texX, texY + h-1, 1, 1, 256, 256);
                    guiGraphics.blit(TEXTURE, FLUID_TANK_X + w - 1, FLUID_TANK_Y + h - 1, texX + w-1, texY + h-1, 1, 1, 256, 256);
                }
            };

            builder.addSlot(RecipeIngredientRole.INPUT, FLUID_TANK_X, FLUID_TANK_Y)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, fluid)
                    .setFluidRenderer(FLUID_TANK_CAPACITY, false, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT)
                    .setOverlay(cornerOverlay, 0, 0);
        }

        // Result item output
        ItemStack result = recipe.getResult();
        if (!result.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, ITEM_OUTPUT_X, ITEM_OUTPUT_Y)
                    .addItemStack(result);
        }
    }

    @Override
    public void draw(RecipeHolder<FluidExtractionRecipe> holder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
    }
}