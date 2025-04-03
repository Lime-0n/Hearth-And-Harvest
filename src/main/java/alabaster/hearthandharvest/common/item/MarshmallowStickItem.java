package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class MarshmallowStickItem extends Item {
    private static final int MAX_COOK_TIME = 10;

    public MarshmallowStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (isPlayerNearHeatSource(player, level)) {
                if (level.getGameTime() % 20 == 0) {
                    CompoundTag tag = stack.getOrCreateTag();
                    int cookTime = tag.getInt("CookTime");

                    if (cookTime < MAX_COOK_TIME) {
                        tag.putInt("CookTime", ++cookTime);
                    }

                    if (stack.getItem() == HHModItems.MARSHMALLOW_STICK.get() && cookTime >= 5) {
                        replaceItemInHand(player, stack, createCookedItem(HHModItems.ROASTED_MARSHMALLOW_STICK.get(), cookTime));
                    } else if (stack.getItem() == HHModItems.ROASTED_MARSHMALLOW_STICK.get() && cookTime >= MAX_COOK_TIME) {
                        replaceItemInHand(player, stack, createCookedItem(HHModItems.CHARRED_MARSHMALLOW_STICK.get(), cookTime));
                    }
                }
            }
        }
    }

    private ItemStack createCookedItem(Item cookedItem, int cookTime) {
        ItemStack newStack = new ItemStack(cookedItem);
        newStack.getOrCreateTag().putInt("CookTime", cookTime);
        return newStack;
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

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.getItem() == HHModItems.MARSHMALLOW_STICK.get()) {
            tooltipComponents.add(Component.literal("Hold near a campfire to cook").withStyle(ChatFormatting.GRAY));
        }
        if (stack.getItem() == HHModItems.ROASTED_MARSHMALLOW_STICK.get()) {
            tooltipComponents.add(Component.literal("Perfectly roasted!").withStyle(ChatFormatting.GOLD));
        }
        if (stack.getItem() == HHModItems.CHARRED_MARSHMALLOW_STICK.get()) {
            tooltipComponents.add(Component.literal("Oh, it's burnt...").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }
}
