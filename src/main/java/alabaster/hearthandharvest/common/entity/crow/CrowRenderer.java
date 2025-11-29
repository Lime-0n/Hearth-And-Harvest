package alabaster.hearthandharvest.common.entity.crow;

import alabaster.hearthandharvest.HearthAndHarvest;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrowRenderer extends MobRenderer<CrowEntity, CrowModel<CrowEntity>> {
    public CrowRenderer(EntityRendererProvider.Context context) {
        super(context, new CrowModel<>(context.bakeLayer(CrowModel.LAYER_LOCATION)), 0.15f);
    }

    @Override
    public ResourceLocation getTextureLocation(CrowEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "textures/entity/crow.png");
    }

    @Override
    public void render(CrowEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }

        if(entity.isInSittingPose()) {
            poseStack.translate(0f, -0.0625f,0f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
