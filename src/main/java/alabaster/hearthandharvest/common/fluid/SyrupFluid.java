package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class SyrupFluid extends HHFluidType {
    public SyrupFluid(boolean source) {
        super(new Properties(HHModFluids.SYRUP_TYPE, HHModFluids.SYRUP, HHModFluids.FLOWING_SYRUP), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.SYRUP_BOTTLE.get();
    }
}