package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Set;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class DeprecationTooltipHandler {

    private static Set<Item> deprecatedItems = null;

    private static Set<Item> getDeprecatedItems() {
        if (deprecatedItems == null) {
            deprecatedItems = Set.of(
            );
        }
        return deprecatedItems;
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!getDeprecatedItems().contains(event.getItemStack().getItem())) return;

        event.getToolTip().add(
                Component.translatable("tooltip.hearthandharvest.deprecated")
                        .withStyle(ChatFormatting.RED)
        );
        event.getToolTip().add(
                Component.translatable("tooltip.hearthandharvest.deprecated.hint")
                        .withStyle(ChatFormatting.GRAY)
        );
    }
}