package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import alabaster.hearthandharvest.common.block.entity.container.CrateSlotHelper;
import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.ContainerHelper;
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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrateBlock extends Block implements EntityBlock {

    public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;

    private static final VoxelShape SHAPE_BOTTOM = Shapes.or(
            Block.box( 0, 0,  0, 16, 4, 16),  // floor
            Block.box( 0, 4,  0, 16, 8,  1),  // north wall
            Block.box( 0, 4, 15, 16, 8, 16),  // south wall
            Block.box( 0, 4,  0,  1, 8, 16),  // west wall
            Block.box(15, 4,  0, 16, 8, 16)   // east wall
    );
    private static final VoxelShape SHAPE_TOP = Shapes.or(
            Block.box( 0,  8,  0, 16, 12, 16),
            Block.box( 0, 12,  0, 16, 16,  1),
            Block.box( 0, 12, 15, 16, 16, 16),
            Block.box( 0, 12,  0,  1, 16, 16),
            Block.box(15, 12,  0, 16, 16, 16)
    );
    private static final VoxelShape SHAPE_DOUBLE = Shapes.or(SHAPE_BOTTOM, SHAPE_TOP);

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
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        LootParams lootParams = params
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .create(LootContextParamSets.BLOCK);

        BlockEntity be   = lootParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        HolderLookup.Provider lookup = lootParams.getLevel().registryAccess();

        if (be instanceof CrateBlockEntity crate) {
            if (state.getValue(TYPE) == SlabType.DOUBLE) {
                return List.of(
                        buildHalfDrop(crate, 0,                            lookup),
                        buildHalfDrop(crate, CrateBlockEntity.SLOTS_PER_HALF, lookup)
                );
            }
            return List.of(buildHalfDrop(crate, 0, lookup));
        }

        return List.of(new ItemStack(this));
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack held     = context.getItemInHand();
        SlabType  existing = state.getValue(TYPE);

        if (existing == SlabType.DOUBLE || !held.is(this.asItem())) return false;

        if (existing == SlabType.BOTTOM && hasTallBottle(context.getLevel(), context.getClickedPos(), existing)) {
            return false;
        }

        if (context.replacingClickedOnBlock()) {
            boolean clickedUpperHalf =
                    context.getClickLocation().y - context.getClickedPos().getY() > 0.5;
            Direction face = context.getClickedFace();
            return existing == SlabType.BOTTOM
                    ? (face == Direction.UP   || clickedUpperHalf)
                    : (face == Direction.DOWN || !clickedUpperHalf);
        }
        return true;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos        = context.getClickedPos();
        BlockState existing = context.getLevel().getBlockState(pos);

        if (existing.is(this) && existing.getValue(TYPE) != SlabType.DOUBLE) {
            return existing.setValue(TYPE, SlabType.DOUBLE);
        }

        Direction face = context.getClickedFace();
        if (face == Direction.DOWN) return this.defaultBlockState().setValue(TYPE, SlabType.TOP);
        if (face == Direction.UP)   return this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM);
        double hitY = context.getClickLocation().y - pos.getY();
        return this.defaultBlockState().setValue(TYPE, hitY > 0.5 ? SlabType.TOP : SlabType.BOTTOM);
    }

    private boolean hasTallBottle(Level level, BlockPos pos, SlabType existingType) {
        if (!(level.getBlockEntity(pos) instanceof CrateBlockEntity be)) return false;
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
    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!(level.getBlockEntity(pos) instanceof CrateBlockEntity rack)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int slot = CrateSlotHelper.getSlotFromHit(state, hit);
        if (slot < 0 || slot >= rack.getContainerSize()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ItemStack current = rack.getItem(slot);

        if (current.isEmpty()) {
            if (!heldStack.isEmpty() && (heldStack.is(HHModTags.BOTTLES) || heldStack.is(HHModTags.CRATEABLE_ITEMS))) {

                ItemStack placed = heldStack.copyWithCount(1);
                rack.setItem(slot, placed);
                if (!player.getAbilities().instabuild) {
                    heldStack.shrink(1);
                }
                level.sendBlockUpdated(pos, state, state, 3);
                return ItemInteractionResult.SUCCESS;
            }
        } else {
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
        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);

        if (level.isClientSide) return;

        if (blockEntity instanceof CrateBlockEntity crate) {
            if (state.getValue(TYPE) == SlabType.DOUBLE) {
                popResource(level, pos, buildHalfDrop(crate, 0,                            level.registryAccess()));
                popResource(level, pos, buildHalfDrop(crate, CrateBlockEntity.SLOTS_PER_HALF, level.registryAccess()));
            } else {
                popResource(level, pos, buildHalfDrop(crate, 0, level.registryAccess()));
            }
        } else {
            popResource(level, pos, new ItemStack(this));
        }
    }

    private ItemStack buildHalfDrop(CrateBlockEntity crate, int slotOffset,
                                    HolderLookup.Provider lookup) {
        ItemStack drop = new ItemStack(this);

        NonNullList<ItemStack> halfItems =
                NonNullList.withSize(CrateBlockEntity.SLOTS_PER_HALF, ItemStack.EMPTY);
        boolean anyFilled = false;
        for (int i = 0; i < CrateBlockEntity.SLOTS_PER_HALF; i++) {
            ItemStack s = crate.getItem(slotOffset + i);
            halfItems.set(i, s.copy());
            if (!s.isEmpty()) anyFilled = true;
        }

        if (anyFilled) {
            CompoundTag tag = new CompoundTag();
            ContainerHelper.saveAllItems(tag, halfItems, lookup);
            BlockItem.setBlockEntityData(drop, HHModBlockEntities.CRATE.get(), tag);
        }

        return drop;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrateBlockEntity(pos, state);
    }
}