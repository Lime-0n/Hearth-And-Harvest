package alabaster.hearthandharvest.common.worldgen;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class HHConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MUMS_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "mums_patch"));


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(MUMS_KEY, new ConfiguredFeature<>(
                HHFeatures.RANDOM_MUM_PAIR.value(),
                NoneFeatureConfiguration.INSTANCE
        ));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, name)
        );
    }
}
