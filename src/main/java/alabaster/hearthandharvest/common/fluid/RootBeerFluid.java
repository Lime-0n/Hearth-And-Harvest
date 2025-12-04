package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class RootBeerFluid extends HHFluidType {
    public RootBeerFluid(boolean source) {
        super(new Properties(HHModFluids.ROOT_BEER_TYPE, HHModFluids.ROOT_BEER, HHModFluids.FLOWING_ROOT_BEER), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.ROOT_BEER.get();
    }
}