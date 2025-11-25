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

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_BLUEBERRY = commonItemTag("storage_blocks/blueberry");
    public static final TagKey<Block> STORAGE_BLOCKS_BLUEBERRY = commonBlockTag("storage_blocks/blueberry");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_RASPBERRY = commonItemTag("storage_blocks/raspberry");
    public static final TagKey<Block> STORAGE_BLOCKS_RASPBERRY = commonBlockTag("storage_blocks/raspberry");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_GRAPE = commonItemTag("storage_blocks/grape");
    public static final TagKey<Block> STORAGE_BLOCKS_GRAPE = commonBlockTag("storage_blocks/grape");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_CHERRY = commonItemTag("storage_blocks/cherry");
    public static final TagKey<Block> STORAGE_BLOCKS_CHERRY = commonBlockTag("storage_blocks/cherry");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_PEANUT = commonItemTag("storage_blocks/peanut");
    public static final TagKey<Block> STORAGE_BLOCKS_PEANUT = commonBlockTag("storage_blocks/peanut");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_COTTON = commonItemTag("storage_blocks/cotton");
    public static final TagKey<Block> STORAGE_BLOCKS_COTTON = commonBlockTag("storage_blocks/cotton");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM_CORN = commonItemTag("storage_blocks/corn");
    public static final TagKey<Block> STORAGE_BLOCKS_CORN = commonBlockTag("storage_blocks/corn");

    public static final TagKey<Item> DRINKS_JUICE = commonItemTag("drinks/juice");
    public static final TagKey<Item> DRINKS_ALCOHOL = commonItemTag("drinks/alcohol");


    private static TagKey<Block> commonBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private static TagKey<Item> commonItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
