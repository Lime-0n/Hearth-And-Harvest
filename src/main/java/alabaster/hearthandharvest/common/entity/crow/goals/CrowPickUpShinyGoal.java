package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class CrowPickUpShinyGoal extends Goal {
    private final CrowEntity crow;
    private final double flySpeed;
    private ItemEntity targetItem;
    private int cooldown;

    public CrowPickUpShinyGoal(CrowEntity crow, double flySpeed) {
        this.crow = crow;
        this.flySpeed = flySpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (crow.isTame() || crow.isPassenger() || crow.isOrderedToSit()) return false;

        // Find nearby shiny items (iron nuggets)
        List<ItemEntity> items = crow.level().getEntitiesOfClass(
                ItemEntity.class,
                crow.getBoundingBox().inflate(10.0D),
                e -> e.isAlive() && e.getItem().is(Items.IRON_NUGGET)
        );

        if (items.isEmpty()) return false;
        targetItem = items.get(0);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return targetItem != null && targetItem.isAlive() && crow.distanceTo(targetItem) > 1.5D && cooldown <= 200;
    }

    @Override
    public void start() {
        cooldown = 0;
    }

    @Override
    public void stop() {
        targetItem = null;
    }

    @Override
    public void tick() {
        if (targetItem == null) return;

        cooldown++;

        Vec3 targetPos = targetItem.position().add(0, 0.5D, 0);
        Vec3 dir = targetPos.subtract(crow.position());
        double distance = dir.length();

        // Normalize direction and scale by a small factor to keep movement smooth
        Vec3 motion = dir.normalize().scale(Math.min(flySpeed * 0.1D, distance * 0.05D));

        crow.getMoveControl().setWantedPosition(
                targetPos.x,
                targetPos.y,
                targetPos.z,
                flySpeed
        );

        // Slow approach — reduces jitter
        crow.setDeltaMovement(crow.getDeltaMovement().add(motion).scale(0.9D));

        // Pickup logic
        if (distance < 1.5D && !crow.level().isClientSide()) {
            targetItem.discard();
            crow.level().broadcastEntityEvent(crow, (byte)7); // play pickup animation
        }
    }
}
