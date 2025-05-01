package alabaster.hearthandharvest.client.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.utilities.CauldronBlockColor;
import alabaster.hearthandharvest.common.utilities.BasinBlockColor;
import alabaster.hearthandharvest.common.utilities.TapperBlockColor;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = "hearthandharvest", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(new CauldronBlockColor(), HHModBlocks.SAP_CAULDRON.get());
        event.register(new TapperBlockColor(), HHModBlocks.TREE_TAPPER.get());
        event.register(new BasinBlockColor(), HHModBlocks.BASIN.get());
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_COOKING_OIL   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/cooking_oil_still");
            private static final ResourceLocation FLOWING_COOKING_OIL = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/cooking_oil_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_COOKING_OIL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_COOKING_OIL;
            }
        }, HHModFluids.COOKING_OIL_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_SAP   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/sap_still");
            private static final ResourceLocation FLOWING_SAP = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/sap_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_SAP;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_SAP;
            }
        }, HHModFluids.SAP_TYPE.get());
    }
}
