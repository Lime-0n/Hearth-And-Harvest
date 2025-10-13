package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.tags.BlockTags;

import javax.annotation.Nullable;

public class CornStalkBlock extends Block implements BonemealableBlock {
    public static final EnumProperty<CornSection> SECTION = EnumProperty.create("section", CornSection.class);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 5);

    private static final int BOTTOM_MAX_AGE = 5;
    private static final int MIDDLE_MAX_AGE = 5;
    private static final int TOP_MAX_AGE = 4;

    private static final int GROWTH_CHANCE = 6;

    private static final VoxelShape SHAPE_POST = Block.box(5, 0, 5, 11, 16, 11);
    private static final VoxelShape SHAPE_NORTH = Block.box(5, 0, 0, 11, 16, 5);
    private static final VoxelShape SHAPE_SOUTH = Block.box(5, 0, 11, 11, 16, 16);
    private static final VoxelShape SHAPE_WEST = Block.box(0, 0, 5, 5, 16, 11);
    private static final VoxelShape SHAPE_EAST = Block.box(11, 0, 5, 16, 16, 11);

    private static final VoxelShape[] SHAPE_CACHE = new VoxelShape[16];

    static {
        for (int mask = 0; mask < 16; mask++) {
            VoxelShape s = SHAPE_POST;
            if ((mask & 1) != 0) s = Shapes.or(s, SHAPE_NORTH);
            if ((mask & 2) != 0) s = Shapes.or(s, SHAPE_EAST);
            if ((mask & 4) != 0) s = Shapes.or(s, SHAPE_SOUTH);
            if ((mask & 8) != 0) s = Shapes.or(s, SHAPE_WEST);
            SHAPE_CACHE[mask] = s;
        }
    }

    public CornStalkBlock() {
        super(BlockBehaviour.Properties.of()
                .noCollission()
                .strength(0.5f)
                .sound(SoundType.CROP)
                .randomTicks()
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(SECTION, CornSection.BOTTOM).setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SECTION, AGE);
    }

    protected ItemLike getBaseSeedId() {
        return HHModItems.CORN_KERNELS.get();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        CornSection section = state.getValue(SECTION);
        if (section == CornSection.BOTTOM) {
            BlockState below = world.getBlockState(pos.below());
            return below.is(BlockTags.DIRT) || below.is(Blocks.FARMLAND) || below.is(Blocks.GRASS_BLOCK) || below.is(Blocks.COARSE_DIRT) || below.is(Blocks.PODZOL);
        } else if (section == CornSection.MIDDLE) {
            BlockState below = world.getBlockState(pos.below());
            return isSameCornSection(below, CornSection.BOTTOM);
        } else {
            BlockState below = world.getBlockState(pos.below());
            return isSameCornSection(below, CornSection.MIDDLE);
        }
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock() instanceof FarmBlock;
    }

    private boolean isSameCornSection(BlockState state, CornSection expected) {
        if (state.getBlock() instanceof CornStalkBlock) {
            if (state.hasProperty(SECTION)) {
                return state.getValue(SECTION) == expected;
            }
        }
        return false;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean moved) {
        super.neighborChanged(state, world, pos, neighborBlock, neighborPos, moved);
        if (!this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

        if (random.nextInt(GROWTH_CHANCE) != 0) return;

        CornSection section = state.getValue(SECTION);
        int age = state.getValue(AGE);

        if (section != CornSection.TOP && age >= 3 && !topIsAtLeastStage2(world, pos)) return;

        if (section == CornSection.BOTTOM) {
            if (age < BOTTOM_MAX_AGE) {
                int newAge = age + 1;
                world.setBlock(pos, state.setValue(AGE, newAge), 3);
                if (newAge == 3) {
                    tryPlaceMiddle(world, pos);
                }
            }
            return;
        }

        if (section == CornSection.MIDDLE) {
            if (age < MIDDLE_MAX_AGE) {
                int newAge = age + 1;
                world.setBlock(pos, state.setValue(AGE, newAge), 2);
                if (newAge == 3) {
                    tryPlaceTop(world, pos);
                }
            }
            return;
        }

        if (section == CornSection.TOP) {
            if (age < TOP_MAX_AGE) {
                int newAge = age + 1;
                world.setBlock(pos, state.setValue(AGE, newAge), 2);
            }
        }
    }

    private boolean topIsAtLeastStage2(LevelReader world, BlockPos pos) {
        BlockPos bottomPos = pos;
        BlockState s = world.getBlockState(pos);
        if (s.getValue(SECTION) == CornSection.MIDDLE) bottomPos = bottomPos.below();
        if (s.getValue(SECTION) == CornSection.TOP) bottomPos = bottomPos.below(2);
        BlockState top = world.getBlockState(bottomPos.above(2));
        return top.getBlock() instanceof CornStalkBlock && top.getValue(AGE) >= 2;
    }

    private void tryPlaceMiddle(Level world, BlockPos bottomPos) {
        BlockPos midPos = bottomPos.above();
        BlockState midState = world.getBlockState(midPos);
        if (midState.isAir()) {
            BlockState newMiddle = this.defaultBlockState().setValue(SECTION, CornSection.MIDDLE).setValue(AGE, 0);
            if (newMiddle.canSurvive(world, midPos)) {
                world.setBlock(midPos, newMiddle, 3);
            }
        }
    }

    private void tryPlaceTop(Level world, BlockPos middlePos) {
        BlockPos topPos = middlePos.above();
        BlockState topState = world.getBlockState(topPos);
        if (topState.isAir()) {
            BlockState newTop = this.defaultBlockState().setValue(SECTION, CornSection.TOP).setValue(AGE, 0);
            if (newTop.canSurvive(world, topPos)) {
                world.setBlock(topPos, newTop, 3);
            }
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        CornSection section = state.getValue(SECTION);
        int age = state.getValue(AGE);

        if (section == CornSection.BOTTOM) {
            if (age >= 4) {
                if (!level.isClientSide) {
                    int count = (age == 5) ? 2 : 1;
                    popResource(level, pos, new ItemStack(HHModItems.CORN.get(), count));
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(pos, state.setValue(AGE, 3), 2);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        } else if (section == CornSection.MIDDLE) {
            if (age >= 4) {
                if (!level.isClientSide) {
                    int count = (age == 5) ? 2 : 1;
                    popResource(level, pos, new ItemStack(HHModItems.CORN.get(), count));
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(pos, state.setValue(AGE, 3), 2);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        } else {
            if (age >= 3) {
                if (!level.isClientSide) {
                    int count = (age == 4) ? 2 : 1;
                    popResource(level, pos, new ItemStack(HHModItems.CORN.get(), count));
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(pos, state.setValue(AGE, 2), 2);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.playerDestroy(level, player, pos, state, blockEntity, stack);

        if (level.isClientSide) return;

        CornSection section = state.getValue(SECTION);
        if (section == CornSection.BOTTOM) {
            BlockPos mid = pos.above();
            BlockPos top = pos.above(2);
            if (level.getBlockState(mid).getBlock() instanceof CornStalkBlock) {
                level.destroyBlock(mid, true);
            }
            if (level.getBlockState(top).getBlock() instanceof CornStalkBlock) {
                level.destroyBlock(top, true);
            }
        } else if (section == CornSection.MIDDLE) {
            BlockPos top = pos.above();
            if (level.getBlockState(top).getBlock() instanceof CornStalkBlock) {
                level.destroyBlock(top, true);
            }
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int mask = shapeMask(world, pos);
        return SHAPE_CACHE[mask];
    }

    private boolean isMatureBottomAt(LevelReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof CornStalkBlock) {
            if (state.hasProperty(SECTION) && state.hasProperty(AGE)) {
                return state.getValue(SECTION) == CornSection.BOTTOM && state.getValue(AGE) >= 3;
            }
        }
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) < 3)
            return Shapes.empty();
        int mask = shapeMask(world, pos);
        return SHAPE_CACHE[mask];
    }

    private int shapeMask(BlockGetter world, BlockPos pos) {
        int mask = 0;
        if (canConnectTo(world.getBlockState(pos.north()))) mask |= 1;
        if (canConnectTo(world.getBlockState(pos.east())))  mask |= 2;
        if (canConnectTo(world.getBlockState(pos.south()))) mask |= 4;
        if (canConnectTo(world.getBlockState(pos.west())))  mask |= 8;
        return mask;
    }

    private boolean canConnectTo(BlockState state) {
        return state.getBlock() instanceof CornStalkBlock && state.getValue(AGE) >= 3;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        CornSection section = state.getValue(SECTION);
        int age = state.getValue(AGE);
        if (section == CornSection.BOTTOM) {
            return age < BOTTOM_MAX_AGE;
        } else if (section == CornSection.MIDDLE) {
            return age < MIDDLE_MAX_AGE;
        } else {
            return age < TOP_MAX_AGE;
        }
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        CornSection section = state.getValue(SECTION);
        int age = state.getValue(AGE);

        if (section != CornSection.TOP && age >= 3 && !topIsAtLeastStage2(world, pos)) return;

        if (section == CornSection.BOTTOM && age < BOTTOM_MAX_AGE) {
            int newAge = age + 1;
            world.setBlock(pos, state.setValue(AGE, newAge), 2);
            if (newAge == 3) tryPlaceMiddle(world, pos);
        } else if (section == CornSection.MIDDLE && age < MIDDLE_MAX_AGE) {
            int newAge = age + 1;
            world.setBlock(pos, state.setValue(AGE, newAge), 2);
            if (newAge == 3) tryPlaceTop(world, pos);
        } else if (section == CornSection.TOP && age < TOP_MAX_AGE) {
            int newAge = age + 1;
            world.setBlock(pos, state.setValue(AGE, newAge), 2);
        }
    }

    public enum CornSection implements net.minecraft.util.StringRepresentable {
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top");

        private final String name;
        CornSection(String name) { this.name = name; }
        @Override
        public String getSerializedName() { return this.name; }
        @Override
        public String toString() { return this.name; }
    }
}
