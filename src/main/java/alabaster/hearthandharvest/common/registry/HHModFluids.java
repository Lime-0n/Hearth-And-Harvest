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

    public static final Supplier<FluidType> SWEET_BERRY_WINE_TYPE = FLUID_TYPES.register("sweet_berry_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> SWEET_BERRY_WINE = FLUIDS.register("sweet_berry_wine", () -> new SweetBerryWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_SWEET_BERRY_WINE = FLUIDS.register("flowing_sweet_berry_wine", () -> new SweetBerryWineFluid(false));

    public static final Supplier<FluidType> GLOW_BERRY_WINE_TYPE = FLUID_TYPES.register("glow_berry_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> GLOW_BERRY_WINE = FLUIDS.register("glow_berry_wine", () -> new SweetBerryWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_GLOW_BERRY_WINE = FLUIDS.register("flowing_glow_berry_wine", () -> new GlowBerryWineFluid(false));

    public static final Supplier<FluidType> MELON_WINE_TYPE = FLUID_TYPES.register("melon_wine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> MELON_WINE = FLUIDS.register("melon_wine", () -> new SweetBerryWineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_MELON_WINE = FLUIDS.register("flowing_melon_wine", () -> new MelonWineFluid(false));

    public static final Supplier<FluidType> MEAD_TYPE = FLUID_TYPES.register("mead", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> MEAD = FLUIDS.register("mead", () -> new MeadFluid(true));
    public static final Supplier<HHFluidType> FLOWING_MEAD = FLUIDS.register("flowing_mead", () -> new MeadFluid(false));

    public static final Supplier<FluidType> MOONSHINE_TYPE = FLUID_TYPES.register("moonshine", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> MOONSHINE = FLUIDS.register("moonshine", () -> new MoonshineFluid(true));
    public static final Supplier<HHFluidType> FLOWING_MOONSHINE = FLUIDS.register("flowing_moonshine", () -> new MoonshineFluid(false));

    public static final Supplier<FluidType> APPLE_CIDER_TYPE = FLUID_TYPES.register("apple_cider", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> APPLE_CIDER = FLUIDS.register("apple_cider", () -> new AppleCiderFluid(true));
    public static final Supplier<HHFluidType> FLOWING_APPLE_CIDER = FLUIDS.register("flowing_apple_cider", () -> new AppleCiderFluid(false));
    
    public static final Supplier<FluidType> HARD_CIDER_TYPE = FLUID_TYPES.register("hard_cider", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> HARD_CIDER = FLUIDS.register("hard_cider", () -> new HardCiderFluid(true));
    public static final Supplier<HHFluidType> FLOWING_HARD_CIDER = FLUIDS.register("flowing_hard_cider", () -> new HardCiderFluid(false));

    public static final Supplier<FluidType> ROOT_BEER_TYPE = FLUID_TYPES.register("root_beer", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> ROOT_BEER = FLUIDS.register("root_beer", () -> new RootBeerFluid(true));
    public static final Supplier<HHFluidType> FLOWING_ROOT_BEER = FLUIDS.register("flowing_root_beer", () -> new RootBeerFluid(false));

    public static final Supplier<FluidType> BLUEBERRY_JUICE_TYPE = FLUID_TYPES.register("blueberry_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> BLUEBERRY_JUICE = FLUIDS.register("blueberry_juice", () -> new BlueberryJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_BLUEBERRY_JUICE = FLUIDS.register("flowing_blueberry_juice", () -> new BlueberryJuiceFluid(false));

    public static final Supplier<FluidType> CHERRY_JUICE_TYPE = FLUID_TYPES.register("cherry_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> CHERRY_JUICE = FLUIDS.register("cherry_juice", () -> new CherryJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_CHERRY_JUICE = FLUIDS.register("flowing_cherry_juice", () -> new CherryJuiceFluid(false));

    public static final Supplier<FluidType> GREEN_GRAPE_JUICE_TYPE = FLUID_TYPES.register("green_grape_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> GREEN_GRAPE_JUICE = FLUIDS.register("green_grape_juice", () -> new GreenGrapeJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_GREEN_GRAPE_JUICE = FLUIDS.register("flowing_green_grape_juice", () -> new GreenGrapeJuiceFluid(false));

    public static final Supplier<FluidType> RASPBERRY_JUICE_TYPE = FLUID_TYPES.register("raspberry_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> RASPBERRY_JUICE = FLUIDS.register("raspberry_juice", () -> new RaspberryJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_RASPBERRY_JUICE = FLUIDS.register("flowing_raspberry_juice", () -> new RaspberryJuiceFluid(false));

    public static final Supplier<FluidType> RED_GRAPE_JUICE_TYPE = FLUID_TYPES.register("red_grape_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> RED_GRAPE_JUICE = FLUIDS.register("red_grape_juice", () -> new RedGrapeJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_RED_GRAPE_JUICE = FLUIDS.register("flowing_red_grape_juice", () -> new RedGrapeJuiceFluid(false));

    public static final Supplier<FluidType> SWEET_BERRY_JUICE_TYPE = FLUID_TYPES.register("sweet_berry_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> SWEET_BERRY_JUICE = FLUIDS.register("sweet_berry_juice", () -> new SweetBerryJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_SWEET_BERRY_JUICE = FLUIDS.register("flowing_sweet_berry_juice", () -> new SweetBerryJuiceFluid(false));

    public static final Supplier<FluidType> MELON_JUICE_TYPE = FLUID_TYPES.register("melon_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400)));
    public static final Supplier<HHFluidType> MELON_JUICE = FLUIDS.register("melon_juice", () -> new MelonJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_MELON_JUICE = FLUIDS.register("flowing_melon_juice", () -> new MelonJuiceFluid(false));

    public static final Supplier<FluidType> GLOW_BERRY_JUICE_TYPE = FLUID_TYPES.register("glow_berry_juice", () -> new FluidType(FluidType.Properties.create().viscosity(2000).density(1400).lightLevel(8)));
    public static final Supplier<HHFluidType> GLOW_BERRY_JUICE = FLUIDS.register("glow_berry_juice", () -> new GlowBerryJuiceFluid(true));
    public static final Supplier<HHFluidType> FLOWING_GLOW_BERRY_JUICE = FLUIDS.register("flowing_glow_berry_juice", () -> new GlowBerryJuiceFluid(false));

}