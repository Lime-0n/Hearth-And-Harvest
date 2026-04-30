package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.block.trellis.GrapeTrellisBlock;
import alabaster.hearthandharvest.common.block.trellis.TrellisBlock;
import alabaster.hearthandharvest.common.block.trellis.TrellisPlant;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class MigrationBlock extends GrapeTrellisBlock {

    private final TrellisPlant targetPlant;

    public MigrationBlock(TrellisPlant targetPlant, Supplier<Block> regularVariant) {
        super(Block.Properties.of().randomTicks().noLootTable().noOcclusion(), regularVariant);
        this.targetPlant = targetPlant;
        registerDefaultState(stateDefinition.any()
                .setValue(MIDDLE_EW, true)
                .setValue(MIDDLE_NS, true)
                .setValue(SIDE_NORTH, false)
                .setValue(SIDE_SOUTH, false)
                .setValue(SIDE_EAST, false)
                .setValue(SIDE_WEST, false)
                .setValue(HAS_FLAT, false)
                .setValue(HAS_TOP, false)
                .setValue(GROWTH_BLOCKED, false)
                .setValue(MATERIAL, alabaster.hearthandharvest.common.block.trellis.TrellisMaterial.STICK)
                .setValue(PLANT, targetPlant)
                .setValue(AGE, 0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        doConvert(state, level, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        doConvert(state, level, pos);
    }

    private void doConvert(BlockState state, ServerLevel level, BlockPos pos) {
        int age = state.getValue(AGE);
        level.setBlock(pos, HHModBlocks.GRAPE_TRELLIS.get().defaultBlockState()
                .setValue(TrellisBlock.MIDDLE_EW, true)
                .setValue(TrellisBlock.MIDDLE_NS, true)
                .setValue(TrellisBlock.PLANT, targetPlant)
                .setValue(TrellisBlock.AGE, age), Block.UPDATE_ALL);
    }
}