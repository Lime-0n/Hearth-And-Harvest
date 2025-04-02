package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class TreeTapperBlock extends Block {

        public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
        public static final IntegerProperty SAP = IntegerProperty.create("sap", 0, 4);
        public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

        public TreeTapperBlock(Properties properties) {
                super(properties);
                this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SAP, 0).setValue(WATERLOGGED, false));
        }

        @Override
        public RenderShape getRenderShape(BlockState pState) {
                return RenderShape.MODEL;
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                Direction direction = state.getValue(FACING);
                if (direction == Direction.NORTH) {
                        return Block.box(5.0D, 2.0D, 0.0D, 11.0D, 13.0D, 6.0D);
                }
                if (direction == Direction.EAST) {
                        return Block.box(10.0D, 2.0D, 5.0D, 16.0D, 13.0D, 11.0D);
                }
                if (direction == Direction.SOUTH) {
                        return Block.box(5.0D, 2.0D, 10.0D, 11.0D, 13.0D, 16.0D);
                }
                if (direction == Direction.WEST) {
                        return Block.box(0.0D, 2.0D, 5.0D, 6.0D, 13.0D, 11.0D);
                }
                return null;
        }

        private boolean canAttachTo(BlockGetter level, BlockPos pos, Direction facing) {
                return level.getBlockState(pos).isFaceSturdy(level, pos, facing);
        }

        @Override
        @SuppressWarnings("deprecation")
        public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
                Direction direction = state.getValue(FACING);
                return this.canAttachTo(level, pos.relative(direction), direction);
        }

        @Nullable
        public BlockState getStateForPlacement(BlockPlaceContext context) {
                BlockState blockState = this.defaultBlockState();
                LevelReader levelReader = context.getLevel();
                BlockPos blockPos = context.getClickedPos();
                FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
                for (Direction direction : context.getNearestLookingDirections()) {
                        if (direction.getAxis().isHorizontal()) {
                                blockState = blockState.setValue(FACING, direction);
                                if (blockState.canSurvive(levelReader, blockPos)) {
                                        return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
                                }
                        }
                }
                return null;
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                builder.add(FACING, SAP, WATERLOGGED);
                super.createBlockStateDefinition(builder);
        }

        public int getMaxSap() {
                return 4;
        }

        @Override
        public boolean isRandomlyTicking(BlockState state) {
                return true;
        }

        @Override
        @SuppressWarnings("deprecation")
        public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
                if (level.isClientSide) return;

                Direction direction = state.getValue(FACING);

                float chance = 0.0F;

                // Check only the four directly adjacent blocks (north, south, east, west)
                if (direction.getAxis().isHorizontal()) {
                        BlockState neighborState = level.getBlockState(pos.relative(direction));
                        if (neighborState.is(HHModTags.TAPPABLE)) {
                                chance += 0.02F;
                        }
                }

                if (level.getRandom().nextFloat() <= chance) {
                        if (state.getValue(SAP) != this.getMaxSap()) {
                                level.setBlock(pos, state.setValue(SAP, state.getValue(SAP) + 1), 3);
                        }
                }
        }

        public InteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
                int sapLevel = state.getValue(SAP);

                if (sapLevel == 4 && player.getItemInHand(hand).is(Items.BUCKET)) {
                        if (!player.getAbilities().instabuild) { // Check if player is in creative mode
                                player.getItemInHand(hand).shrink(1); // Remove the empty bucket
                        }

                        ItemStack sapBucket = new ItemStack(HHModItems.SAP_BUCKET.get());
                        player.addItem(sapBucket); // Give the player the sap bucket

                        level.setBlock(pos, state.setValue(SAP, 0), 2);

                        return InteractionResult.SUCCESS;
                }

                return InteractionResult.PASS;
        }

        @Override
        public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
                super.onPlace(state, world, pos, oldState, isMoving);

                // Ensure that ticking begins immediately
                if (!world.isClientSide) {
                        world.scheduleTick(pos, this, 100);
                }
        }
}
