package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.block.entity.JarBlockEntity;
import alabaster.hearthandharvest.common.item.JarBlockItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class JarBlock extends BaseEntityBlock {

    public static final MapCodec<JarBlock> CODEC = simpleCodec(JarBlock::new);
    public static final BooleanProperty SLOT_0 = BooleanProperty.create("slot_0"); // NW (-x, -z)
    public static final BooleanProperty SLOT_1 = BooleanProperty.create("slot_1"); // NE (+x, -z)
    public static final BooleanProperty SLOT_2 = BooleanProperty.create("slot_2"); // SW (-x, +z)
    public static final BooleanProperty SLOT_3 = BooleanProperty.create("slot_3"); // SE (+x, +z)

    public static final BooleanProperty[] SLOTS = { SLOT_0, SLOT_1, SLOT_2, SLOT_3 };

    private static final VoxelShape SHAPE_NW = box(1, 0,  1,  7, 10,  7);
    private static final VoxelShape SHAPE_NE = box(9, 0,  1, 15, 10,  7);
    private static final VoxelShape SHAPE_SW = box(1, 0,  9,  7, 10, 15);
    private static final VoxelShape SHAPE_SE = box(9, 0,  9, 15, 10, 15);

    private static final VoxelShape[] CORNER_SHAPES = { SHAPE_NW, SHAPE_NE, SHAPE_SW, SHAPE_SE };

    private static final VoxelShape[] COMBINED_SHAPES = new VoxelShape[16];
    static {
        for (int mask = 0; mask < 16; mask++) {
            VoxelShape shape = Shapes.empty();
            for (int i = 0; i < 4; i++) {
                if ((mask & (1 << i)) != 0) {
                    shape = Shapes.or(shape, CORNER_SHAPES[i]);
                }
            }
            COMBINED_SHAPES[mask] = mask == 0 ? SHAPE_NW : shape;
        }
    }

    public JarBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(SLOT_0, false)
                .setValue(SLOT_1, false)
                .setValue(SLOT_2, false)
                .setValue(SLOT_3, false));
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
        return defaultBlockState();
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(stack.getItem() instanceof JarBlockItem jarItem)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int targetSlot = quadrantFromHit(hit.getLocation(), pos);

        if (state.getValue(SLOTS[targetSlot])) {
            targetSlot = firstEmptySlot(state);
            if (targetSlot == -1) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        }

        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof JarBlockEntity be) {
                be.setSlot(targetSlot, jarItem.getDisplayBlock());
                BlockState newState = state.setValue(SLOTS[targetSlot], true);
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
        return COMBINED_SHAPES[slotMask(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return getShape(state, world, pos, ctx);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(SLOT_0, SLOT_1, SLOT_2, SLOT_3);
    }

    public static int quadrantFromHit(Vec3 hitLocation, BlockPos blockPos) {
        double localX = hitLocation.x - blockPos.getX();
        double localZ = hitLocation.z - blockPos.getZ();
        int east  = localX >= 0.5 ? 1 : 0;
        int south = localZ >= 0.5 ? 2 : 0;
        return south + east;
    }

    public static int firstEmptySlot(BlockState state) {
        for (int i = 0; i < SLOTS.length; i++) {
            if (!state.getValue(SLOTS[i])) return i;
        }
        return -1;
    }

    public static int slotMask(BlockState state) {
        int mask = 0;
        for (int i = 0; i < SLOTS.length; i++) {
            if (state.getValue(SLOTS[i])) mask |= (1 << i);
        }
        return mask;
    }
}