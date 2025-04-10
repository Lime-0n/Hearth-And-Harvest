package alabaster.hearthandharvest.common.utilities;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TapperBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
        if (tintIndex == 0) {
            return 0xF8A835;
        }
        return -1;
    }
}