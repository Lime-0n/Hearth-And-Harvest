package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.block.entity.JarBlockEntity;
import alabaster.hearthandharvest.common.item.JarBlockItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class JarBlock extends BaseEntityBlock {

    public static final MapCodec<JugBlock> CODEC = simpleCodec(JugBlock::new);
    public static final int MAX_JARS = 4;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty JARS = IntegerProperty.create("jars", 1, MAX_JARS);

    protected static final VoxelShape ONE_NORTH = box(1, 0,  1,  7, 10,  7);
    protected static final VoxelShape ONE_EAST = box(9, 0,  1, 15, 10,  7);
    protected static final VoxelShape ONE_SOUTH = box(9, 0,  9, 15, 10, 15);
    protected static final VoxelShape ONE_WEST = box(1, 0,  9,  7, 10, 15);

    protected static final VoxelShape TWO_NORTH = box(1, 0,  1, 15, 10,  7);
    protected static final VoxelShape TWO_EAST = box(9, 0,  1, 15, 10, 15);
    protected static final VoxelShape TWO_SOUTH = box(1, 0,  9, 15, 10, 15);
    protected static final VoxelShape TWO_WEST = box(1, 0,  1,  7, 10, 15);

    protected static final VoxelShape THREE_NORTH = box(1, 0, 1, 15, 10, 15);
    protected static final VoxelShape FOUR_NORTH = box(1, 0, 1, 15, 10, 15);

    public JarBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(JARS, 1)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JarBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        for (Direction d : ctx.getNearestLookingDirections()) {
            if (d.getAxis().isHorizontal()) {
                return defaultBlockState().setValue(FACING, d).setValue(JARS, 1);
            }
        }
        return defaultBlockState();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(stack.getItem() instanceof JarBlockItem)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        int current = state.getValue(JARS);
        if (current >= MAX_JARS) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof JarBlockEntity be) {
                be.addJar(stack.getItem());
                BlockState newState = state.setValue(JARS, current + 1);
                level.setBlock(pos, newState, 3);
                level.sendBlockUpdated(pos, state, newState, 3);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof JarBlockEntity be) {
                be.dropAllJars(level, pos);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return Collections.emptyList();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        int jars = state.getValue(JARS);
        Direction face = state.getValue(FACING);
        return switch (jars) {
            case 2 -> switch (face) {
                case EAST  -> TWO_EAST;
                case SOUTH -> TWO_SOUTH;
                case WEST  -> TWO_WEST;
                default    -> TWO_NORTH;
            };
            case 3, 4 -> THREE_NORTH;
            default -> switch (face) {
                case EAST  -> ONE_EAST;
                case SOUTH -> ONE_SOUTH;
                case WEST  -> ONE_WEST;
                default    -> ONE_NORTH;
            };
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return getShape(state, world, pos, ctx);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> b) {
        b.add(JARS, FACING);
    }
}