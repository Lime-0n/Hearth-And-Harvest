package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class RaspberryJuiceFluid extends HHFluidType {
    public RaspberryJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.RASPBERRY_JUICE_TYPE, HHModFluids.RASPBERRY_JUICE, HHModFluids.FLOWING_RASPBERRY_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.RASPBERRY_JUICE.get();
    }
}