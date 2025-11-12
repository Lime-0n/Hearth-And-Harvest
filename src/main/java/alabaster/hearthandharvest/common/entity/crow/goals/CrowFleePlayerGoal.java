package alabaster.hearthandharvest.common.entity.crow.goals;

import java.util.EnumSet;
import javax.annotation.Nullable;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class CrowFleePlayerGoal extends Goal {
    private final CrowEntity crow;
    private final double speedModifier;
    private Player nearestPlayer;
    private Vec3 fleeTarget;

    private static final double FLEE_DISTANCE = 6.0D;   // start fleeing when player within this distance
    private static final double STOP_DISTANCE = 10.0D;  // stop fleeing once this far away

    public CrowFleePlayerGoal(CrowEntity crow, double speedModifier) {
        this.crow = crow;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (crow.isTame() || crow.isOrderedToSit() || crow.isPassenger()) {
            return false; // only wild crows flee
        }

        Player player = crow.level().getNearestPlayer(crow, FLEE_DISTANCE);
        if (player == null || player.isCreative() || player.isSpectator()) {
            return false;
        }

        this.nearestPlayer = player;
        Vec3 away = getFleePos(player);
        if (away == null) return false;

        this.fleeTarget = away;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (nearestPlayer == null || crow.isTame()) return false;
        double dist = crow.distanceToSqr(nearestPlayer);
        return dist < (STOP_DISTANCE * STOP_DISTANCE);
    }

    @Override
    public void start() {
        if (fleeTarget != null) {
            crow.getNavigation().moveTo(fleeTarget.x, fleeTarget.y, fleeTarget.z, speedModifier);
        }
    }

    @Override
    public void stop() {
        nearestPlayer = null;
        fleeTarget = null;
    }

    @Override
    public void tick() {
        if (nearestPlayer != null && crow.getNavigation().isDone()) {
            Vec3 newTarget = getFleePos(nearestPlayer);
            if (newTarget != null) {
                crow.getNavigation().moveTo(newTarget.x, newTarget.y, newTarget.z, speedModifier);
            }
        }
    }

    @Nullable
    private Vec3 getFleePos(Player player) {
        Vec3 awayDir = new Vec3(
                crow.getX() - player.getX(),
                0.0,
                crow.getZ() - player.getZ()
        ).normalize().scale(8.0D); // move roughly 8 blocks away

        Vec3 target = new Vec3(
                crow.getX() + awayDir.x,
                crow.getY() + 3 + crow.getRandom().nextInt(3), // gain altitude slightly
                crow.getZ() + awayDir.z
        );

        Vec3 pos = LandRandomPos.getPosTowards(crow, 12, 8, target);
        return pos != null ? pos : target;
    }
}
