package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.fluid.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class HHModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, HearthAndHarvest.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, HearthAndHarvest.MODID);

    public static final Supplier<FluidType> COOKING_OIL_TYPE = FLUID_TYPES.register("cooking_oil", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> COOKING_OIL = FLUIDS.register("cooking_oil", () -> new CookingOilFluid(true));
    public static final Supplier<HHFluidType> FLOWING_COOKING_OIL = FLUIDS.register("flowing_cooking_oil", () -> new CookingOilFluid(false));

    public static final Supplier<FluidType> SAP_TYPE = FLUID_TYPES.register("sap", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> SAP = FLUIDS.register("sap", () -> new SapFluid(true));
    public static final Supplier<HHFluidType> FLOWING_SAP = FLUIDS.register("flowing_sap", () -> new SapFluid(false));

    public static final Supplier<FluidType> SYRUP_TYPE = FLUID_TYPES.register("syrup", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> SYRUP = FLUIDS.register("syrup", () -> new SyrupFluid(true));
    public static final Supplier<HHFluidType> FLOWING_SYRUP = FLUIDS.register("flowing_syrup", () -> new SyrupFluid(false));
}