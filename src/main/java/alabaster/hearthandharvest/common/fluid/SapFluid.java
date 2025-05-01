package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class SapFluid extends HHFluidType {
    public SapFluid(boolean source) {
        super(new Properties(HHModFluids.SAP_TYPE, HHModFluids.SAP, HHModFluids.FLOWING_SAP), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.SAP_BUCKET.get();
    }

}