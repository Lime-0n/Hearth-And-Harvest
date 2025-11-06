package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;


import java.util.EnumSet;

public class CrowAttackTargetGoal extends Goal {
    private final CrowEntity crow;
    private LivingEntity target;
    private final double speedModifier;
    private int attackCooldown;

    public CrowAttackTargetGoal(CrowEntity crow, double speed) {
        this.crow = crow;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        target = crow.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && crow.distanceTo(target) < 12.0D;
    }

    @Override
    public void start() {
        crow.setNoGravity(true);
        attackCooldown = 0;
    }

    @Override
    public void stop() {
        target = null;
        crow.setNoGravity(false);
    }

    @Override
    public void tick() {
        if (target == null) return;

        crow.getLookControl().setLookAt(target, 30.0F, 30.0F);

        Vec3 dir = new Vec3(
                target.getX() - crow.getX(),
                target.getY() + target.getBbHeight() * 0.5 - crow.getY(),
                target.getZ() - crow.getZ()
        ).normalize();

        // Move toward target
        crow.getMoveControl().setWantedPosition(
                crow.getX() + dir.x * 4.0D,
                crow.getY() + dir.y * 4.0D,
                crow.getZ() + dir.z * 4.0D,
                speedModifier
        );

        double distance = crow.distanceToSqr(target);
        if (distance < 2.5D && attackCooldown-- <= 0) {
            crow.doHurtTarget(target);
            attackCooldown = 20 + crow.getRandom().nextInt(20);
        }
    }
}
