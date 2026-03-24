package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class GreenGrapeJuiceFluid extends HHFluidType {
    public GreenGrapeJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.GREEN_GRAPE_JUICE_TYPE, HHModFluids.GREEN_GRAPE_JUICE, HHModFluids.FLOWING_GREEN_GRAPE_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.GREEN_GRAPE_JUICE.get();
    }
}