package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.block.entity.WineRackBlockEntity;
import alabaster.hearthandharvest.common.block.entity.container.WineRackSlotHelper;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WineRackBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public WineRackBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (direction == Direction.NORTH) {
            return Block.box(0.0D, 0.0D, 4.0, 16.0D, 16.0D, 16.0D);
        }
        if (direction == Direction.EAST) {
            return Block.box(0.0D, 0.0D, 0.0, 12.0D, 16.0D, 16.0D);
        }
        if (direction == Direction.SOUTH) {
            return Block.box(0.0D, 0.0D, 0.0, 16.0D, 16.0D, 12.0D);
        }
        if (direction == Direction.WEST) {
            return Block.box(4.0D, 0.0D, 0.0, 16.0D, 16.0D, 16.0D);
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof WineRackBlockEntity rack)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int slot = WineRackSlotHelper.getSlotFromHit(state, hit);
        if (slot < 0 || slot >= rack.getContainerSize()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ItemStack current = rack.getItem(slot);

        if (current.isEmpty()) {
            if (!heldStack.isEmpty() && ! (heldStack.getItem() instanceof BlockItem)
                    && heldStack.is(HHModTags.WINE_BOTTLES)) {

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
        return true; // Tells Minecraft this block can output comparator signals
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof WineRackBlockEntity rack)) {
            return 0;
        }

        int filledSlots = 0;
        for (int i = 0; i < rack.getContainerSize(); i++) {
            if (!rack.getItem(i).isEmpty()) {
                filledSlots++;
            }
        }

        // 1 signal strength per bottle, capped at 15
        return Math.min(filledSlots, 15);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool) {
        if (blockEntity instanceof WineRackBlockEntity rack) {
            for (int i = 0; i < rack.getContainerSize(); i++) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), rack.getItem(i));
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WineRackBlockEntity(pos, state);
    }
}
