package alabaster.hearthandharvest.common.tag;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class HHModTags {

    // Blocks that can produce sap
    public static final TagKey<Block> TAPPABLE = modBlockTag("tappable");

    public static final TagKey<Item> CLEAVERS = modItemTag("cleavers");
    public static final TagKey<Item> JAMS = modItemTag("jelly");
    public static final TagKey<Item> CHEESE_SLICES = modItemTag("cheese_slices");

    // Items that have a bottle as their container/craft remainder
    public static final TagKey<Item> BOTTLES = modItemTag("bottles");

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, path));
    }


    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, path));
    }

}
