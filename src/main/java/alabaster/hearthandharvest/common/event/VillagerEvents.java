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
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mod.EventBusSubscriber(modid = HearthAndHarvest.MODID)
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
            trades.get(1).add(itemForEmeraldTrade(HHModItems.GRAPES.get(), 1, 12));
            trades.get(1).add(itemForEmeraldTrade(HHModItems.BLUEBERRIES.get(), 1, 12));
            trades.get(1).add(itemForEmeraldTrade(HHModItems.RASPBERRY.get(), 1, 12));
            trades.get(2).add(itemForEmeraldTrade(HHModItems.PEANUT.get(), 1, 12));
            trades.get(2).add(itemForEmeraldTrade(HHModItems.COTTON_SEEDS.get(), 1, 12));
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
            List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
            trades.add(itemForEmeraldTrade(HHModItems.GRAPES.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.BLUEBERRIES.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.RASPBERRY.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.PEANUT.get(), 1, 12));
            trades.add(itemForEmeraldTrade(HHModItems.COTTON_SEEDS.get(), 1, 12));
    }

    public static BasicItemListing emeraldForItemsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemForEmeraldTrade(ItemLike item, int maxTrades, int xp) {
        return new BasicItemListing(1, new ItemStack(item), maxTrades, xp, 0.05F);
    }
}
