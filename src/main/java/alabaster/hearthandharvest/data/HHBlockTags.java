package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.tag.HHCommonTags;
import alabaster.hearthandharvest.common.tag.HHCompatibilityTags;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.common.registry.ModBlocks;
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

        tag(HHModTags.CROW_EDIBLE_CROPS).add(
                Blocks.WHEAT,
                Blocks.PUMPKIN_STEM,
                Blocks.MELON_STEM,
                Blocks.SWEET_BERRY_BUSH,
                Blocks.CAVE_VINES_PLANT,
                ModBlocks.CABBAGE_CROP.get(),
                ModBlocks.TOMATO_CROP.get(),
                ModBlocks.BUDDING_TOMATO_CROP.get(),
                HHModBlocks.BLUEBERRY_BUSH.get(),
                HHModBlocks.RASPBERRY_BUSH.get(),
                HHModBlocks.GREEN_GRAPE_CROP.get(),
                HHModBlocks.RED_GRAPE_CROP.get(),
                HHModBlocks.PEANUT_CROP.get(),
                HHModBlocks.CORN_STALK.get()
        );

        tag(HHModTags.REPELS_CROWS).add(
                Blocks.CARVED_PUMPKIN,
                Blocks.JACK_O_LANTERN,
                HHModBlocks.SCARECROW.get()
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
                HHModBlocks.WILD_COTTON.get(),
                HHModBlocks.WILD_PEANUTS.get(),
                HHModBlocks.WILD_GREEN_GRAPES.get(),
                HHModBlocks.WILD_RED_GRAPES.get(),
                HHModBlocks.YELLOW_MUM.get(),
                HHModBlocks.ORANGE_MUM.get(),
                HHModBlocks.RED_MUM.get(),
                HHModBlocks.BLUE_MUM.get(),
                HHModBlocks.LIGHT_BLUE_MUM.get(),
                HHModBlocks.PURPLE_MUM.get(),
                HHModBlocks.PINK_MUM.get(),
                HHModBlocks.WHITE_MUM.get());

        tag(BlockTags.FLOWER_POTS).add(
                HHModBlocks.POTTED_YELLOW_MUM.get(),
                HHModBlocks.POTTED_ORANGE_MUM.get(),
                HHModBlocks.POTTED_RED_MUM.get(),
                HHModBlocks.POTTED_BLUE_MUM.get(),
                HHModBlocks.POTTED_LIGHT_BLUE_MUM.get(),
                HHModBlocks.POTTED_PURPLE_MUM.get(),
                HHModBlocks.POTTED_PINK_MUM.get(),
                HHModBlocks.POTTED_WHITE_MUM.get());

    }

    protected void registerCommonTags() {
        tag(HHCommonTags.STORAGE_BLOCKS_BLUEBERRY)
                .add(HHModBlocks.BLUEBERRY_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_RASPBERRY)
                .add(HHModBlocks.RASPBERRY_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_GRAPE)
                .add(HHModBlocks.GREEN_GRAPE_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_GRAPE)
                .add(HHModBlocks.RED_GRAPE_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_CHERRY)
                .add(HHModBlocks.CHERRY_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_PEANUT)
                .add(HHModBlocks.PEANUT_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_COTTON)
                .add(HHModBlocks.COTTON_BALE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_CORN)
                .add(HHModBlocks.CORN_CRATE.get());

        tag(Tags.Blocks.STORAGE_BLOCKS).add(
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
                HHModBlocks.ROOT_BEER_CRATE.get(),
                HHModBlocks.HARD_CIDER_CRATE.get(),
                HHModBlocks.WATER_CRATE.get(),
                HHModBlocks.HONEY_CRATE.get(),
                HHModBlocks.SYRUP_CRATE.get(),
                HHModBlocks.GUNPOWDER_BAG.get(),
                HHModBlocks.SALT_BAG.get(),
                HHModBlocks.SUGAR_BAG.get(),
                HHModBlocks.COCOA_BEAN_BAG.get(),
                HHModBlocks.COTTON_BALE.get(),
                HHModBlocks.SPOOL.get(),
                HHModBlocks.ROPE_COIL.get(),
                HHModBlocks.CORN_HUSK_BUNDLE.get(),
                HHModBlocks.CORN_KERNEL_BAG.get()
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

        tag(CompatibilityTags.SERENE_SEASONS_SPRING_CROPS_BLOCK).add(
                HHModBlocks.BLUEBERRY_BUSH.get(),
                HHModBlocks.RASPBERRY_BUSH.get(),
                HHModBlocks.PEANUT_CROP.get()
        );

        tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(
                HHModBlocks.BLUEBERRY_BUSH.get(),
                HHModBlocks.RASPBERRY_BUSH.get(),
                HHModBlocks.RED_GRAPE_CROP.get(),
                HHModBlocks.GREEN_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_GREEN_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_RED_GRAPE_CROP.get(),
                HHModBlocks.COTTON_CROP.get(),
                HHModBlocks.PEANUT_CROP.get(),
                HHModBlocks.CORN_STALK.get()
        );

        tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS_BLOCK).add(
                HHModBlocks.RED_GRAPE_CROP.get(),
                HHModBlocks.GREEN_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_GREEN_GRAPE_CROP.get(),
                HHModBlocks.BUDDING_RED_GRAPE_CROP.get(),
                HHModBlocks.COTTON_CROP.get(),
                HHModBlocks.CORN_STALK.get()
        );

        tag(HHCompatibilityTags.SERENE_SEASONS_UNBREAKABLE_INFERTILE_CROPS).add(
                HHModBlocks.BLUEBERRY_BUSH.get(),
                HHModBlocks.RASPBERRY_BUSH.get(),
                HHModBlocks.CORN_STALK.get()
        );
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
                HHModBlocks.ROOT_BEER_CRATE.get(),
                HHModBlocks.HARD_CIDER_CRATE.get(),
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

        tag(BlockTags.MINEABLE_WITH_HOE).add(
                HHModBlocks.NEST.get(),
                HHModBlocks.CORN_HUSK_BUNDLE.get(),
                HHModBlocks.COTTON_BALE.get()
        );

        tag(ModTags.MINEABLE_WITH_KNIFE).add(
                HHModBlocks.HAY_RUG.get(),
                HHModBlocks.SALT_BAG.get(),
                HHModBlocks.SUGAR_BAG.get(),
                HHModBlocks.COCOA_BEAN_BAG.get(),
                HHModBlocks.CORN_KERNEL_BAG.get(),
                HHModBlocks.GUNPOWDER_BAG.get()
        );
    }
}
