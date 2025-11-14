package alabaster.hearthandharvest.common.worldgen.structure.corn_maze;

import alabaster.hearthandharvest.common.registry.HHModStructures;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class CornMazeStructure extends Structure {

    public static final MapCodec<CornMazeStructure> CODEC = simpleCodec(CornMazeStructure::new);

    public CornMazeStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        // Sample surface height at the chunk origin
        int surfaceY = context.chunkGenerator().getBaseHeight(
                context.chunkPos().getMinBlockX(),
                context.chunkPos().getMinBlockZ(),
                Heightmap.Types.WORLD_SURFACE_WG,
                context.heightAccessor(),
                context.randomState()
        );

        // Create a start position at surface
        BlockPos surfacePos = new BlockPos(
                context.chunkPos().getMinBlockX(),
                surfaceY,
                context.chunkPos().getMinBlockZ()
        );

        return Optional.of(new GenerationStub(surfacePos, builder -> buildMazePieces(builder, surfacePos)));
    }

    private void buildMazePieces(StructurePiecesBuilder builder, BlockPos origin) {
        // 25x25 maze piece
        CornMazeStructurePiece piece = new CornMazeStructurePiece(origin, 25, 25);
        builder.addPiece(piece);
    }

    @Override
    public StructureType<?> type() {
        return HHModStructures.CORN_MAZE.get();
    }
}
