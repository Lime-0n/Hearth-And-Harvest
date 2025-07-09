package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class RaspberryWineFluid extends HHFluidType {
    public RaspberryWineFluid(boolean source) {
        super(new Properties(HHModFluids.RASPBERRY_WINE_TYPE, HHModFluids.RASPBERRY_WINE, HHModFluids.FLOWING_RASPBERRY_WINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.RASPBERRY_WINE.get();
    }
}