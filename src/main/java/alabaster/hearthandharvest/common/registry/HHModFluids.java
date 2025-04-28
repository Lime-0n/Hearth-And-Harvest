package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.fluid.CookingOilFluid;
import alabaster.hearthandharvest.common.fluid.HHFluidType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class HHModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, HearthAndHarvest.MODID);

    public static final Supplier<FluidType> COOKING_OIL_TYPE = FLUID_TYPES.register("cooking_oil", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, HearthAndHarvest.MODID);
    public static final Supplier<HHFluidType> COOKING_OIL = FLUIDS.register("cooking_oil",
            () -> new CookingOilFluid(true));

    public static final Supplier<HHFluidType> FLOWING_COOKING_OIL = FLUIDS.register("flowing_cooking_oil",
            () -> new CookingOilFluid(false));
}