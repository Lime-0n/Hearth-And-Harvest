package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class CrowSeekShinyItemGoal extends Goal {
    private final CrowEntity crow;
    private final double speed;

    private ItemEntity targetItem;
    private BlockPos nestTarget;

    private int cooldown = 0;
    private int dropTimer = 0;
    private int nestArrivalTimer = -1;
    private static final int IGNORE_RADIUS_AROUND_NEST = 1;

    public CrowSeekShinyItemGoal(CrowEntity crow, double speed) {
        this.crow = crow;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (crow.isTame() || crow.isOrderedToSit() || crow.isPassenger())
            return false;

        if (!crow.getMainHandItem().isEmpty() && crow.getMainHandItem().is(HHModTags.CROW_SHINY_ITEMS))
            return true;

        if (--cooldown > 0)
            return false;

        List<ItemEntity> nearby = crow.level().getEntitiesOfClass(
                ItemEntity.class,
                crow.getBoundingBox().inflate(10),
                e -> e.isAlive()
                        && e.getItem().is(HHModTags.CROW_SHINY_ITEMS)
                        && !isNearNest(e.blockPosition(), IGNORE_RADIUS_AROUND_NEST)
        );

        if (nearby.isEmpty())
            return false;

        targetItem = nearby.get(crow.getRandom().nextInt(nearby.size()));
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!crow.getMainHandItem().isEmpty() && crow.getMainHandItem().is(HHModTags.CROW_SHINY_ITEMS))
            return true;
        return targetItem != null && targetItem.isAlive();
    }

    @Override
    public void start() {
        dropTimer = 0;
        nestTarget = null;
        nestArrivalTimer = -1;
    }

    @Override
    public void stop() {
        targetItem = null;
        nestTarget = null;
        nestArrivalTimer = -1;
        cooldown = 100 + crow.getRandom().nextInt(80);
    }

    @Override
    public void tick() {
        if (targetItem != null && crow.getMainHandItem().isEmpty()) {
            Vec3 targetPos = targetItem.position();
            crow.getLookControl().setLookAt(targetPos.x, targetPos.y + 0.3, targetPos.z);

            if (crow.distanceTo(targetItem) > 1.5) {
                crow.getNavigation().moveTo(targetItem, speed);
                return;
            }

            ItemStack stack = targetItem.getItem();
            ItemStack single = stack.split(1);
            crow.setItemInHand(crow.getUsedItemHand(), single);

            if (!crow.level().isClientSide) {
                Entity owner = targetItem.getOwner();
                if (owner instanceof Player player) {
                    crow.tryTameFromPickup(player);
                }
            }

            if (stack.isEmpty()) {
                targetItem.discard();
            }

            targetItem = null;
            dropTimer = 40;
            return;
        }

        if (!crow.getMainHandItem().isEmpty() && crow.getMainHandItem().is(HHModTags.CROW_SHINY_ITEMS)) {

            if (dropTimer > 0) {
                dropTimer--;
                return;
            }

            // Only scan for nest once; reuse cached nestTarget for the remainder of this goal
            if (nestTarget == null) {
                nestTarget = findNearestNest(16);
            }

            if (nestTarget == null) {
                fallbackDropBehavior();
                return;
            }

            // Invalidate cached nest if it was broken
            BlockState nestState = crow.level().getBlockState(nestTarget);
            if (!nestState.is(HHModBlocks.NEST.get())) {
                nestTarget = null;
                return;
            }

            crow.getNavigation().moveTo(
                    nestTarget.getX() + 0.5,
                    nestTarget.getY() + 1.1,
                    nestTarget.getZ() + 0.5,
                    speed * 1.2
            );

            double dist = crow.distanceToSqr(
                    nestTarget.getX() + 0.5,
                    nestTarget.getY() + 1.1,
                    nestTarget.getZ() + 0.5
            );

            if (dist < 1.5) {
                crow.getNavigation().stop();

                if (nestArrivalTimer < 0) {
                    nestArrivalTimer = 40;
                    return;
                }

                nestArrivalTimer--;

                if (nestArrivalTimer <= 0) {
                    depositIntoNest(nestTarget);
                    crow.setItemInHand(crow.getUsedItemHand(), ItemStack.EMPTY);

                    cooldown = 80;
                    nestTarget = null;
                    nestArrivalTimer = -1;
                }

                return;
            } else {
                nestArrivalTimer = -1;
            }
        }
    }

    private BlockPos findNearestNest(int radius) {
        BlockPos crowPos = crow.blockPosition();
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    check.set(crowPos.getX() + dx, crowPos.getY() + dy, crowPos.getZ() + dz);

                    BlockState state = crow.level().getBlockState(check);
                    if (state.is(HHModBlocks.NEST.get()))
                        return check.immutable();
                }
            }
        }

        return null;
    }

    private boolean isNearNest(BlockPos pos, int radius) {
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    check.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                    if (crow.level().getBlockState(check).is(HHModBlocks.NEST.get())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void depositIntoNest(BlockPos pos) {
        if (!(crow.level() instanceof ServerLevel server))
            return;

        ItemStack stack = crow.getMainHandItem().copy();

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.25;
        double z = pos.getZ() + 0.5;

        ItemEntity itemEntity = new ItemEntity(server, x, y, z, stack);
        itemEntity.setDefaultPickUpDelay();
        itemEntity.setDeltaMovement(0, 0, 0);
        server.addFreshEntity(itemEntity);
    }

    private void fallbackDropBehavior() {
        dropTimer--;
        if (dropTimer <= 0) {
            crow.spawnAtLocation(crow.getMainHandItem().copy());
            crow.setItemInHand(crow.getUsedItemHand(), ItemStack.EMPTY);
        }
    }
}