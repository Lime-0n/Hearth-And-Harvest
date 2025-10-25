package alabaster.hearthandharvest.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class HHCommonTags {
    public static final TagKey<Item> CROPS_CORN = commonItemTag("crops/corn");
    public static final TagKey<Item> CROPS_GRAIN = commonItemTag("crops/grain");
    public static final TagKey<Item> SEEDS_CORN = commonItemTag("seeds/corn");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_CORN = commonItemTag("storage_blocks/corn");
    public static final TagKey<Block> STORAGE_BLOCKS_CORN = commonBlockTag("storage_blocks/corn");

    private static TagKey<Block> commonBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private static TagKey<Item> commonItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
