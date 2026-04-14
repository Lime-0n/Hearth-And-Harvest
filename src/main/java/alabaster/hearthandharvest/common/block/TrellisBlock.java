package alabaster.hearthandharvest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TrellisBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_MIDDLE = BooleanProperty.create("has_middle");
    public static final BooleanProperty SIDE_NORTH = BooleanProperty.create("side_north");
    public static final BooleanProperty SIDE_SOUTH = BooleanProperty.create("side_south");
    public static final BooleanProperty SIDE_EAST = BooleanProperty.create("side_east");
    public static final BooleanProperty SIDE_WEST = BooleanProperty.create("side_west");
    public static final BooleanProperty HAS_FLAT = BooleanProperty.create("has_flat");
    public static final BooleanProperty HAS_TOP = BooleanProperty.create("has_top");
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    private static final VoxelShape MIDDLE_PLANE_EW = Block.box( 0, 0, 7, 16, 16,  9);
    private static final VoxelShape MIDDLE_PLANE_NS = Block.box( 7, 0, 0,  9, 16, 16);
    private static final VoxelShape MIDDLE_PLANE_BOTH = Shapes.or(MIDDLE_PLANE_EW, MIDDLE_PLANE_NS);
    private static final VoxelShape FLAT_SHAPE = Block.box( 0, 0, 0, 16,  2, 16);
    private static final VoxelShape TOP_SHAPE = Block.box( 0,14, 0, 16, 16, 16);

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
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_MIDDLE, false)
                .setValue(SIDE_NORTH, false)
                .setValue(SIDE_SOUTH, false)
                .setValue(SIDE_EAST, false)
                .setValue(SIDE_WEST, false)
                .setValue(HAS_FLAT, false)
                .setValue(HAS_TOP, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_MIDDLE, SIDE_NORTH, SIDE_SOUTH, SIDE_EAST, SIDE_WEST,
                HAS_FLAT, HAS_TOP, NORTH, SOUTH, EAST, WEST);
    }

    public static BooleanProperty sidePropForFacing(Direction facing) {
        return switch (facing) {
            case NORTH -> SIDE_NORTH;
            case SOUTH -> SIDE_SOUTH;
            case EAST -> SIDE_EAST;
            case WEST -> SIDE_WEST;
            default -> SIDE_NORTH;
        };
    }

    public boolean hasSide(BlockState state) {
        return state.getValue(SIDE_NORTH) || state.getValue(SIDE_SOUTH)
                || state.getValue(SIDE_EAST) || state.getValue(SIDE_WEST);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (!context.getItemInHand().is(this.asItem())) return false;
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        if (sneaking) {
            return !state.getValue(HAS_MIDDLE);
        }
        Direction clickedFace = context.getClickedFace();
        if (clickedFace.getAxis().isHorizontal()) {
            return !state.getValue(sidePropForFacing(clickedFace));
        } else if (clickedFace == Direction.UP) {
            return !state.getValue(HAS_FLAT);
        } else {
            return !state.getValue(HAS_TOP);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        BlockPos pos = context.getClickedPos();
        LevelAccessor level = context.getLevel();

        BlockState existing = level.getBlockState(pos);
        BlockState base = (existing.getBlock() == this)
                ? existing
                : this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());

        if (sneaking) {
            return computeConnections(
                    base.setValue(HAS_MIDDLE, true).setValue(FACING, context.getHorizontalDirection()),
                    level, pos);
        }

        if (clickedFace.getAxis().isHorizontal()) {
            return computeConnections(base.setValue(sidePropForFacing(clickedFace), true), level, pos);
        } else if (clickedFace == Direction.UP) {
            return computeConnections(base.setValue(HAS_FLAT, true), level, pos);
        } else {
            return computeConnections(base.setValue(HAS_TOP, true), level, pos);
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.isShiftKeyDown()) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.SUCCESS;

        Direction face = hit.getDirection();
        double hitY = hit.getLocation().y - pos.getY();

        BooleanProperty toRemove = null;
        if (face.getAxis().isHorizontal() && state.getValue(sidePropForFacing(face))) {
            toRemove = sidePropForFacing(face);
        } else if (face == Direction.UP && hitY < 0.15 && state.getValue(HAS_FLAT)) {
            toRemove = HAS_FLAT;
        } else if (hitY > 0.85 && state.getValue(HAS_TOP)) {
            toRemove = HAS_TOP;
        } else if (state.getValue(HAS_MIDDLE)) {
            toRemove = HAS_MIDDLE;
        }

        if (toRemove == null) return InteractionResult.PASS;

        if (!player.getAbilities().instabuild) {
            Block.popResource(level, pos, new ItemStack(this));
        }

        BlockState newState = state.setValue(toRemove, false);
        if (!newState.getValue(HAS_MIDDLE) && !newState.getValue(SIDE_NORTH)
                && !newState.getValue(SIDE_SOUTH) && !newState.getValue(SIDE_EAST)
                && !newState.getValue(SIDE_WEST) && !newState.getValue(HAS_FLAT)
                && !newState.getValue(HAS_TOP)) {
            level.removeBlock(pos, false);
        } else {
            level.setBlock(pos, computeConnections(newState, level, pos), 3);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        int count = 0;
        if (state.getValue(HAS_MIDDLE)) count++;
        if (state.getValue(SIDE_NORTH)) count++;
        if (state.getValue(SIDE_SOUTH)) count++;
        if (state.getValue(SIDE_EAST)) count++;
        if (state.getValue(SIDE_WEST)) count++;
        if (state.getValue(HAS_FLAT)) count++;
        if (state.getValue(HAS_TOP)) count++;
        List<ItemStack> drops = new ArrayList<>();
        for (int i = 0; i < count; i++) drops.add(new ItemStack(this));
        return drops;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return computeConnections(state, level, pos);
    }

    public BlockState computeConnections(BlockState state, BlockGetter level, BlockPos pos) {
        return state
                .setValue(NORTH, connectsTo(level.getBlockState(pos.north())))
                .setValue(SOUTH, connectsTo(level.getBlockState(pos.south())))
                .setValue(EAST, connectsTo(level.getBlockState(pos.east())))
                .setValue(WEST, connectsTo(level.getBlockState(pos.west())));
    }

    public static boolean connectsTo(BlockState state) {
        return state.getBlock() instanceof TrellisBlock && state.getValue(HAS_MIDDLE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        List<VoxelShape> parts = new ArrayList<>();

        if (state.getValue(HAS_MIDDLE)) {
            boolean hasNS = state.getValue(NORTH) || state.getValue(SOUTH);
            boolean hasEW = state.getValue(EAST)  || state.getValue(WEST);
            if (hasNS && hasEW) parts.add(MIDDLE_PLANE_BOTH);
            else if (hasNS) parts.add(MIDDLE_PLANE_NS);
            else if (hasEW) parts.add(MIDDLE_PLANE_EW);
            else {
                Direction f = state.getValue(FACING);
                parts.add((f == Direction.NORTH || f == Direction.SOUTH) ? MIDDLE_PLANE_EW : MIDDLE_PLANE_NS);
            }
        }
        if (state.getValue(SIDE_NORTH)) parts.add(SIDE_SHAPE.get(Direction.NORTH));
        if (state.getValue(SIDE_SOUTH)) parts.add(SIDE_SHAPE.get(Direction.SOUTH));
        if (state.getValue(SIDE_EAST)) parts.add(SIDE_SHAPE.get(Direction.EAST));
        if (state.getValue(SIDE_WEST)) parts.add(SIDE_SHAPE.get(Direction.WEST));
        if (state.getValue(HAS_FLAT)) parts.add(FLAT_SHAPE);
        if (state.getValue(HAS_TOP)) parts.add(TOP_SHAPE);

        if (parts.isEmpty()) return Shapes.block();
        VoxelShape result = parts.get(0);
        for (int i = 1; i < parts.size(); i++) result = Shapes.or(result, parts.get(i));
        return result;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return hasSide(state) || state.getValue(HAS_MIDDLE);
    }
}