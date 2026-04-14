package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.TrellisBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TrellisBlockItem extends BlockItem {

    public TrellisBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockPos sourcePos = context.getClickedPos().relative(clickedFace.getOpposite());
        BlockState sourceState = context.getLevel().getBlockState(sourcePos);

        if (!(sourceState.getBlock() instanceof TrellisBlock)) {
            BlockState atClicked = context.getLevel().getBlockState(context.getClickedPos());
            if (atClicked.getBlock() instanceof TrellisBlock) {
                sourceState = atClicked;
                sourcePos = context.getClickedPos();
            }
        }

        if (sourceState.getBlock() instanceof TrellisBlock trellis) {
            boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
            if (!sneaking) return context;

            double hitX = context.getClickLocation().x - sourcePos.getX() - 0.5;
            double hitY = context.getClickLocation().y - sourcePos.getY() - 0.5;
            double hitZ = context.getClickLocation().z - sourcePos.getZ() - 0.5;

            double absX = Math.abs(hitX);
            double absY = Math.abs(hitY);
            double absZ = Math.abs(hitZ);

            Direction dir;
            if (absY >= absX && absY >= absZ) {
                dir = hitY > 0 ? Direction.UP : Direction.DOWN;
            } else {
                dir = absX >= absZ
                        ? (hitX > 0 ? Direction.EAST : Direction.WEST)
                        : (hitZ > 0 ? Direction.SOUTH : Direction.NORTH);
            }

            BlockPos target = sourcePos.relative(dir);
            if (!context.getLevel().getBlockState(target).canBeReplaced())
                return context;
            return BlockPlaceContext.at(context, target, dir);
        }
        return context;
    }
}