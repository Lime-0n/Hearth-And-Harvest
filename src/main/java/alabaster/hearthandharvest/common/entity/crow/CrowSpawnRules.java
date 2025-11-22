package alabaster.hearthandharvest.common.entity.crow;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class CrowSpawnRules {

    public static boolean canSpawnCrow(
            EntityType<? extends PathfinderMob> type,
            ServerLevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos,
            RandomSource random)
    {
        // Only spawn on the surface
        if (!level.getBlockState(pos.below()).isSolid())
            return false;

        // Count crops near the spawn attempt
        int cropCount = countNearbyCrops(level, pos, 8); // 8-block radius

        // Require at least X crops to allow crow spawns
        return cropCount >= 8; // << adjust for tuning
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
