package alabaster.hearthandharvest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.CabinetBlock;

public class WineRackBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public WineRackBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (direction == Direction.NORTH) {
            return Block.box(0.0D, 0.0D, 4.0, 16.0D, 16.0D, 16.0D);//
        }
        if (direction == Direction.EAST) {
            return Block.box(0.0D, 0.0D, 0.0, 4.0D, 16.0D, 16.0D);
        }
        if (direction == Direction.SOUTH) {
            return Block.box(0.0D, 0.0D, 0.0, 16.0D, 16.0D, 4.0D);//
        }
        if (direction == Direction.WEST) {
            return Block.box(4.0D, 0.0D, 0.0, 16.0D, 16.0D, 16.0D);//
        }
        return null;
    }
}
