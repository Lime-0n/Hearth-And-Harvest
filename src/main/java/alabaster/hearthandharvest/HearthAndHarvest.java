package alabaster.hearthandharvest;

import alabaster.hearthandharvest.client.gui.CaskGUI;
import alabaster.hearthandharvest.client.recipebook.RecipeCategories;
import alabaster.hearthandharvest.common.entity.goal.PungentEffectGoal;
import alabaster.hearthandharvest.common.entity.goal.TemptingEffectGoal;
import alabaster.hearthandharvest.common.registry.*;
import alabaster.hearthandharvest.common.event.PigLitters;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HearthAndHarvest.MODID)
public class HearthAndHarvest {
    public static final String MODID = "hearthandharvest";
    public static final Logger LOGGER = LogManager.getLogger();

    public HearthAndHarvest(IEventBus modEventBus, ModContainer modContainer) {

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(this::registerScreens);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        HHModBlocks.BLOCKS.register(modEventBus);
        HHModItems.ITEMS.register(modEventBus);
        HHModEffects.EFFECTS.register(modEventBus);
        HHModPotions.POTIONS.register(modEventBus);
        HHModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        HHModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        HHModDataComponents.DATA_COMPONENTS.register(modEventBus);
        HHModMenuTypes.MENU_TYPES.register(modEventBus);
        HHModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        HHModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PigLitters());
    }

    public void registerScreens(RegisterMenuScreensEvent event) {
        event.register(HHModMenuTypes.CASK_MENU.get(), CaskGUI::new);
    }

    public static final RecipeBookType RECIPE_TYPE_COOKING = RecipeBookType.create("AGING");

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
}