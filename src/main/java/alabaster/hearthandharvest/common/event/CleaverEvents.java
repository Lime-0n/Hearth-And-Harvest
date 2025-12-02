package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.common.item.CleaverItem;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber(modid = "hearthandharvest")
public class CleaverEvents {

    private static final java.util.WeakHashMap<LivingEntity, Boolean> CLEAVER_KILL = new java.util.WeakHashMap<>();

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity target = event.getEntity();
        if (!(event.getSource().getEntity() instanceof Player player))
            return;

        ItemStack weapon = player.getMainHandItem();
        if (weapon.getItem() instanceof CleaverItem) {
            CLEAVER_KILL.put(target, true);
        }
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        LivingEntity target = event.getEntity();

        // Only affect animal mobs, nothing else
        if (!target.getType().is(HHModTags.CAN_BE_BUTCHERED))
            return;

        if (!CLEAVER_KILL.containsKey(target))
            return;

        // Clean up marker
        CLEAVER_KILL.remove(target);

        // Remove non-meat drops
        event.getDrops().removeIf(drop -> !drop.getItem().is(ItemTags.MEAT));

        // If there are no meat drops left, nothing more to do
        if (event.getDrops().isEmpty())
            return;

        // Add bonus meat
        int meatCount = event.getDrops().stream()
                .mapToInt(e -> e.getItem().getCount())
                .sum();

        int bonus = Math.max(2, (int)Math.ceil(meatCount * 0.5));

        ItemStack firstMeat = event.getDrops().stream()
                .findFirst()
                .map(e -> e.getItem().copy())
                .orElse(ItemStack.EMPTY);

        if (!firstMeat.isEmpty()) {
            firstMeat.setCount(bonus);
            target.spawnAtLocation(firstMeat);
        }
    }
}
