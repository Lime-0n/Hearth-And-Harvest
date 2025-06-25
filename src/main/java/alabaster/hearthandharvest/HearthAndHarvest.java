package alabaster.hearthandharvest;

import alabaster.hearthandharvest.client.gui.CaskGUI;
import alabaster.hearthandharvest.client.recipebook.RecipeCategories;
import alabaster.hearthandharvest.common.entity.goal.PungentEffectGoal;
import alabaster.hearthandharvest.common.entity.goal.TemptingEffectGoal;
import alabaster.hearthandharvest.common.event.RabbitLitters;
import alabaster.hearthandharvest.common.registry.*;
import alabaster.hearthandharvest.common.event.PigLitters;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.neoforged.neoforge.common.NeoForge;
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
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        HHModBlocks.BLOCKS.register(modEventBus);
        HHModItems.ITEMS.register(modEventBus);
        HHModEffects.EFFECTS.register(modEventBus);
        HHModPotions.POTIONS.register(modEventBus);
        HHModFluids.FLUIDS.register(modEventBus);
        HHModFluids.FLUID_TYPES.register(modEventBus);
        HHModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        HHModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        HHModDataComponents.DATA_COMPONENTS.register(modEventBus);
        HHModMenuTypes.MENUS.register(modEventBus);
        HHModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        HHModRecipeTypes.RECIPE_TYPES.register(modEventBus);

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

    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            mob.goalSelector.addGoal(1, new PungentEffectGoal(mob, 1.0D, 1.5D, 8.0D));
        }
        if (event.getEntity() instanceof Mob mob) {
            mob.goalSelector.addGoal(1, new TemptingEffectGoal(mob, 1.0D, 1.25D, 8.0D));
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
}