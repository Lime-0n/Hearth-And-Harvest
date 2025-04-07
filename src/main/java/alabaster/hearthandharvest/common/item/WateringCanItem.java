package alabaster.hearthandharvest.common.item;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.nbt.CompoundTag;

public class WateringCanItem extends Item {

    // Maximum water and bone meal charges.
    private static final int MAX_WATER = 16;
    private static final int MAX_BONEMEAL = 16;

    // NBT keys for storing charges
    private static final String WATER_NBT = "WaterLevel";
    private static final String BONEMEAL_NBT = "BoneMealLevel";

    public WateringCanItem(Properties properties) {
        // Remove component initializers – use NBT instead.
        super(properties.stacksTo(1));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getWaterCharge(stack) > 0 || getBoneMealCharge(stack) > 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x0437F2;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int totalCharge = getWaterCharge(stack) + getBoneMealCharge(stack);
        return Math.round(13.0F * ((float) totalCharge / (MAX_WATER + MAX_BONEMEAL)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack canStack = player.getItemInHand(hand);

        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack offhandStack = player.getOffhandItem();

            if (offhandStack.is(Items.BONE_MEAL)) {
                int currentBonemeal = getBoneMealCharge(canStack);
                if (currentBonemeal < MAX_BONEMEAL) {
                    if (!level.isClientSide()) {
                        offhandStack.shrink(1);
                        setBoneMealCharge(canStack, currentBonemeal + 1);
                        level.playSound(null, player.blockPosition(), SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 0.8F, 1.0F);
                        if (level instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel) level;
                            double x = player.getX();
                            double y = player.getY() + 0.8;
                            double z = player.getZ();
                            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 10, 0.5, 0.4, 0.5, 0.05);
                        }
                    }
                    return InteractionResultHolder.sidedSuccess(canStack, level.isClientSide());
                }
            }

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

                // Extinguish fire or any LIT block
                if (isExtinguishable(targetState)) {
                    if (getWaterCharge(canStack) > 0) {
                        // Extinguish the fire by setting the block to AIR (for fire blocks)
                        if (targetState.getBlock() == Blocks.FIRE) {
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }
                        // Extinguish the campfire or any other LIT block by toggling LIT to false
                        else {
                            level.setBlock(pos, targetState.setValue(CampfireBlock.LIT, false), 3);
                        }

                        if (level.isClientSide()) {
                            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                        }

                        // Consume one water charge
                        consumeWater(canStack);
                        return InteractionResultHolder.sidedSuccess(canStack, level.isClientSide());
                    }
                }

                // Apply bonemeal to crop, if applicable
                if (targetState.getBlock() instanceof BonemealableBlock) {
                    int water = getWaterCharge(canStack);
                    int bonemeal = getBoneMealCharge(canStack);
                    if (water > 0 && bonemeal > 0) {
                        boolean applied = applyBonemeal(level, pos, targetState, player);
                        if (applied) {
                            consumeBoth(canStack);
                            level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            if (level instanceof ServerLevel) {
                                ServerLevel serverLevel = (ServerLevel) level;
                                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, 0.25, 0.3, 0.25, 0.05);
                            }
                            return InteractionResultHolder.sidedSuccess(canStack, level.isClientSide());
                        }
                    }
                }
            }
        }
        return InteractionResultHolder.pass(canStack);
    }

    private boolean isExtinguishable(BlockState state) {
        // Check for blocks with the LIT property
        if (state.hasProperty(CampfireBlock.LIT) && state.getValue(CampfireBlock.LIT)) {
            return true;
        }
        if (state.getBlock() == Blocks.FIRE) {
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack canStack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);

        if (!level.isClientSide() && player != null) {
            if (state.getBlock() instanceof BonemealableBlock) {
                int water = getWaterCharge(canStack);
                int bonemeal = getBoneMealCharge(canStack);
                if (water > 0 && bonemeal > 0) {
                    boolean applied = applyBonemeal(level, pos, state, player);
                    if (applied) {
                        consumeBoth(canStack);
                        level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (level instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel) level;
                            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, 0.25, 0.3, 0.25, 0.05);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    private void consumeWater(ItemStack stack) {
        int water = getWaterCharge(stack);
        setWaterCharge(stack, water - 1);
    }

    private void consumeBoth(ItemStack stack) {
        int water = getWaterCharge(stack);
        int boneMeal = getBoneMealCharge(stack);
        setWaterCharge(stack, water - 1);
        setBoneMealCharge(stack, boneMeal - 1);
    }

    private int getWaterCharge(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(WATER_NBT);
    }

    private void setWaterCharge(ItemStack stack, int value) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(WATER_NBT, value);
    }

    private int getBoneMealCharge(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(BONEMEAL_NBT);
    }

    private void setBoneMealCharge(ItemStack stack, int value) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(BONEMEAL_NBT, value);
    }

    private boolean applyBonemeal(Level level, BlockPos centerPos, BlockState state, @Nullable Player player) {
        if (!(level instanceof ServerLevel)) {
            return false;
        }
        ServerLevel serverLevel = (ServerLevel) level;
        boolean appliedAny = false;
        int radius = 1; // 3x3 area around center

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                mutablePos.set(centerPos.getX() + dx, centerPos.getY(), centerPos.getZ() + dz);
                BlockState targetState = level.getBlockState(mutablePos);
                Block block = targetState.getBlock();

                if (block instanceof BonemealableBlock) {
                    BonemealableBlock growable = (BonemealableBlock) block;
                    if (growable.isValidBonemealTarget(level, mutablePos, targetState, false)) {
                        if (growable.isBonemealSuccess(serverLevel, level.getRandom(), mutablePos, targetState)) {
                            growable.performBonemeal(serverLevel, level.getRandom(), mutablePos, targetState);
                            serverLevel.levelEvent(2005, mutablePos, 0);
                            appliedAny = true;
                        }
                    }
                }
            }
        }

        return appliedAny;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int waterLevel = getWaterCharge(stack);
        int boneMealLevel = getBoneMealCharge(stack);

        tooltip.add(Component.literal("Water Level: " + waterLevel + " / " + MAX_WATER)
                .withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        tooltip.add(Component.literal("Bone Meal Level: " + boneMealLevel + " / " + MAX_BONEMEAL)
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
