package alabaster.hearthandharvest.common.tag;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    // Blocks that can produce sap
    public static final TagKey<Block> TAPPABLE = modBlockTag("tappable");

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, path));
    }

}
