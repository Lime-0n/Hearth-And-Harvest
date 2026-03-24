package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class RedGrapeJuiceFluid extends HHFluidType {
    public RedGrapeJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.RED_GRAPE_JUICE_TYPE, HHModFluids.RED_GRAPE_JUICE, HHModFluids.FLOWING_RED_GRAPE_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.RED_GRAPE_JUICE.get();
    }
}