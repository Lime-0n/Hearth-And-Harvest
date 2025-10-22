package alabaster.hearthandharvest.common.entity.crow;

import alabaster.hearthandharvest.HearthAndHarvest;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CrowModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "crow"), "main");

    private final ModelPart body;
	private final ModelPart legs;
	private final ModelPart rightleg;
	private final ModelPart leftleg;
	private final ModelPart wings;
	private final ModelPart head;
	private final ModelPart beak;
	private final ModelPart lowerbeak;

	public CrowModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legs = root.getChild("legs");
		this.rightleg = this.legs.getChild("rightleg");
		this.leftleg = this.legs.getChild("leftleg");
		this.wings = root.getChild("wings");
		this.head = root.getChild("head");
		this.beak = this.head.getChild("beak");
		this.lowerbeak = this.beak.getChild("lowerbeak");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(4.5F, 20.0F, 2.25F));

		PartDefinition torso_r1 = body.addOrReplaceChild("torso_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.0F, -4.25F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail_r1 = body.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(12, 8).addBox(-1.5F, -1.0F, 0.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-2.0F, 24.0F, 1.0F));

		PartDefinition rightleg = legs.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(10, 14).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftleg = legs.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(10, 14).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition wings = partdefinition.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(-0.5F, 20.0F, 2.25F));

		PartDefinition rightwing_r1 = wings.addOrReplaceChild("rightwing_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-3.0F, -1.0F, -4.25F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(2.0F, -1.0F, -4.25F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -1.75F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 17.75F, -3.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, -0.75F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition lowerbeak = beak.addOrReplaceChild("lowerbeak", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition tounge_r1 = lowerbeak.addOrReplaceChild("tounge_r1", CubeListBuilder.create().texOffs(16, 4).addBox(-0.5F, 0.75F, -3.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(18, 4).addBox(-0.5F, 0.25F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		legs.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		wings.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

    @Override
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {

    }
}