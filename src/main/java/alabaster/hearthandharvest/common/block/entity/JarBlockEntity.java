package alabaster.hearthandharvest.common.block.entity;

import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Arrays;

public class JarBlockEntity extends BlockEntity {

    private final Block[] slots = new Block[4];

    public JarBlockEntity(BlockPos pos, BlockState state) {
        super(HHModBlockEntities.JAR.get(), pos, state);
    }

    public void setSlot(int index, @Nullable Block displayBlock) {
        if (index < 0 || index >= 4) return;
        slots[index] = (displayBlock == Blocks.AIR) ? null : displayBlock;
        setChanged();
    }

    @Nullable
    public Block getSlot(int index) {
        if (index < 0 || index >= 4) return null;
        return slots[index];
    }

    public int getCount() {
        int count = 0;
        for (Block slot : slots) if (slot != null) count++;
        return count;
    }

    public void dropAllJars(Level level, BlockPos pos) {
        for (Block block : slots) {
            if (block != null && block != Blocks.AIR) {
                Containers.dropItemStack(level,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(block.asItem()));
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag slotsTag = new CompoundTag();
        for (int i = 0; i < 4; i++) {
            if (slots[i] != null) {
                slotsTag.putString("slot_" + i, BuiltInRegistries.BLOCK.getKey(slots[i]).toString());
            }
        }
        tag.put("slots", slotsTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        Arrays.fill(slots, null);
        CompoundTag slotsTag = tag.getCompound("slots");
        for (int i = 0; i < 4; i++) {
            String key = "slot_" + i;
            if (slotsTag.contains(key)) {
                ResourceLocation rl = ResourceLocation.parse(slotsTag.getString(key));
                Block block = BuiltInRegistries.BLOCK.get(rl);
                if (block != Blocks.AIR) slots[i] = block;
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, registries);
        }
    }
}