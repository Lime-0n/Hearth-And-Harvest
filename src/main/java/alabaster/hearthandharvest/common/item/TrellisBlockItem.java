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
        BlockPos clickedPos = ctx.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        Direction face = ctx.getClickedFace();
        boolean sneaking = ctx.getPlayer() != null && ctx.getPlayer().isShiftKeyDown();

        if (state.getBlock() instanceof TrellisBlock) {
            if (sneaking && face == Direction.UP) {
                return handleAddToBlock(ctx, state, clickedPos, level, face);
            }

            BooleanProperty componentOnFace = primaryPropertyOnFace(state, face);
            if (componentOnFace != null) {
                return handleExtension(ctx, state, clickedPos, level, face, componentOnFace);
            } else {
                return handleAddToBlock(ctx, state, clickedPos, level, face);
            }
        }

        return handleFreshPlacement(ctx, level, clickedPos, face);
    }

    private InteractionResult handleExtension(UseOnContext ctx, BlockState state, BlockPos clickedPos, Level level, Direction face, BooleanProperty component) {
        Vec3 hit = ctx.getClickLocation();
        double lx = hit.x - clickedPos.getX();
        double ly = hit.y - clickedPos.getY();
        double lz = hit.z - clickedPos.getZ();

        Direction extDir = extensionDirection(face, lx, ly, lz);
        BlockPos targetPos = clickedPos.relative(extDir);
        BlockState targetState = level.getBlockState(targetPos);

        if (targetState.getBlock() instanceof TrellisBlock) {
            if (targetState.getValue(component)) return InteractionResult.FAIL;
            if (!level.isClientSide()) {
                level.setBlock(targetPos, targetState.setValue(component, true), Block.UPDATE_ALL);
                playSoundAt(level, targetPos);
                consumeItem(ctx);
            }
        } else if (targetState.isAir() || targetState.canBeReplaced()) {
            BlockState newState = getBlock().defaultBlockState()
                    .setValue(TrellisBlock.MATERIAL, state.getValue(TrellisBlock.MATERIAL))
                    .setValue(component, true);
            if (!level.isClientSide()) {
                level.setBlock(targetPos, newState, Block.UPDATE_ALL);
                playSoundAt(level, targetPos);
                consumeItem(ctx);
            }
        } else {
            return InteractionResult.FAIL;
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private InteractionResult handleAddToBlock(UseOnContext ctx, BlockState state, BlockPos pos, Level level, Direction face) {
        BooleanProperty toAdd = componentForFace(face, ctx);
        if (toAdd == null || state.getValue(toAdd)) return InteractionResult.FAIL;

        if (!level.isClientSide()) {
            level.setBlock(pos, state.setValue(toAdd, true), Block.UPDATE_ALL);
            playSoundAt(level, pos);
            consumeItem(ctx);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private InteractionResult handleFreshPlacement(UseOnContext ctx, Level level, BlockPos clickedPos, Direction face) {
        BlockPos targetPos = clickedPos.relative(face);
        BlockState targetState = level.getBlockState(targetPos);

        BooleanProperty component = componentForFace(face, ctx);

        if (targetState.getBlock() instanceof TrellisBlock) {
            if (component != null && !targetState.getValue(component)) {
                if (!level.isClientSide()) {
                    level.setBlock(targetPos, targetState.setValue(component, true), Block.UPDATE_ALL);
                    playSoundAt(level, targetPos);
                    consumeItem(ctx);
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
            return InteractionResult.FAIL;
        }

        if (!targetState.isAir() && !targetState.canBeReplaced()) return InteractionResult.FAIL;

        BlockState newState = getBlock().defaultBlockState()
                .setValue(TrellisBlock.MATERIAL, material);
        if (component != null) newState = newState.setValue(component, true);

        if (!level.isClientSide()) {
            level.setBlock(targetPos, newState, Block.UPDATE_ALL);
            playSoundAt(level, targetPos);
            consumeItem(ctx);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    private static BooleanProperty primaryPropertyOnFace(BlockState state, Direction face) {
        return switch (face) {
            case NORTH -> {
                if (state.getValue(TrellisBlock.SIDE_NORTH)) yield TrellisBlock.SIDE_NORTH;
                if (state.getValue(TrellisBlock.MIDDLE_EW)) yield TrellisBlock.MIDDLE_EW;
                yield null;
            }
            case SOUTH -> {
                if (state.getValue(TrellisBlock.SIDE_SOUTH)) yield TrellisBlock.SIDE_SOUTH;
                if (state.getValue(TrellisBlock.MIDDLE_EW)) yield TrellisBlock.MIDDLE_EW;
                yield null;
            }
            case EAST -> {
                if (state.getValue(TrellisBlock.SIDE_EAST)) yield TrellisBlock.SIDE_EAST;
                if (state.getValue(TrellisBlock.MIDDLE_NS)) yield TrellisBlock.MIDDLE_NS;
                yield null;
            }
            case WEST -> {
                if (state.getValue(TrellisBlock.SIDE_WEST)) yield TrellisBlock.SIDE_WEST;
                if (state.getValue(TrellisBlock.MIDDLE_NS)) yield TrellisBlock.MIDDLE_NS;
                yield null;
            }
            case UP -> state.getValue(TrellisBlock.HAS_TOP) ? TrellisBlock.HAS_TOP :
                    (state.getValue(TrellisBlock.HAS_FLAT) ? TrellisBlock.HAS_FLAT : null);
            case DOWN -> state.getValue(TrellisBlock.HAS_FLAT) ? TrellisBlock.HAS_FLAT :
                    (state.getValue(TrellisBlock.HAS_TOP) ? TrellisBlock.HAS_TOP : null);
        };
    }

    @Nullable
    private static BooleanProperty componentForFace(Direction face, UseOnContext ctx) {
        boolean sneaking = ctx.getPlayer() != null && ctx.getPlayer().isShiftKeyDown();
        if (sneaking && face == Direction.UP) {
            Direction horiz = ctx.getHorizontalDirection();
            return (horiz == Direction.NORTH || horiz == Direction.SOUTH)
                    ? TrellisBlock.MIDDLE_EW : TrellisBlock.MIDDLE_NS;
        }
        return switch (face) {
            case NORTH -> TrellisBlock.SIDE_NORTH;
            case SOUTH -> TrellisBlock.SIDE_SOUTH;
            case EAST -> TrellisBlock.SIDE_EAST;
            case WEST -> TrellisBlock.SIDE_WEST;
            case UP -> TrellisBlock.HAS_FLAT;
            case DOWN -> TrellisBlock.HAS_TOP;
        };
    }

    private static Direction extensionDirection(Direction face, double lx, double ly, double lz) {
        return switch (face) {
            case NORTH -> {
                if (ly > 0.75) yield Direction.UP;
                if (ly < 0.25) yield Direction.DOWN;
                yield lx < 0.5 ? Direction.WEST : Direction.EAST;
            }
            case SOUTH -> {
                if (ly > 0.75) yield Direction.UP;
                if (ly < 0.25) yield Direction.DOWN;
                yield lx < 0.5 ? Direction.EAST : Direction.WEST;
            }
            case EAST -> {
                if (ly > 0.75) yield Direction.UP;
                if (ly < 0.25) yield Direction.DOWN;
                yield lz < 0.5 ? Direction.NORTH : Direction.SOUTH;
            }
            case WEST -> {
                if (ly > 0.75) yield Direction.UP;
                if (ly < 0.25) yield Direction.DOWN;
                yield lz < 0.5 ? Direction.SOUTH : Direction.NORTH;
            }
            case UP, DOWN -> {
                if (lz < 0.25) yield Direction.NORTH;
                if (lz > 0.75) yield Direction.SOUTH;
                yield lx < 0.5 ? Direction.WEST : Direction.EAST;
            }
        };
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