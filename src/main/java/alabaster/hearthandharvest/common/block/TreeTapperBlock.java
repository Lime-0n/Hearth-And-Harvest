package alabaster.hearthandharvest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class TreeTapperBlock extends Block {

        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
        public static final IntegerProperty SAP = IntegerProperty.create("sap", 0, 4);

        protected static final VoxelShape SHAPE = Block.box(5.0D, 2.0D, 0.0D, 11.0D, 13.0D, 6.0D);

        public TreeTapperBlock(Properties properties) {
                super(properties);
                this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SAP, 0));
        }

        @Override
        public RenderShape getRenderShape(BlockState pState) {
                return RenderShape.MODEL;
        }
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                Direction direction = state.getValue(FACING);
                if (direction == Direction.NORTH) {
                        return Block.box(5.0D, 2.0D, 0.0D, 11.0D, 13.0D, 6.0D);
                }
                if (direction == Direction.EAST) {
                        return Block.box(10.0D, 2.0D, 5.0D, 16.0D, 13.0D, 11.0D);
                }
                if (direction == Direction.SOUTH) {
                        return Block.box(5.0D, 2.0D, 10.0D, 11.0D, 13.0D, 16.0D);
                }
                if (direction == Direction.WEST) {
                        return Block.box(0.0D, 2.0D, 5.0D, 6.0D, 13.0D, 11.0D);
                }
                return null;
        };

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
                Level level = context.getLevel();

                return this.defaultBlockState()
                        .setValue(FACING, context.getHorizontalDirection());
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                builder.add(FACING, SAP);
        }
}