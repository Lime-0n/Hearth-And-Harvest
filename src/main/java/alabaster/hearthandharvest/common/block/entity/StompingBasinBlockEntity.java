package alabaster.hearthandharvest.common.block.entity;

import alabaster.hearthandharvest.common.crafting.StompingBasinRecipe;
import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import alabaster.hearthandharvest.common.registry.HHModRecipeTypes;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class StompingBasinBlockEntity extends BlockEntity {

    public static final int ITEM_SLOTS    = 1;
    public static final int TANK_CAPACITY = 8000;

    private final Map<UUID, Long> stompCooldowns = new HashMap<>();

    private final ItemStackHandler itemHandler = new ItemStackHandler(ITEM_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            syncToClient();
        }
    };

    private final FluidTank fluidTank = new FluidTank(TANK_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            syncToClient();
        }
    };

    public StompingBasinBlockEntity(BlockPos pos, BlockState state) {
        super(HHModBlockEntities.STOMPING_BASIN.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, StompingBasinBlockEntity be) {
        // Reserved for future use (slow fermentation, particles, etc.)
    }

    public ItemStack insertItem(ItemStack toInsert) {
        if (toInsert.isEmpty()) return ItemStack.EMPTY;
        if (!toInsert.is(HHModTags.STOMPABLE)) return toInsert;

        for (int i = 0; i < itemHandler.getSlots() && !toInsert.isEmpty(); i++) {
            ItemStack inSlot = itemHandler.getStackInSlot(i);
            if (!inSlot.isEmpty() && ItemStack.isSameItemSameComponents(inSlot, toInsert)) {
                toInsert = itemHandler.insertItem(i, toInsert, false);
            }
        }

        for (int i = 0; i < itemHandler.getSlots() && !toInsert.isEmpty(); i++) {
            if (itemHandler.getStackInSlot(i).isEmpty()) {
                toInsert = itemHandler.insertItem(i, toInsert, false);
            }
        }

        return toInsert;
    }

    public void extractOne(Player player) {
        for (int i = itemHandler.getSlots() - 1; i >= 0; i--) {
            ItemStack inSlot = itemHandler.getStackInSlot(i);
            if (!inSlot.isEmpty()) {
                ItemStack extracted = itemHandler.extractItem(i, 1, false);
                giveOrDrop(player, extracted);
                return;
            }
        }
    }

    public void extractAll(Player player) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack inSlot = itemHandler.getStackInSlot(i);
            if (!inSlot.isEmpty()) {
                ItemStack extracted = itemHandler.extractItem(i, inSlot.getCount(), false);
                giveOrDrop(player, extracted);
            }
        }
    }

    private void giveOrDrop(Player player, ItemStack stack) {
        if (stack.isEmpty()) return;
        if (!player.addItem(stack)) {
            dropAtBasin(stack);
        }
    }

    public void tryProcess(Player player) {
        if (level == null || level.isClientSide) return;
        long now = level.getGameTime();
        if (stompCooldowns.getOrDefault(player.getUUID(), -1L) == now) return;
        stompCooldowns.put(player.getUUID(), now);

        RecipeWrapper wrapper = new RecipeWrapper(itemHandler);
        if (wrapper.isEmpty()) return;

        Optional<StompingBasinRecipe> match = level.getRecipeManager()
                .getAllRecipesFor(HHModRecipeTypes.STOMPING.get())
                .stream()
                .map(holder -> holder.value())
                .filter(r -> r.matches(wrapper, level))
                .findFirst();

        if (match.isEmpty()) {
            level.playSound(null, worldPosition, SoundEvents.WOOD_STEP, SoundSource.BLOCKS, 0.6f, 0.7f);
            return;
        }
        else {
            level.playSound(null, worldPosition, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 0.6f, 0.7f);
        }

        StompingBasinRecipe recipe = match.get();

        FluidStack resultFluid = recipe.getResultFluid();
        if (!resultFluid.isEmpty()) {
            int accepted = fluidTank.fill(resultFluid.copy(), IFluidHandler.FluidAction.SIMULATE);
            if (accepted < resultFluid.getAmount()) return;
        }

        for (Ingredient ingredient : recipe.getIngredients()) {
            for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
                ItemStack slotStack = itemHandler.getStackInSlot(slot);
                if (!slotStack.isEmpty() && ingredient.test(slotStack)) {
                    itemHandler.extractItem(slot, 1, false);
                    break;
                }
            }
        }

        if (!resultFluid.isEmpty()) {
            fluidTank.fill(resultFluid.copy(), IFluidHandler.FluidAction.EXECUTE);
        }

        ItemStack resultItem = recipe.getResultItem();
        if (!resultItem.isEmpty()) {
            dropAtBasin(resultItem.copy());
        }

        setChanged();
        syncToClient();
    }

    private void dropAtBasin(ItemStack stack) {
        if (level == null || stack.isEmpty()) return;

        double x = worldPosition.getX() + 0.5;
        double y = worldPosition.getY() + 0.25;
        double z = worldPosition.getZ() + 0.5;

        ItemEntity entity = new ItemEntity(level, x, y, z, stack, 0, 0, 0);
        entity.setPickUpDelay(10);
        level.addFreshEntity(entity);
    }

    public void dropContents() {
        if (level == null) return;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack s = itemHandler.getStackInSlot(i);
            if (!s.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), s);
            }
        }
    }

    private void syncToClient() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        if (pkt.getTag() != null) loadAdditional(pkt.getTag(), lookupProvider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Items", itemHandler.serializeNBT(registries));
        tag.put("Tank", fluidTank.writeToNBT(registries, new CompoundTag()));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Items")) itemHandler.deserializeNBT(registries, tag.getCompound("Items"));
        if (tag.contains("Tank")) fluidTank.readFromNBT(registries, tag.getCompound("Tank"));
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }
}