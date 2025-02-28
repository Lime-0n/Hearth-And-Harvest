package alabaster.hearthandharvest.data.loot;

import alabaster.hearthandharvest.common.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class HHBlockLoot extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public HHBlockLoot(HolderLookup.Provider holder) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), holder);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.TREE_TAPPER.get());
        dropSelf(ModBlocks.CASK.get());
        dropSelf(ModBlocks.JUG.get());
        dropSelf(ModBlocks.RASPBERRY_CRATE.get());
        dropSelf(ModBlocks.BLUEBERRY_CRATE.get());
        dropSelf(ModBlocks.GRAPE_CRATE.get());
        dropSelf(ModBlocks.PEANUT_CRATE.get());
        dropSelf(ModBlocks.APPLE_CRATE.get());
        dropSelf(ModBlocks.GOLDEN_APPLE_CRATE.get());
        dropSelf(ModBlocks.GOLDEN_CARROT_CRATE.get());
        dropSelf(ModBlocks.SALT_BAG.get());
        dropSelf(ModBlocks.SUGAR_BAG.get());
        dropSelf(ModBlocks.COCOA_BEAN_BAG.get());
        dropSelf(ModBlocks.GUNPOWDER_BAG.get());
        dropSelf(ModBlocks.COTTON_BALE.get());
        dropSelf(ModBlocks.SPOOL.get());
        dropSelf(ModBlocks.EGG_CRATE.get());
        dropSelf(ModBlocks.TURTLE_EGG_CRATE.get());
        dropSelf(ModBlocks.MILK_CRATE.get());
        dropSelf(ModBlocks.GOAT_MILK_CRATE.get());
        dropSelf(ModBlocks.MEAD_CRATE.get());
        dropSelf(ModBlocks.WINE_CRATE.get());
        dropSelf(ModBlocks.WATER_CRATE.get());
        dropSelf(ModBlocks.HONEY_CRATE.get());
        dropSelf(ModBlocks.BROWN_MUSHROOM_CRATE.get());
        dropSelf(ModBlocks.RED_MUSHROOM_CRATE.get());
        dropSelf(ModBlocks.CRIMSON_FUNGUS_CRATE.get());
        dropSelf(ModBlocks.WARPED_FUNGUS_CRATE.get());
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
