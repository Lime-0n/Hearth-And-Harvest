package alabaster.hearthandharvest.client.event;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.utilities.CauldronBlockColor;
import alabaster.hearthandharvest.common.utilities.BasinBlockColor;
import alabaster.hearthandharvest.common.utilities.TapperBlockColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = "hearthandharvest", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(new CauldronBlockColor(), HHModBlocks.SAP_CAULDRON.get());
        event.register(new TapperBlockColor(), HHModBlocks.TREE_TAPPER.get());
        event.register(new BasinBlockColor(), HHModBlocks.BASIN.get());
    }
}
