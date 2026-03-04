package alabaster.hearthandharvest.client.renderer;

import alabaster.hearthandharvest.common.block.entity.WineRackBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class WineRackRenderer implements BlockEntityRenderer<WineRackBlockEntity> {

    private final ItemRenderer itemRenderer;

    public WineRackRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    private BakedModel getWineRackModel(ItemStack stack) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        ModelResourceLocation modelLoc = ModelResourceLocation.standalone(
                ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "wine_rack/" + itemId.getPath())
        );
        BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelLoc);
        if (model == Minecraft.getInstance().getModelManager().getMissingModel()) return null;
        return model;
    }

    @Override
    public void render(WineRackBlockEntity rack, float pt, PoseStack pose, MultiBufferSource buf, int light, int overlay) {
        Direction facing = rack.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        pose.pushPose();
        pose.translate(0.5F, 0.5F, 0.5F);
        pose.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        pose.translate(-0.5F, -0.5F, -0.5F);

        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = rack.getItem(slot);
            if (stack.isEmpty()) continue;

            BakedModel model = getWineRackModel(stack);
            if (model == null) continue; // No wine_rack/ model for this item, skip

            pose.pushPose();
            int row = slot / 3, col = slot % 3;
            double px = 1./16, spacing = px, slotSize = 4*px;

            double baseX = spacing + col * (slotSize + spacing);
            double baseY = 1.0 - (spacing + slotSize + row * (slotSize + spacing));
            double baseZ = px;

            double x = baseX + 2 * px;
            double y = baseY + 2 * px;
            double z = baseZ + 8 * px;

            pose.translate(x, y, z);
            pose.mulPose(Axis.XP.rotationDegrees(90));
            itemRenderer.render(stack, ItemDisplayContext.FIXED, false, pose, buf, light, overlay, model);
            pose.popPose();
        }
        pose.popPose();
    }
}