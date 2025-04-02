package alabaster.hearthandharvest.common.item;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WateringCanItem extends Item {

    // Maximum water and bone meal charges.
    private static final int MAX_WATER = 16;
    private static final int MAX_BONEMEAL = 16;

    public WateringCanItem(Properties properties) {
        super(properties.component(HHModDataComponents.WATER_LEVEL, 0).component(HHModDataComponents.BONEMEAL_LEVEL, 0).stacksTo(1));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getWaterCharge(stack) > 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x119BCD; // Blue color
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int waterLevel = getWaterCharge(stack);
        return Math.round(13.0F * (waterLevel / MAX_WATER));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack canStack = player.getItemInHand(hand);

        // Check if the player is targeting a water block
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState targetState = level.getBlockState(pos);

            if (targetState.getBlock() == Blocks.WATER) {
                if (getWaterCharge(canStack) < MAX_WATER) {
                    setWaterCharge(canStack, MAX_WATER);
                    if (!level.isClientSide()) {
                        level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                    return InteractionResultHolder.sidedSuccess(canStack, level.isClientSide());
                }
            }
        }

        return InteractionResultHolder.pass(canStack);
    }


    // Consume only water charge unless Infinity is applied.
    private void consumeWater(ItemStack stack) {
        int water = stack.get(HHModDataComponents.WATER_LEVEL);
        stack.update(HHModDataComponents.WATER_LEVEL, 0, oldValue -> water - 1);
    }

    // Consume both water (if not infinite) and bone meal.
    private void consumeBoth(ItemStack stack) {
        int water = stack.get(HHModDataComponents.WATER_LEVEL);
        int boneMeal = stack.get(HHModDataComponents.BONEMEAL_LEVEL);
        stack.update(HHModDataComponents.WATER_LEVEL, 0, oldValue -> water - 1);
        stack.update(HHModDataComponents.BONEMEAL_LEVEL, 0, oldValue -> boneMeal - 1);
    }

    // Getters and setters for water charge.
    private int getWaterCharge(ItemStack stack) {
        return stack.get(HHModDataComponents.WATER_LEVEL);
    }

    private void setWaterCharge(ItemStack stack, int value) {
        stack.set(HHModDataComponents.WATER_LEVEL, value);
    }

    // Getters and setters for bone meal charge.
    private int getBoneMealCharge(ItemStack stack) {
        return stack.get(HHModDataComponents.BONEMEAL_LEVEL);
    }

    private void setBoneMealCharge(ItemStack stack, int value) {
        stack.set(HHModDataComponents.BONEMEAL_LEVEL, value);
    }

    // Checks if the given block state is a crop.
    private boolean isCrop(BlockState state) {
        return state.getBlock() instanceof CropBlock;
    }

    // Applies bonemeal effect to a crop (for simplicity, increases its age by 1).
    private boolean applyBonemeal(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        if (state.getBlock() instanceof CropBlock crop) {
            int age = crop.getAge(state);
            int maxAge = crop.getMaxAge();
            if (age < maxAge) {
                BlockState newState = crop.getStateForAge(age + 1);
                level.setBlock(pos, newState, 3);
                level.levelEvent(2005, pos, 0);
                return true;
            }
        }
        return false;
    }

    // Checks if the block is fire or a campfire.
    private boolean isFire(BlockState state) {
        return state.getBlock() == Blocks.FIRE || state.getBlock() instanceof CampfireBlock;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int waterLevel = getWaterCharge(stack);
        int boneMealLevel = getBoneMealCharge(stack);

        tooltipComponents.add(Component.literal("Water Level: " + waterLevel + " / " + MAX_WATER).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
        tooltipComponents.add(Component.literal("Bone Meal Level: " + boneMealLevel + " / " + MAX_BONEMEAL).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltipComponents.add(Component.literal("NOT YET FUNCTIONAL").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}