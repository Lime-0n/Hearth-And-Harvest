package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.registry.ModDataComponents;
import alabaster.hearthandharvest.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class MarshmallowStickItem extends Item {
    private static final int MAX_COOK_TIME = 10;

    public MarshmallowStickItem(Properties properties) {
        super(properties.component(ModDataComponents.COOK_TIME.get(), 0));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (isPlayerNearHeatSource(player, level)) {
                if (level.getGameTime() % 20 == 0) {
                    int cookTime = stack.get(ModDataComponents.COOK_TIME.get());
                    if (cookTime < MAX_COOK_TIME) {
                        cookTime++;
                        int finalCookTime = cookTime;
                        stack.update(ModDataComponents.COOK_TIME.get(), 0, oldValue -> finalCookTime);
                    }

                    if (stack.getItem() == ModItems.MARSHMALLOW_STICK.get() && cookTime >= 5) {
                        ItemStack newStack = ModItems.ROASTED_MARSHMALLOW_STICK.get().getDefaultInstance();
                        int finalCookTime1 = cookTime;
                        newStack.update(ModDataComponents.COOK_TIME.get(), 0, oldValue -> finalCookTime1);
                        replaceItemInHand(player, stack, newStack);
                    }
                    else if (stack.getItem() == ModItems.ROASTED_MARSHMALLOW_STICK.get() && cookTime >= MAX_COOK_TIME) {
                        ItemStack newStack = ModItems.CHARRED_MARSHMALLOW_STICK.get().getDefaultInstance();
                        int finalCookTime2 = cookTime;
                        newStack.update(ModDataComponents.COOK_TIME.get(), 0, oldValue -> finalCookTime2);
                        replaceItemInHand(player, stack, newStack);
                    }
                }
            }
        }
    }

    private void replaceItemInHand(Player player, ItemStack oldStack, ItemStack newStack) {
        if (player.getMainHandItem() == oldStack) {
            player.setItemInHand(InteractionHand.MAIN_HAND, newStack);
        } else if (player.getOffhandItem() == oldStack) {
            player.setItemInHand(InteractionHand.OFF_HAND, newStack);
        }
    }

    private static boolean isPlayerNearHeatSource(Player player, Level level) {
        if (player.isOnFire()) {
            return true;
        }
        BlockPos pos = player.blockPosition();
        for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            if (level.getBlockState(nearbyPos).is(Blocks.CAMPFIRE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
