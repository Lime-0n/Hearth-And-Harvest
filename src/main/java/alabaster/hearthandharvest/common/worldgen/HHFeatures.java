package alabaster.hearthandharvest.common.worldgen;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.worldgen.feature.RandomMumPairFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class HHFeatures {
    // Create the Deferred Register for worldgen Features
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, HearthAndHarvest.MODID);

    // Register your custom feature using DeferredHolder
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> RANDOM_MUM_PAIR =
            FEATURES.register("random_mum_pair",
                    () -> new RandomMumPairFeature(NoneFeatureConfiguration.CODEC));
}
