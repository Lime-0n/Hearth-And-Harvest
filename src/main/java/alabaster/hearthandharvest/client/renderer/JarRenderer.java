package alabaster.hearthandharvest.client.renderer;

import alabaster.hearthandharvest.common.block.JarBlock;
import alabaster.hearthandharvest.common.block.entity.JarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class JarRenderer implements BlockEntityRenderer<JarBlockEntity> {

    private static final float[][] SLOT_OFFSETS = {
            { -4/16f, 0f, -4/16f },
            {  4/16f, 0f, -4/16f },
            { -4/16f, 0f,  4/16f },
            {  4/16f, 0f,  4/16f },
    };

    public JarRenderer(BlockEntityRendererProvider.Context ctx) { }

    @Override
    public void render(JarBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        var blockRenderer = Minecraft.getInstance().getBlockRenderer();

        for (int i = 0; i < 4; i++) {
            if (!be.getBlockState().getValue(JarBlock.SLOTS[i])) continue;

            Block jar = be.getSlot(i);
            if (jar == null || jar == Blocks.AIR) continue;

            float[] offset = SLOT_OFFSETS[i];

            poseStack.pushPose();
            poseStack.translate(offset[0], offset[1], offset[2]);

            blockRenderer.renderSingleBlock(
                    jar.defaultBlockState(),
                    poseStack,
                    bufferSource,
                    packedLight,
                    packedOverlay
            );

            poseStack.popPose();
        }
    }
}
