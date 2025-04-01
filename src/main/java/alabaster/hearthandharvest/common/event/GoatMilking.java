package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class GoatMilking {

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = player.level();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);

        if (event.getTarget() instanceof Goat goat && heldItem.is(Items.GLASS_BOTTLE)) {
            if (!level.isClientSide) {
                heldItem.shrink(1);
                goat.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                ItemStack goatMilk = new ItemStack(HHModItems.GOAT_MILK_BOTTLE.get());
                boolean added = player.addItem(goatMilk);
                if (!added) {
                    player.drop(goatMilk, false);
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}
