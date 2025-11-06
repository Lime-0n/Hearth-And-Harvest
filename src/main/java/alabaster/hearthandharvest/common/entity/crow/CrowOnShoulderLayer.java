package alabaster.hearthandharvest.common.entity.crow;

import alabaster.hearthandharvest.HearthAndHarvest;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import alabaster.hearthandharvest.common.registry.HHModEntities;

public class CrowOnShoulderLayer
        extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private final CrowModel crowModel;
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "textures/entity/crow.png");

    public CrowOnShoulderLayer(PlayerRenderer renderer, EntityModelSet modelSet) {
        super(renderer);
        this.crowModel = new CrowModel(modelSet.bakeLayer(CrowModel.LAYER_LOCATION));
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        renderCrowOnShoulder(poseStack, buffer, packedLight, player, true);
        renderCrowOnShoulder(poseStack, buffer, packedLight, player, false);
    }

    private void renderCrowOnShoulder(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            AbstractClientPlayer player,
            boolean leftShoulder
    ) {
        CompoundTag tag = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
        if (tag.contains("id") && tag.getString("id").equals(BuiltInRegistries.ENTITY_TYPE.getKey(HHModEntities.CROW.get()).toString())) {
            poseStack.pushPose();
            poseStack.translate(leftShoulder ? 0.4D : -0.4D,
                    player.isCrouching() ? -1.3D : -1.5D, 0.0D);
            poseStack.mulPose(Axis.YP.rotationDegrees(leftShoulder ? 0.0F : 180.0F));

            VertexConsumer vertexConsumer = buffer.getBuffer(crowModel.renderType(TEXTURE));
            crowModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);

            poseStack.popPose();
        }
    }
}
