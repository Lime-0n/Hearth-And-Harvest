package alabaster.hearthandharvest.common.worldgen;

import alabaster.hearthandharvest.common.block.SaltDripBlock;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

public class SaltCaveFeature extends Feature<NoneFeatureConfiguration> {

    public SaltCaveFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        List<int[]> blobs = buildBlobs(random);

        boolean anyPlaced = false;
        for (int dx = -15; dx <= 15; dx++) {
            for (int dy = -9; dy <= 9; dy++) {
                for (int dz = -15; dz <= 15; dz++) {
                    if (!inAnyOuter(dx, dy, dz, blobs)) continue;
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (isReplaceable(level.getBlockState(pos))) {
                        level.setBlock(pos, pickBlock(random), 2);
                        anyPlaced = true;
                    }
                }
            }
        }
        if (!anyPlaced) return false;

        for (int dx = -15; dx <= 15; dx++) {
            for (int dy = -9; dy <= 9; dy++) {
                for (int dz = -15; dz <= 15; dz++) {
                    if (!inAnyInner(dx, dy, dz, blobs)) continue;
                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (!state.isAir() && state.getFluidState().isEmpty()) {
                        level.setBlock(pos, Blocks.CAVE_AIR.defaultBlockState(), 2);
                    }
                }
            }
        }

        for (int dx = -12; dx <= 12; dx++) {
            for (int dy = -8; dy <= 8; dy++) {
                for (int dz = -12; dz <= 12; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (!isMineralBlock(state)) continue;

                    BlockPos above = pos.above();
                    BlockPos below = pos.below();

                    if (level.getBlockState(above).isAir() && random.nextFloat() < 0.02f) {
                        level.setBlock(pos, Blocks.WATER.defaultBlockState(), 2);
                        level.scheduleTick(pos, Fluids.WATER, 0);
                    }

                    if (state.is(HHModBlocks.SALT_BLOCK.get())
                            && level.getBlockState(below).isAir()
                            && !level.getBlockState(above).isAir()
                            && random.nextFloat() < 0.03f) {
                        level.setBlock(above, Blocks.WATER.defaultBlockState(), 2);
                        level.scheduleTick(above, Fluids.WATER, 0);
                    }
                }
            }
        }

        for (int dx = -15; dx <= 15; dx++) {
            for (int dy = -9; dy <= 9; dy++) {
                for (int dz = -15; dz <= 15; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (!isMineralBlock(level.getBlockState(pos))) continue;

                    BlockPos below = pos.below();
                    if (level.getBlockState(below).isAir() && random.nextFloat() < 0.28f) {
                        placeDrip(level, below, Direction.DOWN, random);
                    }

                    BlockPos above = pos.above();
                    if (level.getBlockState(above).isAir() && random.nextFloat() < 0.22f) {
                        placeDrip(level, above, Direction.UP, random);
                    }
                }
            }
        }

        return true;
    }

    private List<int[]> buildBlobs(RandomSource random) {
        List<int[]> blobs = new ArrayList<>();

        int mH = 6 + random.nextInt(3);
        int mV = 5 + random.nextInt(2);

        int mIH = Math.max(2, mH - 2);
        int mIV = Math.max(2, mV - 1);
        blobs.add(new int[]{0, 0, 0, mH, mV, mIH, mIV});

        int sides = 2 + random.nextInt(3);
        for (int i = 0; i < sides; i++) {
            int sH = 4 + random.nextInt(2);
            int sV = 4 + random.nextInt(2);
            int sIH = Math.max(1, sH - 1);
            int sIV = Math.max(2, sV - 1);

            double angle = random.nextDouble() * 2 * Math.PI;
            int dist = (mH - 2) + random.nextInt(4);
            int ox = (int) (dist * Math.cos(angle));
            int oy = random.nextInt(3) - 1;
            int oz = (int) (dist * Math.sin(angle));

            blobs.add(new int[]{ox, oy, oz, sH, sV, sIH, sIV});
        }
        return blobs;
    }

    private static boolean inAnyOuter(int dx, int dy, int dz, List<int[]> blobs) {
        for (int[] b : blobs)
            if (inEllipsoid(dx - b[0], dy - b[1], dz - b[2], b[3], b[4])) return true;
        return false;
    }

    private static boolean inAnyInner(int dx, int dy, int dz, List<int[]> blobs) {
        for (int[] b : blobs)
            if (inEllipsoid(dx - b[0], dy - b[1], dz - b[2], b[5], b[6])) return true;
        return false;
    }

    private static boolean inEllipsoid(int dx, int dy, int dz, int h, int v) {
        if (h <= 0 || v <= 0) return false;
        return (double) (dx * dx + dz * dz) / (h * h)
                + (double) (dy * dy) / (v * v) <= 1.0;
    }

    private static BlockState pickBlock(RandomSource random) {
        return switch (random.nextInt(10)) {
            case 6, 7, 8 -> Blocks.CALCITE.defaultBlockState();
            case 9 -> Blocks.TUFF.defaultBlockState();
            default -> HHModBlocks.SALT_BLOCK.get().defaultBlockState();
        };
    }

    private static boolean isMineralBlock(BlockState state) {
        return state.is(HHModBlocks.SALT_BLOCK.get())
                || state.is(Blocks.CALCITE)
                || state.is(Blocks.TUFF);
    }

    private static boolean isReplaceable(BlockState state) {
        return state.is(BlockTags.STONE_ORE_REPLACEABLES)
                || state.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    }

    private void placeDrip(WorldGenLevel level, BlockPos pos, Direction tipDir, RandomSource random) {
        SaltDripBlock.SaltDripThickness thickness = switch (random.nextInt(3)) {
            case 0 -> SaltDripBlock.SaltDripThickness.SMALL;
            case 1 -> SaltDripBlock.SaltDripThickness.MEDIUM;
            default -> SaltDripBlock.SaltDripThickness.LARGE;
        };
        level.setBlock(pos, HHModBlocks.SALT_DRIP.get().defaultBlockState()
                .setValue(SaltDripBlock.TIP_DIRECTION, tipDir)
                .setValue(SaltDripBlock.THICKNESS, thickness), 2);
        if (thickness == SaltDripBlock.SaltDripThickness.LARGE) {
            BlockPos tip = pos.relative(tipDir);
            if (level.getBlockState(tip).isAir()) {
                level.setBlock(tip, HHModBlocks.SALT_DRIP.get().defaultBlockState()
                        .setValue(SaltDripBlock.TIP_DIRECTION, tipDir)
                        .setValue(SaltDripBlock.THICKNESS, SaltDripBlock.SaltDripThickness.POINT), 2);
            }
        }
    }
}