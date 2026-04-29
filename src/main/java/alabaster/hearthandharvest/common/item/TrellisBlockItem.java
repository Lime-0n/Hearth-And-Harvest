package alabaster.hearthandharvest.common.item;

import alabaster.hearthandharvest.common.block.trellis.TrellisBlock;
import alabaster.hearthandharvest.common.block.trellis.TrellisMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TrellisBlockItem extends BlockItem {

    private final TrellisMaterial material;

    public TrellisBlockItem(Block block, TrellisMaterial material, Properties props) {
        super(block, props);
        this.material = material;
    }

    public TrellisMaterial getMaterial() {
        return material;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Direction face = ctx.getClickedFace();
        boolean sneaking = ctx.getPlayer() != null && ctx.getPlayer().isShiftKeyDown();
        Vec3 hit = ctx.getClickLocation();
        double lx = hit.x - pos.getX();
        double ly = hit.y - pos.getY();
        double lz = hit.z - pos.getZ();

        if (state.getBlock() instanceof TrellisBlock) {
            return handleTrellisClick(ctx, state, pos, level, face, sneaking, lx, ly, lz);
        }
        return handleFreshPlacement(ctx, level, pos, face, sneaking, lx, lz);
    }

    private InteractionResult handleTrellisClick(UseOnContext ctx, BlockState state, BlockPos pos, Level level, Direction face, boolean sneaking, double lx, double ly, double lz) {
        return switch (face) {
            case UP    -> handleTrellisTopFace(ctx, state, pos, level, sneaking, lx, lz);
            case DOWN  -> handleTrellisBottomFace(ctx, state, pos, level, sneaking, lx, lz);
            case NORTH -> handleTrellisSideFace(ctx, state, pos, level, Direction.NORTH,
                    TrellisBlock.SIDE_SOUTH, TrellisBlock.SIDE_NORTH, TrellisBlock.MIDDLE_EW, sneaking, ly, lx, lz);
            case SOUTH -> handleTrellisSideFace(ctx, state, pos, level, Direction.SOUTH,
                    TrellisBlock.SIDE_NORTH, TrellisBlock.SIDE_SOUTH, TrellisBlock.MIDDLE_EW, sneaking, ly, lx, lz);
            case EAST  -> handleTrellisSideFace(ctx, state, pos, level, Direction.EAST,
                    TrellisBlock.SIDE_WEST, TrellisBlock.SIDE_EAST, TrellisBlock.MIDDLE_NS, sneaking, ly, lx, lz);
            case WEST  -> handleTrellisSideFace(ctx, state, pos, level, Direction.WEST,
                    TrellisBlock.SIDE_EAST, TrellisBlock.SIDE_WEST, TrellisBlock.MIDDLE_NS, sneaking, ly, lx, lz);
        };
    }

    private InteractionResult handleTrellisTopFace(UseOnContext ctx, BlockState state, BlockPos pos, Level level, boolean sneaking, double lx, double lz) {
        if (sneaking) {
            return placeOrMerge(ctx, level, pos.above(), state.getValue(TrellisBlock.MATERIAL), TrellisBlock.HAS_FLAT);
        }

        if (hasSide(state) && !state.getValue(TrellisBlock.HAS_TOP)) {
            return mergeIntoBlock(ctx, state, pos, level, TrellisBlock.HAS_TOP);
        }

        if (state.getValue(TrellisBlock.HAS_FLAT) || state.getValue(TrellisBlock.HAS_TOP)) {
            BooleanProperty toExtend = state.getValue(TrellisBlock.HAS_FLAT) ? TrellisBlock.HAS_FLAT : TrellisBlock.HAS_TOP;
            Direction extDir = horizontalDirectionFromHit(lx, lz);
            if (extDir != null) {
                return placeOrMerge(ctx, level, pos.relative(extDir), state.getValue(TrellisBlock.MATERIAL), toExtend);
            }
        }

        BooleanProperty component = componentFromTopHit(lx, lz, ctx);
        if (component == null) return InteractionResult.FAIL;
        if (isMiddle(component) && hasSide(state)) return InteractionResult.FAIL;
        if (isSide(component) && hasMiddle(state)) return InteractionResult.FAIL;
        return mergeIntoBlock(ctx, state, pos, level, component);
    }

    private InteractionResult handleTrellisBottomFace(UseOnContext ctx, BlockState state, BlockPos pos, Level level, boolean sneaking, double lx, double lz) {
        if (sneaking) {
            return placeOrMerge(ctx, level, pos.below(), state.getValue(TrellisBlock.MATERIAL), TrellisBlock.HAS_TOP);
        }

        if (state.getValue(TrellisBlock.HAS_TOP) || state.getValue(TrellisBlock.HAS_FLAT)) {
            BooleanProperty toExtend = state.getValue(TrellisBlock.HAS_TOP) ? TrellisBlock.HAS_TOP : TrellisBlock.HAS_FLAT;
            Direction extDir = horizontalDirectionFromHit(lx, lz);
            if (extDir != null) {
                return placeOrMerge(ctx, level, pos.relative(extDir), state.getValue(TrellisBlock.MATERIAL), toExtend);
            }
        }

        return InteractionResult.PASS;
    }

    private InteractionResult handleTrellisSideFace(UseOnContext ctx, BlockState state, BlockPos pos, Level level, Direction dir, BooleanProperty sideProp, BooleanProperty oppSideProp, BooleanProperty middleProp, boolean sneaking, double vert, double lx, double lz) {

        boolean hasSideProp = state.getValue(sideProp);
        boolean hasOppSide = state.getValue(oppSideProp);
        boolean hasMiddleProp = state.getValue(middleProp);

        if (sneaking && hasSideProp) {
            return placeOrMerge(ctx, level, pos.relative(dir), state.getValue(TrellisBlock.MATERIAL), oppSideProp);
        }

        if (sneaking && hasOppSide) {
            BooleanProperty toPlace = vert > 0.5 ? TrellisBlock.HAS_TOP : TrellisBlock.HAS_FLAT;
            return mergeIntoBlock(ctx, state, pos, level, toPlace);
        }

        if (!sneaking && hasSideProp) {
            Direction extDir = extensionDirection(dir, vert, lx, lz);
            return placeOrMerge(ctx, level, pos.relative(extDir), state.getValue(TrellisBlock.MATERIAL), sideProp);
        }

        if (!sneaking && hasMiddleProp) {
            Direction extDir = extensionDirection(dir, vert, lx, lz);
            return placeOrMerge(ctx, level, pos.relative(extDir), state.getValue(TrellisBlock.MATERIAL), middleProp);
        }

        if (!sneaking) {
            if (hasMiddle(state)) return InteractionResult.FAIL;
            return mergeIntoBlock(ctx, state, pos, level, sideProp);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult handleFreshPlacement(UseOnContext ctx, Level level, BlockPos clickedPos, Direction face, boolean sneaking, double lx, double lz) {
        BlockPos targetPos = clickedPos.relative(face);

        BooleanProperty component = switch (face) {
            case UP -> sneaking ? TrellisBlock.HAS_FLAT : componentFromTopHit(lx, lz, ctx);
            case DOWN -> TrellisBlock.HAS_TOP;
            case NORTH -> TrellisBlock.SIDE_NORTH;
            case SOUTH -> TrellisBlock.SIDE_SOUTH;
            case EAST -> TrellisBlock.SIDE_EAST;
            case WEST -> TrellisBlock.SIDE_WEST;
        };

        if (component == null) return InteractionResult.FAIL;
        return placeOrMerge(ctx, level, targetPos, material, component);
    }

    private InteractionResult placeOrMerge(UseOnContext ctx, Level level, BlockPos targetPos, TrellisMaterial mat, BooleanProperty component) {
        BlockState targetState = level.getBlockState(targetPos);

        if (targetState.getBlock() instanceof TrellisBlock) {
            if (targetState.getValue(TrellisBlock.MATERIAL) != material) return InteractionResult.FAIL;
            if (targetState.getValue(component)) return InteractionResult.FAIL;
            if (isSide(component) && hasMiddle(targetState)) return InteractionResult.FAIL;
            if (isMiddle(component) && hasSide(targetState)) return InteractionResult.FAIL;
            if (!level.isClientSide()) {
                level.setBlock(targetPos, targetState.setValue(component, true), Block.UPDATE_ALL);
                playSoundAt(level, targetPos);
                consumeItem(ctx);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (!targetState.isAir() && !targetState.canBeReplaced()) return InteractionResult.FAIL;

        BlockState newState = getBlock().defaultBlockState()
                .setValue(TrellisBlock.MATERIAL, mat)
                .setValue(component, true);
        if (!level.isClientSide()) {
            level.setBlock(targetPos, newState, Block.UPDATE_ALL);
            playSoundAt(level, targetPos);
            consumeItem(ctx);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private InteractionResult mergeIntoBlock(UseOnContext ctx, BlockState state, BlockPos pos, Level level, BooleanProperty component) {
        if (state.getValue(TrellisBlock.MATERIAL) != material) return InteractionResult.FAIL;
        if (state.getValue(component)) return InteractionResult.FAIL;
        if (!level.isClientSide()) {
            level.setBlock(pos, state.setValue(component, true), Block.UPDATE_ALL);
            playSoundAt(level, pos);
            consumeItem(ctx);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    private BooleanProperty componentFromTopHit(double lx, double lz, UseOnContext ctx) {
        if (lz < 0.25) return TrellisBlock.SIDE_SOUTH;
        if (lz > 0.75) return TrellisBlock.SIDE_NORTH;
        if (lx < 0.25) return TrellisBlock.SIDE_EAST;
        if (lx > 0.75) return TrellisBlock.SIDE_WEST;
        Direction horiz = ctx.getHorizontalDirection();
        return (horiz == Direction.NORTH || horiz == Direction.SOUTH)
                ? TrellisBlock.MIDDLE_EW : TrellisBlock.MIDDLE_NS;
    }

    @Nullable
    private static Direction horizontalDirectionFromHit(double lx, double lz) {
        if (lz < 0.25) return Direction.NORTH;
        if (lz > 0.75) return Direction.SOUTH;
        if (lx < 0.25) return Direction.WEST;
        if (lx > 0.75) return Direction.EAST;
        return null;
    }

    private static Direction extensionDirection(Direction face, double vert, double lx, double lz) {
        if (vert > 0.75) return Direction.UP;
        if (vert < 0.25) return Direction.DOWN;
        return switch (face) {
            case NORTH -> lx < 0.5 ? Direction.WEST : Direction.EAST;
            case SOUTH -> lx < 0.5 ? Direction.EAST : Direction.WEST;
            case EAST -> lz < 0.5 ? Direction.NORTH : Direction.SOUTH;
            case WEST -> lz < 0.5 ? Direction.SOUTH : Direction.NORTH;
            default -> Direction.NORTH;
        };
    }

    private static boolean hasMiddle(BlockState state) {
        return state.getValue(TrellisBlock.MIDDLE_EW) || state.getValue(TrellisBlock.MIDDLE_NS);
    }

    private static boolean hasSide(BlockState state) {
        return state.getValue(TrellisBlock.SIDE_NORTH) || state.getValue(TrellisBlock.SIDE_SOUTH)
                || state.getValue(TrellisBlock.SIDE_EAST) || state.getValue(TrellisBlock.SIDE_WEST);
    }

    private static boolean isMiddle(BooleanProperty p) {
        return p == TrellisBlock.MIDDLE_EW || p == TrellisBlock.MIDDLE_NS;
    }

    private static boolean isSide(BooleanProperty p) {
        return p == TrellisBlock.SIDE_NORTH || p == TrellisBlock.SIDE_SOUTH
                || p == TrellisBlock.SIDE_EAST || p == TrellisBlock.SIDE_WEST;
    }

    private void playSoundAt(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1f, 1f);
    }

    private void consumeItem(UseOnContext ctx) {
        if (ctx.getPlayer() != null && !ctx.getPlayer().isCreative()) {
            ctx.getItemInHand().shrink(1);
        }
    }

    @Override
    public String getDescriptionId() {
        return "item." + BuiltInRegistries.ITEM.getKey(this).getNamespace()
                + "." + BuiltInRegistries.ITEM.getKey(this).getPath();
    }
}