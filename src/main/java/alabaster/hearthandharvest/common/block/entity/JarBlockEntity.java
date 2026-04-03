package alabaster.hearthandharvest.common.block.entity;

import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JarBlockEntity extends BlockEntity {

    private final List<Item> jars = new ArrayList<>(4);

    public JarBlockEntity(BlockPos pos, BlockState state) {
        super(HHModBlockEntities.JAR.get(), pos, state);
    }

    // ─────── Data access ───────

    public void addJar(Item jar) {
        if (jars.size() < 4) {
            jars.add(jar);
            setChanged();
        }
    }

    public List<Item> getJars() {
        return Collections.unmodifiableList(jars);
    }

    public int getCount() {
        return jars.size();
    }

    // ─────── Drop all jars into the world ───────

    public void dropAllJars(Level level, BlockPos pos) {
        for (Item jar : jars) {
            if (jar != null && jar != Items.AIR) {
                Containers.dropItemStack(level,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(jar));
            }
        }
    }

    // ─────── NBT persistence ───────

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ListTag list = new ListTag();
        for (Item jar : jars) {
            CompoundTag entry = new CompoundTag();
            entry.putString("item", BuiltInRegistries.ITEM.getKey(jar).toString());
            list.add(entry);
        }
        tag.put("jars", list);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        jars.clear();
        ListTag list = tag.getList("jars", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size() && i < 4; i++) {
            CompoundTag entry = list.getCompound(i);
            ResourceLocation key = ResourceLocation.parse(entry.getString("item"));
            Item item = BuiltInRegistries.ITEM.get(key);
            if (item != Items.AIR) {
                jars.add(item);
            }
        }
    }

    // ─────── Client sync ───────

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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt,
                             HolderLookup.Provider registries) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, registries);
        }
    }
}