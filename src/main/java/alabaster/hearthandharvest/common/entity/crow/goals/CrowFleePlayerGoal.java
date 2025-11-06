package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CrowFleePlayerGoal extends Goal {
    private final CrowEntity crow;
    private Player threat;
    private Vec3 fleeTarget;
    private final double speedModifier;

    public CrowFleePlayerGoal(CrowEntity crow, double speed) {
        this.crow = crow;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (crow.isOrderedToSit() || crow.isTame()) return false;

        Player player = crow.level().getNearestPlayer(crow, 6.0D);
        if (player != null && crow.distanceTo(player) < 4.0D) {
            threat = player;
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        if (threat == null) return;

        Vec3 away = crow.position().subtract(threat.position()).normalize().scale(8.0D);
        fleeTarget = crow.position().add(away).add(0, 2.0D, 0);

        crow.getMoveControl().setWantedPosition(fleeTarget.x, fleeTarget.y, fleeTarget.z, speedModifier);
        crow.setNoGravity(true);
    }

    @Override
    public boolean canContinueToUse() {
        return threat != null && crow.distanceTo(threat) < 12.0D;
    }

    @Override
    public void tick() {
        if (fleeTarget != null) {
            crow.getMoveControl().setWantedPosition(fleeTarget.x, fleeTarget.y, fleeTarget.z, speedModifier);
        }
    }

    @Override
    public void stop() {
        threat = null;
        fleeTarget = null;
    }
}
