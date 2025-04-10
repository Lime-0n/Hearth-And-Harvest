package alabaster.hearthandharvest.client.event;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.utilities.CauldronBlockColor;
import alabaster.hearthandharvest.common.utilities.TapperBlockColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "hearthandharvest", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(new CauldronBlockColor(), HHModBlocks.SAP_CAULDRON.get());
        event.register(new TapperBlockColor(), HHModBlocks.TREE_TAPPER.get());
    }
}
