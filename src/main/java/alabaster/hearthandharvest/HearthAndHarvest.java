package alabaster.hearthandharvest;

import alabaster.hearthandharvest.client.gui.CaskGUI;
import alabaster.hearthandharvest.client.recipebook.RecipeCategories;
import alabaster.hearthandharvest.common.registry.*;
import alabaster.hearthandharvest.common.event.PigLitters;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.neoforged.neoforge.common.NeoForge;
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
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new PigLitters());
    }

    public void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.CASK_MENU.get(), CaskGUI::new);
    }

    public void registerRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        RecipeCategories.init(event);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Hearth and Harvest is starting");
    }
}