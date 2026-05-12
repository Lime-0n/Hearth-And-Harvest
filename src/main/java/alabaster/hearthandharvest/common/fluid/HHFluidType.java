package alabaster.hearthandharvest.common.fluid;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import java.util.function.Supplier;

public class HHFluidType extends BaseFlowingFluid {
    private final boolean source;
    private final Supplier<Item> bucketItem;
    private final int amount;

    public HHFluidType(BaseFlowingFluid.Properties properties, boolean source, Supplier<Item> bucketItem, int amount) {
        super(properties);
        this.source = source;
        this.bucketItem = bucketItem;
        this.amount = amount;
    }

    @Override
    public Fluid getSource() {
        return source ? this : super.getSource();
    }

    @Override
    public Fluid getFlowing() {
        return source ? super.getFlowing() : this;
    }

    @Override
    public boolean isSource(FluidState fluidState) {
        return source;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return source ? amount : 8;
    }

    @Override
    public Item getBucket() {
        return bucketItem.get();
    }
}