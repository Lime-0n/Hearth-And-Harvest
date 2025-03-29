package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.ModBlocks;
import alabaster.hearthandharvest.common.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, HearthAndHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerMinecraftTags();
        this.registerBlockMineables();
    }

    protected void registerMinecraftTags() {
        tag(net.minecraft.tags.BlockTags.CROPS).add(
                ModBlocks.GRAPE_CROP.get(),
                ModBlocks.BUDDING_GRAPE_CROP.get(),
                ModBlocks.COTTON_CROP.get(),
                ModBlocks.PEANUT_CROP.get());

        tag(ModTags.TAPPABLE).add(
                Blocks.SPRUCE_LOG,
                Blocks.DARK_OAK_LOG
        );
    }

    protected void registerBlockMineables() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
                ModBlocks.TREE_TAPPER.get(),
                ModBlocks.CASK.get(),
                ModBlocks.RASPBERRY_CRATE.get(),
                ModBlocks.BLUEBERRY_CRATE.get(),
                ModBlocks.GRAPE_CRATE.get(),
                ModBlocks.PEANUT_CRATE.get(),
                ModBlocks.APPLE_CRATE.get(),
                ModBlocks.GOLDEN_APPLE_CRATE.get(),
                ModBlocks.GOLDEN_CARROT_CRATE.get(),
                ModBlocks.POISONOUS_POTATO_CRATE.get(),
                ModBlocks.EGG_CRATE.get(),
                ModBlocks.TURTLE_EGG_CRATE.get(),
                ModBlocks.MILK_CRATE.get(),
                ModBlocks.GOAT_MILK_CRATE.get(),
                ModBlocks.BROWN_MUSHROOM_CRATE.get(),
                ModBlocks.RED_MUSHROOM_CRATE.get(),
                ModBlocks.WARPED_FUNGUS_CRATE.get(),
                ModBlocks.CRIMSON_FUNGUS_CRATE.get(),
                ModBlocks.MEAD_CRATE.get(),
                ModBlocks.WATER_CRATE.get(),
                ModBlocks.HONEY_CRATE.get()
        );

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.JUG.get(),
                ModBlocks.JAR.get()
        );
    }
}
