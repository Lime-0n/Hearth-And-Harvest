package alabaster.hearthandharvest.common.event;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModItems;

@Mod.EventBusSubscriber
public class CowMilking {

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = player.level();

        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);

        if (event.getTarget() instanceof Cow cow && heldItem.is(Items.GLASS_BOTTLE)) {
            if (!level.isClientSide) {
                heldItem.shrink(1);
                cow.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                ItemStack cowMilk = new ItemStack(ModItems.MILK_BOTTLE.get());
                boolean added = player.addItem(cowMilk);
                if (!added) {
                    player.drop(cowMilk, false);
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}
