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
                .add(Items.DIAMOND)
                .add(Items.EMERALD)
                .add(Items.IRON_INGOT)
                .add(Items.IRON_NUGGET)
                .add(Items.COPPER_INGOT)
                .add(Items.GOLD_INGOT)
                .add(Items.GOLD_NUGGET);

        tag(ItemTags.CHICKEN_FOOD)
                .add(HHModItems.CORN_KERNELS.get());

        tag(ItemTags.PARROT_FOOD)
                .add(HHModItems.CORN_KERNELS.get());

        tag(HHCommonTags.CROPS_CORN)
                .add(HHModItems.CORN.get());

        tag(HHCommonTags.CROPS_GRAIN)
                .add(HHModItems.CORN.get());

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
                .add(HHModItems.YELLOW_MUM.get())
                .add(HHModItems.ORANGE_MUM.get())
                .add(HHModItems.RED_MUM.get())
                .add(HHModItems.BLUE_MUM.get())
                .add(HHModItems.LIGHT_BLUE_MUM.get())
                .add(HHModItems.PURPLE_MUM.get())
                .add(HHModItems.PINK_MUM.get())
                .add(HHModItems.WHITE_MUM.get());
    }
}