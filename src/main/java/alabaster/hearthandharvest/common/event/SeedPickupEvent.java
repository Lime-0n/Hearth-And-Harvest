package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.item.SeedPouchItem;
import alabaster.hearthandharvest.common.item.component.SeedPouchContents;
import alabaster.hearthandharvest.common.registry.HHModDataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

@EventBusSubscriber(modid = HearthAndHarvest.MODID)
public class SeedPickupEvent {

    @SubscribeEvent
    public static void onSeedPickup(ItemEntityPickupEvent.Post event) {
        Player player = event.getPlayer();
        Item pickedItem = event.getOriginalStack().getItem();

        ItemStack pouch = findCompatiblePouch(player, pickedItem, event.getOriginalStack());
        if (pouch == null) return;

        SeedPouchContents contents = pouch.get(HHModDataComponents.SEED_POUCH_CONTENTS.get());
        int currentCount = contents != null ? contents.count() : 0;
        int available = SeedPouchContents.MAX_COUNT - currentCount;
        if (available <= 0) return;

        int pickedUp = event.getOriginalStack().getCount() - event.getCurrentStack().getCount();
        int toAbsorb = Math.min(available, pickedUp);

        int removed = 0;
        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize() && removed < toAbsorb; i++) {
            ItemStack slot = inv.getItem(i);
            if (slot.getItem() == pickedItem) {
                int take = Math.min(toAbsorb - removed, slot.getCount());
                slot.shrink(take);
                removed += take;
            }
        }

        if (removed > 0) {
            int existingRadius = contents != null ? contents.plantRadius() : 0;
            pouch.set(HHModDataComponents.SEED_POUCH_CONTENTS.get(),
                    new SeedPouchContents(pickedItem, currentCount + removed, existingRadius));
        }
    }

    private static ItemStack findCompatiblePouch(Player player, Item pickedItem, ItemStack pickedStack) {
        for (int i = 0; i < 9; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (!(slot.getItem() instanceof SeedPouchItem)) continue;
            SeedPouchContents contents = slot.get(HHModDataComponents.SEED_POUCH_CONTENTS.get());
            if (contents != null && contents.count() > 0) {
                if (contents.seedType() == pickedItem && contents.count() < SeedPouchContents.MAX_COUNT) return slot;
            } else {
                if (pickedStack.is(Tags.Items.SEEDS)) return slot;
            }
        }
        return null;
    }
}