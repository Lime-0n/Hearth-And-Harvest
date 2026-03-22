package alabaster.hearthandharvest.client.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.client.particle.DrippingSapParticle;
import alabaster.hearthandharvest.client.renderer.CrateItemRenderer;
import alabaster.hearthandharvest.client.renderer.CrateRenderer;
import alabaster.hearthandharvest.client.renderer.BottleRackRenderer;
import alabaster.hearthandharvest.client.renderer.StompingBasinRenderer;
import alabaster.hearthandharvest.common.entity.crow.CrowOnShoulderLayer;
import alabaster.hearthandharvest.common.registry.*;
import alabaster.hearthandharvest.common.utilities.BasinBlockColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = "hearthandharvest", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(new BasinBlockColor(), HHModBlocks.BASIN.get());
    }

    @SubscribeEvent
    public static void onRegisterAdditional(ModelEvent.RegisterAdditional event) {
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        rm.listResources("models/bottle_rack", path -> path.getPath().endsWith(".json"))
                .keySet()
                .forEach(resourceLocation -> {
                    String path = resourceLocation.getPath();
                    String modelPath = path.substring("models/".length(), path.length() - ".json".length());
                    event.register(ModelResourceLocation.standalone(
                            ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), modelPath)
                    ));
                });
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                HHModBlockEntities.BOTTLE_RACK.get(),
                BottleRackRenderer::new
        );
        event.registerBlockEntityRenderer(
                HHModBlockEntities.CRATE.get(),
                CrateRenderer::new
        );
        event.registerBlockEntityRenderer(
                HHModBlockEntities.STOMPING_BASIN.get(),
                StompingBasinRenderer::new
        );

    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(
                HHModParticleTypes.DRIPPING_SAP.get(),
                DrippingSapParticle.Provider::new
        );
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (var skinModel : PlayerSkin.Model.values()) {
            PlayerRenderer renderer = event.getSkin(skinModel);
            if (renderer != null) {
                renderer.addLayer(new CrowOnShoulderLayer(renderer, event.getEntityModels()));
            }
        }
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return CrateItemRenderer.getInstance();
                    }
                },
                HHModItems.CRATE.get()
        );

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
            private static final ResourceLocation STILL_SWEET_BERRY_WINE   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/sweet_berry_wine_still");
            private static final ResourceLocation FLOWING_SWEET_BERRY_WINE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/sweet_berry_wine_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_SWEET_BERRY_WINE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_SWEET_BERRY_WINE;
            }
        }, HHModFluids.SWEET_BERRY_WINE_TYPE.get());

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

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_ROOT_BEER   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/root_beer_still");
            private static final ResourceLocation FLOWING_ROOT_BEER = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/root_beer_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_ROOT_BEER;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_ROOT_BEER;
            }
        }, HHModFluids.ROOT_BEER_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_HARD_CIDER   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/hard_cider_still");
            private static final ResourceLocation FLOWING_HARD_CIDER = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/hard_cider_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_HARD_CIDER;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_HARD_CIDER;
            }
        }, HHModFluids.HARD_CIDER_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_MOONSHINE   = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/moonshine_still");
            private static final ResourceLocation FLOWING_MOONSHINE = ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/moonshine_flow");

            @Override
            public ResourceLocation getStillTexture() {return STILL_MOONSHINE;}

            @Override
            public ResourceLocation getFlowingTexture() {return FLOWING_MOONSHINE;}
        }, HHModFluids.MOONSHINE_TYPE.get());
    }
}
