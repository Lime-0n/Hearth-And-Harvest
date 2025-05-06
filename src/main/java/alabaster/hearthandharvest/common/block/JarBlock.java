package alabaster.hearthandharvest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class JarBlock extends Block {
    public static final int MAX_JARS = 4;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty JARS  = IntegerProperty.create("jars", 1, MAX_JARS);

    // ─────── Hard-coded AABBs ───────
    protected static final VoxelShape ONE_NORTH = Block.box(1, 0, 1,  7, 8,  7);
    protected static final VoxelShape ONE_EAST  = Block.box(9, 0, 1, 15, 8,  7);
    protected static final VoxelShape ONE_SOUTH = Block.box(9, 0, 9, 15, 8, 15);
    protected static final VoxelShape ONE_WEST  = Block.box(1, 0, 9,  7, 8, 15);

    protected static final VoxelShape TWO_NORTH = Block.box(1, 0, 1, 15, 8,  7);
    protected static final VoxelShape TWO_EAST  = Block.box(9, 0, 1, 15, 8, 15);
    protected static final VoxelShape TWO_SOUTH = Block.box(1, 0, 9, 15, 8, 15);
    protected static final VoxelShape TWO_WEST  = Block.box(1, 0, 1,  7, 8, 15);

    protected static final VoxelShape THREE_NORTH = Block.box(1, 0, 1, 15, 8, 15);
    protected static final VoxelShape THREE_EAST  = THREE_NORTH;
    protected static final VoxelShape THREE_SOUTH = THREE_NORTH;
    protected static final VoxelShape THREE_WEST  = THREE_NORTH;

    protected static final VoxelShape FOUR_NORTH  = Block.box(1, 0, 1, 15, 8, 15);
    protected static final VoxelShape FOUR_EAST   = FOUR_NORTH;
    protected static final VoxelShape FOUR_SOUTH  = FOUR_NORTH;
    protected static final VoxelShape FOUR_WEST   = FOUR_NORTH;

    public JarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(JARS, 1)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelReader world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState here = world.getBlockState(pos);

        if (here.is(this)) {
            int c = here.getValue(JARS);
            return here.setValue(JARS, Math.min(MAX_JARS, c + 1));
        }

        for (Direction d : ctx.getNearestLookingDirections()) {
            if (d.getAxis().isHorizontal()) {
                return this.defaultBlockState()
                        .setValue(FACING, d)
                        .setValue(JARS, 1);
            }
        }
        return this.defaultBlockState();
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        return !ctx.isSecondaryUseActive()
                && ctx.getItemInHand().is(this.asItem())
                && state.getValue(JARS) < MAX_JARS
                || super.canBeReplaced(state, ctx);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        int jars = state.getValue(JARS);
        Direction face = state.getValue(FACING);
        switch (jars) {
            case 2:
                switch (face) {
                    case EAST:  return TWO_EAST;
                    case SOUTH: return TWO_SOUTH;
                    case WEST:  return TWO_WEST;
                    default:    return TWO_NORTH;
                }
            case 3:
                switch (face) {
                    case EAST:  return THREE_EAST;
                    case SOUTH: return THREE_SOUTH;
                    case WEST:  return THREE_WEST;
                    default:    return THREE_NORTH;
                }
            case 4:
                switch (face) {
                    case EAST:  return FOUR_EAST;
                    case SOUTH: return FOUR_SOUTH;
                    case WEST:  return FOUR_WEST;
                    default:    return FOUR_NORTH;
                }
            case 1:
            default:
                switch (face) {
                    case EAST:  return ONE_EAST;
                    case SOUTH: return ONE_SOUTH;
                    case WEST:  return ONE_WEST;
                    default:    return ONE_NORTH;
                }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return getShape(state, world, pos, ctx);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(JARS, FACING);
    }
}