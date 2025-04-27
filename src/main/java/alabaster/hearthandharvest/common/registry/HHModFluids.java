package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.fluid.OilFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class HHModFluids {

    private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, HearthAndHarvest.MODID);
    private static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, HearthAndHarvest.MODID);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }

    public static final DeferredHolder<FluidType, FluidType> OIL_TYPE = FLUID_TYPES.register("oil", () -> new FluidType(FluidType.Properties.create()));
    public static final DeferredHolder<FluidType, FluidType> SAP_TYPE = FLUID_TYPES.register("sap", () -> new FluidType(FluidType.Properties.create()));
    public static final DeferredHolder<FluidType, FluidType> SYRUP_TYPE = FLUID_TYPES.register("syrup", () -> new FluidType(FluidType.Properties.create()));

//    public static final DeferredHolder<Fluid, OilFluid> OIL = registerNoop("oil", OIL_TYPE::value);

//    private static DeferredHolder<Fluid, OilFluid> registerNoop(String name, Supplier<FluidType> fluidType) {
//        DeferredHolder<Fluid, OilFluid> holder = DeferredHolder.create(Registries.FLUID, HearthAndHarvest.MODID);
//        BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(fluidType, holder::value, holder::value).bucket(() -> Items.AIR);
//        FLUIDS.register(name, () -> new OilFluid(properties));
//        return holder;
//    }
}