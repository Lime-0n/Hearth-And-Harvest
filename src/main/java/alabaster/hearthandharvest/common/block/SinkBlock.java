package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.registry.HHModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SinkBlock extends Block {
    public static final int MAX_WATER = 3;
    public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("water_level", 0, 3);
    private static final VoxelShape INSIDE = box(2.0F, 1.0F, 2.0F, 14.0F, 16.0F, 14.0F);
    protected static final VoxelShape SHAPE;

    public SinkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATER_LEVEL, 0));
    }

    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(HHModItems.SINK.get());
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return INSIDE;
    }

    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) return ItemInteractionResult.SUCCESS;

        ItemStack heldItem = player.getItemInHand(hand);
        int currentLevel = state.getValue(WATER_LEVEL);
        boolean changed = false;

        if (heldItem.is(Items.POTION)) {
            if (currentLevel < 3) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, Math.min(currentLevel + 1, MAX_WATER)), 3);
                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    player.addItem(new ItemStack(Items.GLASS_BOTTLE));
                }
                changed = true;
            }
        } else if (heldItem.is(Items.WATER_BUCKET)) {
            if (currentLevel < 3) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, Math.min(currentLevel + 3, MAX_WATER)), 3);
                if (!player.getAbilities().instabuild) {
                    level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                changed = true;
            }
        } else if (heldItem.is(Items.GLASS_BOTTLE)) {
            if (currentLevel >= 1) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, currentLevel - 1), 3);
                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                    level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    player.addItem(new ItemStack(Items.POTION));
                }
                changed = true;
            }
        } else if (heldItem.is(Items.BUCKET)) {
            if (currentLevel >= 3) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, currentLevel - 3), 3);
                if (!player.getAbilities().instabuild) {
                    level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                }
                changed = true;
            }
        }

        return changed ? ItemInteractionResult.CONSUME : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected double getContentHeight(BlockState state) {
        return 0.0F;
    }

    protected boolean isEntityInsideContent(BlockState state, BlockPos pos, Entity entity) {
        return entity.getY() < (double)pos.getY() + this.getContentHeight(state) && entity.getBoundingBox().maxY > (double)pos.getY() + (double)0.25F;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATER_LEVEL);
    }

    static {
        SHAPE = Shapes.join(Shapes.block(), INSIDE, BooleanOp.ONLY_FIRST);
    }
}
