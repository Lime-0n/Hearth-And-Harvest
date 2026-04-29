package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;
import vectorwing.farmersdelight.common.registry.ModItems;

public class AppleCiderFluid extends HHFluidType {
    public AppleCiderFluid(boolean source) {
        super(new Properties(HHModFluids.APPLE_CIDER_TYPE, HHModFluids.APPLE_CIDER, HHModFluids.FLOWING_APPLE_CIDER), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return ModItems.APPLE_CIDER.get();
    }
}