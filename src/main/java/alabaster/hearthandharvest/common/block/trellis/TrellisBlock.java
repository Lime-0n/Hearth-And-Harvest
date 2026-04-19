package alabaster.hearthandharvest.common.block.trellis;

import alabaster.hearthandharvest.common.item.TrellisBlockItem;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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

    public static final BooleanProperty MIDDLE_EW = BooleanProperty.create("middle_ew");
    public static final BooleanProperty MIDDLE_NS = BooleanProperty.create("middle_ns");
    public static final BooleanProperty SIDE_NORTH = BooleanProperty.create("side_north");
    public static final BooleanProperty SIDE_SOUTH = BooleanProperty.create("side_south");
    public static final BooleanProperty SIDE_EAST = BooleanProperty.create("side_east");
    public static final BooleanProperty SIDE_WEST = BooleanProperty.create("side_west");
    public static final BooleanProperty HAS_FLAT = BooleanProperty.create("has_flat");
    public static final BooleanProperty HAS_TOP = BooleanProperty.create("has_top");
    public static final EnumProperty<TrellisShape> SHAPE = EnumProperty.create("shape",    TrellisShape.class);
    public static final EnumProperty<TrellisMaterial> MATERIAL = EnumProperty.create("material", TrellisMaterial.class);
    public static final EnumProperty<TrellisPlant> PLANT = EnumProperty.create("plant", TrellisPlant.class);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    private static final VoxelShape MIDDLE_EW_SHAPE = Block.box( 0, 0,  7, 16, 16,  9);
    private static final VoxelShape MIDDLE_NS_SHAPE = Block.box( 7, 0,  0,  9, 16, 16);
    private static final VoxelShape FLAT_SHAPE = Block.box( 0, 0,  0, 16,  2, 16);
    private static final VoxelShape TOP_SHAPE = Block.box( 0,14,  0, 16, 16, 16);
    private static final VoxelShape SIDE_N_SHAPE = Block.box( 0, 0, 14, 16, 16, 16);
    private static final VoxelShape SIDE_S_SHAPE = Block.box( 0, 0,  0, 16, 16,  2);
    private static final VoxelShape SIDE_E_SHAPE = Block.box( 0, 0,  0,  2, 16, 16);
    private static final VoxelShape SIDE_W_SHAPE    = Block.box(14, 0,  0, 16, 16, 16);

    public TrellisBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState()
                .setValue(MIDDLE_EW, false)
                .setValue(MIDDLE_NS, false)
                .setValue(SIDE_NORTH, false)
                .setValue(SIDE_SOUTH, false)
                .setValue(SIDE_EAST, false)
                .setValue(SIDE_WEST, false)
                .setValue(HAS_FLAT, false)
                .setValue(HAS_TOP, false)
                .setValue(MATERIAL, TrellisMaterial.STICK)
                .setValue(PLANT, TrellisPlant.NONE)
                .setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MIDDLE_EW, MIDDLE_NS, SIDE_NORTH, SIDE_SOUTH, SIDE_EAST, SIDE_WEST,
                HAS_FLAT, HAS_TOP, MATERIAL, PLANT, AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        List<VoxelShape> parts = new ArrayList<>();
        if (state.getValue(MIDDLE_EW)) parts.add(MIDDLE_EW_SHAPE);
        if (state.getValue(MIDDLE_NS)) parts.add(MIDDLE_NS_SHAPE);
        if (state.getValue(SIDE_NORTH)) parts.add(SIDE_N_SHAPE);
        if (state.getValue(SIDE_SOUTH)) parts.add(SIDE_S_SHAPE);
        if (state.getValue(SIDE_EAST)) parts.add(SIDE_E_SHAPE);
        if (state.getValue(SIDE_WEST)) parts.add(SIDE_W_SHAPE);
        if (state.getValue(HAS_FLAT)) parts.add(FLAT_SHAPE);
        if (state.getValue(HAS_TOP)) parts.add(TOP_SHAPE);
        if (parts.isEmpty()) return Shapes.block();
        VoxelShape result = parts.get(0);
        for (int i = 1; i < parts.size(); i++) result = Shapes.or(result, parts.get(i));
        return result;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(PLANT) != TrellisPlant.NONE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        switch (state.getValue(PLANT)) {
            case VINE, ROSE -> { if (random.nextInt(5) == 0) trySpreadUp(state, level, pos); }
            case RED_GRAPE, GREEN_GRAPE -> tickGrape(state, level, pos, random);
            default -> {}
        }
    }

    private void tickGrape(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age < 3) {
            level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
        } else if (random.nextInt(7) == 0) {
            trySpreadUp(state, level, pos);
        }
    }

    private void trySpreadUp(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        if (aboveState.getBlock() instanceof TrellisBlock
                && aboveState.getValue(PLANT) == TrellisPlant.NONE) {
            level.setBlock(above, aboveState
                    .setValue(PLANT, state.getValue(PLANT))
                    .setValue(AGE, 0), Block.UPDATE_ALL);
        }
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                           BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        TrellisPlant currentPlant = state.getValue(PLANT);

        // Vanilla vine → apply VINE
        if (stack.is(Items.VINE) && currentPlant == TrellisPlant.NONE) {
            return applyPlant(stack, state, level, pos, player, TrellisPlant.VINE);
        }
        // Vanilla rose bush → apply ROSE
        if (stack.is(Items.ROSE_BUSH) && currentPlant == TrellisPlant.NONE) {
            return applyPlant(stack, state, level, pos, player, TrellisPlant.ROSE);
        }
        // Grape harvest
        if (currentPlant.isGrape() && state.getValue(AGE) == 3) {
            if (!level.isClientSide()) {
                Item drop = currentPlant == TrellisPlant.RED_GRAPE
                        ? HHModItems.RED_GRAPES.get() : HHModItems.GREEN_GRAPES.get();
                popResource(level, pos, new ItemStack(drop, 1 + level.random.nextInt(2)));
                level.setBlock(pos, state.setValue(AGE, 0), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                        SoundSource.BLOCKS, 1f, 0.8f + level.random.nextFloat() * 0.4f);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private ItemInteractionResult applyPlant(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, TrellisPlant plant) {
        if (!level.isClientSide()) {
            level.setBlock(pos, state.setValue(PLANT, plant).setValue(AGE, 0), Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1f, 1f);
            if (!player.isCreative()) stack.shrink(1);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        if (!(ctx.getItemInHand().getItem() instanceof TrellisBlockItem)) return false;
        boolean sneaking = ctx.getPlayer() != null && ctx.getPlayer().isShiftKeyDown();
        if (sneaking) {
            Direction facing = ctx.getHorizontalDirection();
            return (facing == Direction.NORTH || facing == Direction.SOUTH)
                    ? !state.getValue(MIDDLE_EW)
                    : !state.getValue(MIDDLE_NS);
        }
        return switch (ctx.getClickedFace()) {
            case NORTH -> !state.getValue(SIDE_NORTH);
            case SOUTH -> !state.getValue(SIDE_SOUTH);
            case EAST  -> !state.getValue(SIDE_EAST);
            case WEST  -> !state.getValue(SIDE_WEST);
            case UP    -> !state.getValue(HAS_FLAT);
            case DOWN  -> !state.getValue(HAS_TOP);
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        BlockState existing = ctx.getLevel().getBlockState(pos);
        TrellisMaterial material = ((TrellisBlockItem) ctx.getItemInHand().getItem()).getMaterial();
        BlockState base = (existing.getBlock() == this) ? existing
                : defaultBlockState().setValue(MATERIAL, material);

        boolean sneaking = ctx.getPlayer() != null && ctx.getPlayer().isShiftKeyDown();
        if (sneaking) {
            Direction facing = ctx.getHorizontalDirection();
            return (facing == Direction.NORTH || facing == Direction.SOUTH)
                    ? base.setValue(MIDDLE_EW, true)
                    : base.setValue(MIDDLE_NS, true);
        }
        return switch (ctx.getClickedFace()) {
            case NORTH -> base.setValue(SIDE_NORTH, true);
            case SOUTH -> base.setValue(SIDE_SOUTH, true);
            case EAST  -> base.setValue(SIDE_EAST,  true);
            case WEST  -> base.setValue(SIDE_WEST,  true);
            case UP    -> base.setValue(HAS_FLAT, true);
            case DOWN  -> base.setValue(HAS_TOP,  true);
        };
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        Item item = switch (state.getValue(MATERIAL)) {
            case STICK -> HHModItems.TRELLIS.get();
            case BAMBOO -> HHModItems.BAMBOO_TRELLIS.get();
            case STRIPPED_BAMBOO  -> HHModItems.STRIPPED_BAMBOO_TRELLIS.get();
        };
        int count = (state.getValue(MIDDLE_EW) ? 1 : 0) + (state.getValue(MIDDLE_NS) ? 1 : 0)
                + (state.getValue(SIDE_NORTH) ? 1 : 0) + (state.getValue(SIDE_SOUTH) ? 1 : 0)
                + (state.getValue(SIDE_EAST) ? 1 : 0) + (state.getValue(SIDE_WEST) ? 1 : 0)
                + (state.getValue(HAS_FLAT) ? 1 : 0) + (state.getValue(HAS_TOP) ? 1 : 0);
        List<ItemStack> drops = new ArrayList<>();
        for (int i = 0; i < count; i++) drops.add(new ItemStack(item));
        return drops;
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return state.getValue(SIDE_NORTH) || state.getValue(SIDE_SOUTH)
                || state.getValue(SIDE_EAST) || state.getValue(SIDE_WEST)
                || state.getValue(MIDDLE_EW) || state.getValue(MIDDLE_NS);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }
}