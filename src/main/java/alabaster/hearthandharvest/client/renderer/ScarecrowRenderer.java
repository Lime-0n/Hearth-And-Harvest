package alabaster.hearthandharvest.client.renderer;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.ScarecrowBlock;
import alabaster.hearthandharvest.common.block.entity.ScarecrowBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.Mod;

public class ScarecrowRenderer implements BlockEntityRenderer<ScarecrowBlockEntity> {

    private final BlockModelShaper blockModelShaper = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();

    public ScarecrowRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(
            ScarecrowBlockEntity be,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            int overlay) {

        BlockState state = be.getBlockState();

        // Load custom scarecrow model
        BakedModel model =
                blockModelShaper.getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.parse("hearthandharvest:blocks/scarecrow")));

        poseStack.pushPose();

        // Translate so model origin is at the block center
        poseStack.translate(0.5, 0.0, 0.5);

        // Apply block facing rotation (if your block has it)
        Direction facing = state.getValue(ScarecrowBlock.FACING);
        poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));

        Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                .renderModel(
                        poseStack.last(),
                        buffer.getBuffer(RenderType.cutout()),
                        state,
                        model,
                        1f, 1f, 1f,
                        light,
                        overlay
                );

        poseStack.popPose();
    }
}
