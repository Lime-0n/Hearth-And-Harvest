package alabaster.hearthandharvest.common.entity.crow.goals;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

import alabaster.hearthandharvest.Config;
import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class CrowFleeEntityGoal extends Goal {

    private final CrowEntity crow;
    private final double speedModifier;

    private LivingEntity threat;   // Either Player or Villager
    private Vec3 fleeTarget;

    private static double FLEE_DISTANCE = Config.CROW_SCARE_RADIUS.get();
    private static double STOP_DISTANCE = Config.CROW_SCARE_RADIUS.get() * 1.6;

    public CrowFleeEntityGoal(CrowEntity crow, double speedModifier) {
        this.crow = crow;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (crow.isTame() || crow.isOrderedToSit() || crow.isPassenger()) {
            return false;
        }

        LivingEntity nearestThreat = getNearestThreat();
        if (nearestThreat == null)
            return false;

        Vec3 away = getFleePos(nearestThreat);
        if (away == null)
            return false;

        this.threat = nearestThreat;
        this.fleeTarget = away;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (threat == null || crow.isTame())
            return false;

        return crow.distanceToSqr(threat) < (STOP_DISTANCE * STOP_DISTANCE);
    }

    @Override
    public void start() {
        if (fleeTarget != null) {
            crow.getNavigation().moveTo(
                    fleeTarget.x,
                    fleeTarget.y,
                    fleeTarget.z,
                    speedModifier
            );
        }
    }

    @Override
    public void stop() {
        this.threat = null;
        this.fleeTarget = null;
    }

    @Override
    public void tick() {
        if (threat != null && crow.getNavigation().isDone()) {
            Vec3 newTarget = getFleePos(threat);
            if (newTarget != null) {
                crow.getNavigation().moveTo(
                        newTarget.x,
                        newTarget.y,
                        newTarget.z,
                        speedModifier
                );
            }
        }
    }

    @Nullable
    private LivingEntity getNearestThreat() {
        // Nearest Player
        Player player = crow.level().getNearestPlayer(crow, FLEE_DISTANCE);

        if (player != null && (player.isCreative() || player.isSpectator())) {
            player = null; // ignore non-threatening players
        }

        // Nearest Villager
        Villager villager = getNearestVillager(FLEE_DISTANCE);

        // Both? Pick the closer one
        if (player != null && villager != null) {
            double dp = crow.distanceToSqr(player);
            double dv = crow.distanceToSqr(villager);
            return dp < dv ? player : villager;
        }

        // Only one
        return player != null ? player : villager;
    }

    @Nullable
    private Villager getNearestVillager(double distance) {
        List<Villager> villagers = crow.level().getEntitiesOfClass(
                Villager.class,
                crow.getBoundingBox().inflate(distance)
        );

        if (villagers.isEmpty())
            return null;

        return crow.level().getNearestEntity(
                villagers,
                TargetingConditions.forNonCombat(),
                crow,
                crow.getX(),
                crow.getY(),
                crow.getZ()
        );
    }

    @Nullable
    private Vec3 getFleePos(LivingEntity threat) {
        Vec3 awayDir = new Vec3(
                crow.getX() - threat.getX(),
                0.0,
                crow.getZ() - threat.getZ()
        ).normalize().scale(8.0D);

        Vec3 target = new Vec3(
                crow.getX() + awayDir.x,
                crow.getY() + 3 + crow.getRandom().nextInt(3),
                crow.getZ() + awayDir.z
        );

        Vec3 pos = LandRandomPos.getPosTowards(crow, 12, 8, target);
        return pos != null ? pos : target;
    }
}
