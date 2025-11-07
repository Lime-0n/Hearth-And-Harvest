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

    private static final double WALK_RADIUS = 8.0;
    private static final double LOW_FLY_RADIUS = 10.0;
    private static final double GLIDE_RADIUS = 12.0;

    public CrowFlyRandomlyGoal(CrowEntity crow) {
        this.crow = crow;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (crow.isOrderedToSit() || crow.isPassenger()) return false;

        // More grounded behavior: only start flying occasionally
        if (crow.onGround()) {
            return crow.getRandom().nextInt(90) == 0;
        }

        // If in air: try to pick a new position to move toward
        return crow.getRandom().nextInt(40) == 0;
    }

    @Override
    public void start() {
        this.target = chooseMovementTarget();
        if (target != null) {
            crow.getMoveControl().setWantedPosition(target.x, target.y, target.z, speed);
        }
        stateTime = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null &&
                crow.distanceToSqr(target) > 2.0D &&
                !crow.isOrderedToSit() &&
                stateTime < 120;
    }

    @Override
    public void tick() {
        stateTime++;

        if (target == null || crow.distanceToSqr(target) < 3.0D || stateTime > 120) {
            target = chooseMovementTarget();
            if (target != null) {
                crow.getMoveControl().setWantedPosition(target.x, target.y, target.z, speed);
            }
            stateTime = 0;
        }
    }

    private Vec3 chooseMovementTarget() {
        RandomSource r = crow.getRandom();
        BlockPos base = crow.blockPosition();

        // 40% target = ground position
        if (r.nextDouble() < 0.4) {
            return findGroundPos(base);
        }

        // 40% target = perch height (1–3 blocks above ground)
        if (r.nextDouble() < 0.8) {
            return findNearGroundAirPos(base);
        }

        // 20% target = short glide
        return findMediumAirPos(base);
    }

    private Vec3 findGroundPos(BlockPos base) {
        RandomSource r = crow.getRandom();
        for (int i = 0; i < 8; i++) {
            BlockPos pos = base.offset(
                    (int) (r.nextInt((int) WALK_RADIUS * 2) - WALK_RADIUS),
                    -2,
                    (int) (r.nextInt((int) WALK_RADIUS * 2) - WALK_RADIUS)
            );

            BlockPos ground = crow.level().getHeightmapPos(
                    net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    pos
            );

            return new Vec3(
                    ground.getX() + 0.5,
                    ground.getY() + 1.0,
                    ground.getZ() + 0.5
            );
        }
        return null;
    }

    private Vec3 findNearGroundAirPos(BlockPos base) {
        RandomSource r = crow.getRandom();
        BlockPos pos = base.offset(
                (int) (r.nextInt((int) LOW_FLY_RADIUS * 2) - LOW_FLY_RADIUS),
                0,
                (int) (r.nextInt((int) LOW_FLY_RADIUS * 2) - LOW_FLY_RADIUS)
        );

        BlockPos ground = crow.level().getHeightmapPos(
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                pos
        );

        return new Vec3(
                ground.getX() + 0.5,
                ground.getY() + 2.0 + r.nextDouble() * 2.0,  // 2–4 blocks up
                ground.getZ() + 0.5
        );
    }

    private Vec3 findMediumAirPos(BlockPos base) {
        RandomSource r = crow.getRandom();
        return new Vec3(
                base.getX() + r.nextDouble() * GLIDE_RADIUS * (r.nextBoolean() ? 1 : -1),
                base.getY() + 3.0 + r.nextDouble() * 3.0,  // 3–6 blocks up
                base.getZ() + r.nextDouble() * GLIDE_RADIUS * (r.nextBoolean() ? 1 : -1)
        );
    }
}
