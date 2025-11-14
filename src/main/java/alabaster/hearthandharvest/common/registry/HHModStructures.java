package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.worldgen.structure.corn_maze.CornMazeStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HHModStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, HearthAndHarvest.MODID);

    public static final java.util.function.Supplier<StructureType<CornMazeStructure>> CORN_MAZE =
            STRUCTURES.register("corn_maze", () -> () -> CornMazeStructure.CODEC);


    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}

