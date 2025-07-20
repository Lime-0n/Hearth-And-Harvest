package alabaster.hearthandharvest.data.loot;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
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
        dropOther(HHModBlocks.SAP_CAULDRON.get(), Items.CAULDRON);
        dropSelf(HHModBlocks.COUNTER.get());
        dropNamedContainer(HHModBlocks.DRAWER.get());
        dropSelf(HHModBlocks.BASIN.get());
        dropNamedContainer(HHModBlocks.OAK_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.SPRUCE_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.BIRCH_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.JUNGLE_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.ACACIA_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.DARK_OAK_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.MANGROVE_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.BAMBOO_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.CHERRY_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.CRIMSON_HALF_CABINET.get());
        dropNamedContainer(HHModBlocks.WARPED_HALF_CABINET.get());
        dropSelf(HHModBlocks.OAK_WINE_RACK.get());
        dropSelf(HHModBlocks.SPRUCE_WINE_RACK.get());
        dropSelf(HHModBlocks.BIRCH_WINE_RACK.get());
        dropSelf(HHModBlocks.JUNGLE_WINE_RACK.get());
        dropSelf(HHModBlocks.ACACIA_WINE_RACK.get());
        dropSelf(HHModBlocks.DARK_OAK_WINE_RACK.get());
        dropSelf(HHModBlocks.MANGROVE_WINE_RACK.get());
        dropSelf(HHModBlocks.BAMBOO_WINE_RACK.get());
        dropSelf(HHModBlocks.CHERRY_WINE_RACK.get());
        dropSelf(HHModBlocks.CRIMSON_WINE_RACK.get());
        dropSelf(HHModBlocks.WARPED_WINE_RACK.get());
        dropSelf(HHModBlocks.RASPBERRY_CRATE.get());
        dropSelf(HHModBlocks.BLUEBERRY_CRATE.get());
        dropSelf(HHModBlocks.RED_GRAPE_CRATE.get());
        dropSelf(HHModBlocks.GREEN_GRAPE_CRATE.get());
        dropSelf(HHModBlocks.CHERRY_CRATE.get());
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
        dropOther(HHModBlocks.RED_GRAPE_CROP.get(), HHModItems.RED_GRAPES.get());
        dropOther(HHModBlocks.GREEN_GRAPE_CROP.get(), HHModItems.GREEN_GRAPES.get());
    }
    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    protected void dropNamedContainer(Block block) {
        add(block, this::createNameableBlockEntityTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}
