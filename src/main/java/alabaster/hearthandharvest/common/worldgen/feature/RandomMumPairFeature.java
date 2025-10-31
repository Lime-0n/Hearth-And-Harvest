package alabaster.hearthandharvest.common.worldgen.feature;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class RandomMumPairFeature extends Feature<NoneFeatureConfiguration> {
    private static final List<Block> MUMS = List.of(
            HHModBlocks.YELLOW_MUM.get(),
            HHModBlocks.ORANGE_MUM.get(),
            HHModBlocks.RED_MUM.get(),
            HHModBlocks.BLUE_MUM.get(),
            HHModBlocks.LIGHT_BLUE_MUM.get(),
            HHModBlocks.PURPLE_MUM.get(),
            HHModBlocks.PINK_MUM.get(),
            HHModBlocks.WHITE_MUM.get()
    );

    public RandomMumPairFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // --- pick two distinct mum colors ---
        int color1 = random.nextInt(MUMS.size());
        int color2;
        do {
            color2 = random.nextInt(MUMS.size());
        } while (color2 == color1);

        BlockState mum1 = MUMS.get(color1).defaultBlockState();
        BlockState mum2 = MUMS.get(color2).defaultBlockState();

        // --- cluster size 10–12 ---
        int count = 10 + random.nextInt(3);
        int placed = 0;

        for (int i = 0; i < count; i++) {
            BlockPos pos = origin.offset(
            6,
            3,
            6
            );
            BlockPos below = pos.below();

            // check valid surface
            BlockState belowState = level.getBlockState(below);
            boolean canPlant = belowState.is(BlockTags.DIRT);
            if (!canPlant) continue;
            if (!level.isEmptyBlock(pos)) continue;

            // choose one of the two
            BlockState chosen = random.nextBoolean() ? mum1 : mum2;

            // actually place it
            level.setBlock(pos, chosen, 2);
            placed++;
        }

        if (placed > 0)
            System.out.println("[H&H] Spawned " + placed + " mums at " + origin);

        return placed > 0;
    }
}
