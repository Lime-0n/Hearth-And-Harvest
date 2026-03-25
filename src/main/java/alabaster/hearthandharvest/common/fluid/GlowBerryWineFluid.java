package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class GlowBerryWineFluid extends HHFluidType {
    public GlowBerryWineFluid(boolean source) {
        super(new Properties(HHModFluids.GLOW_BERRY_WINE_TYPE, HHModFluids.GLOW_BERRY_WINE, HHModFluids.FLOWING_GLOW_BERRY_WINE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.GLOW_BERRY_WINE.get();
    }
}