package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.tag.HHCommonTags;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.tag.CompatibilityTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HHItemTags extends ItemTagsProvider {

    public HHItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, HearthAndHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.registerModTags();
    }

    private void registerModTags() {
        tag(HHModTags.CLEAVERS)
                .add(HHModItems.FLINT_CLEAVER.get())
                .add(HHModItems.IRON_CLEAVER.get())
                .add(HHModItems.DIAMOND_CLEAVER.get())
                .add(HHModItems.GOLDEN_CLEAVER.get())
                .add(HHModItems.NETHERITE_CLEAVER.get());

        tag(ItemTags.PIGLIN_LOVED)
                .add(HHModItems.GOLDEN_CLEAVER.get());

        tag(ModTags.KNIVES)
                .addTag(HHModTags.CLEAVERS);

        tag(CommonTags.TOOLS_KNIFE)
                .addTag(HHModTags.CLEAVERS);

        tag(HHModTags.WINE_BOTTLES)
                .add(HHModItems.BLUEBERRY_WINE.get())
                .add(HHModItems.CHERRY_WINE.get())
                .add(HHModItems.GREEN_GRAPE_WINE.get())
                .add(HHModItems.MEAD.get())
                .add(HHModItems.RASPBERRY_WINE.get())
                .add(HHModItems.RED_GRAPE_WINE.get())
                .add(HHModItems.SWEET_BERRY_WINE.get());

        tag(HHModTags.JAMS)
                .add(HHModItems.GRAPE_JAM.get())
                .add(HHModItems.CHERRY_JAM.get())
                .add(HHModItems.RASPBERRY_JAM.get())
                .add(HHModItems.BLUEBERRY_JAM.get())
                .add(HHModItems.APPLE_JAM.get())
                .add(HHModItems.GLOW_BERRY_JAM.get())
                .add(HHModItems.SWEET_BERRY_JAM.get())
                .add(HHModItems.MELON_JAM.get());

        tag(CommonTags.FOODS_MILK)
                .add(HHModItems.GOAT_MILK_BOTTLE.get());

        tag(HHModTags.CHEESE_SLICES)
                .add(HHModItems.CHEDDAR_CHEESE_SLICE.get())
                .add(HHModItems.GOAT_CHEESE_SLICE.get());

        tag(Tags.Items.FOODS_BERRY)
                .add(HHModItems.BLUEBERRIES.get())
                .add(HHModItems.CHERRY.get())
                .add(HHModItems.RASPBERRY.get())
                .add(HHModItems.RED_GRAPES.get())
                .add(HHModItems.GREEN_GRAPES.get());

        tag(Tags.Items.SEEDS)
                .add(HHModItems.COTTON_SEEDS.get())
                .add(HHModItems.SUNFLOWER_SEEDS.get())
                .add(HHModItems.CORN_KERNELS.get());

        tag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .add(HHModItems.COTTON_SEEDS.get())
                .add(HHModItems.SUNFLOWER_SEEDS.get())
                .add(HHModItems.CORN_KERNELS.get())
                .add(HHModItems.PEANUT.get())
                .add(HHModItems.GREEN_GRAPES.get())
                .add(HHModItems.RED_GRAPES.get());

        tag(Tags.Items.CROPS)
                .add(HHModItems.BLUEBERRIES.get())
                .add(HHModItems.CHERRY.get())
                .add(HHModItems.RASPBERRY.get())
                .add(HHModItems.RED_GRAPES.get())
                .add(HHModItems.GREEN_GRAPES.get())
                .add(HHModItems.PEANUT_BUTTER.get())
                .add(HHModItems.COTTON.get())
                .add(HHModItems.CORN.get());

        tag(Tags.Items.ANIMAL_FOODS)
                .add(HHModItems.CORN.get())
                .add(HHModItems.CORN_KERNELS.get());

        tag(ItemTags.COW_FOOD)
                .add(HHModItems.CORN.get());

        tag(ItemTags.GOAT_FOOD)
                .add(HHModItems.CORN.get());

        tag(ItemTags.HORSE_FOOD)
                .add(HHModItems.CORN.get());

        tag(ItemTags.LLAMA_FOOD)
                .add(HHModItems.CORN.get());

        tag(ItemTags.SHEEP_FOOD)
                .add(HHModItems.CORN.get());

        tag(ItemTags.PIG_FOOD)
                .add(HHModItems.CORN.get());

        tag(HHModTags.CROW_FOOD)
                .add(HHModItems.CORN_KERNELS.get());

        tag(HHModTags.CROW_TEMPT_ITEMS)
                .add(HHModItems.CORN_KERNELS.get());

        tag(HHModTags.CROW_SHINY_ITEMS)
                .addTag(Tags.Items.GEMS)
                .addTag(Tags.Items.INGOTS)
                .addTag(Tags.Items.NUGGETS)
                .addTag(Tags.Items.ORES)
                .addTag(Tags.Items.ARMORS)
                .addTag(Tags.Items.TOOLS)
                .add(Items.ENCHANTED_BOOK)
                .add(Items.GLOWSTONE)
                .add(Items.GLOWSTONE_DUST)
                .add(Items.ENDER_PEARL)
                .add(Items.ENDER_EYE);

        tag(ItemTags.CHICKEN_FOOD)
                .add(HHModItems.CORN_KERNELS.get());

        tag(ItemTags.PARROT_FOOD)
                .add(HHModItems.CORN_KERNELS.get());

        tag(HHCommonTags.CROPS_CORN)
                .add(HHModItems.CORN.get());

        tag(HHCommonTags.CROPS_GRAIN)
                .add(HHModItems.CORN.get());

        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_BLUEBERRY)
                .add(HHModItems.BLUEBERRY_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_RASPBERRY)
                .add(HHModItems.RASPBERRY_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_GRAPE)
                .add(HHModItems.GREEN_GRAPE_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_GRAPE)
                .add(HHModItems.RED_GRAPE_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_CHERRY)
                .add(HHModItems.CHERRY_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_PEANUT)
                .add(HHModItems.PEANUT_CRATE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_COTTON)
                .add(HHModItems.COTTON_BALE.get());
        tag(HHCommonTags.STORAGE_BLOCKS_ITEM_CORN)
                .add(HHModItems.CORN_CRATE.get());

        tag(HHCommonTags.SEEDS_CORN)
                .add(HHModItems.CORN_KERNELS.get());

        tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS)
                .add(HHModItems.CORN_KERNELS.get());

        tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS)
                .add(HHModItems.CORN_KERNELS.get());

        tag(ModTags.WOODEN_CABINETS)
                .add(HHModItems.OAK_HALF_CABINET.get())
                .add(HHModItems.SPRUCE_HALF_CABINET.get())
                .add(HHModItems.BIRCH_HALF_CABINET.get())
                .add(HHModItems.JUNGLE_HALF_CABINET.get())
                .add(HHModItems.ACACIA_HALF_CABINET.get())
                .add(HHModItems.DARK_OAK_HALF_CABINET.get())
                .add(HHModItems.MANGROVE_HALF_CABINET.get())
                .add(HHModItems.CHERRY_HALF_CABINET.get())
                .add(HHModItems.BAMBOO_HALF_CABINET.get())
                .add(HHModItems.CRIMSON_HALF_CABINET.get())
                .add(HHModItems.WARPED_HALF_CABINET.get());

        tag(ItemTags.SMALL_FLOWERS)
                .add(HHModItems.WILD_PEANUTS.get())
                .add(HHModItems.WILD_COTTON.get())
                .add(HHModItems.WILD_GREEN_GRAPES.get())
                .add(HHModItems.WILD_RED_GRAPES.get())
                .add(HHModItems.YELLOW_MUM.get())
                .add(HHModItems.ORANGE_MUM.get())
                .add(HHModItems.RED_MUM.get())
                .add(HHModItems.BLUE_MUM.get())
                .add(HHModItems.LIGHT_BLUE_MUM.get())
                .add(HHModItems.PURPLE_MUM.get())
                .add(HHModItems.PINK_MUM.get())
                .add(HHModItems.WHITE_MUM.get());

        tag(Tags.Items.FEATHERS)
                .add(HHModItems.CROW_FEATHER.get());

        tag(HHCommonTags.DRINKS_JUICE)
                .add(HHModItems.BLUEBERRY_JUICE.get())
                .add(HHModItems.RASPBERRY_JUICE.get())
                .add(HHModItems.GREEN_GRAPE_JUICE.get())
                .add(HHModItems.RED_GRAPE_JUICE.get())
                .add(HHModItems.CHERRY_JUICE.get());

        tag(HHCommonTags.DRINKS_ALCOHOL)
                .add(HHModItems.BLUEBERRY_WINE.get())
                .add(HHModItems.RASPBERRY_WINE.get())
                .add(HHModItems.GREEN_GRAPE_WINE.get())
                .add(HHModItems.RED_GRAPE_WINE.get())
                .add(HHModItems.CHERRY_WINE.get())
                .add(HHModItems.SWEET_BERRY_WINE.get())
                .add(HHModItems.MEAD.get())
                .add(HHModItems.MOONSHINE.get());

        tag(Tags.Items.FOODS)
                .add(HHModItems.BLUEBERRIES.get())
                .add(HHModItems.CHERRY.get())
                .add(HHModItems.RASPBERRY.get())
                .add(HHModItems.RED_GRAPES.get())
                .add(HHModItems.GREEN_GRAPES.get())
                .add(HHModItems.PEANUT.get())
                .add(HHModItems.CORN.get())
                .add(HHModItems.BLUEBERRY_JAM.get())
                .add(HHModItems.CHERRY_JAM.get())
                .add(HHModItems.RASPBERRY.get())
                .add(HHModItems.GRAPE_JAM.get())
                .add(HHModItems.APPLE_JAM.get())
                .add(HHModItems.SWEET_BERRY_JAM.get())
                .add(HHModItems.GLOW_BERRY_JAM.get())
                .add(HHModItems.MELON_JAM.get())
                .add(HHModItems.PEANUT_BUTTER.get())
                .add(HHModItems.PICKLED_BEETROOTS.get())
                .add(HHModItems.PICKLED_CABBAGE.get())
                .add(HHModItems.PICKLED_CARROTS.get())
                .add(HHModItems.PICKLED_ONIONS.get())
                .add(HHModItems.PICKLED_POTATOES.get())
                .add(HHModItems.CARAMEL.get())
                .add(HHModItems.CARAMEL_APPLE.get())
                .add(HHModItems.CHOCOLATE_BAR.get())
                .add(HHModItems.COTTON_CANDY.get())
                .add(HHModItems.BLUEBERRY_MUFFIN.get())
                .add(HHModItems.PEANUT_BUTTER_COOKIE.get())
                .add(HHModItems.TRAIL_MIX.get())
                .add(HHModItems.ROASTED_PEANUTS.get())
                .add(HHModItems.MARSHMALLOW_STICK.get())
                .add(HHModItems.ROASTED_MARSHMALLOW_STICK.get())
                .add(HHModItems.CHARRED_MARSHMALLOW_STICK.get())
                .add(HHModItems.SMORE.get())
                .add(HHModItems.BLUEBERRY_PIE.get())
                .add(HHModItems.BLUEBERRY_PIE_SLICE.get())
                .add(HHModItems.RASPBERRY_PIE.get())
                .add(HHModItems.RASPBERRY_PIE_SLICE.get())
                .add(HHModItems.GRAPE_PIE.get())
                .add(HHModItems.GRAPE_PIE_SLICE.get())
                .add(HHModItems.PEANUT_BUTTER_PIE.get())
                .add(HHModItems.PEANUT_BUTTER_PIE_SLICE.get())
                .add(HHModItems.CHICKEN_POT_PIE.get())
                .add(HHModItems.CHICKEN_POT_PIE_SLICE.get())
                .add(HHModItems.CARROT_CAKE.get())
                .add(HHModItems.CARROT_CAKE_SLICE.get())
                .add(HHModItems.CHEDDAR_CHEESE_SLICE.get())
                .add(HHModItems.GOAT_CHEESE_SLICE.get())
                .add(HHModItems.RAW_SAUSAGE.get())
                .add(HHModItems.COOKED_SAUSAGE.get())
                .add(HHModItems.RAW_SKEWERED_SAUSAGE.get())
                .add(HHModItems.SKEWERED_SAUSAGE.get())
                .add(HHModItems.JERKY.get())
                .add(HHModItems.RAISINS.get())
                .add(HHModItems.SUNFLOWER_SEEDS.get())
                .add(HHModItems.POPCORN.get())
                .add(HHModItems.UNCOOKED_CORN_ON_THE_COB.get())
                .add(HHModItems.COOKED_CORN_ON_THE_COB.get())
                .add(HHModItems.CIDER_DONUT.get())
                .add(HHModItems.CANDY_CORN.get())
                .add(HHModItems.CORN_BREAD.get())
                .add(HHModItems.CORN_STEW.get())
                .add(HHModItems.TAMALE.get())
                .add(HHModItems.ELOTE.get())
                .add(HHModItems.ONION_SOUP.get())
                .add(HHModItems.MACARONI_AND_CHEESE.get())
                .add(HHModItems.MASHED_POTATOES.get())
                .add(HHModItems.PEANUT_BUTTER_AND_JELLY_SANDWICH.get())
                .add(HHModItems.WAFFLE.get())
                .add(HHModItems.BISCUITS_AND_GRAVY.get());
    }
}