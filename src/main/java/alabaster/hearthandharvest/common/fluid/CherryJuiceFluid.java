package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class CherryJuiceFluid extends HHFluidType {
    public CherryJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.CHERRY_JUICE_TYPE, HHModFluids.CHERRY_JUICE, HHModFluids.FLOWING_CHERRY_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.CHERRY_JUICE.get();
    }
}