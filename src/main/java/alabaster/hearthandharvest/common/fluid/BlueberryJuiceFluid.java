package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class BlueberryJuiceFluid extends HHFluidType {
    public BlueberryJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.BLUEBERRY_JUICE_TYPE, HHModFluids.BLUEBERRY_JUICE, HHModFluids.FLOWING_BLUEBERRY_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.BLUEBERRY_JUICE.get();
    }
}