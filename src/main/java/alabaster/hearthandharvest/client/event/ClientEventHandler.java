package alabaster.hearthandharvest.client.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.utilities.BasinBlockColor;
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

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_SYRUP   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/syrup_still");
            private static final ResourceLocation FLOWING_SYRUP = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/syrup_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_SYRUP;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_SYRUP;
            }
        }, HHModFluids.SYRUP_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_BLUEBERRY_WINE   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/blueberry_wine_still");
            private static final ResourceLocation FLOWING_BLUEBERRY_WINE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/blueberry_wine_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_BLUEBERRY_WINE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_BLUEBERRY_WINE;
            }
        }, HHModFluids.BLUEBERRY_WINE_TYPE.get());
        
        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_CHERRY_WINE   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/cherry_wine_still");
            private static final ResourceLocation FLOWING_CHERRY_WINE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/cherry_wine_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_CHERRY_WINE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_CHERRY_WINE;
            }
        }, HHModFluids.CHERRY_WINE_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_GREEN_GRAPE_WINE   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/green_grape_wine_still");
            private static final ResourceLocation FLOWING_GREEN_GRAPE_WINE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/green_grape_wine_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_GREEN_GRAPE_WINE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_GREEN_GRAPE_WINE;
            }
        }, HHModFluids.GREEN_GRAPE_WINE_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_RASPBERRY_WINE   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/raspberry_wine_still");
            private static final ResourceLocation FLOWING_RASPBERRY_WINE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/raspberry_wine_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_RASPBERRY_WINE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_RASPBERRY_WINE;
            }
        }, HHModFluids.RASPBERRY_WINE_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_RED_GRAPE_WINE   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/red_grape_wine_still");
            private static final ResourceLocation FLOWING_RED_GRAPE_WINE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/red_grape_wine_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_RED_GRAPE_WINE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_RED_GRAPE_WINE;
            }
        }, HHModFluids.RED_GRAPE_WINE_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_MEAD   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/mead_still");
            private static final ResourceLocation FLOWING_MEAD = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/mead_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_MEAD;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_MEAD;
            }
        }, HHModFluids.MEAD_TYPE.get());
    }
}
