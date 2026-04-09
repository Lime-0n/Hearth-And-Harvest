package alabaster.hearthandharvest.common.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;

public class TrellisBlock extends Block {

    public enum TrellisShape implements StringRepresentable {
        MIDDLE("middle"),
        SIDE("side"),
        FLAT("flat");

        private final String name;
        TrellisShape(String name) { this.name = name; }

        @Override
        public String getSerializedName() { return name; }
    }

    public static final EnumProperty<TrellisShape> SHAPE = EnumProperty.create("shape", TrellisShape.class);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    private static final VoxelShape MIDDLE_PLANE_EW = Block.box( 0, 0, 7, 16, 16,  9);
    private static final VoxelShape MIDDLE_PLANE_NS = Block.box( 7, 0, 0,  9, 16, 16);
    private static final VoxelShape MIDDLE_PLANE_BOTH = Shapes.or(MIDDLE_PLANE_EW, MIDDLE_PLANE_NS);

    private static final VoxelShape FLAT_SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    private static final Map<Direction, VoxelShape> SIDE_SHAPE = new EnumMap<>(Direction.class);

    static {
        SIDE_SHAPE.put(Direction.NORTH, Block.box( 0, 0, 14, 16, 16, 16));
        SIDE_SHAPE.put(Direction.SOUTH, Block.box( 0, 0,  0, 16, 16,  2));
        SIDE_SHAPE.put(Direction.EAST, Block.box( 0, 0,  0,  2, 16, 16));
        SIDE_SHAPE.put(Direction.WEST, Block.box(14, 0,  0, 16, 16, 16));
    }

    public TrellisBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SHAPE, TrellisShape.MIDDLE)
                .setValue(FACING, Direction.NORTH)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, FACING, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        BlockPos pos = context.getClickedPos();
        LevelAccessor level = context.getLevel();

        BlockPos sourcePos = pos.relative(clickedFace.getOpposite());
        BlockState sourceState = level.getBlockState(sourcePos);
        if (!(sourceState.getBlock() instanceof TrellisBlock)) {
            BlockState atPos = level.getBlockState(pos);
            if (atPos.getBlock() instanceof TrellisBlock) {
                sourceState = atPos;
            }
        }

        TrellisShape shape;
        Direction facing = context.getHorizontalDirection();

        if (sourceState.getBlock() instanceof TrellisBlock) {
            if (sneaking && clickedFace == Direction.UP) {
                shape = TrellisShape.FLAT;
                facing = context.getHorizontalDirection();
            } else {
                shape = sourceState.getValue(SHAPE);
                facing = switch (shape) {
                    case SIDE -> sourceState.getValue(FACING);
                    default   -> context.getHorizontalDirection();
                };
            }
        } else if (clickedFace.getAxis().isHorizontal()) {
            shape = TrellisShape.SIDE;
            facing = clickedFace;
        } else if (clickedFace == Direction.UP && !sneaking) {
            shape = TrellisShape.MIDDLE;
        } else {
            shape = TrellisShape.FLAT;
        }

        return computeConnections(
                this.defaultBlockState().setValue(SHAPE, shape).setValue(FACING, facing),
                level, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().isHorizontal()) {
            return computeConnections(state, level, pos);
        }
        return state;
    }

    public BlockState computeConnections(BlockState state, BlockGetter level, BlockPos pos) {
        return state
                .setValue(NORTH, connectsTo(level.getBlockState(pos.north())))
                .setValue(SOUTH, connectsTo(level.getBlockState(pos.south())))
                .setValue(EAST, connectsTo(level.getBlockState(pos.east())))
                .setValue(WEST, connectsTo(level.getBlockState(pos.west())));
    }

    public static boolean connectsTo(BlockState state) {
        return state.getBlock() instanceof TrellisBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SHAPE)) {

            case MIDDLE -> {
                boolean hasNS = state.getValue(NORTH) || state.getValue(SOUTH);
                boolean hasEW = state.getValue(EAST)  || state.getValue(WEST);
                if (hasNS && hasEW) yield MIDDLE_PLANE_BOTH;
                if (hasNS) yield MIDDLE_PLANE_NS;
                if (hasEW) yield MIDDLE_PLANE_EW;
                Direction facing = state.getValue(FACING);
                yield (facing == Direction.NORTH || facing == Direction.SOUTH) ? MIDDLE_PLANE_EW : MIDDLE_PLANE_NS;
            }

            case FLAT -> FLAT_SHAPE;

            case SIDE -> SIDE_SHAPE.get(state.getValue(FACING));
        };
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return state.getValue(SHAPE) != TrellisShape.FLAT;
    }
}