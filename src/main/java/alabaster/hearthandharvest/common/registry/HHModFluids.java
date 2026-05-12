package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.fluid.HHFluidType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Supplier;

public class HHModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, HearthAndHarvest.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, HearthAndHarvest.MODID);

    private static FluidType.Properties defaultProps() {
        return FluidType.Properties.create().viscosity(2000).density(1400);
    }

    private static FluidEntry registerFluid(String name, FluidType.Properties typeProps, int amount, Supplier<Item> bottleItem) {
        Supplier<FluidType> type = FLUID_TYPES.register(name, () -> new FluidType(typeProps));
        Supplier<HHFluidType>[] holders = new Supplier[2];
        holders[0] = FLUIDS.register(name, () ->
                new HHFluidType(new BaseFlowingFluid.Properties(type, holders[0], holders[1]),
                        true, bottleItem, amount));
        holders[1] = FLUIDS.register("flowing_" + name, () ->
                new HHFluidType(new BaseFlowingFluid.Properties(type, holders[0], holders[1]),
                        false, bottleItem, amount));
        return new FluidEntry(type, holders[0], holders[1]);
    }

    private static FluidEntry registerFluidWithBucket(String name, FluidType.Properties typeProps, int amount, Supplier<Item> bottleItem, Supplier<Item> bucketItem) {
        Supplier<FluidType> type = FLUID_TYPES.register(name, () -> new FluidType(typeProps));
        Supplier<HHFluidType>[] holders = new Supplier[2];
        holders[0] = FLUIDS.register(name, () ->
                new HHFluidType(
                        new BaseFlowingFluid.Properties(type, holders[0], holders[1]).bucket(bucketItem),
                        true, bottleItem, amount));
        holders[1] = FLUIDS.register("flowing_" + name, () ->
                new HHFluidType(
                        new BaseFlowingFluid.Properties(type, holders[0], holders[1]),
                        false, bottleItem, amount));
        return new FluidEntry(type, holders[0], holders[1]);
    }

    private static FluidEntry registerFluid(String name, FluidType.Properties typeProps,
                                            Supplier<Item> bottleItem) {
        return registerFluid(name, typeProps, 250, bottleItem);
    }

    private static FluidEntry registerFluid(String name, Supplier<Item> bottleItem) {
        return registerFluid(name, defaultProps(), 250, bottleItem);
    }

    public record FluidEntry(
            Supplier<FluidType> type,
            Supplier<HHFluidType> source,
            Supplier<HHFluidType> flowing
    ) {}

    public static final FluidEntry COOKING_OIL = registerFluid("cooking_oil",
            HHModItems.COOKING_OIL);

    public static final FluidEntry SAP = registerFluidWithBucket("sap", defaultProps(), 1000,
            () -> Items.AIR,
            HHModItems.SAP_BUCKET);

    public static final FluidEntry SYRUP = registerFluid("syrup",
            HHModItems.SYRUP_BOTTLE);

    public static final FluidEntry APPLE_CIDER = registerFluid("apple_cider",
            ModItems.APPLE_CIDER);

    public static final FluidEntry HARD_CIDER = registerFluid("hard_cider",
            HHModItems.HARD_CIDER);

    public static final FluidEntry ROOT_BEER = registerFluid("root_beer",
            HHModItems.ROOT_BEER);

    public static final FluidEntry MEAD = registerFluid("mead",
            HHModItems.MEAD);

    public static final FluidEntry MOONSHINE = registerFluid("moonshine",
            HHModItems.MOONSHINE);

    public static final FluidEntry BLUEBERRY_JUICE = registerFluid("blueberry_juice",
            HHModItems.BLUEBERRY_JUICE);

    public static final FluidEntry CHERRY_JUICE = registerFluid("cherry_juice",
            HHModItems.CHERRY_JUICE);

    public static final FluidEntry GREEN_GRAPE_JUICE = registerFluid("green_grape_juice",
            HHModItems.GREEN_GRAPE_JUICE);

    public static final FluidEntry RASPBERRY_JUICE = registerFluid("raspberry_juice",
            HHModItems.RASPBERRY_JUICE);

    public static final FluidEntry RED_GRAPE_JUICE = registerFluid("red_grape_juice",
            HHModItems.RED_GRAPE_JUICE);

    public static final FluidEntry SWEET_BERRY_JUICE = registerFluid("sweet_berry_juice",
            HHModItems.SWEET_BERRY_JUICE);

    public static final FluidEntry MELON_JUICE = registerFluid("melon_juice",
            ModItems.MELON_JUICE);

    public static final FluidEntry GLOW_BERRY_JUICE = registerFluid("glow_berry_juice",
            defaultProps().lightLevel(8),
            HHModItems.GLOW_BERRY_JUICE);

    public static final FluidEntry BLUEBERRY_WINE = registerFluid("blueberry_wine",
            HHModItems.BLUEBERRY_WINE);

    public static final FluidEntry CHERRY_WINE = registerFluid("cherry_wine",
            HHModItems.CHERRY_WINE);

    public static final FluidEntry GREEN_GRAPE_WINE = registerFluid("green_grape_wine",
            HHModItems.GREEN_GRAPE_WINE);

    public static final FluidEntry RASPBERRY_WINE = registerFluid("raspberry_wine",
            HHModItems.RASPBERRY_WINE);

    public static final FluidEntry RED_GRAPE_WINE = registerFluid("red_grape_wine",
            HHModItems.RED_GRAPE_WINE);

    public static final FluidEntry SWEET_BERRY_WINE = registerFluid("sweet_berry_wine",
            HHModItems.SWEET_BERRY_WINE);

    public static final FluidEntry GLOW_BERRY_WINE = registerFluid("glow_berry_wine",
            HHModItems.GLOW_BERRY_WINE);

    public static final FluidEntry MELON_WINE = registerFluid("melon_wine",
            HHModItems.MELON_WINE);
}