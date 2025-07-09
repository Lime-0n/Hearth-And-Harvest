package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class MeadFluid extends HHFluidType {
    public MeadFluid(boolean source) {
        super(new Properties(HHModFluids.MEAD_TYPE, HHModFluids.MEAD, HHModFluids.FLOWING_MEAD), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.MEAD.get();
    }
}