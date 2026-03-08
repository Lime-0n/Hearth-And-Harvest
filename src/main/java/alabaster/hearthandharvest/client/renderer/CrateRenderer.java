package alabaster.hearthandharvest.client.renderer;

import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import alabaster.hearthandharvest.common.tag.HHModTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;

import javax.annotation.Nullable;

public class CrateRenderer implements BlockEntityRenderer<CrateBlockEntity> {

    // Pixel grid constants – must match BottleSlabSlotHelper.
    private static final double PX        = 1.0 / 16.0;
    private static final double SPACING   = PX;          // 1 px border / gap
    private static final double SLOT_SIZE = 4 * PX;      // 4 px wide slot

    private final ItemRenderer itemRenderer;

    public CrateRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Nullable
    private BakedModel getSlabModel(ItemStack stack) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        ModelResourceLocation modelLoc = ModelResourceLocation.standalone(
                ResourceLocation.fromNamespaceAndPath(
                        itemId.getNamespace(),
                        "bottle_rack/" + itemId.getPath()
                )
        );
        BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelLoc);
        if (model == Minecraft.getInstance().getModelManager().getMissingModel()) return null;
        return model;
    }

    @Override
    public void render(CrateBlockEntity slab, float partialTick,
                       PoseStack pose, MultiBufferSource buf, int light, int overlay) {

        SlabType type = slab.getBlockState().getValue(BlockStateProperties.SLAB_TYPE);

        if (type == SlabType.BOTTOM || type == SlabType.DOUBLE) {
            renderHalf(slab, 0, 0.5, pose, buf, light, overlay);
        }

        if (type == SlabType.DOUBLE) {
            renderHalf(slab, CrateBlockEntity.SLOTS_PER_HALF, 1.0, pose, buf, light, overlay);
        }

        if (type == SlabType.TOP) {
            renderHalf(slab, 0, 1.0, pose, buf, light, overlay);
        }
    }
    private void renderHalf(CrateBlockEntity slab, int slotOffset, double surfaceY,
                            PoseStack pose, MultiBufferSource buf, int light, int overlay) {

        for (int i = 0; i < CrateBlockEntity.SLOTS_PER_HALF; i++) {
            ItemStack stack = slab.getItem(slotOffset + i);
            if (stack.isEmpty()) continue;

            BakedModel model = getSlabModel(stack);
            if (model == null) continue;

            int col = i % 3;
            int row = i / 3;

            double x = SPACING + col * (SLOT_SIZE + SPACING) + SLOT_SIZE / 2.0;
            double z = SPACING + row * (SLOT_SIZE + SPACING) + SLOT_SIZE / 2.0;
            double y = surfaceY - (stack.is(HHModTags.SHORT_BOTTLES) ? PX : 0);

            pose.pushPose();
            pose.translate(x, y, z);
            itemRenderer.render(stack, ItemDisplayContext.FIXED, false, pose, buf, light, overlay, model);
            pose.popPose();
        }
    }
}