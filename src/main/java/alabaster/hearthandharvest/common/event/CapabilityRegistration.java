package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.MultiblockPart;
import alabaster.hearthandharvest.common.block.entity.StompingBasinBlockEntity;
import alabaster.hearthandharvest.common.fluid.HHFluidType;
import alabaster.hearthandharvest.common.item.JugBlockItem;
import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CapabilityRegistration {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        // Stomping Basin
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                HHModBlockEntities.STOMPING_BASIN.get(),
                (be, side) -> {
                    if (be.getMultiblockRole() == MultiblockPart.MEMBER) {
                        StompingBasinBlockEntity controller = be.getControllerBE();
                        return controller != null ? controller.getItemHandler() : be.getItemHandler();
                    }
                    return be.getItemHandler();
                }
        );

        // Jug block entity
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                HHModBlockEntities.JUG.get(),
                (be, side) -> be.getFluidTank()
        );

        // Jug item
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, ctx) -> new IFluidHandlerItem() {
                    private final FluidTank tank = JugBlockItem.readTankStatic(stack);

                    @Override public int getTanks() { return 1; }
                    @Override public FluidStack getFluidInTank(int t) { return tank.getFluid(); }
                    @Override public int getTankCapacity(int t) { return JugBlockItem.JUG_CAPACITY; }
                    @Override public boolean isFluidValid(int t, FluidStack f) { return true; }
                    @Override public int fill(FluidStack r, IFluidHandler.FluidAction a) { return 0; }
                    @Override public FluidStack drain(FluidStack r, IFluidHandler.FluidAction a) { return FluidStack.EMPTY; }
                    @Override public FluidStack drain(int m, IFluidHandler.FluidAction a) { return FluidStack.EMPTY; }
                    @Override public ItemStack getContainer() { return stack; }
                },
                HHModItems.JUG.get()
        );

        // Register fluid capabilities per fluid item
        for (var fluidHolder : HHModFluids.FLUIDS.getEntries()) {
            Fluid fluid = fluidHolder.get();
            if (!(fluid instanceof HHFluidType hhFluid)) continue;
            if (!hhFluid.isSource(fluid.defaultFluidState())) continue;

            Item bucketItem = hhFluid.getBucket();
            if (bucketItem == Items.AIR || bucketItem == null) continue;

            event.registerItem(
                    Capabilities.FluidHandler.ITEM,
                    (stack, ctx) -> new IFluidHandlerItem() {
                        private static final int BOTTLE_VOLUME = 250;
                        private final Fluid containedFluid = hhFluid;

                        @Override public int getTanks() { return 1; }

                        @Override
                        public FluidStack getFluidInTank(int tank) {
                            return new FluidStack(containedFluid, BOTTLE_VOLUME);
                        }

                        @Override public int getTankCapacity(int tank) { return BOTTLE_VOLUME; }
                        @Override public boolean isFluidValid(int tank, FluidStack fluid) { return false; }
                        @Override public int fill(FluidStack resource, IFluidHandler.FluidAction action) { return 0; }

                        @Override
                        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
                            if (!resource.getFluid().isSame(containedFluid)) return FluidStack.EMPTY;
                            return drain(resource.getAmount(), action);
                        }

                        @Override
                        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
                            int drained = Math.min(maxDrain, BOTTLE_VOLUME);
                            return new FluidStack(containedFluid, drained);
                        }

                        @Override
                        public ItemStack getContainer() {
                            return new ItemStack(Items.GLASS_BOTTLE);
                        }
                    },
                    bucketItem
            );
        }
    }
}