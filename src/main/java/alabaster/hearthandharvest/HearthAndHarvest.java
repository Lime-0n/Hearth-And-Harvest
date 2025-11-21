package alabaster.hearthandharvest;

import alabaster.hearthandharvest.client.gui.CaskGUI;
import alabaster.hearthandharvest.client.recipebook.RecipeCategories;
import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import alabaster.hearthandharvest.common.entity.crow.CrowModel;
import alabaster.hearthandharvest.common.entity.crow.CrowRenderer;
import alabaster.hearthandharvest.common.entity.goal.PungentEffectGoal;
import alabaster.hearthandharvest.common.entity.goal.SeekNestGoal;
import alabaster.hearthandharvest.common.entity.goal.TemptingEffectGoal;
import alabaster.hearthandharvest.common.event.RabbitLitters;
import alabaster.hearthandharvest.common.registry.*;
import alabaster.hearthandharvest.common.event.PigLitters;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HearthAndHarvest.MODID)
public class HearthAndHarvest {
    public static final String MODID = "hearthandharvest";
    public static final Logger LOGGER = LogManager.getLogger();

    public HearthAndHarvest(IEventBus modEventBus, ModContainer modContainer) {

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(this::registerScreens);
            modEventBus.addListener(this::registerRecipeBookCategories);
            modEventBus.addListener(this::flowerSetup);
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        HHModBlocks.BLOCKS.register(modEventBus);
        HHModItems.ITEMS.register(modEventBus);
        HHModEffects.EFFECTS.register(modEventBus);
        HHModEntities.ENTITY_TYPES.register(modEventBus);
        HHModParticleTypes.PARTICLE_TYPES.register(modEventBus);
        HHModPotions.POTIONS.register(modEventBus);
        HHModFluids.FLUIDS.register(modEventBus);
        HHModFluids.FLUID_TYPES.register(modEventBus);
        HHModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        HHModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        HHModDataComponents.DATA_COMPONENTS.register(modEventBus);
        HHModMenuTypes.MENUS.register(modEventBus);
        HHModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        HHModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        HHModStructurePieces.STRUCTURE_PIECES.register(modEventBus);
        HHModStructures.STRUCTURES.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new PigLitters());
        NeoForge.EVENT_BUS.register(new RabbitLitters());
    }

    public void registerScreens(RegisterMenuScreensEvent event) {
        event.register(HHModMenuTypes.CASK_MENU.get(), CaskGUI::new);
    }

    public void registerRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        RecipeCategories.init(event);
    }

    private void flowerSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;

            // Register all 8 colored mums with the flower pot
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.YELLOW_MUM.get()), HHModBlocks.POTTED_YELLOW_MUM);
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.ORANGE_MUM.get()), HHModBlocks.POTTED_ORANGE_MUM);
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.RED_MUM.get()), HHModBlocks.POTTED_RED_MUM);
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.BLUE_MUM.get()), HHModBlocks.POTTED_BLUE_MUM);
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.LIGHT_BLUE_MUM.get()), HHModBlocks.POTTED_LIGHT_BLUE_MUM);
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.PURPLE_MUM.get()), HHModBlocks.POTTED_PURPLE_MUM);
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.PINK_MUM.get()), HHModBlocks.POTTED_PINK_MUM);
            pot.addPlant(BuiltInRegistries.BLOCK.getKey(HHModBlocks.WHITE_MUM.get()), HHModBlocks.POTTED_WHITE_MUM);
        });
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            mob.goalSelector.addGoal(1, new PungentEffectGoal(mob, 1.0D, 1.5D, 8.0D));
        }
        if (event.getEntity() instanceof Mob mob) {
            mob.goalSelector.addGoal(1, new TemptingEffectGoal(mob, 1.0D, 1.25D, 8.0D));
        }
        if (event.getEntity() instanceof Chicken chicken) {
            chicken.goalSelector.addGoal(2, new SeekNestGoal(chicken, 1.0D));
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Hearth and Harvest is starting");
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(HHModEntities.CROW.get(), CrowRenderer::new);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CrowModel.LAYER_LOCATION, CrowModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(HHModEntities.CROW.get(), CrowEntity.createAttributes().build());
        }
    }
}