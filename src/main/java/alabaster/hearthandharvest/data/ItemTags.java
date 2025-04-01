package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.CommonTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, HearthAndHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.registerModTags();
    }

    private void registerModTags() {
        tag(HHModTags.CLEAVERS).add(HHModItems.FLINT_CLEAVER.get(), HHModItems.IRON_CLEAVER.get(), HHModItems.DIAMOND_CLEAVER.get(), HHModItems.GOLDEN_CLEAVER.get(), HHModItems.NETHERITE_CLEAVER.get());
        tag(HHModTags.JAMS).add(HHModItems.GRAPE_JAM.get(), HHModItems.RASPBERRY_JAM.get(), HHModItems.BLUEBERRY_JAM.get(), HHModItems.APPLE_JAM.get(), HHModItems.GLOW_BERRY_JAM.get(), HHModItems.SWEET_BERRY_JAM.get(), HHModItems.MELON_JAM.get());
        tag(CommonTags.FOODS_MILK).add(HHModItems.GOAT_MILK_BOTTLE.get());
        tag(HHModTags.CHEESE_SLICES).add(HHModItems.CHEDDAR_CHEESE_SLICE.get(), HHModItems.GOAT_CHEESE_SLICE.get());
        tag(Tags.Items.FOODS_BERRY).add(HHModItems.BLUEBERRIES.get()).add(HHModItems.CHERRY.get()).add(HHModItems.RASPBERRY.get()).add(HHModItems.GRAPES.get());
        tag(Tags.Items.SEEDS).add(HHModItems.COTTON_SEEDS.get()).add(HHModItems.SUNFLOWER_SEEDS.get());
    }
}