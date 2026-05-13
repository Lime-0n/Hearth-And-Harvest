package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import static alabaster.hearthandharvest.common.block.SaltDripBlock.*;

public class SaltBlock extends Block {

    private static final float GROWTH_CHANCE = 0.1f;

    public SaltBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() > GROWTH_CHANCE) return;

        if (isWaterSource(level, pos.above())) {
            tryGrow(level, pos, Direction.DOWN, random);
        }

        if (isWaterSource(level, pos.below())) {
            tryGrow(level, pos, Direction.UP, random);
        }
    }

    private static void tryGrow(ServerLevel level, BlockPos saltPos, Direction growDir, RandomSource random) {
        BlockPos growPos = saltPos.relative(growDir);
        BlockState growState = level.getBlockState(growPos);

        if (growState.isAir()) {
            placeDrip(level, growPos, growDir, SaltDripThickness.SMALL);
            return;
        }

        if (!isSaltDrip(growState)) return;
        if (growState.getValue(TIP_DIRECTION) != growDir) return;

        SaltDripThickness current = growState.getValue(THICKNESS);
        switch (current) {
            case SMALL -> level.setBlockAndUpdate(growPos, growState.setValue(THICKNESS, SaltDripThickness.MEDIUM));
            case MEDIUM -> level.setBlockAndUpdate(growPos, growState.setValue(THICKNESS, SaltDripThickness.LARGE));
            case LARGE -> tryGrowPoint(level, growPos, growDir);
            default -> {}
        }
    }

    private static void tryGrowPoint(ServerLevel level, BlockPos largePos, Direction growDir) {
        BlockPos pointPos = largePos.relative(growDir);
        if (!level.getBlockState(pointPos).isAir()) return;

        placeDrip(level, pointPos, growDir, SaltDripThickness.POINT);
        tryMerge(level, pointPos);
    }

    static void tryMerge(ServerLevel level, BlockPos pointPos) {
        boolean downLargeAbove = isLargeFacing(level.getBlockState(pointPos.above()), Direction.DOWN);
        boolean upLargeBelow   = isLargeFacing(level.getBlockState(pointPos.below()),  Direction.UP);

        if (downLargeAbove && upLargeBelow) {
            level.setBlockAndUpdate(pointPos,
                    level.getBlockState(pointPos).setValue(THICKNESS, SaltDripThickness.POINT_MERGE));
        }
    }

    private static void placeDrip(ServerLevel level, BlockPos pos, Direction tipDir, SaltDripThickness thickness) {
        level.setBlockAndUpdate(pos, HHModBlocks.SALT_DRIP.get().defaultBlockState()
                .setValue(TIP_DIRECTION, tipDir)
                .setValue(THICKNESS, thickness));
    }

    private static boolean isWaterSource(ServerLevel level, BlockPos pos) {
        var fluid = level.getFluidState(pos);
        return fluid.isSource() && fluid.getType() == Fluids.WATER;
    }

    private static boolean isLargeFacing(BlockState state, Direction tipDir) {
        return isSaltDrip(state)
                && state.getValue(TIP_DIRECTION) == tipDir
                && state.getValue(THICKNESS) == SaltDripThickness.LARGE;
    }
}