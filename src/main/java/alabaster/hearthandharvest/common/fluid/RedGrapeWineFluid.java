package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class RedGrapeWineFluid extends HHFluidType {
    public RedGrapeWineFluid(boolean source) {
        super(new Properties(HHModFluids.RED_GRAPE_WINE_TYPE, HHModFluids.RED_GRAPE_WINE, HHModFluids.FLOWING_RED_GRAPE_WINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.RED_GRAPE_WINE.get();
    }
}