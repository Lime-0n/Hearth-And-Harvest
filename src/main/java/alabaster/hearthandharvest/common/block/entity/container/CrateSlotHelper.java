package alabaster.hearthandharvest.common.block.entity.container;

import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class CrateSlotHelper {

    public static int getSlotFromHit(BlockState state, BlockHitResult hit) {
        Vec3 local = hit.getLocation().subtract(
                hit.getBlockPos().getX(),
                hit.getBlockPos().getY(),
                hit.getBlockPos().getZ()
        );

        int col = getSlotCoord(local.x);
        int row = getSlotCoord(local.z);
        if (col < 0 || row < 0) return -1;

        // Determine which half was interacted with.
        SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);
        int baseSlot;
        if (type == SlabType.DOUBLE) {
            // Bottom half → slots 0–8; top half → slots 9–17.
            baseSlot = (local.y < 0.5) ? 0 : CrateBlockEntity.SLOTS_PER_HALF;
        } else {
            // Single slab always uses the first bank of slots.
            baseSlot = 0;
        }

        return baseSlot + row * 3 + col;
    }

    private static int getSlotCoord(double coord) {
        int pixel = (int) (coord * 16);
        if (pixel < 1 || pixel > 14) return -1;
        int index  = (pixel - 1) / 5;
        int offset = (pixel - 1) % 5;
        return offset < 4 ? index : -1; // offset == 4 is a gap pixel
    }
}