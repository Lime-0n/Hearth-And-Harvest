package alabaster.hearthandharvest.common.fluid;

import alabaster.hearthandharvest.common.registry.HHModFluids;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import java.util.Properties;

public class OilFluid extends BaseFlowingFluid {

    public OilFluid(Properties properties) {
        super(properties);
    }

    @Override
    public Item getBucket() {
        return HHModItems.COOKING_OIL.get();
    }

    @Override
    public boolean isSource(FluidState p_207193_1_) {
        return true;
    }

    @Override
    public int getAmount(FluidState p_207192_1_) {
        return 0;
    }
}