package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class CookingOilFluid extends HHFluidType {
    public CookingOilFluid(boolean source) {
        super(new Properties(HHModFluids.COOKING_OIL_TYPE, HHModFluids.COOKING_OIL, HHModFluids.FLOWING_COOKING_OIL), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.COOKING_OIL.get();
    }

}