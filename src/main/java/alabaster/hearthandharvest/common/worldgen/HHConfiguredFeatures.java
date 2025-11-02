package alabaster.hearthandharvest.common.worldgen;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.ArrayList;
import java.util.List;

public class HHConfiguredFeatures {

    public static final List<ResourceKey<ConfiguredFeature<?, ?>>> ALL_2COLOR_KEYS = new ArrayList<>();

    // 8 colors in order
    private static final String[] COLOR_NAMES = {
            "yellow", "orange", "red", "blue",
            "light_blue", "purple", "pink", "white"
    };

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        // Corresponding blockstates
        List<BlockState> COLORS = List.of(
                HHModBlocks.YELLOW_MUM.get().defaultBlockState(),
                HHModBlocks.ORANGE_MUM.get().defaultBlockState(),
                HHModBlocks.RED_MUM.get().defaultBlockState(),
                HHModBlocks.BLUE_MUM.get().defaultBlockState(),
                HHModBlocks.LIGHT_BLUE_MUM.get().defaultBlockState(),
                HHModBlocks.PURPLE_MUM.get().defaultBlockState(),
                HHModBlocks.PINK_MUM.get().defaultBlockState(),
                HHModBlocks.WHITE_MUM.get().defaultBlockState()
        );

        // Generate all unordered 2-color combinations (8 choose 2 = 28)
        for (int i = 0; i < COLORS.size(); i++) {
            for (int j = i + 1; j < COLORS.size(); j++) {
                BlockState color1 = COLORS.get(i);
                BlockState color2 = COLORS.get(j);

                String name = COLOR_NAMES[i] + "_" + COLOR_NAMES[j] + "_mums";
                ResourceKey<ConfiguredFeature<?, ?>> key = registerKey(name);
                ALL_2COLOR_KEYS.add(key);

                // Create weighted provider (equal weights)
                SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
                builder.add(color1, 1);
                builder.add(color2, 1);
                WeightedStateProvider weightedProvider = new WeightedStateProvider(builder);

                // Patch config (cluster of 10–12 mums)
                RandomPatchConfiguration config = new RandomPatchConfiguration(
                        50, // tries per patch
                        4,  // xz spread
                        3,  // y spread
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(weightedProvider))
                );

                context.register(key, new ConfiguredFeature<>(Feature.FLOWER, config));
            }
        }
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, name)
        );
    }
}
