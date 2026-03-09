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
                    HHModItems.MILK_CRATE.get(),
                    HHModItems.BLUEBERRY_WINE_CRATE.get(),
                    HHModItems.CHERRY_WINE_CRATE.get(),
                    HHModItems.RASPBERRY_WINE_CRATE.get(),
                    HHModItems.GREEN_GRAPE_WINE_CRATE.get(),
                    HHModItems.RED_GRAPE_WINE_CRATE.get(),
                    HHModItems.MEAD_CRATE.get(),
                    HHModItems.SYRUP_CRATE.get(),
                    HHModItems.GOAT_MILK_CRATE.get(),
                    HHModItems.WATER_CRATE.get(),
                    HHModItems.HONEY_CRATE.get(),
                    HHModItems.EGG_CRATE.get(),
                    HHModItems.TURTLE_EGG_CRATE.get(),
                    HHModItems.ROOT_BEER_CRATE.get(),
                    HHModItems.HARD_CIDER_CRATE.get(),
                    HHModItems.SWEET_BERRY_WINE_CRATE.get()
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