package alabaster.hearthandharvest;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    private static final Map<String, ForgeConfigSpec.BooleanValue> ITEMS = new HashMap<>();

    public static ForgeConfigSpec.BooleanValue WANDERING_TRADER_CROPS;
    public static ForgeConfigSpec.DoubleValue TREE_TAPPER_BASE_CHANCE;

    public Config() {
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
    }

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        WANDERING_TRADER_CROPS = COMMON_BUILDER
                .comment("Should the Wandering Trader sell the mods crops?")
                .define("wanderingTraderCrops", true);

        TREE_TAPPER_BASE_CHANCE = COMMON_BUILDER
                .comment("Base chance (0.0 - 1.0) per random tick for a Tree Tapper to increase sap when on a tappable block.\n"
                        + "Higher values make sap fill faster.")
                .defineInRange("treeTapperBaseChance", 0.5D, 0.0D, 1.0D);

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void put(ForgeConfigSpec.Builder builder, String name) {
        ITEMS.put(name, builder.define(name, true));
    }

    private static boolean contains(String item) {
        return ITEMS.containsKey(item);
    }
}