package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.registry.ModDataComponents;
import alabaster.hearthandharvest.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.tag.ModTags;

public class MarshmallowStickItem extends Item {

    private static final int COOKING_TIME = 100; // 5 seconds (100 ticks)

    public MarshmallowStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack marshmallowStack = player.getItemInHand(hand);

        // Check if the player is near a heat source
        if (isPlayerNearHeatSource(player, level)) {
            // Check if the marshmallow stick is raw and should start cooking
            if (marshmallowStack.is(ModItems.MARSHMALLOW_STICK.get())) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.BLOCK_SKILLET_SIZZLE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

                // Start the cooking process (set initial cooking progress)
                if (!level.isClientSide) {
                    var components = marshmallowStack.getComponents(); // Get the components map
                    if (!marshmallowStack.has(ModDataComponents.ROASTING_TIME)) {
                        marshmallowStack.set(ModDataComponents.ROASTING_TIME, 0); // Initialize cooking time as 0
                    }
                }

                return InteractionResultHolder.success(marshmallowStack);
            }
        }
        return InteractionResultHolder.pass(marshmallowStack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if (entity instanceof Player player && stack.is(ModItems.MARSHMALLOW_STICK.get())) {
            // Check if the player is near a heat source while cooking
            if (isPlayerNearHeatSource(player, level)) {
                // Get the cooking time from the components map
                var components = stack.getComponents();
                if (stack.has(ModDataComponents.ROASTING_TIME)) {
                    int cookingTime = stack.get(ModDataComponents.ROASTING_TIME);

                    // Increment the cooking time
                    if (cookingTime < COOKING_TIME) {
                        stack.set((ModDataComponents.ROASTING_TIME), cookingTime + 1);
                    }

                    // If the cooking time reaches the target, cook the marshmallow
                    if (cookingTime >= COOKING_TIME) {
                        if (!level.isClientSide) {
                            // Remove the raw marshmallow and give the player a cooked marshmallow
                            stack.shrink(1);
                            player.getInventory().add(new ItemStack(ModItems.ROASTED_MARSHMALLOW_STICK.get()));
                            // Reset cooking time
                            stack.remove(ModDataComponents.ROASTING_TIME);
                        }
                    }
                }
            }
        }
    }

    private static boolean isPlayerNearHeatSource(Player player, Level level) {
        if (player.isOnFire()) {
            return true;
        }
        BlockPos pos = player.blockPosition();
        for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            if (level.getBlockState(nearbyPos).is(ModTags.HEAT_SOURCES)) {
                return true;
            }
        }
        return false;
    }
}
