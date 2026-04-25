package alabaster.hearthandharvest.common.worldgen;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import vectorwing.farmersdelight.common.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class VillageCrops {
    public static void addVillageCrops(final ServerAboutToStartEvent event) {
        if (Configuration.GENERATE_VILLAGE_FARM_FD_CROPS.get()) {
            Registry<StructureProcessorList> processorLists = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();

            StructureProcessor temperateCropProcessor = new RuleProcessor(List.of(
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.CARROTS, 0.3F), AlwaysTrueTest.INSTANCE, HHModBlocks.CORN_STALK.get().defaultBlockState()),
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.POTATOES, 0.3F), AlwaysTrueTest.INSTANCE, HHModBlocks.PEANUT_CROP.get().defaultBlockState())
            ));

            StructureProcessor coldCropProcessor = new RuleProcessor(List.of(
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.CARROTS, 0.3F), AlwaysTrueTest.INSTANCE, HHModBlocks.COTTON_CROP.get().defaultBlockState()),
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.POTATOES, 0.3F), AlwaysTrueTest.INSTANCE, HHModBlocks.PEANUT_CROP.get().defaultBlockState())
            ));

            StructureProcessor aridCropProcessor = new RuleProcessor(List.of(
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.CARROTS, 0.3F), AlwaysTrueTest.INSTANCE, HHModBlocks.CORN_STALK.get().defaultBlockState()),
                    new ProcessorRule(new RandomBlockMatchTest(Blocks.POTATOES, 0.3F), AlwaysTrueTest.INSTANCE, HHModBlocks.COTTON_CROP.get().defaultBlockState())
            ));

            addNewRuleToProcessorList(ResourceLocation.parse("minecraft:farm_plains"), temperateCropProcessor, processorLists);
            addNewRuleToProcessorList(ResourceLocation.parse("minecraft:farm_savanna"), aridCropProcessor, processorLists);
            addNewRuleToProcessorList(ResourceLocation.parse("minecraft:farm_snowy"), coldCropProcessor, processorLists);
            addNewRuleToProcessorList(ResourceLocation.parse("minecraft:farm_taiga"), temperateCropProcessor, processorLists);
            addNewRuleToProcessorList(ResourceLocation.parse("minecraft:farm_desert"), aridCropProcessor, processorLists);
        }
    }

    private static void addNewRuleToProcessorList(ResourceLocation targetProcessorList, StructureProcessor processorToAdd, Registry<StructureProcessorList> processorListRegistry) {
        processorListRegistry.getOptional(targetProcessorList).ifPresent(processorList -> {
            try {
                Field listField = StructureProcessorList.class.getDeclaredField("list");
                listField.setAccessible(true);
                List<StructureProcessor> newList = new ArrayList<>((List<StructureProcessor>) listField.get(processorList));
                newList.add(0, processorToAdd); // insert first so we run before FD's processor
                listField.set(processorList, newList);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to inject into StructureProcessorList", e);
            }
        });
    }
}