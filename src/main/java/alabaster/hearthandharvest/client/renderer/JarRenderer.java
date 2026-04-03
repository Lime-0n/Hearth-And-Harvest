package alabaster.hearthandharvest.client.renderer;

import alabaster.hearthandharvest.common.block.JarBlock;
import alabaster.hearthandharvest.common.block.entity.JarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class JarRenderer implements BlockEntityRenderer<JarBlockEntity> {

    private static final float[][] SLOTS = {
            { -0.25f, 0f, -0.25f },
            {  0.25f, 0f, -0.25f },
            { -0.25f, 0f,  0.25f },
            {  0.25f, 0f,  0.25f },
    };

    private static final float JAR_SCALE = 1f;

    public JarRenderer(BlockEntityRendererProvider.Context ctx) { }

    @Override
    public void render(JarBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        List<Item> jars = be.getJars();
        if (jars.isEmpty()) return;

        Direction facing = be.getBlockState().getValue(JarBlock.FACING);
        float rotationY = facingToRotationY(facing);

        var itemRenderer = Minecraft.getInstance().getItemRenderer();

        for (int i = 0; i < jars.size(); i++) {
            Item jar = jars.get(i);
            if (jar == null || jar == Items.AIR) continue;

            float[] slot = SLOTS[i];

            poseStack.pushPose();

            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(rotationY));
            poseStack.translate(slot[0], slot[1], slot[2]);
            poseStack.scale(JAR_SCALE, JAR_SCALE, JAR_SCALE);

            itemRenderer.renderStatic(
                    new ItemStack(jar),
                    ItemDisplayContext.NONE,
                    packedLight,
                    packedOverlay,
                    poseStack,
                    bufferSource,
                    be.getLevel(),
                    (int) be.getBlockPos().asLong()
            );

            poseStack.popPose();
        }
    }

    private static float facingToRotationY(Direction facing) {
        return switch (facing) {
            case SOUTH -> 180f;
            case WEST -> 90f;
            case EAST -> -90f;
            default -> 0f;
        };
    }
}