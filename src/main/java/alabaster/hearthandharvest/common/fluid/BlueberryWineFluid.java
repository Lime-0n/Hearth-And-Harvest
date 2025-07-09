package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class BlueberryWineFluid extends HHFluidType {
    public BlueberryWineFluid(boolean source) {
        super(new Properties(HHModFluids.BLUEBERRY_WINE_TYPE, HHModFluids.BLUEBERRY_WINE, HHModFluids.FLOWING_BLUEBERRY_WINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.BLUEBERRY_WINE.get();
    }
}