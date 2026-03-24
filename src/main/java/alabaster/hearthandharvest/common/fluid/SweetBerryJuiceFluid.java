package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class SweetBerryJuiceFluid extends HHFluidType {
    public SweetBerryJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.SWEET_BERRY_JUICE_TYPE, HHModFluids.SWEET_BERRY_JUICE, HHModFluids.FLOWING_SWEET_BERRY_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.SWEET_BERRY_WINE.get();
    }
}