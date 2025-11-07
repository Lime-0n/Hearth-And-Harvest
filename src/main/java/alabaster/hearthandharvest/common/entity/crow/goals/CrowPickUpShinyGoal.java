package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class CrowPickUpShinyGoal extends Goal {

    private final CrowEntity crow;
    private final double flySpeed;

    private ItemEntity targetItem;

    private int thinkingTimer;
    private int maxThinkingTime;
    private int tickTimer;
    private static final double GROUND_SPEED = 0.25D;
    private static final double FLY_SPEED = 0.9D;

    public CrowPickUpShinyGoal(CrowEntity crow, double flySpeed) {
        this.crow = crow;
        this.flySpeed = flySpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Only wild crows steal shiny items
        if (crow.isTame()) return false;

        if (crow.isPassenger() || crow.isOrderedToSit()) return false;
        if (crow.level().isClientSide()) return false;

        if (crow.getRandom().nextInt(4) != 0)
            return false;

        // Find shiny items from tag
        List<ItemEntity> items = crow.level().getEntitiesOfClass(
                ItemEntity.class,
                crow.getBoundingBox().inflate(10),
                e -> e.isAlive() && e.getItem().is(HHModTags.CROW_SHINY_ITEMS)
        );

        if (items.isEmpty()) return false;

        targetItem = items.stream()
                .min(Comparator.comparingDouble(crow::distanceToSqr))
                .orElse(null);

        return targetItem != null;
    }

    @Override
    public boolean canContinueToUse() {
        // If crow becomes tame mid-behavior → immediately stop
        if (crow.isTame()) return false;

        return targetItem != null &&
                targetItem.isAlive() &&
                tickTimer < 200 &&
                !crow.isOrderedToSit() &&
                !crow.isPassenger();
    }


    @Override
    public void start() {
        tickTimer = 0;
        thinkingTimer = 0;
        maxThinkingTime = 20 + crow.getRandom().nextInt(20);
    }

    @Override
    public void stop() {
        targetItem = null;
    }

    @Override
    public void tick() {
        if (crow.isTame()) {
            stop();
            return;
        }

        if (targetItem != null) {
            Vec3 itemPos = targetItem.position();
            double distSq = crow.distanceToSqr(itemPos);
            double dist = Math.sqrt(distSq);

            if (dist < 3.0 && this.crow.onGround()) {
                // kill all flying motion
                Vec3 mv = this.crow.getDeltaMovement();
                this.crow.setDeltaMovement(mv.x * 0.3, 0, mv.z * 0.3);
            }
        }

        tickTimer++;
        if (targetItem == null) return;

        Vec3 itemPos = targetItem.position().add(0, 0.1, 0);
        double distSq = crow.distanceToSqr(itemPos);
        double dist = Math.sqrt(distSq);

        crow.getLookControl().setLookAt(itemPos.x, itemPos.y, itemPos.z, 20f, 20f);

        if (dist > 3.0) {
            if (!crow.onGround()) {
                crow.getMoveControl().setWantedPosition(
                        itemPos.x,
                        itemPos.y,
                        itemPos.z,
                        FLY_SPEED
                );
            } else {
                crow.setDeltaMovement(crow.getDeltaMovement().add(0, 0.2, 0));
            }
            return;
        }

        if (!crow.onGround()) {
            crow.setDeltaMovement(
                    crow.getDeltaMovement().x * 0.8,
                    -0.25,
                    crow.getDeltaMovement().z * 0.8
            );

            crow.getMoveControl().setWantedPosition(
                    itemPos.x,
                    itemPos.y,
                    itemPos.z,
                    0.5
            );

            return;
        }

        if (dist > 1.0) {
            crow.getNavigation().moveTo(itemPos.x, itemPos.y, itemPos.z, GROUND_SPEED);
            return;
        }

        hoverOrWait(itemPos);
    }

    private void hoverOrWait(Vec3 itemPos) {
        if (crow.isTame()) {
            stop();
            return;
        }
        crow.getNavigation().stop();
        crow.setDeltaMovement(Vec3.ZERO);

        thinkingTimer++;

        if (crow.level().random.nextInt(8) == 0) {
            crow.getLookControl().setLookAt(
                    itemPos.x,
                    itemPos.y + crow.getRandom().nextFloat() * 0.1,
                    itemPos.z,
                    10f,
                    10f
            );
        }

        if (thinkingTimer >= maxThinkingTime) {
            handlePickup((ServerLevel) crow.level());
        }
    }

    private void handlePickup(ServerLevel level) {
        if (targetItem == null) return;

        targetItem.discard();
        level.broadcastEntityEvent(crow, (byte) 7); // pickup animation
        crow.playPickupSound();

        if (!crow.isTame()) {
            attemptTameProgress(level);
        }

        targetItem = null;
    }

    private void attemptTameProgress(ServerLevel level) {
        Player nearby = level.getNearestPlayer(crow, 6.0);

        if (nearby == null) {
            return;
        }

        // 20% chance to gain tame progress
        if (level.random.nextFloat() < 0.20F) {

            // Increase progress and check if it tames
            boolean wasTamed = crow.isTame();
            crow.increaseTameProgress(nearby);

            // If it just became tamed → happy particles
            if (!wasTamed && crow.isTame()) {
                crow.showHappyParticles();
            }

        } else {
            // Chance failed → show unhappy particles
            crow.showUnhappyParticles();
        }
    }
}
