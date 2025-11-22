package alabaster.hearthandharvest.common.worldgen.structure.corn_maze;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.CornStalkBlock;
import alabaster.hearthandharvest.common.block.ScarecrowBlock;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.storage.loot.LootTable;

public class CornMazeStructurePiece extends StructurePiece {

    private final int width;
    private final int height;

    public CornMazeStructurePiece(BlockPos origin, int width, int height) {
        super(HHModStructurePieces.CORN_MAZE_PIECE.get(), 0,
                BoundingBox.fromCorners(origin, origin.offset(width, 10, height)));
        this.width = width;
        this.height = height;
    }

    public CornMazeStructurePiece(StructurePieceSerializationContext ctx, CompoundTag tag) {
        super(HHModStructurePieces.CORN_MAZE_PIECE.get(), tag);
        this.width = tag.getInt("Width");
        this.height = tag.getInt("Height");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tag) {
        tag.putInt("Width", width);
        tag.putInt("Height", height);
    }

    @Override
    public void postProcess(
            WorldGenLevel level,
            StructureManager structureManager,
            ChunkGenerator generator,
            RandomSource random,
            BoundingBox box,
            ChunkPos chunkPos,
            BlockPos pivot
    ) {
        BlockPos origin = new BlockPos(
                this.boundingBox.minX(),
                this.boundingBox.minY(),
                this.boundingBox.minZ()
        );

        // Generate maze layout
        boolean[][] maze = MazeGenerator.generate(width, height, random);

        // Build maze
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                BlockPos pos = origin.offset(x, 0, z);
                boolean isWall = maze[x][z];

                if (isWall) {
                    placeFullCornStalk(level, pos);
                } else {
                    for (int y = 0; y < 3; y++) {
                        level.setBlock(pos.above(y), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
                var floor = random.nextFloat() < 0.5f
                        ? Blocks.COARSE_DIRT.defaultBlockState()
                        : Blocks.GRASS_BLOCK.defaultBlockState();

                level.setBlock(pos.below(), floor, 3);
            }
        }

        // Entrance & Exit
        BlockPos entrance = origin.offset(0, 0, height / 2);
        BlockPos exit = origin.offset(width - 1, 0, height / 2);

        carveOpening(level, entrance);
        carveOpening(level, exit);

        // Set maze path openings
        maze[0][height / 2] = false;
        maze[width - 1][height / 2] = false;

        // Add outer corn irregularities
        int reserveRadius = 1;
        for (int x = -1; x <= width; x++) {
            for (int z = -1; z <= height; z++) {

                boolean edge = x == -1 || z == -1 || x == width || z == height;
                if (!edge) continue;

                BlockPos p = origin.offset(x, 0, z);

                // Skip reserved area around entrance/exit
                if (Math.abs(p.getX() - entrance.getX()) <= reserveRadius &&
                        Math.abs(p.getZ() - entrance.getZ()) <= reserveRadius)
                    continue;

                if (Math.abs(p.getX() - exit.getX()) <= reserveRadius &&
                        Math.abs(p.getZ() - exit.getZ()) <= reserveRadius)
                    continue;

                if (random.nextFloat() < 0.5f) {
                    if (x >= 0 && x < width && z >= 0 && z < height) {
                        boolean nearWall = maze[x][z];
                        if (!nearWall) continue;
                    }

                    placeFullCornStalk(level, p);
                }

                var floor = random.nextFloat() < 0.5f
                        ? Blocks.COARSE_DIRT.defaultBlockState()
                        : Blocks.GRASS_BLOCK.defaultBlockState();

                level.setBlock(p.below(), floor, 3);
            }
        }

        // Dead ends
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!maze[x][z]) {

                    if ((x == 0 && z == height / 2) || (x == width - 1 && z == height / 2))
                        continue;

                    int open = 0;
                    Direction face = null;

                    if (x > 0 && !maze[x - 1][z]) { open++; face = Direction.WEST; }
                    if (x < width - 1 && !maze[x + 1][z]) { open++; face = Direction.EAST; }
                    if (z > 0 && !maze[x][z - 1]) { open++; face = Direction.NORTH; }
                    if (z < height - 1 && !maze[x][z + 1]) { open++; face = Direction.SOUTH; }

                    if (open == 1) {
                        BlockPos placePos = origin.offset(x, 0, z);

                        float roll = random.nextFloat();

                        if (roll < 0.3f) {
                            level.setBlock(placePos,
                                    Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, face),
                                    3);
                            var be = level.getBlockEntity(placePos);
                            if (be instanceof ChestBlockEntity chest) {
                                ResourceLocation lootId =
                                        ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "chests/corn_maze");
                                ResourceKey<LootTable> lootKey =
                                        ResourceKey.create(Registries.LOOT_TABLE, lootId);
                                chest.setLootTable(lootKey, random.nextLong());
                            }
                        } else if (roll < 0.6f) {
                            level.setBlock(placePos,
                                    HHModBlocks.SCARECROW.get().defaultBlockState()
                                            .setValue(ScarecrowBlock.FACING, face),
                                    3);
                        } else if (roll < 0.9f) {
                            level.setBlock(placePos, Blocks.HAY_BLOCK.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }

        // Entrance/Exit Log + Lantern Structures
        buildEntryStructure(level, entrance);
        buildEntryStructure(level, exit);
    }

    private void placeFullCornStalk(WorldGenLevel level, BlockPos pos) {
        level.setBlock(pos,
                HHModBlocks.CORN_STALK.get().defaultBlockState()
                        .setValue(CornStalkBlock.SECTION, CornStalkBlock.CornSection.BOTTOM)
                        .setValue(CornStalkBlock.CROW_PROOF, true)
                        .setValue(CornStalkBlock.AGE, 5), 3);

        level.setBlock(pos.above(),
                HHModBlocks.CORN_STALK.get().defaultBlockState()
                        .setValue(CornStalkBlock.SECTION, CornStalkBlock.CornSection.MIDDLE)
                        .setValue(CornStalkBlock.CROW_PROOF, true)
                        .setValue(CornStalkBlock.AGE, 5), 3);

        level.setBlock(pos.above(2),
                HHModBlocks.CORN_STALK.get().defaultBlockState()
                        .setValue(CornStalkBlock.SECTION, CornStalkBlock.CornSection.TOP)
                        .setValue(CornStalkBlock.CROW_PROOF, true)
                        .setValue(CornStalkBlock.AGE, 5), 3);
    }

    private void carveOpening(WorldGenLevel level, BlockPos pos) {
        for (int y = 0; y < 3; y++) {
            level.setBlock(pos.above(y), Blocks.AIR.defaultBlockState(), 3);
        }
        level.setBlock(pos.below(), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
    }

    private void buildEntryStructure(WorldGenLevel level, BlockPos center) {

        // Determine direction of maze opening
        boolean west = center.getX() % width == 0;

        Direction outward = west ? Direction.WEST : Direction.EAST;

        // Two fence posts 1 block to left and right of center
        BlockPos left = center.relative(Direction.NORTH);
        BlockPos right = center.relative(Direction.SOUTH);

        // Raise posts 4 high
        for (int y = 0; y <= 4; y++) {
            level.setBlock(left.above(y), Blocks.OAK_FENCE.defaultBlockState(), 3);
            level.setBlock(right.above(y), Blocks.OAK_FENCE.defaultBlockState(), 3);
        }

        // Horizontal oak log connecting posts at height 4
        BlockPos logStart = left.above(4);
        BlockPos logEnd = right.above(4);

        level.setBlock(logStart, Blocks.OAK_LOG.defaultBlockState().setValue(net.minecraft.world.level.block.RotatedPillarBlock.AXIS, net.minecraft.core.Direction.Axis.Z), 3);
        level.setBlock(center.above(4), Blocks.OAK_LOG.defaultBlockState().setValue(net.minecraft.world.level.block.RotatedPillarBlock.AXIS, net.minecraft.core.Direction.Axis.Z), 3);
        level.setBlock(logEnd, Blocks.OAK_LOG.defaultBlockState().setValue(net.minecraft.world.level.block.RotatedPillarBlock.AXIS, net.minecraft.core.Direction.Axis.Z), 3);

        // Lantern hanging from the center log
        level.setBlock(center.above(3), Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true), 3);
    }
}
