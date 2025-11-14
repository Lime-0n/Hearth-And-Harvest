package alabaster.hearthandharvest.common.worldgen.structure.corn_maze;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.CornStalkBlock;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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

        // === 1. Generate maze layout ===
        boolean[][] maze = MazeGenerator.generate(width, height, random);

        // === 2. Build the maze walls and paths ===
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                BlockPos pos = origin.offset(x, 0, z);
                boolean isWall = maze[x][z];

                if (isWall) {
                    // Build full corn stalk if wall
                    placeFullCornStalk(level, pos);
                } else {
                    // Path: remove any corn blocks above and place air 3 blocks tall
                    for (int y = 0; y < 3; y++) {
                        level.setBlock(pos.above(y), Blocks.AIR.defaultBlockState(), 3);
                    }
                    // Grass below path
                    level.setBlock(pos.below(), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
                }
            }
        }

        // === 3. Carve entrance & exit on edges ===
        BlockPos entrance = origin.offset(0, 0, height / 2);           // west edge
        BlockPos exit = origin.offset(width - 1, 0, height / 2);       // east edge

        for (int y = 0; y < 3; y++) {
            level.setBlock(entrance.above(y), Blocks.AIR.defaultBlockState(), 3);
            level.setBlock(exit.above(y), Blocks.AIR.defaultBlockState(), 3);
        }

        level.setBlock(entrance.below(), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
        level.setBlock(exit.below(), Blocks.GRASS_BLOCK.defaultBlockState(), 3);

        // Make sure entrance/exit are paths in the maze
        maze[0][height / 2] = false;
        maze[width - 1][height / 2] = false;

        // === 4. Place random loot chests in paths ===
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!maze[x][z]) {
                    // Skip entrance and exit
                    if ((x == 0 && z == height / 2) || (x == width - 1 && z == height / 2)) continue;

                    int openNeighbors = 0;
                    if (x > 0 && !maze[x - 1][z]) openNeighbors++;
                    if (x < width - 1 && !maze[x + 1][z]) openNeighbors++;
                    if (z > 0 && !maze[x][z - 1]) openNeighbors++;
                    if (z < height - 1 && !maze[x][z + 1]) openNeighbors++;

                    if (openNeighbors == 1) { // dead end
                        if (random.nextFloat() < 0.3f) { // 30% chance
                            BlockPos chestPos = origin.offset(x, 0, z);
                            level.setBlock(chestPos, Blocks.CHEST.defaultBlockState(), 3);
                            var be = level.getBlockEntity(chestPos);
                            if (be instanceof ChestBlockEntity chest) {
                                ResourceLocation lootId = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "chests/corn_maze");
                                ResourceKey<LootTable> lootKey = ResourceKey.create(Registries.LOOT_TABLE, lootId);
                                chest.setLootTable(lootKey, random.nextLong());
                            }
                        }
                    }
                }
            }
        }
    }

    private void placeFullCornStalk(WorldGenLevel level, BlockPos pos) {
        level.setBlock(pos,
                HHModBlocks.CORN_STALK.get().defaultBlockState()
                        .setValue(CornStalkBlock.SECTION, CornStalkBlock.CornSection.BOTTOM)
                        .setValue(CornStalkBlock.AGE, 5), 3);
        level.setBlock(pos.above(),
                HHModBlocks.CORN_STALK.get().defaultBlockState()
                        .setValue(CornStalkBlock.SECTION, CornStalkBlock.CornSection.MIDDLE)
                        .setValue(CornStalkBlock.AGE, 5), 3);
        level.setBlock(pos.above(2),
                HHModBlocks.CORN_STALK.get().defaultBlockState()
                        .setValue(CornStalkBlock.SECTION, CornStalkBlock.CornSection.TOP)
                        .setValue(CornStalkBlock.AGE, 5), 3);
    }
}
