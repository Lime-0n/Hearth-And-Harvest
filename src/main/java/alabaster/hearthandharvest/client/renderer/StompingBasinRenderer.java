package alabaster.hearthandharvest.client.renderer;

import alabaster.hearthandharvest.common.block.entity.StompingBasinBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class StompingBasinRenderer implements BlockEntityRenderer<StompingBasinBlockEntity> {

    private static final float INNER_MIN  = 2f  / 16f;
    private static final float INNER_MAX  = 14f / 16f;

    private static final float SCATTER_MIN  = 5f  / 16f;
    private static final float SCATTER_MAX  = 11f / 16f;
    private static final float SCATTER_SIZE = SCATTER_MAX - SCATTER_MIN;

    private static final float FLOOR_Y     = 1f / 16f + 0.002f;

    private static final float FLUID_MIN_Y = 1f  / 16f + 0.01f;
    private static final float FLUID_MAX_Y = 11f / 16f;

    private static final float ITEM_Y_STEP = 0.001f;

    public StompingBasinRenderer(BlockEntityRendererProvider.Context ctx) { }

    @Override
    public void render(StompingBasinBlockEntity be, float partialTick, PoseStack ps, MultiBufferSource buf, int packedLight, int packedOverlay) {
        renderScatteredItems(be, ps, buf, packedLight, packedOverlay);
        renderFluidSurface(be, ps, buf, packedLight);
    }

    // Randomized item scattering
    private void renderScatteredItems(StompingBasinBlockEntity be, PoseStack ps, MultiBufferSource buf, int packedLight, int packedOverlay) {
        ItemStack stack = be.getItemHandler().getStackInSlot(0);
        if (stack.isEmpty()) return;

        int count = stack.getCount();
        long seed = be.getBlockPos().asLong();
        for (int i = 0; i < count; i++) {
            float[] pos    = itemPosition(seed, i);
            float offsetX  = SCATTER_MIN + pos[0] * SCATTER_SIZE;
            float offsetZ  = SCATTER_MIN + pos[1] * SCATTER_SIZE;
            float rotation = pos[2] * 360f;
            float itemY    = FLOOR_Y + i * ITEM_Y_STEP;

            ps.pushPose();
            ps.translate(offsetX, itemY, offsetZ);
            ps.mulPose(Axis.YP.rotationDegrees(rotation));
            ps.mulPose(Axis.XP.rotationDegrees(-90f));

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack,
                    net.minecraft.world.item.ItemDisplayContext.GROUND,
                    packedLight,
                    packedOverlay,
                    ps,
                    buf,
                    null,
                    i
            );

            ps.popPose();
        }
    }

    private static float[] itemPosition(long worldSeed, int index) {
        long r = worldSeed ^ (index * 0x9e3779b97f4a7c15L);
        r = r * 0x6c62272e07bb0142L + 0x62b821756295c58dL;
        float px = ((r >>> 33) & 0xFFFFL) / 65535f;
        r = r * 0x6c62272e07bb0142L + 0x62b821756295c58dL;
        float pz = ((r >>> 33) & 0xFFFFL) / 65535f;
        r = r * 0x6c62272e07bb0142L + 0x62b821756295c58dL;
        float rot = ((r >>> 33) & 0xFFFFL) / 65535f;
        return new float[]{ px, pz, rot };
    }

    // Fluid Render
    private void renderFluidSurface(StompingBasinBlockEntity be, PoseStack ps, MultiBufferSource buf, int packedLight) {
        FluidStack fluid = be.getFluidTank().getFluid();
        if (fluid.isEmpty()) return;

        float fill = (float) be.getFluidTank().getFluidAmount()
                / (float) be.getFluidTank().getCapacity();
        if (fill <= 0f) return;

        float surfaceY = FLUID_MIN_Y + fill * (FLUID_MAX_Y - FLUID_MIN_Y);

        IClientFluidTypeExtensions ext = IClientFluidTypeExtensions.of(fluid.getFluid());
        ResourceLocation stillTex = ext.getStillTexture(fluid);
        if (stillTex == null) return;

        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(stillTex);

        int color = ext.getTintColor(fluid);
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >>  8) & 0xFF) / 255f;
        float b = ( color        & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;
        if (a == 0f) a = 0.75f;

        VertexConsumer vc = buf.getBuffer(RenderType.entityTranslucentCull(InventoryMenu.BLOCK_ATLAS));
        Matrix4f m = ps.last().pose();

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();

        int overlay = net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

        vc.addVertex(m, INNER_MIN, surfaceY, INNER_MIN).setColor(r, g, b, a).setUv(u0, v0).setOverlay(overlay).setLight(packedLight).setNormal(0, 1, 0);
        vc.addVertex(m, INNER_MIN, surfaceY, INNER_MAX).setColor(r, g, b, a).setUv(u0, v1).setOverlay(overlay).setLight(packedLight).setNormal(0, 1, 0);
        vc.addVertex(m, INNER_MAX, surfaceY, INNER_MAX).setColor(r, g, b, a).setUv(u1, v1).setOverlay(overlay).setLight(packedLight).setNormal(0, 1, 0);
        vc.addVertex(m, INNER_MAX, surfaceY, INNER_MIN).setColor(r, g, b, a).setUv(u1, v0).setOverlay(overlay).setLight(packedLight).setNormal(0, 1, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(StompingBasinBlockEntity be) {
        return false;
    }
}