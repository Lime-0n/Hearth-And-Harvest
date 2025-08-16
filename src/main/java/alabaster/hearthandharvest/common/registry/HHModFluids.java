package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.fluid.*;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HHModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, HearthAndHarvest.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, HearthAndHarvest.MODID);

    public static final RegistryObject<FluidType> SAP_FLUID_TYPE = FLUID_TYPES.register("sap", BlueberryWineFluidType::new);
    public static final RegistryObject<FlowingFluid> SAP = FLUIDS.register("sap", () -> new ForgeFlowingFluid.Source(HHModFluids.SAP_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SAP = FLUIDS.register("flowing_sap", () -> new ForgeFlowingFluid.Flowing(HHModFluids.SAP_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties SAP_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(SAP_FLUID_TYPE, SAP, FLOWING_SAP);

    public static final RegistryObject<FluidType> BLUEBERRY_WINE_FLUID_TYPE = FLUID_TYPES.register("blueberry_wine", BlueberryWineFluidType::new);
    public static final RegistryObject<FlowingFluid> BLUEBERRY_WINE = FLUIDS.register("blueberry_wine", () -> new ForgeFlowingFluid.Source(HHModFluids.BLUEBERRY_WINE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_BLUEBERRY_WINE = FLUIDS.register("flowing_blueberry_wine", () -> new ForgeFlowingFluid.Flowing(HHModFluids.BLUEBERRY_WINE_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties BLUEBERRY_WINE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(BLUEBERRY_WINE_FLUID_TYPE, BLUEBERRY_WINE, FLOWING_BLUEBERRY_WINE);

    public static final RegistryObject<FluidType> CHERRY_WINE_FLUID_TYPE = FLUID_TYPES.register("cherry_wine", CherryWineFluidType::new);
    public static final RegistryObject<FlowingFluid> CHERRY_WINE = FLUIDS.register("cherry_wine", () -> new ForgeFlowingFluid.Source(HHModFluids.CHERRY_WINE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CHERRY_WINE = FLUIDS.register("flowing_cherry_wine", () -> new ForgeFlowingFluid.Flowing(HHModFluids.CHERRY_WINE_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties CHERRY_WINE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(CHERRY_WINE_FLUID_TYPE, CHERRY_WINE, FLOWING_CHERRY_WINE);

    public static final RegistryObject<FluidType> GREEN_GRAPE_WINE_FLUID_TYPE = FLUID_TYPES.register("green_grape_wine", GreenGrapeWineFluidType::new);
    public static final RegistryObject<FlowingFluid> GREEN_GRAPE_WINE = FLUIDS.register("green_grape_wine", () -> new ForgeFlowingFluid.Source(HHModFluids.GREEN_GRAPE_WINE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_GREEN_GRAPE_WINE = FLUIDS.register("flowing_green_grape_wine", () -> new ForgeFlowingFluid.Flowing(HHModFluids.GREEN_GRAPE_WINE_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties GREEN_GRAPE_WINE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(GREEN_GRAPE_WINE_FLUID_TYPE, GREEN_GRAPE_WINE, FLOWING_GREEN_GRAPE_WINE);

    public static final RegistryObject<FluidType> RASPBERRY_WINE_FLUID_TYPE = FLUID_TYPES.register("raspberry_wine", RaspberryWineFluidType::new);
    public static final RegistryObject<FlowingFluid> RASPBERRY_WINE = FLUIDS.register("raspberry_wine", () -> new ForgeFlowingFluid.Source(HHModFluids.RASPBERRY_WINE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_RASPBERRY_WINE = FLUIDS.register("flowing_raspberry_wine", () -> new ForgeFlowingFluid.Flowing(HHModFluids.RASPBERRY_WINE_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties RASPBERRY_WINE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(RASPBERRY_WINE_FLUID_TYPE, RASPBERRY_WINE, FLOWING_RASPBERRY_WINE);

    public static final RegistryObject<FluidType> RED_GRAPE_WINE_FLUID_TYPE = FLUID_TYPES.register("red_grape_wine", RedGrapeWineFluidType::new);
    public static final RegistryObject<FlowingFluid> RED_GRAPE_WINE = FLUIDS.register("red_grape_wine", () -> new ForgeFlowingFluid.Source(HHModFluids.RED_GRAPE_WINE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_RED_GRAPE_WINE = FLUIDS.register("flowing_red_grape_wine", () -> new ForgeFlowingFluid.Flowing(HHModFluids.RED_GRAPE_WINE_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties RED_GRAPE_WINE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(RED_GRAPE_WINE_FLUID_TYPE, RED_GRAPE_WINE, FLOWING_RED_GRAPE_WINE);

    public static final RegistryObject<FluidType> SWEET_BERRY_WINE_FLUID_TYPE = FLUID_TYPES.register("sweet_berry_wine", SweetBerryWineFluidType::new);
    public static final RegistryObject<FlowingFluid> SWEET_BERRY_WINE = FLUIDS.register("sweet_berry_wine", () -> new ForgeFlowingFluid.Source(HHModFluids.SWEET_BERRY_WINE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SWEET_BERRY_WINE = FLUIDS.register("flowing_sweet_berry_wine", () -> new ForgeFlowingFluid.Flowing(HHModFluids.SWEET_BERRY_WINE_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties SWEET_BERRY_WINE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(SWEET_BERRY_WINE_FLUID_TYPE, SWEET_BERRY_WINE, FLOWING_SWEET_BERRY_WINE);

    public static final RegistryObject<FluidType> MEAD_FLUID_TYPE = FLUID_TYPES.register("mead", MeadFluidType::new);
    public static final RegistryObject<FlowingFluid> MEAD = FLUIDS.register("mead", () -> new ForgeFlowingFluid.Source(HHModFluids.MEAD_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MEAD = FLUIDS.register("flowing_mead", () -> new ForgeFlowingFluid.Flowing(HHModFluids.MEAD_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties MEAD_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(MEAD_FLUID_TYPE, MEAD, FLOWING_MEAD);
}
