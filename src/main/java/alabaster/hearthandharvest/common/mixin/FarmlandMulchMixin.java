package alabaster.hearthandharvest.common.mixin;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;

@Mixin(value = {FarmBlock.class, RichSoilFarmlandBlock.class}, remap = false)
public class FarmlandMulchMixin {
    @Inject(method = "isNearWater", at = @At("RETURN"), cancellable = true)
    private static void checkForMulch(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
        for (BlockPos check : BlockPos.betweenClosed(pos.offset(-2, 0, -2), pos.offset(2, 0, 2))) {
            if (level.getBlockState(check).is(HHModBlocks.MULCH.get())) {
                cir.setReturnValue(true);
                return;
            }
        }
    }
}