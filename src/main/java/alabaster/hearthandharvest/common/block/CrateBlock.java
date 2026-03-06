package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import alabaster.hearthandharvest.common.block.entity.container.CrateSlotHelper;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CrateBlock extends Block implements EntityBlock {

    public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;

    private static final VoxelShape SHAPE_BOTTOM = Block.box(0, 0,  0, 16,  8, 16);
    private static final VoxelShape SHAPE_TOP    = Block.box(0, 8,  0, 16, 16, 16);
    private static final VoxelShape SHAPE_DOUBLE = Block.box(0, 0,  0, 16, 16, 16);

    public CrateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, SlabType.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos        = context.getClickedPos();
        Level level         = context.getLevel();
        BlockState existing = level.getBlockState(pos);

        if (existing.is(this)) {
            SlabType existingType = existing.getValue(TYPE);
            if (existingType != SlabType.DOUBLE) {
                if (hasTallBottle(level, pos, existingType)) {
                    return null;
                }
                return existing.setValue(TYPE, SlabType.DOUBLE);
            }
            return null;
        }

        Direction clickedFace = context.getClickedFace();
        if (clickedFace == Direction.DOWN) {
            return this.defaultBlockState().setValue(TYPE, SlabType.TOP);
        }
        if (clickedFace == Direction.UP) {
            return this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM);
        }

        double hitY = context.getClickLocation().y - pos.getY();
        SlabType half = hitY > 0.5 ? SlabType.TOP : SlabType.BOTTOM;
        return this.defaultBlockState().setValue(TYPE, half);
    }

    private boolean hasTallBottle(Level level, BlockPos pos, SlabType existingType) {
        if (!(level.getBlockEntity(pos) instanceof CrateBlockEntity be)) return false;

        // Only the bottom half has height-sensitive items.
        if (existingType == SlabType.TOP) return false;

        for (int i = 0; i < CrateBlockEntity.SLOTS_PER_HALF; i++) {
            if (be.getItem(i).is(HHModTags.TALL_BOTTLES)) return true;
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(TYPE)) {
            case BOTTOM -> SHAPE_BOTTOM;
            case TOP    -> SHAPE_TOP;
            case DOUBLE -> SHAPE_DOUBLE;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return state.getValue(TYPE) == SlabType.DOUBLE;
    }

    @Override
    public ItemInteractionResult useItemOn(
            ItemStack heldStack, BlockState state, Level level,
            BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!(level.getBlockEntity(pos) instanceof CrateBlockEntity rack)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int slot = CrateSlotHelper.getSlotFromHit(state, hit);
        if (slot < 0 || slot >= rack.getContainerSize()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ItemStack current = rack.getItem(slot);

        if (current.isEmpty()) {
            // Place a bottle into the slot.
            if (!heldStack.isEmpty()
                    && !(heldStack.getItem() instanceof BlockItem)
                    && heldStack.is(HHModTags.BOTTLES)) {

                ItemStack placed = heldStack.copyWithCount(1);
                rack.setItem(slot, placed);
                if (!player.getAbilities().instabuild) {
                    heldStack.shrink(1);
                }
                level.sendBlockUpdated(pos, state, state, 3);
                return ItemInteractionResult.SUCCESS;
            }
        } else {
            // Remove the bottle from the slot.
            if (!level.isClientSide) {
                player.addItem(current.copy());
            }
            rack.setItem(slot, ItemStack.EMPTY);
            level.sendBlockUpdated(pos, state, state, 3);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.CONSUME;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof CrateBlockEntity rack)) return 0;

        int filledSlots = 0;
        // Only count slots that belong to the current slab configuration.
        int maxSlot = state.getValue(TYPE) == SlabType.DOUBLE
                ? CrateBlockEntity.TOTAL_SLOTS
                : CrateBlockEntity.SLOTS_PER_HALF;

        for (int i = 0; i < maxSlot; i++) {
            if (!rack.getItem(i).isEmpty()) filledSlots++;
        }
        return Math.min(filledSlots, 15);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos,
                              BlockState state, BlockEntity blockEntity, ItemStack tool) {
        if (blockEntity instanceof CrateBlockEntity rack) {
            for (int i = 0; i < rack.getContainerSize(); i++) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), rack.getItem(i));
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrateBlockEntity(pos, state);
    }
}