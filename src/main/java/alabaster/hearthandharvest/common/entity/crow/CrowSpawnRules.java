package alabaster.hearthandharvest.common.entity.crow;

import alabaster.hearthandharvest.Config;
import alabaster.hearthandharvest.common.block.NestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class CrowSpawnRules {

    public static boolean canSpawnCrow(EntityType<? extends PathfinderMob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (!level.getBlockState(pos.below()).isSolid())
            return false;

        int radius = Config.CROW_SPAWN_RADIUS.get();

        if (hasNearbyGeneratedNest(level, pos, radius)) {
            return true;
        }

        int cropCount = countNearbyCrops(level, pos, radius);

        return cropCount >= Config.CROW_SPAWN_NUMBER_OF_CROPS.get();
    }

    private static boolean hasNearbyGeneratedNest(ServerLevelAccessor level, BlockPos pos, int radius) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -2; dy <= 2; dy++) {
                    cursor.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);

                    BlockState state = level.getBlockState(cursor);

                    if (state.getBlock() instanceof NestBlock nest &&
                            state.getValue(NestBlock.GENERATED)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static int countNearbyCrops(ServerLevelAccessor level, BlockPos pos, int radius) {
        int count = 0;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -2; dy <= 2; dy++) {
                    cursor.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);

                    BlockState state = level.getBlockState(cursor);

                    if (state.is(BlockTags.CROPS)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
