package alabaster.hearthandharvest.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BasinBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("level", 0, 3);
    private static final VoxelShape INSIDE = box(2.0F, 1.0F, 2.0F, 14.0F, 16.0F, 14.0F);
    protected static final VoxelShape SHAPE;

    public BasinBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATER_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATER_LEVEL);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection());
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return INSIDE;
    }

    private static boolean isWaterBottle(ItemStack stack) {
        if (!stack.is(Items.POTION)) return false;
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        return contents != null
                && contents.potion().isPresent()
                && contents.potion().get().is(Potions.WATER);
    }

    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        int currentLevel = state.getValue(WATER_LEVEL);
        boolean changed = false;
        ItemStack waterBottle = PotionContents.createItemStack(Items.POTION, Potions.WATER);

        if (isWaterBottle(heldItem)) {
            if (currentLevel < 3) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, Math.min(currentLevel + 1, 3)), 3);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(heldItem, player, new ItemStack(Items.GLASS_BOTTLE)));
                }
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                changed = true;
            }
        } else if (heldItem.is(Items.WATER_BUCKET)) {
            if (currentLevel < 3) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, Math.min(currentLevel + 3, 3)), 3);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(heldItem, player, new ItemStack(Items.BUCKET)));
                }
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                changed = true;
            }
        } else if (heldItem.is(Items.GLASS_BOTTLE)) {
            if (currentLevel >= 1) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, currentLevel - 1), 3);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(heldItem, player, waterBottle));
                }
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                changed = true;
            }
        } else if (heldItem.is(Items.BUCKET)) {
            if (currentLevel >= 3) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, currentLevel - 3), 3);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(heldItem, player, new ItemStack(Items.WATER_BUCKET)));
                }
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                changed = true;
            }
        }

        if (level.isClientSide() && changed) return ItemInteractionResult.SUCCESS;

        return changed ? ItemInteractionResult.CONSUME : super.useItemOn(heldStack, state, level, pos, player, hand, hit);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide && !level.hasNeighborSignal(pos)) {
            level.scheduleTick(pos, this, 60);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean isPowered = level.hasNeighborSignal(pos);
            boolean isAlreadyScheduled = level.getBlockTicks().hasScheduledTick(pos, this);

            if (!isPowered && !isAlreadyScheduled && state.getValue(WATER_LEVEL) < 3) {
                level.scheduleTick(pos, this, 60);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.hasNeighborSignal(pos)) {
            return;
        }

        int currentLevel = state.getValue(WATER_LEVEL);
        if (currentLevel < 3) {
            int newLevel = currentLevel + 1;
            BlockState newState = state.setValue(WATER_LEVEL, newLevel);
            level.setBlock(pos, newState, 3);
            level.playSound(null, pos, SoundEvents.FISH_SWIM, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (newLevel < 3) {
                level.scheduleTick(pos, this, 60);
            }
        }
    }

    static {
        SHAPE = Shapes.join(Shapes.block(), INSIDE, BooleanOp.ONLY_FIRST);
    }
}