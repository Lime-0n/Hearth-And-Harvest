package alabaster.hearthandharvest.common.block.trellis;

import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TrellisBlock extends Block implements BonemealableBlock {

    public static final BooleanProperty MIDDLE_EW = BooleanProperty.create("middle_ew");
    public static final BooleanProperty MIDDLE_NS = BooleanProperty.create("middle_ns");
    public static final BooleanProperty SIDE_NORTH = BooleanProperty.create("side_north");
    public static final BooleanProperty SIDE_SOUTH = BooleanProperty.create("side_south");
    public static final BooleanProperty SIDE_EAST = BooleanProperty.create("side_east");
    public static final BooleanProperty SIDE_WEST = BooleanProperty.create("side_west");
    public static final BooleanProperty HAS_FLAT = BooleanProperty.create("has_flat");
    public static final BooleanProperty HAS_TOP = BooleanProperty.create("has_top");
    public static final EnumProperty<TrellisMaterial> MATERIAL = EnumProperty.create("material", TrellisMaterial.class);
    public static final EnumProperty<TrellisPlant> PLANT = EnumProperty.create("plant", TrellisPlant.class);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);

    private static final VoxelShape MIDDLE_EW_SHAPE = Block.box( 0, 0,  7, 16, 16,  9);
    private static final VoxelShape MIDDLE_NS_SHAPE = Block.box( 7, 0,  0, 9, 16, 16);
    private static final VoxelShape FLAT_SHAPE = Block.box( 0, 0, 0, 16, 2, 16);
    private static final VoxelShape TOP_SHAPE = Block.box( 0,14,  0, 16, 16, 16);
    private static final VoxelShape SIDE_N_SHAPE = Block.box( 0, 0, 14, 16, 16, 16);
    private static final VoxelShape SIDE_S_SHAPE = Block.box( 0, 0,  0, 16, 16,  2);
    private static final VoxelShape SIDE_E_SHAPE = Block.box( 0, 0,  0, 2, 16, 16);
    private static final VoxelShape SIDE_W_SHAPE = Block.box(14, 0,  0, 16, 16, 16);

    public static final BooleanProperty GROWTH_BLOCKED = BooleanProperty.create("growth_blocked");

    private static final int MAX_GRAPE_HEIGHT = 5;

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
                .setValue(GROWTH_BLOCKED, false)
                .setValue(MATERIAL, TrellisMaterial.STICK)
                .setValue(PLANT, TrellisPlant.NONE)
                .setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MIDDLE_EW, MIDDLE_NS, SIDE_NORTH, SIDE_SOUTH, SIDE_EAST, SIDE_WEST,
                HAS_FLAT, HAS_TOP, GROWTH_BLOCKED, MATERIAL, PLANT, AGE);
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
            case VINE, ROSE -> { if (random.nextInt(5) == 0) trySpread(state, level, pos); }
            case RED_GRAPE, GREEN_GRAPE -> tickGrape(state, level, pos, random);
            default -> {}
        }
    }

    private void tickGrape(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos base = findGrapeColumnBase(level, pos, state.getValue(PLANT));
        if (!level.getBlockState(base.below()).is(Blocks.FARMLAND)) return;

        int heightFromBase = pos.getY() - base.getY();
        int age = state.getValue(AGE);

        if (age < 4) {
            if (random.nextInt(5) == 0) {
                level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
            }
        } else if (random.nextInt(7) == 0) {
            tryGrapeSpread(state, level, pos, heightFromBase);
        }
    }

    private BlockPos findGrapeColumnBase(ServerLevel level, BlockPos pos, TrellisPlant plant) {
        BlockPos current = pos;
        while (true) {
            BlockPos below = current.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.getBlock() instanceof TrellisBlock && belowState.getValue(PLANT) == plant) {
                current = below;
            } else {
                break;
            }
        }
        return current;
    }

    private void tryGrapeSpread(BlockState state, ServerLevel level, BlockPos pos, int heightFromBase) {
        TrellisPlant plant = state.getValue(PLANT);

        if (heightFromBase < MAX_GRAPE_HEIGHT) {
            BlockPos above = pos.above();
            BlockState aboveState = level.getBlockState(above);
            if (aboveState.getBlock() instanceof TrellisBlock && aboveState.getValue(PLANT) == TrellisPlant.NONE) {
                level.setBlock(above, aboveState.setValue(PLANT, plant).setValue(AGE, 0), Block.UPDATE_ALL);
                return;
            }
        }

        Direction[] dirs = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        Direction dir = dirs[level.random.nextInt(4)];
        BlockPos side = pos.relative(dir);
        BlockState sideState = level.getBlockState(side);
        if (sideState.getBlock() instanceof TrellisBlock && sideState.getValue(PLANT) == TrellisPlant.NONE) {
            level.setBlock(side, sideState.setValue(PLANT, plant).setValue(AGE, 0), Block.UPDATE_ALL);
        }
    }

    private void trySpread(BlockState state, ServerLevel level, BlockPos pos) {
        TrellisPlant plant = state.getValue(PLANT);
        List<BlockPos> candidates = new ArrayList<>();

        BlockPos above = pos.above();
        if (isEmptyTrellis(level, above)) { candidates.add(above); candidates.add(above); }

        for (Direction dir : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
            BlockPos side = pos.relative(dir);
            if (isEmptyTrellis(level, side)) candidates.add(side);
        }

        BlockPos below = pos.below();
        if (isEmptyTrellis(level, below) && candidates.isEmpty()) candidates.add(below);

        if (candidates.isEmpty()) return;
        BlockPos target = candidates.get(level.random.nextInt(candidates.size()));
        BlockState targetState = level.getBlockState(target);
        level.setBlock(target, targetState.setValue(PLANT, plant).setValue(AGE, 0), Block.UPDATE_ALL);
    }

    private boolean isEmptyTrellis(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof TrellisBlock
                && state.getValue(PLANT) == TrellisPlant.NONE
                && !state.getValue(GROWTH_BLOCKED);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        TrellisPlant currentPlant = state.getValue(PLANT);

        if (stack.is(Items.SHEARS) && currentPlant == TrellisPlant.NONE) {
            if (!level.isClientSide()) {
                boolean nowBlocked = !state.getValue(GROWTH_BLOCKED);
                level.setBlock(pos, state.setValue(GROWTH_BLOCKED, nowBlocked), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1f, 1f);
                ((ServerLevel) level).sendParticles(ParticleTypes.CRIT,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        5, 0.3, 0.3, 0.3, 0.0
                );
                player.displayClientMessage(Component.translatable(nowBlocked
                        ? "block.hearthandharvest.trellis.growth_blocked"
                        : "block.hearthandharvest.trellis.growth_allowed"), true);
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        if (stack.is(Items.SHEARS) && currentPlant != TrellisPlant.NONE) {
            if (!level.isClientSide()) {
                ItemStack drop = switch (currentPlant) {
                    case VINE -> new ItemStack(Items.VINE);
                    case ROSE -> new ItemStack(Items.ROSE_BUSH);
                    case RED_GRAPE -> new ItemStack(HHModItems.RED_GRAPES.get());
                    case GREEN_GRAPE -> new ItemStack(HHModItems.GREEN_GRAPES.get());
                    default -> ItemStack.EMPTY;
                };
                if (!drop.isEmpty()) popResource(level, pos, drop);
                level.setBlock(pos, state.setValue(PLANT, TrellisPlant.NONE).setValue(AGE, 0), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1f, 1f);
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        if (stack.is(Items.VINE) && currentPlant == TrellisPlant.NONE) {
            return applyPlant(stack, state, level, pos, player, TrellisPlant.VINE);
        }
        if (stack.is(Items.ROSE_BUSH) && currentPlant == TrellisPlant.NONE) {
            return applyPlant(stack, state, level, pos, player, TrellisPlant.ROSE);
        }
        if (stack.is(HHModItems.RED_GRAPES.get()) && currentPlant == TrellisPlant.NONE) {
            return applyPlant(stack, state, level, pos, player, TrellisPlant.RED_GRAPE);
        }
        if (stack.is(HHModItems.GREEN_GRAPES.get()) && currentPlant == TrellisPlant.NONE) {
            return applyPlant(stack, state, level, pos, player, TrellisPlant.GREEN_GRAPE);
        }

        if (currentPlant.isGrape() && state.getValue(AGE) == 4) {
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
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return switch (state.getValue(MATERIAL)) {
            case STICK -> new ItemStack(HHModItems.TRELLIS.get());
            case BAMBOO -> new ItemStack(HHModItems.BAMBOO_TRELLIS.get());
            case STRIPPED_BAMBOO -> new ItemStack(HHModItems.STRIPPED_BAMBOO_TRELLIS.get());
        };
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        Item item = switch (state.getValue(MATERIAL)) {
            case STICK -> HHModItems.TRELLIS.get();
            case BAMBOO -> HHModItems.BAMBOO_TRELLIS.get();
            case STRIPPED_BAMBOO -> HHModItems.STRIPPED_BAMBOO_TRELLIS.get();
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

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        TrellisPlant plant = state.getValue(PLANT);
        if (!plant.isGrape()) return false;
        if (level instanceof ServerLevel serverLevel) {
            BlockPos base = findGrapeColumnBase(serverLevel, pos, plant);
            if (!serverLevel.getBlockState(base.below()).is(Blocks.FARMLAND)) return false;
        }
        return state.getValue(AGE) < 4;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age < 4) {
            level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.isShiftKeyDown()) return InteractionResult.PASS;

        if (state.getValue(PLANT) != TrellisPlant.NONE) {
            if (level.isClientSide()) {
                player.displayClientMessage(
                        Component.translatable("block.hearthandharvest.trellis.remove_plant_first"), true);
            }
            return InteractionResult.FAIL;
        }

        BooleanProperty toRemove = componentFromHit(state, hit);
        if (toRemove == null) return InteractionResult.PASS;
        if (!state.getValue(toRemove)) return InteractionResult.PASS;

        if (!level.isClientSide()) {
            BlockState newState = state.setValue(toRemove, false);

            boolean anyLeft = newState.getValue(MIDDLE_EW)  || newState.getValue(MIDDLE_NS)
                    || newState.getValue(SIDE_NORTH) || newState.getValue(SIDE_SOUTH)
                    || newState.getValue(SIDE_EAST) || newState.getValue(SIDE_WEST)
                    || newState.getValue(HAS_FLAT) || newState.getValue(HAS_TOP);

            if (anyLeft) {
                level.setBlock(pos, newState, Block.UPDATE_ALL);
            } else {
                level.removeBlock(pos, false);
            }

            Item item = switch (state.getValue(MATERIAL)) {
                case STICK -> HHModItems.TRELLIS.get();
                case BAMBOO -> HHModItems.BAMBOO_TRELLIS.get();
                case STRIPPED_BAMBOO -> HHModItems.STRIPPED_BAMBOO_TRELLIS.get();
            };
            ItemStack returned = new ItemStack(item);
            if (!player.getInventory().add(returned)) {
                popResource(level, pos, returned);
            }

            level.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 0.8f, 1.2f);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    private BooleanProperty componentFromHit(BlockState state, BlockHitResult hit) {
        Direction face = hit.getDirection();
        BlockPos pos = hit.getBlockPos();
        double lx = hit.getLocation().x - pos.getX();
        double ly = hit.getLocation().y - pos.getY();
        double lz = hit.getLocation().z - pos.getZ();

        return switch (face) {
            case NORTH, SOUTH -> {
                if (ly > 0.8 && state.getValue(HAS_TOP))  yield HAS_TOP;
                if (ly < 0.2 && state.getValue(HAS_FLAT)) yield HAS_FLAT;
                if (face == Direction.NORTH && state.getValue(SIDE_NORTH)) yield SIDE_NORTH;
                if (face == Direction.SOUTH && state.getValue(SIDE_SOUTH)) yield SIDE_SOUTH;
                yield state.getValue(MIDDLE_EW) ? MIDDLE_EW : null;
            }
            case EAST, WEST -> {
                if (ly > 0.8 && state.getValue(HAS_TOP))  yield HAS_TOP;
                if (ly < 0.2 && state.getValue(HAS_FLAT)) yield HAS_FLAT;
                if (face == Direction.EAST && state.getValue(SIDE_EAST)) yield SIDE_EAST;
                if (face == Direction.WEST && state.getValue(SIDE_WEST)) yield SIDE_WEST;
                yield state.getValue(MIDDLE_NS) ? MIDDLE_NS : null;
            }
            case UP -> {
                if (state.getValue(HAS_TOP)) yield HAS_TOP;
                if (lx < 0.3 || lx > 0.7) yield state.getValue(MIDDLE_NS) ? MIDDLE_NS : null;
                if (lz < 0.3 || lz > 0.7) yield state.getValue(MIDDLE_EW) ? MIDDLE_EW : null;
                yield state.getValue(HAS_FLAT) ? HAS_FLAT : null;
            }
            case DOWN -> {
                if (state.getValue(HAS_FLAT)) yield HAS_FLAT;
                yield state.getValue(HAS_TOP) ? HAS_TOP : null;
            }
        };
    }
}