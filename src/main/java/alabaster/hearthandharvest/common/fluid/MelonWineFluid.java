package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;
import vectorwing.farmersdelight.common.registry.ModItems;

public class MelonWineFluid extends HHFluidType {
    public MelonWineFluid(boolean source) {
        super(new Properties(HHModFluids.MELON_WINE_TYPE, HHModFluids.MELON_WINE, HHModFluids.FLOWING_MELON_WINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.MELON_WINE.get();
    }
}