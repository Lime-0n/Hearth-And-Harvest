package alabaster.hearthandharvest.common.worldgen;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class HHBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_MUMS = registerKey("add_mums");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        // Target biomes explicitly
        HolderSet<Biome> targetBiomes = HolderSet.direct(
                biomes.getOrThrow(Biomes.PLAINS),
                biomes.getOrThrow(Biomes.FOREST),
                biomes.getOrThrow(Biomes.FLOWER_FOREST),
                biomes.getOrThrow(Biomes.MEADOW)
        );

        HolderSet<PlacedFeature> mumPlacedFeatures = HolderSet.direct(
                HHPlacedFeatures.ALL_2COLOR_PLACED_KEYS.stream()
                        .map(placedFeatures::getOrThrow)
                        .toList()
        );

        context.register(ADD_MUMS, new BiomeModifiers.AddFeaturesBiomeModifier(
                targetBiomes,
                mumPlacedFeatures,
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, name)
        );
    }
}
