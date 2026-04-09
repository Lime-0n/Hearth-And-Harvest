package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.TrellisBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

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

        if (sourceState.getBlock() instanceof TrellisBlock) {
            boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
            if (sneaking) return context;
            return switch (sourceState.getValue(TrellisBlock.SHAPE)) {
                case MIDDLE, SIDE -> {
                    Direction facing = sourceState.getValue(TrellisBlock.FACING);
                    double hitY = context.getClickLocation().y - sourcePos.getY() - 0.5;
                    double hitPerp = facing.getAxis() == Direction.Axis.Z
                            ? context.getClickLocation().x - sourcePos.getX() - 0.5
                            : context.getClickLocation().z - sourcePos.getZ() - 0.5;

                    Direction dir;
                    if (Math.abs(hitY) >= Math.abs(hitPerp)) {
                        dir = hitY > 0 ? Direction.UP : Direction.DOWN;
                    } else {
                        Direction clockwise = facing.getClockWise();
                        dir = hitPerp > 0 ? clockwise : clockwise.getOpposite();
                    }

                    BlockPos target = sourcePos.relative(dir);
                    if (!context.getLevel().getBlockState(target).canBeReplaced())
                        yield context;
                    yield BlockPlaceContext.at(context, target, dir);
                }
                case FLAT -> {
                    double hitX = context.getClickLocation().x - sourcePos.getX() - 0.5;
                    double hitZ = context.getClickLocation().z - sourcePos.getZ() - 0.5;
                    Direction dir = Math.abs(hitX) > Math.abs(hitZ)
                            ? (hitX > 0 ? Direction.EAST : Direction.WEST)
                            : (hitZ > 0 ? Direction.SOUTH : Direction.NORTH);
                    BlockPos target = sourcePos.relative(dir);
                    if (!context.getLevel().getBlockState(target).canBeReplaced())
                        yield context;
                    yield BlockPlaceContext.at(context, target, dir);
                }
            };
        }
        return context;
    }
}