package alabaster.hearthandharvest.data.loot;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class HHBlockLoot extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public HHBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(HHModBlocks.TREE_TAPPER.get());
        dropSelf(HHModBlocks.CASK.get());
        dropSelf(HHModBlocks.JUG.get());
        dropSelf(HHModBlocks.RASPBERRY_CRATE.get());
        dropSelf(HHModBlocks.BLUEBERRY_CRATE.get());
        dropSelf(HHModBlocks.RED_GRAPE_CRATE.get());
        dropSelf(HHModBlocks.GREEN_GRAPE_CRATE.get());
        dropSelf(HHModBlocks.PEANUT_CRATE.get());
        dropSelf(HHModBlocks.APPLE_CRATE.get());
        dropSelf(HHModBlocks.GOLDEN_APPLE_CRATE.get());
        dropSelf(HHModBlocks.GOLDEN_CARROT_CRATE.get());
        dropSelf(HHModBlocks.POISONOUS_POTATO_CRATE.get());
        dropSelf(HHModBlocks.GLOW_BERRY_CRATE.get());
        dropSelf(HHModBlocks.SWEET_BERRY_CRATE.get());
        dropSelf(HHModBlocks.SALT_BAG.get());
        dropSelf(HHModBlocks.SUGAR_BAG.get());
        dropSelf(HHModBlocks.COCOA_BEAN_BAG.get());
        dropSelf(HHModBlocks.GUNPOWDER_BAG.get());
        dropSelf(HHModBlocks.COTTON_BALE.get());
        dropSelf(HHModBlocks.SPOOL.get());
        dropSelf(HHModBlocks.ROPE_COIL.get());
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}
