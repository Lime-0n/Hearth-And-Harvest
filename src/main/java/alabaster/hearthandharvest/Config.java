package alabaster.hearthandharvest;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    public static ModConfigSpec COMMON_CONFIG;
    private static final Map<String, ModConfigSpec.BooleanValue> ITEMS = new HashMap<>();

    public static ModConfigSpec.DoubleValue TREE_TAPPER_BASE_CHANCE;
    public static ModConfigSpec.IntValue CROW_SPAWN_NUMBER_OF_CROPS;
    public static ModConfigSpec.IntValue CROW_SPAWN_RADIUS;
    public static ModConfigSpec.IntValue CROW_SCARE_RADIUS;
    public static ModConfigSpec.BooleanValue ENABLE_TWT_COMPAT;

    public Config() {
    }


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
    }

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        TREE_TAPPER_BASE_CHANCE = COMMON_BUILDER
                .comment("Base chance (0.0 - 1.0) per random tick for a Tree Tapper to increase sap when on a tappable block.\n"
                        + "Higher values make sap fill faster.")
                .defineInRange("treeTapperBaseChance", 0.5D, 0.0D, 1.0D);

        CROW_SPAWN_NUMBER_OF_CROPS = COMMON_BUILDER
                .comment("Amount of crops that need to be in an area for a crow to spawn nearby. Used alongside the crowSpawnRadius config to control crow spawning.\n" +
                        "Setting to 0 would prevent crow spawning")
                .defineInRange("crowCropRequirement", 8, 0, 192);

        CROW_SPAWN_RADIUS = COMMON_BUILDER
                .comment("Radius that crows check for crops to be in when trying to spawn. Larger radius means higher changes of spawning.\n" +
                        "Setting to 0 would prevent crow spawning")
                .defineInRange("crowSpawnRadius", 8, 0, 64);

        CROW_SCARE_RADIUS = COMMON_BUILDER
                .comment("Radius that players, villgers, and repelling blocks will be effective towards scaring wild crows.\n" +
                        "Setting to 0 would prevent crows from being scared")
                .defineInRange("crowScareRadius", 6, 0, 192);

        ENABLE_TWT_COMPAT = COMMON_BUILDER
                .comment("Enable Thirst Was Taken Compat")
                .define("enabled", true);

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void put(ModConfigSpec.Builder builder, String name) {
        ITEMS.put(name, builder.define(name, true));
    }

    private static boolean contains(String item) {
        return ITEMS.containsKey(item);
    }
}