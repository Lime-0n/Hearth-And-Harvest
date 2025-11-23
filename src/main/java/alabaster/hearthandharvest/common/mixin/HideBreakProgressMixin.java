package alabaster.hearthandharvest.common.mixin;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderDispatcher.class)
public abstract class HideBreakProgressMixin
{
    @Inject(method = "renderBreakingTexture(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/neoforged/neoforge/client/model/data/ModelData;)V", at = @At("HEAD"), cancellable = true)
    private void hideBlockDamage(BlockState blockStateIn, BlockPos posIn, BlockAndTintGetter lightReaderIn, PoseStack matrixStackIn, VertexConsumer vertexBuilderIn, net.neoforged.neoforge.client.model.data.ModelData modelData, CallbackInfo ci) {
        if (blockStateIn.getBlock() == HHModBlocks.HAY_RUG.get()) {
            ci.cancel();
        }
    }
}
