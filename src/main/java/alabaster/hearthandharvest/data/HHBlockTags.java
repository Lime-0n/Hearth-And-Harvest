package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.tag.HHCommonTags;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.common.tag.CompatibilityTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HHBlockTags extends BlockTagsProvider {
    public HHBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, HearthAndHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerModTags();
        this.registerMinecraftTags();
        this.registerCommonTags();
        this.registerCompatTags();
        this.registerFDTags();
        this.registerBlockMineables();
    }

    protected void registerModTags() {
        tag(HHModTags.TAPPABLE).add(
                Blocks.SPRUCE_LOG,
                Blocks.DARK_OAK_LOG
        );
    }

    protected void registerMinecraftTags() {
        tag(BlockTags.CROPS).add(
                HHModBlocks.RED_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_RED_GRAPE_CROP.get(),
                HHModBlocks.GREEN_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_GREEN_GRAPE_CROP.get(),
                HHModBlocks.COTTON_CROP.get(),
                HHModBlocks.PEANUT_CROP.get(),
                HHModBlocks.CORN_STALK.get()
        );

        tag(BlockTags.MAINTAINS_FARMLAND).add(
                HHModBlocks.RED_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_RED_GRAPE_CROP.get(),
                HHModBlocks.GREEN_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_GREEN_GRAPE_CROP.get(),
                HHModBlocks.COTTON_CROP.get(),
                HHModBlocks.PEANUT_CROP.get(),
                HHModBlocks.CORN_STALK.get());

        tag(BlockTags.FLOWERS).add(
                HHModBlocks.YELLOW_MUM.get(),
                HHModBlocks.ORANGE_MUM.get(),
                HHModBlocks.RED_MUM.get(),
                HHModBlocks.BLUE_MUM.get(),
                HHModBlocks.LIGHT_BLUE_MUM.get(),
                HHModBlocks.PURPLE_MUM.get(),
                HHModBlocks.PINK_MUM.get(),
                HHModBlocks.WHITE_MUM.get());

        tag(BlockTags.SMALL_FLOWERS).add(
                HHModBlocks.YELLOW_MUM.get(),
                HHModBlocks.ORANGE_MUM.get(),
                HHModBlocks.RED_MUM.get(),
                HHModBlocks.BLUE_MUM.get(),
                HHModBlocks.LIGHT_BLUE_MUM.get(),
                HHModBlocks.PURPLE_MUM.get(),
                HHModBlocks.PINK_MUM.get(),
                HHModBlocks.WHITE_MUM.get());

    }

    protected void registerCommonTags() {
        tag(HHCommonTags.STORAGE_BLOCKS_CORN).add(
                HHModBlocks.CORN_CRATE.get()
        );
    }

    protected void registerFDTags() {
        tag(ModTags.WILD_CROPS).add(
                HHModBlocks.WILD_RED_GRAPES.get(),
                HHModBlocks.WILD_GREEN_GRAPES.get(),
                HHModBlocks.WILD_COTTON.get(),
                HHModBlocks.WILD_PEANUTS.get()
                );
    }

    protected void registerCompatTags() {
        tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(
                HHModBlocks.CORN_STALK.get());

        tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS_BLOCK).add(
                HHModBlocks.CORN_STALK.get());
    }

    protected void registerBlockMineables() {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                HHModBlocks.TREE_TAPPER.get(),
                HHModBlocks.CASK.get(),
                HHModBlocks.OAK_HALF_CABINET.get(),
                HHModBlocks.BIRCH_HALF_CABINET.get(),
                HHModBlocks.SPRUCE_HALF_CABINET.get(),
                HHModBlocks.JUNGLE_HALF_CABINET.get(),
                HHModBlocks.ACACIA_HALF_CABINET.get(),
                HHModBlocks.DARK_OAK_HALF_CABINET.get(),
                HHModBlocks.MANGROVE_HALF_CABINET.get(),
                HHModBlocks.CHERRY_HALF_CABINET.get(),
                HHModBlocks.BAMBOO_HALF_CABINET.get(),
                HHModBlocks.CRIMSON_HALF_CABINET.get(),
                HHModBlocks.WARPED_HALF_CABINET.get(),
                HHModBlocks.OAK_HALF_CABINET.get(),
                HHModBlocks.BIRCH_WINE_RACK.get(),
                HHModBlocks.SPRUCE_WINE_RACK.get(),
                HHModBlocks.JUNGLE_WINE_RACK.get(),
                HHModBlocks.ACACIA_WINE_RACK.get(),
                HHModBlocks.DARK_OAK_WINE_RACK.get(),
                HHModBlocks.MANGROVE_WINE_RACK.get(),
                HHModBlocks.CHERRY_WINE_RACK.get(),
                HHModBlocks.BAMBOO_WINE_RACK.get(),
                HHModBlocks.CRIMSON_WINE_RACK.get(),
                HHModBlocks.WARPED_WINE_RACK.get(),
                HHModBlocks.RASPBERRY_CRATE.get(),
                HHModBlocks.BLUEBERRY_CRATE.get(),
                HHModBlocks.RED_GRAPE_CRATE.get(),
                HHModBlocks.GREEN_GRAPE_CRATE.get(),
                HHModBlocks.CHERRY_CRATE.get(),
                HHModBlocks.PEANUT_CRATE.get(),
                HHModBlocks.CORN_CRATE.get(),
                HHModBlocks.APPLE_CRATE.get(),
                HHModBlocks.GOLDEN_APPLE_CRATE.get(),
                HHModBlocks.GOLDEN_CARROT_CRATE.get(),
                HHModBlocks.POISONOUS_POTATO_CRATE.get(),
                HHModBlocks.ROTTEN_TOMATO_CRATE.get(),
                HHModBlocks.GLOW_BERRY_CRATE.get(),
                HHModBlocks.SWEET_BERRY_CRATE.get(),
                HHModBlocks.EGG_CRATE.get(),
                HHModBlocks.TURTLE_EGG_CRATE.get(),
                HHModBlocks.MILK_CRATE.get(),
                HHModBlocks.GOAT_MILK_CRATE.get(),
                HHModBlocks.BROWN_MUSHROOM_CRATE.get(),
                HHModBlocks.RED_MUSHROOM_CRATE.get(),
                HHModBlocks.WARPED_FUNGUS_CRATE.get(),
                HHModBlocks.CRIMSON_FUNGUS_CRATE.get(),
                HHModBlocks.BLUEBERRY_WINE_CRATE.get(),
                HHModBlocks.CHERRY_WINE_CRATE.get(),
                HHModBlocks.RASPBERRY_WINE_CRATE.get(),
                HHModBlocks.RED_GRAPE_WINE_CRATE.get(),
                HHModBlocks.GREEN_GRAPE_WINE_CRATE.get(),
                HHModBlocks.SWEET_BERRY_WINE_CRATE.get(),
                HHModBlocks.MEAD_CRATE.get(),
                HHModBlocks.WATER_CRATE.get(),
                HHModBlocks.HONEY_CRATE.get(),
                HHModBlocks.SYRUP_CRATE.get(),
                HHModBlocks.CORN_STALK.get()
        );

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                HHModBlocks.JUG.get(),
                HHModBlocks.SAP_CAULDRON.get(),
                HHModBlocks.COUNTER.get(),
                HHModBlocks.DRAWER.get(),
                HHModBlocks.BASIN.get()
        );
    }
}
