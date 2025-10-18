package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;

public class CornStalkBlock extends Block implements BonemealableBlock {
    public static final EnumProperty<CornSection> SECTION = EnumProperty.create("section", CornSection.class);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 5);
    public static final BooleanProperty TRIM_NORTH = BooleanProperty.create("trim_north");
    public static final BooleanProperty TRIM_EAST  = BooleanProperty.create("trim_east");
    public static final BooleanProperty TRIM_SOUTH = BooleanProperty.create("trim_south");
    public static final BooleanProperty TRIM_WEST  = BooleanProperty.create("trim_west");

    private static final int BOTTOM_MAX_AGE = 5;
    private static final int MIDDLE_MAX_AGE = 5;
    private static final int TOP_MAX_AGE = 4;

    private static final int BASE_GROW_CHANCE = 25;

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
                .forceSolidOff()
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SECTION, CornSection.BOTTOM)
                .setValue(AGE, 0)
                .setValue(TRIM_NORTH, false)
                .setValue(TRIM_EAST, false)
                .setValue(TRIM_SOUTH, false)
                .setValue(TRIM_WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SECTION, AGE, TRIM_NORTH, TRIM_EAST, TRIM_SOUTH, TRIM_WEST);
    }

    protected ItemLike getBaseSeedId() {
        return HHModItems.CORN_KERNELS.get();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!this.canSurvive(state, world, pos)) {
            if (world instanceof Level lvl && !lvl.isClientSide()) {
                lvl.destroyBlock(pos, true);
            } else {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        CornSection section = state.getValue(SECTION);
        BlockPos below = pos.below();
        BlockState soil = world.getBlockState(below);

        TriState soilDecision = soil.canSustainPlant(world, below, Direction.UP, state);
        if (!soilDecision.isDefault()) {
            return soilDecision.isTrue();
        }

        if (!(hasSufficientLight(world, pos) && super.canSurvive(state, world, pos))) {
            return false;
        }

        if (section == CornSection.BOTTOM) {
            return soil.is(BlockTags.DIRT)
                    || soil.is(Blocks.FARMLAND)
                    || soil.is(Blocks.GRASS_BLOCK)
                    || soil.is(Blocks.COARSE_DIRT)
                    || soil.is(Blocks.PODZOL);
        } else if (section == CornSection.MIDDLE) {
            return isSameCornSection(soil, CornSection.BOTTOM);
        } else {
            return isSameCornSection(soil, CornSection.MIDDLE);
        }
    }

    public static boolean hasSufficientLight(LevelReader level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) >= 8;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock;
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
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

        if (world.getMaxLocalRawBrightness(pos) < 9) return;

        CornSection section = state.getValue(SECTION);
        int age = state.getValue(AGE);

        if (section != CornSection.TOP && age >= 3 && !topIsAtLeastStage2(world, pos)) return;

        int chance = computeGrowthChance(world, pos);
        if (random.nextInt(chance) != 0) return;

        if (section == CornSection.BOTTOM) {
            if (age < BOTTOM_MAX_AGE) {
                int newAge = age + 1;
                world.setBlock(pos, state.setValue(AGE, newAge), 3);
                if (newAge == 3) tryPlaceMiddle(world, pos);
            }
            return;
        }

        if (section == CornSection.MIDDLE) {
            if (age < MIDDLE_MAX_AGE) {
                int newAge = age + 1;
                world.setBlock(pos, state.setValue(AGE, newAge), 2);
                if (newAge == 3) tryPlaceTop(world, pos);
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

    private int computeGrowthChance(LevelReader world, BlockPos pos) {
        float f = 1.0F;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos soilPos = pos.offset(dx, -1, dz);
                BlockState soil = world.getBlockState(soilPos);
                float contribution = 0.0F;
                if (soil.getBlock() instanceof FarmBlock) {
                    try {
                        Integer moisture = soil.hasProperty(FarmBlock.MOISTURE) ? soil.getValue(FarmBlock.MOISTURE) : 0;
                        contribution = (moisture > 0) ? 3.0F : 1.0F;
                    } catch (Exception e) {
                        contribution = 1.0F;
                    }
                }
                if (dx != 0 || dz != 0) {
                    contribution /= 4.0F;
                }
                f += contribution;
            }
        }

        int chance = (int)((float) BASE_GROW_CHANCE / f) + 1;
        if (chance < 1) chance = 1;
        return chance;
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
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, world, pos, newState, isMoving);

        if (world.isClientSide) return;

        if (state.getBlock() != newState.getBlock()) {
            CornSection section = state.getValue(SECTION);

            if (section == CornSection.TOP) {
                BlockPos below = pos.below();
                BlockState belowState = world.getBlockState(below);
                if (belowState.getBlock() instanceof CornStalkBlock && belowState.getValue(SECTION) == CornSection.MIDDLE) {
                    int currentAge = belowState.getValue(AGE);
                    if (currentAge > 2) {
                        world.setBlock(below, belowState.setValue(AGE, 2), 2);
                    }
                }
            }

            if (section == CornSection.MIDDLE) {
                BlockPos below = pos.below();
                BlockState belowState = world.getBlockState(below);
                if (belowState.getBlock() instanceof CornStalkBlock && belowState.getValue(SECTION) == CornSection.BOTTOM) {
                    int currentAge = belowState.getValue(AGE);
                    if (currentAge > 2) {
                        world.setBlock(below, belowState.setValue(AGE, 2), 2);
                    }
                }
            }
        }
    }

    @Override
    @NotNull
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        int mask = shapeMask(world, pos);
        return SHAPE_CACHE[mask];
    }

    private Direction directionFromHit(BlockPos pos, double hitX, double hitY, double hitZ, Direction faceFallback) {
        double localX = hitX - pos.getX();
        double localZ = hitZ - pos.getZ();

        final double LEFT_THRESHOLD = 0.30;
        final double RIGHT_THRESHOLD = 0.70;
        final double FRONT_THRESHOLD = 0.30;
        final double BACK_THRESHOLD = 0.70;

        if (localX < LEFT_THRESHOLD) return Direction.WEST;
        if (localX > RIGHT_THRESHOLD) return Direction.EAST;
        if (localZ < FRONT_THRESHOLD) return Direction.NORTH;
        if (localZ > BACK_THRESHOLD) return Direction.SOUTH;

        if (faceFallback != null && faceFallback.getAxis().isHorizontal()) {
            return faceFallback;
        }

        return null;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Direction face = hitResult.getDirection();
        Direction clickedDir = directionFromHit(pos, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, face);

        if (stack.getItem() == Items.SHEARS && clickedDir != null) {
            BooleanProperty selfProp = trimPropertyFor(clickedDir);
            BooleanProperty otherProp = trimPropertyFor(clickedDir.getOpposite());

            if (!state.hasProperty(selfProp)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            if (!level.isClientSide) {
                boolean newValue = !state.getValue(selfProp);
                BlockState newState = state.setValue(selfProp, newValue);
                level.setBlock(pos, newState, 3);

                BlockPos neighborPos = pos.relative(clickedDir);
                BlockState neighborState = level.getBlockState(neighborPos);
                if (neighborState.getBlock() instanceof CornStalkBlock && neighborState.hasProperty(otherProp)) {
                    BlockState newNeighbor = neighborState.setValue(otherProp, newValue);
                    level.setBlock(neighborPos, newNeighbor, 3);
                }

                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);

                EquipmentSlot slot = (hand == InteractionHand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                stack.hurtAndBreak(1, player, slot);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        CornSection section = state.getValue(SECTION);
        int age = state.getValue(AGE);

        if (section == CornSection.BOTTOM) {
            if (age >= 4) {
                if (!level.isClientSide) {
                    int count = (age == 5) ? 2 : 1;
                    popResource(level, pos, new ItemStack(HHModItems.CORN.get(), count));
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(pos, state.setValue(AGE, 3), 3);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        } else if (section == CornSection.MIDDLE) {
            if (age >= 4) {
                if (!level.isClientSide) {
                    int count = (age == 5) ? 2 : 1;
                    popResource(level, pos, new ItemStack(HHModItems.CORN.get(), count));
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(pos, state.setValue(AGE, 3), 3);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        } else { // TOP
            if (age >= 3) {
                if (!level.isClientSide) {
                    int count = (age == 4) ? 2 : 1;
                    popResource(level, pos, new ItemStack(HHModItems.CORN.get(), count));
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(pos, state.setValue(AGE, 2), 3);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
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

    @Override
    @NotNull
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        CornSection section = state.getValue(SECTION);
        if (section == CornSection.BOTTOM && state.getValue(AGE) < 3) {
            return Shapes.empty();
        }

        int mask = shapeMask(level, pos);
        VoxelShape sideShape = SHAPE_CACHE[mask];

        VoxelShape topBottomSlice = Shapes.or(
                Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D),   // bottom slice
                Block.box(0.0D, 14.5D, 0.0D, 16.0D, 16.0D, 16.0D)  // top slice
        );
        VoxelShape sideOnly = Shapes.join(sideShape, topBottomSlice, BooleanOp.ONLY_FIRST);

        if (context instanceof EntityCollisionContext entityCtx && entityCtx.getEntity() != null) {
            Entity entity = entityCtx.getEntity();

            if (entity.getY() > pos.getY() + 0.4D) { // tweak threshold feel here (0.3 - 0.5 ideal)
                return Shapes.empty();
            }

            VoxelShape bottomCollisionCheck = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
            if (entityCtx.isAbove(bottomCollisionCheck, pos, false)) {
                return Shapes.empty();
            }

            return sideOnly;
        }

        return sideOnly;
    }


    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    private int shapeMask(BlockGetter level, BlockPos pos) {
        int mask = 0;
        if (canConnectTo(level, pos, Direction.NORTH)) mask |= 1;
        if (canConnectTo(level, pos, Direction.EAST))  mask |= 2;
        if (canConnectTo(level, pos, Direction.SOUTH)) mask |= 4;
        if (canConnectTo(level, pos, Direction.WEST))  mask |= 8;
        return mask;
    }

    private boolean canConnectTo(BlockGetter level, BlockPos pos, Direction dir) {
        BlockPos neighborPos = pos.relative(dir);
        BlockState neighbor = level.getBlockState(neighborPos);
        if (!(neighbor.getBlock() instanceof CornStalkBlock)) return false;
        if (!neighbor.hasProperty(AGE) || neighbor.getValue(AGE) < 3) return false;

        BlockState self = level.getBlockState(pos);
        BooleanProperty selfProp = trimPropertyFor(dir);
        BooleanProperty otherProp = trimPropertyFor(dir.getOpposite());

        if (!self.hasProperty(selfProp) || !neighbor.hasProperty(otherProp)) return false;

        return !self.getValue(selfProp) && !neighbor.getValue(otherProp);
    }

    private static BooleanProperty trimPropertyFor(Direction dir) {
        return switch (dir) {
            case NORTH -> TRIM_NORTH;
            case EAST  -> TRIM_EAST;
            case SOUTH -> TRIM_SOUTH;
            case WEST  -> TRIM_WEST;
            default    -> TRIM_NORTH;
        };
    }

    /* ----------------------
       Bonemeal
       ---------------------- */

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
