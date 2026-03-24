package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FluidState;

public class GlowBerryJuiceFluid extends HHFluidType {
    public GlowBerryJuiceFluid(boolean source) {
        super(new Properties(HHModFluids.GLOW_BERRY_JUICE_TYPE, HHModFluids.GLOW_BERRY_JUICE, HHModFluids.FLOWING_GLOW_BERRY_JUICE), source);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 1000;
    }

    public Item getBucket() {
        return HHModItems.GLOW_BERRY_CRATE.get();
    }
}