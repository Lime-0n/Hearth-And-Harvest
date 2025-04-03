package alabaster.hearthandharvest.common.block.entity;

import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class JugBlockEntity extends BlockEntity {

    private final FluidTank fluidTank;

    public JugBlockEntity(BlockPos pos, BlockState state) {
        super(HHModBlockEntities.JUG.get(), pos, state);
        this.fluidTank = createFluidTank();
    }

    public FluidStack getOutput() {
        return fluidTank.getFluid();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        fluidTank.readFromNBT(compound.getCompound("FluidTank"));
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        CompoundTag tankTag = new CompoundTag();
        fluidTank.writeToNBT(tankTag);
        compound.put("FluidTank", tankTag);
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return fluidTank.fill(resource, action);
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return fluidTank.drain(maxDrain, action);
    }

    private FluidTank createFluidTank() {
        return new FluidTank(8000) {
            @Override
            protected void onContentsChanged() {
                super.onContentsChanged();
                setChanged();
            }
        };
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = new CompoundTag();
        saveAdditional(compound);
        return compound;
    }
}
