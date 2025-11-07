package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class CrowPickUpShinyGoal extends Goal {

    private final CrowEntity crow;
    private ItemEntity targetItem;
    private Vec3 cachedWalkTarget = null;
    private int tickTimer;
    private int walkCooldown;

    private static final double GROUND_SPEED = 0.75D;
    private static final double FLY_SPEED = 0.9D;
    private static final double PICKUP_DISTANCE = 0.65;
    private static final double MAX_DISTANCE = 10.0;

    private final Predicate<ItemEntity> targetSelector;
    private final NearestItemSorter sorter = new NearestItemSorter();

    public CrowPickUpShinyGoal(CrowEntity crow) {
        this.crow = crow;
        this.targetSelector = item -> item.isAlive() && item.getItem().is(HHModTags.CROW_SHINY_ITEMS);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (crow.isTame() || crow.isPassenger() || crow.isOrderedToSit()) return false;

        List<ItemEntity> items = crow.level().getEntitiesOfClass(
                ItemEntity.class,
                new AABB(crow.blockPosition()).inflate(MAX_DISTANCE),
                targetSelector
        );

        if (items.isEmpty()) return false;

        Collections.sort(items, sorter);
        targetItem = items.get(0);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return targetItem != null && targetItem.isAlive() && !crow.isTame() && !crow.isOrderedToSit();
    }

    @Override
    public void start() {
        tickTimer = 0;
        walkCooldown = 0;
        cachedWalkTarget = null;
    }

    @Override
    public void stop() {
        targetItem = null;
        cachedWalkTarget = null;
        crow.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (targetItem == null || !targetItem.isAlive()) return;

        tickTimer++;
        Vec3 itemPos = targetItem.position();
        double distance = crow.distanceToSqr(itemPos);

        // Look at the item
        crow.getLookControl().setLookAt(itemPos.x, itemPos.y + 0.1, itemPos.z, 10f, 10f);

        if (!crow.onGround()) {
            // Flying motion toward the item
            crow.getMoveControl().setWantedPosition(itemPos.x, itemPos.y, itemPos.z, FLY_SPEED);
            crow.setDeltaMovement(crow.getDeltaMovement().scale(0.8).subtract(0, 0.2, 0));
            return;
        }

        // Ground navigation
        if (distance > PICKUP_DISTANCE) {
            if (cachedWalkTarget == null || crow.position().distanceToSqr(cachedWalkTarget) < 0.25 || crow.getNavigation().isDone()) {
                Vec3 dir = itemPos.subtract(crow.position()).normalize();
                cachedWalkTarget = itemPos.subtract(dir.scale(PICKUP_DISTANCE));
                cachedWalkTarget = new Vec3(cachedWalkTarget.x, crow.getY(), cachedWalkTarget.z);
                crow.getNavigation().moveTo(cachedWalkTarget.x, cachedWalkTarget.y, cachedWalkTarget.z, GROUND_SPEED);
            }
            return;
        }

        // Pickup
        crow.getNavigation().stop();
        crow.setDeltaMovement(Vec3.ZERO);
        handlePickup((ServerLevel) crow.level());
    }

    private void handlePickup(ServerLevel level) {
        if (targetItem == null) return;

        targetItem.discard();
        level.broadcastEntityEvent(crow, (byte) 7);
        crow.playPickupSound();

        if (!crow.isTame()) {
            Player nearby = level.getNearestPlayer(crow, 6.0);
            if (nearby != null && level.random.nextFloat() < 0.2F) {
                crow.increaseTameProgress(nearby);
                if (crow.isTame()) crow.showHappyParticles();
            } else {
                crow.showUnhappyParticles();
            }
        }

        targetItem = null;
    }

    private class NearestItemSorter implements Comparator<ItemEntity> {
        @Override
        public int compare(ItemEntity a, ItemEntity b) {
            double d1 = crow.distanceToSqr(a);
            double d2 = crow.distanceToSqr(b);
            return Double.compare(d1, d2);
        }
    }
}
