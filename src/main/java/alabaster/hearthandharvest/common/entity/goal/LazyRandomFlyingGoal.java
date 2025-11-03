package alabaster.hearthandharvest.common.entity.goal;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import javax.annotation.Nullable;
import java.util.EnumSet;

public class LazyRandomFlyingGoal extends Goal {
    private final CrowEntity crow;
    private final double speedModifier;
    private final int chance; // higher = less often

    public LazyRandomFlyingGoal(CrowEntity crow, double speedModifier, int chance) {
        this.crow = crow;
        this.speedModifier = speedModifier;
        this.chance = chance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // If crow has a target or navigation is already running, skip random move more often
        if (this.crow.getTarget() != null) return false;
        if (this.crow.getRandom().nextInt(this.chance) != 0) return false;
        // pick a random air target
        Vec3 v = findAirTarget();
        return v != null;
    }

    @Override
    public void start() {
        Vec3 target = findAirTarget();
        if (target != null) {
            this.crow.getNavigation().moveTo(target.x, target.y, target.z, this.speedModifier);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.crow.getNavigation().isDone() && this.crow.getTarget() == null;
    }

    @Nullable
    private Vec3 findAirTarget() {
        RandomSource rand = this.crow.getRandom();
        BlockPos origin = this.crow.blockPosition();
        // Try a few attempts for a valid air spot
        for (int i = 0; i < 8; i++) {
            int dx = rand.nextInt(17) - 8;
            int dy = rand.nextInt(9) - 4; // allow some vertical movement
            int dz = rand.nextInt(17) - 8;
            BlockPos pos = origin.offset(dx, dy, dz);
            // simple emptiness check: prefer positions with air above ground or mid-air
            if (this.crow.level().isEmptyBlock(pos)) {
                return Vec3.atCenterOf(pos);
            }
        }
        return null;
    }
}
