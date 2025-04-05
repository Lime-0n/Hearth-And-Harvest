package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModItems;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemModels extends ItemModelProvider
{
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, HearthAndHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = BuiltInRegistries.ITEM.stream().filter(i -> HearthAndHarvest.MODID.equals(BuiltInRegistries.ITEM.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        // Workstations
        blockBasedModel(HHModItems.TREE_TAPPER.get(),"");
        items.remove(HHModItems.TREE_TAPPER.get());

        blockBasedModel(HHModItems.CASK.get(),"");
        items.remove(HHModItems.CASK.get());

        blockBasedModel(HHModItems.JUG.get(),"");
        items.remove(HHModItems.JUG.get());

        // Campfire Roastables
        items.remove(HHModItems.MARSHMALLOW_STICK.get());
        items.remove(HHModItems.ROASTED_MARSHMALLOW_STICK.get());
        items.remove(HHModItems.CHARRED_MARSHMALLOW_STICK.get());

        // Slab Crates
        blockBasedModel(HHModItems.EGG_CRATE.get(), "_bottom");
        items.remove(HHModItems.EGG_CRATE.get());

        blockBasedModel(HHModItems.TURTLE_EGG_CRATE.get(), "_bottom");
        items.remove(HHModItems.TURTLE_EGG_CRATE.get());

        blockBasedModel(HHModItems.MILK_CRATE.get(), "_bottom");
        items.remove(HHModItems.MILK_CRATE.get());

        blockBasedModel(HHModItems.GOAT_MILK_CRATE.get(), "_bottom");
        items.remove(HHModItems.GOAT_MILK_CRATE.get());

        blockBasedModel(HHModItems.BLUEBERRY_WINE_CRATE.get(), "_bottom");
        items.remove(HHModItems.BLUEBERRY_WINE_CRATE.get());

        blockBasedModel(HHModItems.CHERRY_WINE_CRATE.get(), "_bottom");
        items.remove(HHModItems.CHERRY_WINE_CRATE.get());

        blockBasedModel(HHModItems.RASPBERRY_WINE_CRATE.get(), "_bottom");
        items.remove(HHModItems.RASPBERRY_WINE_CRATE.get());

        blockBasedModel(HHModItems.RED_GRAPE_WINE_CRATE.get(), "_bottom");
        items.remove(HHModItems.RED_GRAPE_WINE_CRATE.get());

        blockBasedModel(HHModItems.MEAD_CRATE.get(), "_bottom");
        items.remove(HHModItems.MEAD_CRATE.get());

        blockBasedModel(HHModItems.WATER_CRATE.get(), "_bottom");
        items.remove(HHModItems.WATER_CRATE.get());

        blockBasedModel(HHModItems.HONEY_CRATE.get(), "_bottom");
        items.remove(HHModItems.HONEY_CRATE.get());

        blockBasedModel(HHModItems.BROWN_MUSHROOM_CRATE.get(), "_bottom");
        items.remove(HHModItems.BROWN_MUSHROOM_CRATE.get());

        blockBasedModel(HHModItems.RED_MUSHROOM_CRATE.get(), "_bottom");
        items.remove(HHModItems.RED_MUSHROOM_CRATE.get());

        blockBasedModel(HHModItems.CRIMSON_FUNGUS_CRATE.get(), "_bottom");
        items.remove(HHModItems.CRIMSON_FUNGUS_CRATE.get());

        blockBasedModel(HHModItems.WARPED_FUNGUS_CRATE.get(), "_bottom");
        items.remove(HHModItems.WARPED_FUNGUS_CRATE.get());

        // Blocks with special item sprites
        Set<Item> spriteBlockItems = Sets.newHashSet(
                HHModItems.JAR.get(),
                HHModItems.RASPBERRY.get(),
                HHModItems.BLUEBERRIES.get(),
                HHModItems.RED_GRAPES.get(),
                HHModItems.GREEN_GRAPES.get(),
                HHModItems.PEANUT.get(),
                HHModItems.COTTON_SEEDS.get(),
                HHModItems.RASPBERRY_PIE.get(),
                HHModItems.BLUEBERRY_PIE.get(),
                HHModItems.GRAPE_PIE.get(),
                HHModItems.CHICKEN_POT_PIE.get(),
                HHModItems.UNRIPE_CHEDDAR_CHEESE_WHEEL.get(),
                HHModItems.CHEDDAR_CHEESE_WHEEL.get(),
                HHModItems.UNRIPE_GOAT_CHEESE_WHEEL.get(),
                HHModItems.GOAT_CHEESE_WHEEL.get(),
                HHModItems.CARROT_CAKE.get()
        );
        takeAll(items, spriteBlockItems.toArray(new Item[0])).forEach(item -> withExistingParent(itemName(item), GENERATED).texture("layer0", resourceItem(itemName(item))));

        // Blocks with flat block textures for their items
        Set<Item> flatBlockItems = Sets.newHashSet(
                HHModItems.WILD_RED_GRAPES.get(),
                HHModItems.WILD_GREEN_GRAPES.get()
        );
        takeAll(items, flatBlockItems.toArray(new Item[0])).forEach(item -> itemGeneratedModel(item, resourceBlock(itemName(item))));

        // Blocks whose items look alike
        takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));

        // Handheld items
        Set<Item> handheldItems = Sets.newHashSet(
                HHModItems.FLINT_CLEAVER.get(),
                HHModItems.IRON_CLEAVER.get(),
                HHModItems.DIAMOND_CLEAVER.get(),
                HHModItems.GOLDEN_CLEAVER.get(),
                HHModItems.NETHERITE_CLEAVER.get()
        );
        takeAll(items, handheldItems.toArray(new Item[0])).forEach(item -> itemHandheldModel(item, resourceItem(itemName(item))));

        // Generated items
        items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));
    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
    }

    public void itemHandheldModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), HANDHELD).texture("layer0", texture);
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    private String itemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(HearthAndHarvest.MODID, "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(HearthAndHarvest.MODID, "item/" + path);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Set<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                HearthAndHarvest.LOGGER.warn("Item {} not found in set", item);
            }
        }
        if (!src.removeAll(ret)) {
            HearthAndHarvest.LOGGER.warn("takeAll array didn't yield anything ({})", Arrays.toString(items));
        }
        return ret;
    }

    public static <T> Collection<T> takeAll(Set<T> src, Predicate<T> pred) {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty()) {
            HearthAndHarvest.LOGGER.warn("takeAll predicate yielded nothing", new Throwable());
        }
        return ret;
    }
}