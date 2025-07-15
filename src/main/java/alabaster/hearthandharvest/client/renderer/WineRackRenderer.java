package alabaster.hearthandharvest.client.renderer;

import alabaster.hearthandharvest.common.block.entity.WineRackBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class WineRackRenderer implements BlockEntityRenderer<WineRackBlockEntity> {

    private final ItemRenderer itemRenderer;

    public WineRackRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(WineRackBlockEntity rack, float pt, PoseStack pose, MultiBufferSource buf, int light, int overlay) {
        Direction facing = rack.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        pose.pushPose();
        pose.translate(0.0, 0.0, 0.0);
        pose.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        pose.translate(-0.0, -0.0, -0.0);

        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = rack.getItem(slot);
            if (stack.isEmpty()) continue;

            pose.pushPose();
            int row = slot / 3, col = slot % 3;
            double px = 1./16, spacing = px, slotSize = 4*px;

            // Adjusting the position of the bottle within the slot
            double x = spacing + col * (slotSize + spacing) - 0.125; // Move 2 pixels to the left
            double y = 1.0 - (spacing + slotSize + row * (slotSize + spacing)) + 0.125; // Move 2 pixels up
            double z = 1.0 / 16.0; // Adjust the Z-axis offset as needed


            pose.translate(x, y, z);
            pose.mulPose(Axis.XP.rotationDegrees(90));
            pose.scale(1.0f, 1.0f, 1.0f); // Adjust the Z-axis scaling factor as needed

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIRST_PERSON_LEFT_HAND, light, overlay, pose, buf, rack.getLevel(), 0);
            pose.popPose();
        }

        pose.popPose();
    }

}

