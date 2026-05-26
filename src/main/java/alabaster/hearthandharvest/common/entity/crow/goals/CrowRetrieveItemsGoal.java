package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public class CrowRetrieveItemsGoal extends Goal {
    private final CrowEntity crow;
    private final double speed;
    private ItemEntity targetItem;

    public CrowRetrieveItemsGoal(CrowEntity crow, double speed) {
        this.crow = crow;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!crow.isTame() || crow.isOrderedToSit() || crow.getOwner() == null) return false;
        if (!crow.getMainHandItem().isEmpty()) return true;

        List<ItemEntity> list = crow.level().getEntitiesOfClass(ItemEntity.class,
                crow.getBoundingBox().inflate(8.0D),
                item -> item.isAlive() && !item.getItem().isEmpty());

        if (!list.isEmpty()) {
            targetItem = list.get(crow.getRandom().nextInt(list.size()));
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return crow.isTame() && !crow.isOrderedToSit() && crow.getOwner() != null &&
                (!crow.getMainHandItem().isEmpty() || (targetItem != null && targetItem.isAlive()));
    }

    @Override
    public void stop() {
        targetItem = null;
        crow.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (crow.getMainHandItem().isEmpty()) {
            if (targetItem != null && targetItem.isAlive()) {
                crow.getNavigation().moveTo(targetItem, speed);
                double dist = crow.distanceTo(targetItem);

                if (dist < 1.5D) {
                    ItemStack stack = targetItem.getItem();
                    if (!stack.isEmpty()) {
                        crow.setItemInHand(crow.getUsedItemHand(), stack.copy());
                        targetItem.discard();
                        crow.level().playSound(null, crow, SoundEvents.ITEM_PICKUP, crow.getSoundSource(), 0.4F, 1.0F);
                    }
                }
            }
        } else {
            if (!(crow.getOwner() instanceof Player owner)) return;

            double dist = crow.distanceTo(owner);
            crow.getNavigation().moveTo(owner, speed);

            if (dist < 2.0D) {
                ItemStack held = crow.getMainHandItem();
                boolean added = owner.getInventory().add(held.copy());
                if (!added) {
                    owner.drop(held.copy(), false);
                }

                crow.setItemInHand(crow.getUsedItemHand(), ItemStack.EMPTY);
                crow.level().playSound(null, owner, SoundEvents.PARROT_FLY, crow.getSoundSource(), 0.5F, 1.2F);

                targetItem = null;
                crow.getNavigation().stop();
            }
        }
    }
}