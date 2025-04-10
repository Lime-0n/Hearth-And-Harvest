package alabaster.hearthandharvest.common.utilities;

import alabaster.hearthandharvest.common.block.SapCauldronBlock;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class CauldronBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
        if (tintIndex == 0) {
            if (state.hasProperty(SapCauldronBlock.SAP_LEVEL)) {
                int sapLevel = state.getValue(SapCauldronBlock.SAP_LEVEL);
                if (sapLevel == 1) {
                    return 0x8B4F20;
                }
                if (sapLevel == 2) {
                    return 0xC27C2B;
                }
            }
            return 0xF8A835;
        }
        return -1;
    }
}
