package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class CherryWineFluid extends HHFluidType {
    public CherryWineFluid(boolean source) {
        super(new Properties(HHModFluids.CHERRY_WINE_TYPE, HHModFluids.CHERRY_WINE, HHModFluids.FLOWING_CHERRY_WINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.CHERRY_WINE.get();
    }
}