package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;
import vectorwing.farmersdelight.common.registry.ModItems;

public class MelonJuiceFluid extends HHFluidType {
    public MelonJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.MELON_JUICE_TYPE, HHModFluids.MELON_JUICE, HHModFluids.FLOWING_MELON_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return ModItems.MELON_JUICE.get();
    }
}