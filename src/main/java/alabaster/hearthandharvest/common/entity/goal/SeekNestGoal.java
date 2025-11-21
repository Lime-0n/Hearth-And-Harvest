package alabaster.hearthandharvest.common.entity.goal;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class SeekNestGoal extends MoveToBlockGoal {

    private final Chicken chicken;

    public SeekNestGoal(Chicken chicken, double speed) {
        super(chicken, speed, 16);
        this.chicken = chicken;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    // Only run when close to egg-laying time
    @Override
    public boolean canUse() {
        return shouldSeekNest() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return shouldSeekNest() && super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();

        // If we reached the nest, stand centered so vanilla laying happens cleanly
        if (this.isReachedTarget()) {
            snapToNestCenter();
        }
    }

    // Center the chicken directly on top of the nest block
    private void snapToNestCenter() {
        double cx = blockPos.getX() + 0.5;
        double cy = blockPos.getY() + 0.1;
        double cz = blockPos.getZ() + 0.5;

        chicken.setPos(cx, cy, cz);
        chicken.getNavigation().stop();
    }

    // Seek nest when there's only 10 seconds (200 ticks) left
    private boolean shouldSeekNest() {
        return !chicken.isBaby() && chicken.eggTime <= 200;
    }

    // Valid nest target
    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(HHModBlocks.NEST.get());
    }
}
