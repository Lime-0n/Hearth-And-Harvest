package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class MoonshineFluid extends HHFluidType {
    public MoonshineFluid(boolean source) {
        super(new Properties(HHModFluids.MOONSHINE_TYPE, HHModFluids.MOONSHINE, HHModFluids.FLOWING_MOONSHINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.MOONSHINE.get();
    }
}