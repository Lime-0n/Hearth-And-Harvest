package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.MultiblockPart;
import alabaster.hearthandharvest.common.block.entity.StompingBasinBlockEntity;
import alabaster.hearthandharvest.common.item.JugBlockItem;
import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.ItemStack;
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
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                HHModBlockEntities.STOMPING_BASIN.get(),
                (be, side) -> be.getMultiblockRole() == MultiblockPart.MEMBER ? null : be.getFluidTank()
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
    }
}