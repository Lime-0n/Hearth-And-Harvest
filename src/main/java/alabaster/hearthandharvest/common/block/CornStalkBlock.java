package alabaster.hearthandharvest.common.block;

import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;

public class CornStalkBlock extends Block implements BonemealableBlock {
    public static final EnumProperty<CornSection> SECTION = EnumProperty.create("section", CornSection.class);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 5);
    public static final BooleanProperty TRIM_NORTH = BooleanProperty.create("trim_north");
    public static final BooleanProperty TRIM_EAST  = BooleanProperty.create("trim_east");
    public static final BooleanProperty TRIM_SOUTH = BooleanProperty.create("trim_south");
    public static final BooleanProperty TRIM_WEST  = BooleanProperty.create("trim_west");

    private static final int MAX_AGE = 5;

    private static final int BASE_GROW_CHANCE = 25;

    private static final VoxelShape SHAPE_POST  = Block.box(6, 0, 6, 10, 16, 10);
    private static final VoxelShape SHAPE_NORTH = Block.box(6, 0, 0, 10, 16, 6);
    private static final VoxelShape SHAPE_SOUTH = Block.box(6, 0, 10, 10, 16, 16);
    private static final VoxelShape SHAPE_WEST  = Block.box(0, 0, 6, 6, 16, 10);
    private static final VoxelShape SHAPE_EAST  = Block.box(10, 0, 6, 16, 16, 10);

    private static final VoxelShape[] SHAPE_CACHE = buildShapeCache();

    private static VoxelShape[] buildShapeCache() {
        VoxelShape[] cache = new VoxelShape[16];
        for (int mask = 0; mask < 16; mask++) {
            VoxelShape s = SHAPE_POST;
            if ((mask & 1) != 0) s = Shapes.or(s, SHAPE_NORTH);
            if ((mask & 2) != 0) s = Shapes.or(s, SHAPE_EAST);
            if ((mask & 4) != 0) s = Shapes.or(s, SHAPE_SOUTH);
            if ((mask & 8) != 0) s = Shapes.or(s, SHAPE_WEST);
            cache[mask] = s;
        }
        return cache;
    }

    public CornStalkBlock() {
        super(BlockBehaviour.Properties.of()
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
                return state;
            }
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        CornSection section = state.getValue(SECTION);
        BlockState below = world.getBlockState(pos.below());

        TriState tri = below.canSustainPlant(world, pos.below(), Direction.UP, state);
        if (!tri.isDefault()) return tri.isTrue();

        if (!(hasSufficientLight(world, pos) && super.canSurvive(state, world, pos))) return false;

        return switch (section) {
            case BOTTOM -> (below.is(BlockTags.DIRT)
                    || below.is(Blocks.FARMLAND)
                    || below.is(Blocks.GRASS_BLOCK)
                    || below.is(Blocks.COARSE_DIRT)
                    || below.is(Blocks.PODZOL));
            case MIDDLE -> isSameCornSection(below, CornSection.BOTTOM);
            case TOP -> isSameCornSection(below, CornSection.MIDDLE);
        };
    }

    public static boolean hasSufficientLight(LevelReader level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) >= 8;
    }

    private boolean isSameCornSection(BlockState state, CornSection expected) {
        return state.getBlock() instanceof CornStalkBlock && state.hasProperty(SECTION) && state.getValue(SECTION) == expected;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getMaxLocalRawBrightness(pos) < 9) return;

        CornSection section = state.getValue(SECTION);
        int age = state.getValue(AGE);

        if (section != CornSection.TOP && age >= 3 && !topIsAtLeastStage2(world, pos)) return;

        int chance = computeGrowthChance(world, pos);
        if (random.nextInt(chance) != 0) return;

        switch (section) {
            case BOTTOM -> {
                if (age < MAX_AGE) {
                    int newAge = age + 1;
                    world.setBlock(pos, state.setValue(AGE, newAge), 3);
                    if (newAge == 3) tryPlaceMiddle(world, pos);
                }
            }
            case MIDDLE -> {
                if (age < MAX_AGE) {
                    int newAge = age + 1;
                    world.setBlock(pos, state.setValue(AGE, newAge), 2);
                    if (newAge == 3) tryPlaceTop(world, pos);
                }
            }
            case TOP -> {
                if (age < MAX_AGE) {
                    world.setBlock(pos, state.setValue(AGE, age + 1), 2);
                }
            }
        }
    }

    private int computeGrowthChance(LevelReader world, BlockPos pos) {
        float f = 1.0F;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockState soil = world.getBlockState(pos.offset(dx, -1, dz));
                float contribution = 0.0F;
                if (soil.getBlock() instanceof FarmBlock) {
                    int moisture = soil.hasProperty(FarmBlock.MOISTURE) ? soil.getValue(FarmBlock.MOISTURE) : 0;
                    contribution = (moisture > 0) ? 3.0F : 1.0F;
                }
                if (!(dx == 0 && dz == 0)) contribution /= 4.0F;
                f += contribution;
            }
        }
        int chance = (int) ((float) BASE_GROW_CHANCE / f) + 1;
        return Math.max(chance, 1);
    }

    private boolean topIsAtLeastStage2(LevelReader world, BlockPos pos) {
        BlockPos bottomPos = switch (world.getBlockState(pos).getValue(SECTION)) {
            case BOTTOM -> pos;
            case MIDDLE -> pos.below();
            case TOP -> pos.below(2);
        };
        BlockState top = world.getBlockState(bottomPos.above(2));
        return top.getBlock() instanceof CornStalkBlock && top.getValue(AGE) >= 2;
    }

    private void tryPlaceMiddle(Level world, BlockPos bottomPos) {
        BlockPos midPos = bottomPos.above();
        if (world.getBlockState(midPos).isAir()) {
            BlockState mid = defaultBlockState().setValue(SECTION, CornSection.MIDDLE).setValue(AGE, 0);
            if (mid.canSurvive(world, midPos)) world.setBlock(midPos, mid, 3);
        }
    }

    private void tryPlaceTop(Level world, BlockPos middlePos) {
        BlockPos topPos = middlePos.above();
        if (world.getBlockState(topPos).isAir()) {
            BlockState top = defaultBlockState().setValue(SECTION, CornSection.TOP).setValue(AGE, 0);
            if (top.canSurvive(world, topPos)) world.setBlock(topPos, top, 3);
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, world, pos, newState, isMoving);
        if (world.isClientSide) return;
        if (state.getBlock() == newState.getBlock()) return;

        CornSection section = state.getValue(SECTION);
        if (section == CornSection.TOP || section == CornSection.MIDDLE) {
            BlockPos below = pos.below();
            BlockState belowState = world.getBlockState(below);
            if (belowState.getBlock() instanceof CornStalkBlock && belowState.getValue(AGE) > 2) {
                world.setBlock(below, belowState.setValue(AGE, 2), 2);
            }
        }
        if (section == CornSection.MIDDLE || section == CornSection.TOP) {
            // bottom segment may also need reduction handled above indirectly when top/middle removed
        }
    }

    @Override
    @NotNull
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return SHAPE_CACHE[shapeMask(world, pos)];
    }

    private Direction directionFromHit(BlockPos pos, double hitX, double hitY, double hitZ, Direction faceFallback) {
        double localX = hitX - pos.getX();
        double localZ = hitZ - pos.getZ();

        final double LEFT = 0.30, RIGHT = 0.70, FRONT = 0.30, BACK = 0.70;
        if (localX < LEFT) return Direction.WEST;
        if (localX > RIGHT) return Direction.EAST;
        if (localZ < FRONT) return Direction.NORTH;
        if (localZ > BACK) return Direction.SOUTH;
        if (faceFallback != null && faceFallback.getAxis().isHorizontal()) return faceFallback;
        return null;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Direction face = hitResult.getDirection();
        Direction clickedDir = directionFromHit(pos, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, face);

        if (stack.getItem() == Items.SHEARS && clickedDir != null && state.getValue(AGE) > 2) {
            return handleShears(stack, state, level, pos, player, hand, clickedDir);
        }

        return handleHarvestInteraction(state, level, pos, player);
    }

    private ItemInteractionResult handleShears(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, Direction clickedDir) {
        BooleanProperty selfProp = trimPropertyFor(clickedDir);
        BooleanProperty otherProp = trimPropertyFor(clickedDir.getOpposite());

        if (!state.hasProperty(selfProp)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (level.isClientSide) return ItemInteractionResult.sidedSuccess(true);

        boolean newValue = !state.getValue(selfProp);
        level.setBlock(pos, state.setValue(selfProp, newValue), 3);

        BlockPos neighborPos = pos.relative(clickedDir);
        BlockState neighborState = level.getBlockState(neighborPos);
        if (neighborState.getBlock() instanceof CornStalkBlock && neighborState.hasProperty(otherProp)) {
            level.setBlock(neighborPos, neighborState.setValue(otherProp, newValue), 3);
        }

        level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
        EquipmentSlot slot = (hand == InteractionHand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        stack.hurtAndBreak(1, player, slot);
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private ItemInteractionResult handleHarvestInteraction(BlockState state, Level level, BlockPos pos, Player player) {
        int age = state.getValue(AGE);

        // Only allow harvesting if fully grown
        boolean canHarvest = (age == MAX_AGE);
        if (!canHarvest) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (level.isClientSide) return ItemInteractionResult.sidedSuccess(true);

        // Drop corn
        int count = 2; // fully grown always drops 2
        level.setBlock(pos, state.setValue(AGE, MAX_AGE - 2), 3); // reset to age 3 after harvest

        popResource(level, pos, new ItemStack(HHModItems.CORN.get(), count));
        level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
        super.playerDestroy(level, player, pos, state, blockEntity, stack);
        if (level.isClientSide) return;

        CornSection section = state.getValue(SECTION);
        if (section == CornSection.BOTTOM) {
            BlockPos mid = pos.above(), top = pos.above(2);
            if (level.getBlockState(mid).getBlock() instanceof CornStalkBlock) level.destroyBlock(mid, true);
            if (level.getBlockState(top).getBlock() instanceof CornStalkBlock) level.destroyBlock(top, true);
        } else if (section == CornSection.MIDDLE) {
            BlockPos top = pos.above();
            if (level.getBlockState(top).getBlock() instanceof CornStalkBlock) level.destroyBlock(top, true);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    private double getVoxelHeight(CornSection section, int age) {
        return switch (section) {
            case BOTTOM -> switch (age) {
                case 0 -> 8.0;
                case 1 -> 10.0;
                default -> 16.0; // 2-4
            };
            case MIDDLE -> switch (age) {
                case 0 -> 6.0;
                case 1 -> 13.0;
                default -> 16.0; // 2-4
            };
            case TOP -> switch (age) {
                case 0 -> 7.0;
                case 1 -> 9.0;
                case 2 -> 14.0;
                default -> 15.0; // 3-5
            };
        };
    }

    @Override
    @NotNull
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Immature corn has no collision at all
        if (state.getValue(AGE) < 3) {
            return Shapes.empty();
        }

        if (context instanceof EntityCollisionContext entityCtx) {
            Entity entity = entityCtx.getEntity();

            // Items
            if (entity instanceof ItemEntity) {
                return Shapes.empty();
            }

            // Players
            if (entity instanceof Player player) {
                double playerFeetY = player.getY();
                double playerEyeY = player.getEyeY();
                double blockTopY = pos.getY() + 1.0;

                if (playerFeetY > blockTopY - 0.4 || playerEyeY > blockTopY + 0.2) {
                    return Shapes.empty();
                }

                if (player.getDeltaMovement().y < -0.2) {
                    if (playerFeetY > pos.getY() + 0.3) {
                        return Shapes.empty();
                    }
                }

                double height = getVoxelHeight(state.getValue(SECTION), state.getValue(AGE));
                VoxelShape stalk = Block.box(6.0, 0.0, 6.0, 10.0, height, 10.0);

                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    if (canConnectTo(level, pos, dir)) {
                        stalk = Shapes.or(stalk, connectionShape(dir, height));
                    }
                }
                return stalk;
            }

            // Other mobs
            if (entity instanceof Mob) {
                double height = getVoxelHeight(state.getValue(SECTION), state.getValue(AGE));
                VoxelShape stalk = Block.box(6.0, 0.0, 6.0, 10.0, height, 10.0);

                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    if (canConnectTo(level, pos, dir)) {
                        stalk = Shapes.or(stalk, connectionShape(dir, height));
                    }
                }
                return stalk;
            }
        }

        // Default collision for projectiles or others
        return Shapes.empty();
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    private VoxelShape connectionShape(Direction dir, double height) {
        return switch (dir) {
            case NORTH -> Block.box(6.0, 0.0, 0.0, 10.0, height, 6.0);
            case SOUTH -> Block.box(6.0, 0.0, 10.0, 10.0, height, 16.0);
            case WEST  -> Block.box(0.0, 0.0, 6.0, 6.0, height, 10.0);
            case EAST  -> Block.box(10.0, 0.0, 6.0, 16.0, height, 10.0);
            default   -> Shapes.empty();
        };
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        double height = getVoxelHeight(state.getValue(SECTION), state.getValue(AGE));

        VoxelShape shape = Block.box(6.0, 0.0, 6.0, 10.0, height, 10.0);

        // Fence-style connections only if age >= 3
        if (state.getValue(AGE) >= 3) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                if (canConnectTo(world, pos, dir)) {
                    shape = Shapes.or(shape, connectionShape(dir, height));
                }
            }
        }

        return shape;
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
        BlockState neighbor = level.getBlockState(pos.relative(dir));
        if (!(neighbor.getBlock() instanceof CornStalkBlock) || !neighbor.hasProperty(AGE) || neighbor.getValue(AGE) < 3) return false;

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

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        CornSection section = state.getValue(SECTION);
        return switch (section) {
            case BOTTOM -> state.getValue(AGE) < MAX_AGE;
            case MIDDLE -> state.getValue(AGE) < MAX_AGE;
            case TOP -> state.getValue(AGE) < MAX_AGE;
        };
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos bottomPos = switch (state.getValue(SECTION)) {
            case BOTTOM -> pos;
            case MIDDLE -> pos.below();
            case TOP -> pos.below(2);
        };

        BlockState bottom = world.getBlockState(bottomPos);
        if (!(bottom.getBlock() instanceof CornStalkBlock)) return;

        BlockPos middlePos = bottomPos.above();
        BlockPos topPos = bottomPos.above(2);
        BlockState middle = world.getBlockState(middlePos);
        BlockState top = world.getBlockState(topPos);

        boolean grew = false;

        // Determine if top is at least stage 2
        boolean topReady = top.getBlock() instanceof CornStalkBlock && top.getValue(AGE) >= 3;

        // Grow bottom
        int bottomAge = bottom.getValue(AGE);
        if (bottomAge < MAX_AGE) {
            // Only allow progression past stage 3 if top is ready
            if (bottomAge < 3 || topReady) {
                int newAge = bottomAge + 1;
                world.setBlock(bottomPos, bottom.setValue(AGE, newAge), 2);
                BoneMealItem.addGrowthParticles(world, bottomPos, 0);
                grew = true;
                if (newAge == 3) tryPlaceMiddle(world, bottomPos);
            }
        }

        // Grow middle
        if (middle.getBlock() instanceof CornStalkBlock) {
            int middleAge = middle.getValue(AGE);
            if (middleAge < MAX_AGE) {
                if (middleAge < 3 || topReady) {
                    int newAge = middleAge + 1;
                    world.setBlock(middlePos, middle.setValue(AGE, newAge), 2);
                    BoneMealItem.addGrowthParticles(world, middlePos, 0);
                    grew = true;
                    if (newAge == 3) tryPlaceTop(world, middlePos);
                }
            }
        }

        // Grow top
        if (top.getBlock() instanceof CornStalkBlock) {
            int topAge = top.getValue(AGE);
            if (topAge < MAX_AGE) {
                int newAge = topAge + 1;
                world.setBlock(topPos, top.setValue(AGE, newAge), 2);
                BoneMealItem.addGrowthParticles(world, topPos, 0);
                grew = true;
            }
        }

        if (!grew) BoneMealItem.addGrowthParticles(world, pos, 0);
    }

    public enum CornSection implements net.minecraft.util.StringRepresentable {
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top");

        private final String name;
        CornSection(String name) { this.name = name; }
        @Override public String getSerializedName() { return this.name; }
        @Override public String toString() { return this.name; }
    }
}
