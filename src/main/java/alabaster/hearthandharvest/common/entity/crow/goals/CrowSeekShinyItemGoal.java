package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class CrowSeekShinyItemGoal extends Goal {
    private final CrowEntity crow;
    private final double speed;
    private ItemEntity targetItem;
    private int cooldown = 0;
    private int dropTimer = 0;
    private Vec3 flyAwayTarget = null;

    public CrowSeekShinyItemGoal(CrowEntity crow, double speed) {
        this.crow = crow;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (crow.isTame() || crow.isOrderedToSit() || crow.isPassenger())
            return false;

        // Already carrying a shiny item?
        if (!crow.getMainHandItem().isEmpty() && crow.getMainHandItem().is(HHModTags.CROW_SHINY_ITEMS)) {
            return true; // continue carrying
        }

        if (--cooldown > 0)
            return false;

        // Look for shiny items on the ground nearby
        List<ItemEntity> nearby = crow.level().getEntitiesOfClass(ItemEntity.class,
                crow.getBoundingBox().inflate(10.0D),
                e -> e.isAlive() && e.getItem().is(HHModTags.CROW_SHINY_ITEMS));

        if (!nearby.isEmpty()) {
            targetItem = nearby.get(crow.getRandom().nextInt(nearby.size()));
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue if still heading to a target OR carrying an item
        if (crow.getMainHandItem().is(HHModTags.CROW_SHINY_ITEMS))
            return true;

        return targetItem != null && targetItem.isAlive();
    }

    @Override
    public void start() {
        cooldown = 200 + crow.getRandom().nextInt(100); // ~10 seconds between searches
        dropTimer = 0;
        flyAwayTarget = null;
    }

    @Override
    public void stop() {
        targetItem = null;
        flyAwayTarget = null;
    }

    @Override
    public void tick() {
        // --- Phase 1: Seeking item ---
        if (targetItem != null && crow.getMainHandItem().isEmpty()) {
            Vec3 targetPos = targetItem.position();
            crow.getLookControl().setLookAt(targetPos.x, targetPos.y + 0.3, targetPos.z);

            if (crow.distanceTo(targetItem) > 1.5D) {
                crow.getNavigation().moveTo(targetItem, speed);
            } else {
                // Pick up shiny item
                ItemStack stack = targetItem.getItem();
                ItemStack single = stack.split(1);
                crow.setItemInHand(crow.getUsedItemHand(), single);
                if (stack.isEmpty()) targetItem.discard();

                targetItem = null;
                dropTimer = 200 + crow.getRandom().nextInt(200); // will drop after 10–20 seconds
            }
            return;
        }

        // --- Phase 2: Carrying shiny ---
        if (!crow.getMainHandItem().isEmpty() && crow.getMainHandItem().is(HHModTags.CROW_SHINY_ITEMS)) {
            if (flyAwayTarget == null || crow.getNavigation().isDone()) {
                // Pick a random elevated position to fly around
                Vec3 pos = new Vec3(
                        crow.getX() + (crow.getRandom().nextDouble() - 0.5D) * 20.0D,
                        crow.getY() + crow.getRandom().nextInt(6) + 8,
                        crow.getZ() + (crow.getRandom().nextDouble() - 0.5D) * 20.0D
                );
                flyAwayTarget = pos;
                crow.getNavigation().moveTo(pos.x, pos.y, pos.z, speed * 1.2);
            }

            // Decrease timer and drop when time expires
            if (--dropTimer <= 0) {
                ItemStack drop = crow.getMainHandItem().copy();
                crow.setItemInHand(crow.getUsedItemHand(), ItemStack.EMPTY);

                if (crow.level() instanceof ServerLevel server) {
                    crow.spawnAtLocation(drop);
                }

                flyAwayTarget = null;
                cooldown = 200;
            }
        }
    }
}
