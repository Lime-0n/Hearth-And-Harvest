package alabaster.hearthandharvest.common.block.entity;

import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ScarecrowBlockEntity extends BlockEntity {
    public ScarecrowBlockEntity(BlockPos pos, BlockState state) {
        super(HHModBlockEntities.SCARECROW.get(), pos, state);
    }
}
