package alabaster.hearthandharvest.client;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.entity.container.CaskMenu;
import alabaster.hearthandharvest.common.utilities.HHTextUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class CaskGUI extends AbstractContainerScreen<CaskMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "textures/gui/cask_gui.png");

    private static final Rectangle PROGRESS_ARROW = new Rectangle(81, 29, 0, 18);

    public static final Rectangle DIM_LIGHT = new Rectangle(89, 52, 13, 16);
    public static final Rectangle MEDIUM_LIGHT = new Rectangle(89, 52, 13, 16);
    public static final Rectangle BRIGHT_LIGHT = new Rectangle(89, 52, 13, 16);

    private static final Rectangle LEFT_BUBBLE = new Rectangle(108, 48, 9, 24);
    private static final Rectangle RIGHT_BUBBLE = new Rectangle(147, 48, 9, 24);

    private boolean widthTooNarrow;

    public CaskGUI(CaskMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
        // Render UI background
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.minecraft == null)
            return;

        gui.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Render progress arrow
        int l = this.menu.getCookProgressionScaled();
        gui.blit(BACKGROUND_TEXTURE, this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 176, 4, l + 1, PROGRESS_ARROW.height);

        // Render bubbles
        if (menu.getProgression() != 0) {
            int bubScale = (int) ((((this.menu.getProgression() / 80)) * LEFT_BUBBLE.height) % (LEFT_BUBBLE.height + 1));
            gui.blit(BACKGROUND_TEXTURE, this.leftPos + LEFT_BUBBLE.x, this.topPos + LEFT_BUBBLE.y - bubScale, 176, 111 - bubScale, LEFT_BUBBLE.width, bubScale + 1);
            gui.blit(BACKGROUND_TEXTURE, this.leftPos + RIGHT_BUBBLE.x, this.topPos + RIGHT_BUBBLE.y - bubScale, 186, 111 - bubScale, RIGHT_BUBBLE.width, bubScale + 1);
        }

        // Render light indicator
        int light = this.menu.blockEntity.getCurrentLightLevel();
        String lightCategory = light <= 5 ? "dim" : light <= 10 ? "medium" : "bright";
        switch (lightCategory) {
            case "dim" -> gui.blit(BACKGROUND_TEXTURE, this.leftPos + DIM_LIGHT.x, this.topPos + DIM_LIGHT.y, 176, 64, DIM_LIGHT.width, DIM_LIGHT.height);
            case "medium" -> gui.blit(BACKGROUND_TEXTURE, this.leftPos + MEDIUM_LIGHT.x, this.topPos + MEDIUM_LIGHT.y, 176, 48, MEDIUM_LIGHT.width, MEDIUM_LIGHT.height);
            case "bright" -> gui.blit(BACKGROUND_TEXTURE, this.leftPos + BRIGHT_LIGHT.x, this.topPos + BRIGHT_LIGHT.y, 176, 32, BRIGHT_LIGHT.width, BRIGHT_LIGHT.height);
        }
    }

    private void renderLightTooltip(GuiGraphics gui, int mouseX, int mouseY) {
        if (this.isHovering(89, 52, 13, 16, mouseX, mouseY)) {
            int light = this.menu.blockEntity.getCurrentLightLevel();
            String lightCategory = light <= 5 ? "dim" : light <= 10 ? "medium" : "bright";
            String key = switch (lightCategory) {
                case "dim" -> "container.cask.dim";
                case "medium" -> "container.cask.medium";
                case "bright" -> "container.cask.bright";
                default -> "container.cask.default";
            };
            gui.renderTooltip(font, HHTextUtils.getTranslation(key), mouseX, mouseY);
        }
    }


        @Override
    public void render(GuiGraphics gui, final int mouseX, final int mouseY, float partialTicks) {
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
        this.renderLightTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return (!this.widthTooNarrow && super.isHovering(x, y, width, height, mouseX, mouseY));
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        super.renderLabels(gui, mouseX, mouseY);
        gui.drawString(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), 4210752, false);
    }
}