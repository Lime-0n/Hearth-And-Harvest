package alabaster.hearthandharvest.client;

import alabaster.hearthandharvest.client.gui.CaskGUI;
import alabaster.hearthandharvest.common.registry.HHModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(HHModMenuTypes.CASK.get(), CaskGUI::new));

    }
}