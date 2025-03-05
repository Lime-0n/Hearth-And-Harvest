package alabaster.hearthandharvest.common.block.entity;

import alabaster.hearthandharvest.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

public class JugBlockEntity extends SyncedBlockEntity {

    private final FluidTank fluidTank;
    private final Lazy<FluidTank> fluidTankHandler;

    public JugBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.JUG.get(), pos, state);
        this.fluidTank = createFluidTank();
        this.fluidTankHandler = Lazy.of(() -> fluidTank);
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

    public FluidStack getOutput() {
        return fluidTank.getFluid();
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadAdditional(compound, provider);
        readFromNbt(compound.getCompound("FluidTank"), provider);
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        compound.put("FluidTank", writeToNbt(provider));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return writeUpdateTag(new CompoundTag(), provider);
    }

    private CompoundTag writeUpdateTag(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        compound.put("FluidTank", writeToNbt(provider));
        return compound;
    }

    public void readFromNbt(CompoundTag tag, HolderLookup.Provider provider) {
        fluidTank.readFromNBT(provider, tag);
    }

    public CompoundTag writeToNbt(HolderLookup.Provider provider) {
        return fluidTank.writeToNBT(provider, new CompoundTag());
    }
}