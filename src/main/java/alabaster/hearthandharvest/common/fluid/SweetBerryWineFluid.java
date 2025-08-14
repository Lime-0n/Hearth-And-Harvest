package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class SweetBerryWineFluid extends HHFluidType {
    public SweetBerryWineFluid(boolean source) {
        super(new Properties(HHModFluids.SWEET_BERRY_WINE_TYPE, HHModFluids.SWEET_BERRY_WINE, HHModFluids.FLOWING_SWEET_BERRY_WINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.SWEET_BERRY_WINE.get();
    }
}