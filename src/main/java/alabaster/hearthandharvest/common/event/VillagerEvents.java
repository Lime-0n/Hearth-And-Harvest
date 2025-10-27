package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import vectorwing.farmersdelight.common.Configuration;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@EventBusSubscriber(modid = HearthAndHarvest.MODID)
@ParametersAreNonnullByDefault

public class VillagerEvents
{
    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {

        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        VillagerProfession profession = event.getType();
        ResourceLocation professionKey = BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession);
        if (professionKey == null) return;
        if (professionKey.getPath().equals("farmer")) {
            trades.get(1).add(emeraldForItemsTrade(HHModItems.BLUEBERRIES.get(), 26, 16, 2));
            trades.get(1).add(emeraldForItemsTrade(HHModItems.RASPBERRY.get(), 26, 16, 2));
            trades.get(1).add(emeraldForItemsTrade(HHModItems.CHERRY.get(), 10, 16, 2));
            trades.get(1).add(emeraldForItemsTrade(HHModItems.PEANUT.get(), 16, 16, 2));
            trades.get(1).add(emeraldForItemsTrade(HHModItems.COTTON.get(), 16, 16, 2));
            trades.get(2).add(emeraldForItemsTrade(HHModItems.CORN.get(), 24, 16, 5));
            trades.get(2).add(emeraldForItemsTrade(HHModItems.RED_GRAPES.get(), 24, 16, 5));
            trades.get(2).add(emeraldForItemsTrade(HHModItems.GREEN_GRAPES.get(), 24, 16, 5));
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        if (Configuration.WANDERING_TRADER_SELLS_FD_ITEMS.get()) {
            List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
            trades.add(itemForEmeraldTrade(HHModItems.RED_GRAPES.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.GREEN_GRAPES.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.BLUEBERRIES.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.RASPBERRY.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.PEANUT.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.COTTON_SEEDS.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.CORN_KERNELS.get(), 1, 12));
        }
    }

    public static BasicItemListing emeraldForItemsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemForEmeraldTrade(ItemLike item, int maxTrades, int xp) {
        return new BasicItemListing(1, new ItemStack(item), maxTrades, xp, 0.05F);
    }
}
