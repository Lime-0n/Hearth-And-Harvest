package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CrowFlyRandomlyGoal extends Goal {
    private final CrowEntity crow;
    private Vec3 target;
    private final double speed = 1.0D;
    private int stateTime = 0;
    private final double flyRadius = 12.0D;       // horizontal radius
    private final double maxAltitudeOffset = 30.0D; // how high above ground

    public CrowFlyRandomlyGoal(CrowEntity crow) {
        this.crow = crow;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (crow.isOrderedToSit() || crow.isPassenger()) return false;
        // If on ground: chance to take off
        if (crow.onGround()) {
            return crow.getRandom().nextInt(120) == 0;
        }
        // If in air: chance to pick a new target
        return crow.getRandom().nextInt(40) == 0;
    }

    @Override
    public void start() {
        this.target = getRandomFlyPos();
        if (target != null) {
            crow.getMoveControl().setWantedPosition(target.x, target.y, target.z, speed);
        }
        stateTime = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && !crow.isOrderedToSit()
                && crow.distanceToSqr(target) > 4.0D;
    }

    @Override
    public void tick() {
        stateTime++;
        // If reached close to target or time limit exceeded, pick new target
        if (target == null || crow.distanceToSqr(target) < 4.0D || stateTime > 200) {
            this.target = getRandomFlyPos();
            if (target != null) {
                crow.getMoveControl().setWantedPosition(target.x, target.y, target.z, speed);
            }
            stateTime = 0;
        }

        // Occasionally add vertical drift (simulate gliding/flap)
        if (crow.getRandom().nextInt(50) == 0) {
            crow.setDeltaMovement(crow.getDeltaMovement().add(
                    0,
                    (crow.getRandom().nextDouble() - 0.5D) * 0.1D,
                    0));
        }
    }

    private Vec3 getRandomFlyPos() {
        RandomSource random = crow.getRandom();
        BlockPos origin = crow.blockPosition();

        // Ground level at origin
        BlockPos groundBelow = crow.level().getHeightmapPos(
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                origin
        );
        double baseY = groundBelow.getY() + 2.0D; // start a bit above ground

        // Choose horizontal offset
        double dx = origin.getX() + Mth.nextDouble(random, -flyRadius, flyRadius);
        double dz = origin.getZ() + Mth.nextDouble(random, -flyRadius, flyRadius);

        // Choose altitude
        double dy = baseY + Mth.nextDouble(random, 0.0D, maxAltitudeOffset);

        return new Vec3(dx, dy, dz);
    }
}
