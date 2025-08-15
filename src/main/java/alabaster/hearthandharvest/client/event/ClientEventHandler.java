package alabaster.hearthandharvest.client.event;

import alabaster.hearthandharvest.client.particle.DrippingSapParticle;
import alabaster.hearthandharvest.client.renderer.WineRackRenderer;
import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModParticleTypes;
import alabaster.hearthandharvest.common.utilities.BasinBlockColor;
import alabaster.hearthandharvest.common.utilities.CauldronBlockColor;
import alabaster.hearthandharvest.common.utilities.TapperBlockColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "hearthandharvest", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
        //event.register(new CauldronBlockColor(), HHModBlocks.SAP_CAULDRON.get());
        //event.register(new TapperBlockColor(), HHModBlocks.TREE_TAPPER.get());
        event.register(new BasinBlockColor(), HHModBlocks.BASIN.get());
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                HHModBlockEntities.WINE_RACK.get(),
                WineRackRenderer::new
        );
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(
                HHModParticleTypes.DRIPPING_SAP.get(),
                DrippingSapParticle.Provider::new
        );
    }

}
