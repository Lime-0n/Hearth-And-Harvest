package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.ModItems;
import alabaster.hearthandharvest.common.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
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
        tag(ModTags.CLEAVERS).add(ModItems.FLINT_CLEAVER.get(), ModItems.IRON_CLEAVER.get(), ModItems.DIAMOND_CLEAVER.get(), ModItems.GOLDEN_CLEAVER.get(), ModItems.NETHERITE_CLEAVER.get());
        tag(ModTags.JAMS).add(ModItems.GRAPE_JAM.get(), ModItems.RASPBERRY_JAM.get(), ModItems.BLUEBERRY_JAM.get(), ModItems.APPLE_JAM.get(), ModItems.GLOW_BERRY_JAM.get(), ModItems.SWEET_BERRY_JAM.get(), ModItems.MELON_JAM.get());
        tag(CommonTags.FOODS_MILK).add(ModItems.GOAT_MILK_BOTTLE.get());
        tag(ModTags.CHEESE_SLICES).add(ModItems.CHEDDAR_CHEESE_SLICE.get(), ModItems.GOAT_CHEESE_SLICE.get());
    }
}