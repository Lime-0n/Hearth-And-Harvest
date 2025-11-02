package alabaster.hearthandharvest.common.block.entity.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CaskItemHandler implements IItemHandler {
    private static final int SLOT_OUTPUT = 4;
    private final IItemHandler itemHandler;
    private final Direction side;

    public CaskItemHandler(IItemHandler itemHandler, @Nullable Direction side) {
        this.itemHandler = itemHandler;
        this.side = side;
    }

    @Override
    public int getSlots() {
        return itemHandler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!canInsert(slot)) {
            return stack;
        }
        return itemHandler.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (side == Direction.DOWN && slot == SLOT_OUTPUT) {
            return itemHandler.extractItem(slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return canInsert(slot);
    }

    private boolean canInsert(int slot) {
        if (side == null) return slot < SLOT_OUTPUT;

        return switch (side) {
            case UP -> slot < SLOT_OUTPUT;
            case NORTH -> slot == 0;
            case EAST -> slot == 1;
            case SOUTH -> slot == 2;
            case WEST -> slot == 3;
            default -> false;
        };
    }
}
