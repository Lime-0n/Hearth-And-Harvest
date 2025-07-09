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

    public static final Supplier<FluidType> BLUEBERRY_WINE_TYPE = FLUID_TYPES.register("blueberry_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> BLUEBERRY_WINE = FLUIDS.register("blueberry_wine", () -> new BlueberryWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_BLUEBERRY_WINE = FLUIDS.register("flowing_blueberry_wine", () -> new BlueberryWineFluid(false));

    public static final Supplier<FluidType> CHERRY_WINE_TYPE = FLUID_TYPES.register("cherry_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> CHERRY_WINE = FLUIDS.register("cherry_wine", () -> new CherryWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_CHERRY_WINE = FLUIDS.register("flowing_cherry_wine", () -> new CherryWineFluid(false));

    public static final Supplier<FluidType> GREEN_GRAPE_WINE_TYPE = FLUID_TYPES.register("green_grape_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> GREEN_GRAPE_WINE = FLUIDS.register("green_grape_wine", () -> new GreenGrapeWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_GREEN_GRAPE_WINE = FLUIDS.register("flowing_green_grape_wine", () -> new GreenGrapeWineFluid(false));

    public static final Supplier<FluidType> RASPBERRY_WINE_TYPE = FLUID_TYPES.register("raspberry_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> RASPBERRY_WINE = FLUIDS.register("raspberry_wine", () -> new RaspberryWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_RASPBERRY_WINE = FLUIDS.register("flowing_raspberry_wine", () -> new RaspberryWineFluid(false));

    public static final Supplier<FluidType> RED_GRAPE_WINE_TYPE = FLUID_TYPES.register("red_grape_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> RED_GRAPE_WINE = FLUIDS.register("red_grape_wine", () -> new RedGrapeWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_RED_GRAPE_WINE = FLUIDS.register("flowing_red_grape_wine", () -> new RedGrapeWineFluid(false));

    public static final Supplier<FluidType> MEAD_TYPE = FLUID_TYPES.register("mead", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> MEAD = FLUIDS.register("mead", () -> new MeadFluid(true));
    public static final Supplier<HHFluidType> FLOWING_MEAD = FLUIDS.register("flowing_mead", () -> new MeadFluid(false));
    
}