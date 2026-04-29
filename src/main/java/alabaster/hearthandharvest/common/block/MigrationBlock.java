package alabaster.hearthandharvest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;

import java.util.function.Supplier;

public class MigrationBlock extends Block {

    private final Supplier<BlockState> target;

    public MigrationBlock(Supplier<BlockState> target) {
        super(BlockBehaviour.Properties.of().randomTicks().noLootTable().noOcclusion());
        this.target = target;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, target.get(), Block.UPDATE_ALL);
    }
}