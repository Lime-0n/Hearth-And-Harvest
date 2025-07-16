package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.HHFoodValues;
import alabaster.hearthandharvest.common.item.MarshmallowStickItem;
import alabaster.hearthandharvest.common.item.UniversalFeedItem;
import alabaster.hearthandharvest.common.item.WateringCanItem;
import alabaster.hearthandharvest.common.item.WineBottleItem;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.FuelBlockItem;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;
import vectorwing.farmersdelight.common.registry.ModMaterials;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class HHModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, HearthAndHarvest.MODID);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static Supplier<Item> registerWithTab(String name, Supplier<Item> supplier) {
        Supplier<Item> item = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    // Helper methods
    public static Item.Properties basicItem() {
        return (new Item.Properties());
    }

    public static Item.Properties cleaverItem(Tier tier) {
        return new Item.Properties().attributes(KnifeItem.createAttributes(tier, 2.0F, -3.0F));
    }

    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }

    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16);
    }

    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }

    public static Item.Properties jarItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(HHModItems.JAR.get()).stacksTo(16);
    }


    // Tools
    public static final Supplier<Item> FLINT_CLEAVER = registerWithTab("flint_cleaver",
            () -> new KnifeItem(ModMaterials.FLINT, cleaverItem(ModMaterials.FLINT)));
    public static final Supplier<Item> IRON_CLEAVER = registerWithTab("iron_cleaver",
            () -> new KnifeItem(Tiers.IRON, cleaverItem(Tiers.IRON)));
    public static final Supplier<Item> DIAMOND_CLEAVER = registerWithTab("diamond_cleaver",
            () -> new KnifeItem(Tiers.DIAMOND, cleaverItem(Tiers.DIAMOND)));
    public static final Supplier<Item> NETHERITE_CLEAVER = registerWithTab("netherite_cleaver",
            () -> new KnifeItem(Tiers.NETHERITE, cleaverItem(Tiers.NETHERITE).fireResistant()));
    public static final Supplier<Item> GOLDEN_CLEAVER = registerWithTab("golden_cleaver",
            () -> new KnifeItem(Tiers.GOLD, cleaverItem(Tiers.GOLD)));

    public static final Supplier<Item> WATERING_CAN = registerWithTab("watering_can",
            () -> new WateringCanItem(basicItem()));
    public static final Supplier<Item> UNIVERSAL_FEED = registerWithTab("universal_feed",
            () -> new UniversalFeedItem(basicItem()));

    // Workstations
    public static final Supplier<Item> TREE_TAPPER = registerWithTab("tree_tapper",
            () -> new BlockItem(HHModBlocks.TREE_TAPPER.get(), basicItem()));
    public static final Supplier<Item> CASK = registerWithTab("cask",
            () -> new BlockItem(HHModBlocks.CASK.get(), basicItem()));
    public static final Supplier<Item> JUG = registerWithTab("jug",
            () -> new BlockItem(HHModBlocks.JUG.get(), basicItem()));

    public static final Supplier<Item> COUNTER = registerWithTab("counter",
            () -> new BlockItem(HHModBlocks.COUNTER.get(), basicItem()));
    public static final Supplier<Item> DRAWER = registerWithTab("drawer",
            () -> new BlockItem(HHModBlocks.DRAWER.get(), basicItem()));
    public static final Supplier<Item> BASIN = registerWithTab("basin",
            () -> new BlockItem(HHModBlocks.BASIN.get(), basicItem()));

    // Half-Cabinets
    public static final Supplier<Item> OAK_HALF_CABINET = registerWithTab("oak_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.OAK_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> SPRUCE_HALF_CABINET = registerWithTab("spruce_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.SPRUCE_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> BIRCH_HALF_CABINET = registerWithTab("birch_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.BIRCH_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> JUNGLE_HALF_CABINET = registerWithTab("jungle_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.JUNGLE_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> ACACIA_HALF_CABINET = registerWithTab("acacia_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.ACACIA_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> DARK_OAK_HALF_CABINET = registerWithTab("dark_oak_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.DARK_OAK_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> MANGROVE_HALF_CABINET = registerWithTab("mangrove_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.MANGROVE_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> CHERRY_HALF_CABINET = registerWithTab("cherry_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.CHERRY_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> BAMBOO_HALF_CABINET = registerWithTab("bamboo_half_cabinet",
            () -> new FuelBlockItem(HHModBlocks.BAMBOO_HALF_CABINET.get(), basicItem(), 300));
    public static final Supplier<Item> CRIMSON_HALF_CABINET = registerWithTab("crimson_half_cabinet",
            () -> new BlockItem(HHModBlocks.CRIMSON_HALF_CABINET.get(), basicItem()));
    public static final Supplier<Item> WARPED_HALF_CABINET = registerWithTab("warped_half_cabinet",
            () -> new BlockItem(HHModBlocks.WARPED_HALF_CABINET.get(), basicItem()));

    // Wine Racks
    public static final Supplier<Item> OAK_WINE_RACK = registerWithTab("oak_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.OAK_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> SPRUCE_WINE_RACK = registerWithTab("spruce_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.SPRUCE_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> BIRCH_WINE_RACK = registerWithTab("birch_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.BIRCH_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> JUNGLE_WINE_RACK = registerWithTab("jungle_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.JUNGLE_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> ACACIA_WINE_RACK = registerWithTab("acacia_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.ACACIA_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> DARK_OAK_WINE_RACK = registerWithTab("dark_oak_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.DARK_OAK_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> MANGROVE_WINE_RACK = registerWithTab("mangrove_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.MANGROVE_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> CHERRY_WINE_RACK = registerWithTab("cherry_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.CHERRY_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> BAMBOO_WINE_RACK = registerWithTab("bamboo_wine_rack",
            () -> new FuelBlockItem(HHModBlocks.BAMBOO_WINE_RACK.get(), basicItem(), 300));
    public static final Supplier<Item> CRIMSON_WINE_RACK = registerWithTab("crimson_wine_rack",
            () -> new BlockItem(HHModBlocks.CRIMSON_WINE_RACK.get(), basicItem()));
    public static final Supplier<Item> WARPED_WINE_RACK = registerWithTab("warped_wine_rack",
            () -> new BlockItem(HHModBlocks.WARPED_WINE_RACK.get(), basicItem()));

    // Crops
    public static final Supplier<Item> BLUEBERRIES = ModList.get().isLoaded("berry_good")
            ? registerWithTab("blueberries", () -> new Item(foodItem(HHFoodValues.BLUEBERRIES)))
            : registerWithTab("blueberries", () -> new ItemNameBlockItem(HHModBlocks.BLUEBERRY_BUSH.get(), foodItem(HHFoodValues.BLUEBERRIES)));
    public static final Supplier<Item> CHERRY = registerWithTab("cherry",
            () -> new Item(foodItem(HHFoodValues.CHERRY)));
    public static final Supplier<Item> RASPBERRY = ModList.get().isLoaded("berry_good")
            ? registerWithTab("raspberry", () -> new Item(foodItem(HHFoodValues.RASPBERRY)))
            :  registerWithTab("raspberry", () -> new ItemNameBlockItem(HHModBlocks.RASPBERRY_BUSH.get(), foodItem(HHFoodValues.RASPBERRY)));
    public static final Supplier<Item> RED_GRAPES = registerWithTab("red_grapes",
            () -> new ItemNameBlockItem(HHModBlocks.BUDDING_RED_GRAPE_CROP.get(), foodItem(HHFoodValues.GRAPES)));
    public static final Supplier<Item> GREEN_GRAPES = registerWithTab("green_grapes",
            () -> new ItemNameBlockItem(HHModBlocks.BUDDING_GREEN_GRAPE_CROP.get(), foodItem(HHFoodValues.GRAPES)));
    public static final Supplier<Item> PEANUT = registerWithTab("peanut",
            () -> new ItemNameBlockItem(HHModBlocks.PEANUT_CROP.get(), foodItem(HHFoodValues.PEANUT)));
    public static final Supplier<Item> COTTON_SEEDS = registerWithTab("cotton_seeds",
            () -> new ItemNameBlockItem(HHModBlocks.COTTON_CROP.get(), basicItem()));
    public static final Supplier<Item> COTTON = registerWithTab("cotton",
            () -> new Item(basicItem()));

    // Berry Good Pips
    public static final Supplier<Item> BLUEBERRY_PIPS = ModList.get().isLoaded("berry_good")
            ? registerWithTab("blueberry_pips",
            () -> new ItemNameBlockItem(HHModBlocks.BLUEBERRY_BUSH.get(), basicItem()))
            : null;
    public static final Supplier<Item> RASPBERRY_PIPS = ModList.get().isLoaded("berry_good")
            ? registerWithTab("raspberry_pips",
            () -> new ItemNameBlockItem(HHModBlocks.RASPBERRY_BUSH.get(), basicItem()))
            : null;

    // Wild Crops
    public static final Supplier<Item> WILD_RED_GRAPES = registerWithTab("wild_red_grapes",
            () -> new BlockItem(HHModBlocks.WILD_RED_GRAPES.get(), basicItem()));
    public static final Supplier<Item> WILD_GREEN_GRAPES = registerWithTab("wild_green_grapes",
            () -> new BlockItem(HHModBlocks.WILD_GREEN_GRAPES.get(), basicItem()));
    public static final Supplier<Item> WILD_COTTON = registerWithTab("wild_cotton",
            () -> new BlockItem(HHModBlocks.WILD_COTTON.get(), basicItem()));
    public static final Supplier<Item> WILD_PEANUTS = registerWithTab("wild_peanuts",
            () -> new BlockItem(HHModBlocks.WILD_PEANUTS.get(), basicItem()));
    
    // Storage Blocks

    // Crates
    public static final Supplier<Item> BLUEBERRY_CRATE = registerWithTab("blueberry_crate",
            () -> new BlockItem(HHModBlocks.BLUEBERRY_CRATE.get(), basicItem()));
    public static final Supplier<Item> CHERRY_CRATE = registerWithTab("cherry_crate",
            () -> new BlockItem(HHModBlocks.CHERRY_CRATE.get(), basicItem()));
    public static final Supplier<Item> RASPBERRY_CRATE = registerWithTab("raspberry_crate",
            () -> new BlockItem(HHModBlocks.RASPBERRY_CRATE.get(), basicItem()));
    public static final Supplier<Item> RED_GRAPE_CRATE = registerWithTab("red_grape_crate",
            () -> new BlockItem(HHModBlocks.RED_GRAPE_CRATE.get(), basicItem()));
    public static final Supplier<Item> GREEN_GRAPE_CRATE = registerWithTab("green_grape_crate",
            () -> new BlockItem(HHModBlocks.GREEN_GRAPE_CRATE.get(), basicItem()));
    public static final Supplier<Item> PEANUT_CRATE = registerWithTab("peanut_crate",
            () -> new BlockItem(HHModBlocks.PEANUT_CRATE.get(), basicItem()));
    public static final Supplier<Item> APPLE_CRATE = registerWithTab("apple_crate",
            () -> new BlockItem(HHModBlocks.APPLE_CRATE.get(), basicItem()));
    public static final Supplier<Item> GOLDEN_APPLE_CRATE = registerWithTab("golden_apple_crate",
            () -> new BlockItem(HHModBlocks.GOLDEN_APPLE_CRATE.get(), basicItem()));
    public static final Supplier<Item> GOLDEN_CARROT_CRATE = registerWithTab("golden_carrot_crate",
            () -> new BlockItem(HHModBlocks.GOLDEN_CARROT_CRATE.get(), basicItem()));
    public static final Supplier<Item> POISONOUS_POTATO_CRATE = registerWithTab("poisonous_potato_crate",
            () -> new BlockItem(HHModBlocks.POISONOUS_POTATO_CRATE.get(), basicItem()));
    public static final Supplier<Item> GLOW_BERRY_CRATE = registerWithTab("glow_berry_crate",
            () -> new BlockItem(HHModBlocks.GLOW_BERRY_CRATE.get(), basicItem()));
    public static final Supplier<Item> SWEET_BERRY_CRATE = registerWithTab("sweet_berry_crate",
            () -> new BlockItem(HHModBlocks.SWEET_BERRY_CRATE.get(), basicItem()));
    
    // Bags
    public static final Supplier<Item> SALT_BAG = registerWithTab("salt_bag",
            () -> new BlockItem(HHModBlocks.SALT_BAG.get(), basicItem()));
    public static final Supplier<Item> SUGAR_BAG = registerWithTab("sugar_bag",
            () -> new BlockItem(HHModBlocks.SUGAR_BAG.get(), basicItem()));
    public static final Supplier<Item> COCOA_BEAN_BAG = registerWithTab("cocoa_bean_bag",
            () -> new BlockItem(HHModBlocks.COCOA_BEAN_BAG.get(), basicItem()));
    public static final Supplier<Item> GUNPOWDER_BAG = registerWithTab("gunpowder_bag",
            () -> new BlockItem(HHModBlocks.GUNPOWDER_BAG.get(), basicItem()));
    
    // Misc
    public static final Supplier<Item> COTTON_BALE = registerWithTab("cotton_bale",
            () -> new BlockItem(HHModBlocks.COTTON_BALE.get(), basicItem()));
    public static final Supplier<Item> SPOOL = registerWithTab("spool",
            () -> new BlockItem(HHModBlocks.SPOOL.get(), basicItem()));
    public static final Supplier<Item> ROPE_COIL = registerWithTab("rope_coil",
            () -> new BlockItem(HHModBlocks.ROPE_COIL.get(), basicItem()));

    // Half-Slab Crates
    public static final Supplier<Item> EGG_CRATE = registerWithTab("egg_crate",
            () -> new BlockItem(HHModBlocks.EGG_CRATE.get(), basicItem()));
    public static final Supplier<Item> TURTLE_EGG_CRATE = registerWithTab("turtle_egg_crate",
            () -> new BlockItem(HHModBlocks.TURTLE_EGG_CRATE.get(), basicItem()));
    public static final Supplier<Item> MILK_CRATE = registerWithTab("milk_crate",
            () -> new BlockItem(HHModBlocks.MILK_CRATE.get(), basicItem()));
    public static final Supplier<Item> GOAT_MILK_CRATE = registerWithTab("goat_milk_crate",
            () -> new BlockItem(HHModBlocks.GOAT_MILK_CRATE.get(), basicItem()));
    public static final Supplier<Item> BLUEBERRY_WINE_CRATE = registerWithTab("blueberry_wine_crate",
            () -> new BlockItem(HHModBlocks.BLUEBERRY_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> CHERRY_WINE_CRATE = registerWithTab("cherry_wine_crate",
            () -> new BlockItem(HHModBlocks.CHERRY_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> RASPBERRY_WINE_CRATE = registerWithTab("raspberry_wine_crate",
            () -> new BlockItem(HHModBlocks.RASPBERRY_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> RED_GRAPE_WINE_CRATE = registerWithTab("red_grape_wine_crate",
            () -> new BlockItem(HHModBlocks.RED_GRAPE_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> GREEN_GRAPE_WINE_CRATE = registerWithTab("green_grape_wine_crate",
            () -> new BlockItem(HHModBlocks.GREEN_GRAPE_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> MEAD_CRATE = registerWithTab("mead_crate",
            () -> new BlockItem(HHModBlocks.MEAD_CRATE.get(), basicItem()));
    public static final Supplier<Item> WATER_CRATE = registerWithTab("water_crate",
            () -> new BlockItem(HHModBlocks.WATER_CRATE.get(), basicItem()));
    public static final Supplier<Item> HONEY_CRATE = registerWithTab("honey_crate",
            () -> new BlockItem(HHModBlocks.HONEY_CRATE.get(), basicItem()));
    public static final Supplier<Item> SYRUP_CRATE = registerWithTab("syrup_crate",
            () -> new BlockItem(HHModBlocks.SYRUP_CRATE.get(), basicItem()));
    public static final Supplier<Item> BROWN_MUSHROOM_CRATE = registerWithTab("brown_mushroom_crate",
            () -> new BlockItem(HHModBlocks.BROWN_MUSHROOM_CRATE.get(), basicItem()));
    public static final Supplier<Item> RED_MUSHROOM_CRATE = registerWithTab("red_mushroom_crate",
            () -> new BlockItem(HHModBlocks.RED_MUSHROOM_CRATE.get(), basicItem()));
    public static final Supplier<Item> CRIMSON_FUNGUS_CRATE = registerWithTab("crimson_fungus_crate",
            () -> new BlockItem(HHModBlocks.CRIMSON_FUNGUS_CRATE.get(), basicItem()));
    public static final Supplier<Item> WARPED_FUNGUS_CRATE = registerWithTab("warped_fungus_crate",
            () -> new BlockItem(HHModBlocks.WARPED_FUNGUS_CRATE.get(), basicItem()));

    // Drinks
    public static final Supplier<Item> MEAD = registerWithTab("mead",
            () -> new WineBottleItem(()-> HHModFluids.MEAD.get(), drinkItem().food(HHFoodValues.MEAD).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true, false));
    public static final Supplier<Item> BLUEBERRY_WINE = registerWithTab("blueberry_wine",
            () -> new WineBottleItem(()-> HHModFluids.BLUEBERRY_WINE.get(), drinkItem().food(HHFoodValues.BLUEBERRY_WINE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true, false));
    public static final Supplier<Item> CHERRY_WINE = registerWithTab("cherry_wine",
            () -> new WineBottleItem(()-> HHModFluids.CHERRY_WINE.get(), drinkItem().food(HHFoodValues.CHERRY_WINE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true, false));
    public static final Supplier<Item> RASPBERRY_WINE = registerWithTab("raspberry_wine",
            () -> new WineBottleItem(()-> HHModFluids.RASPBERRY_WINE.get(), drinkItem().food(HHFoodValues.RASPBERRY_WINE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true, false));
    public static final Supplier<Item> RED_GRAPE_WINE = registerWithTab("red_grape_wine",
            () -> new WineBottleItem(()-> HHModFluids.RED_GRAPE_WINE.get(), drinkItem().food(HHFoodValues.RED_GRAPE_WINE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true, false));
    public static final Supplier<Item> GREEN_GRAPE_WINE = registerWithTab("green_grape_wine",
            () -> new WineBottleItem(()-> HHModFluids.GREEN_GRAPE_WINE.get(), drinkItem().food(HHFoodValues.GREEN_GRAPE_WINE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true, false));
    public static final Supplier<Item> GOAT_MILK_BOTTLE = registerWithTab("goat_milk_bottle",
            () -> new MilkBottleItem(drinkItem().food(HHFoodValues.GOAT_MILK_BOTTLE)));
    public static final Supplier<Item> BLUEBERRY_JUICE = registerWithTab("blueberry_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.BLUEBERRY_JUICE), true, false));
    public static final Supplier<Item> CHERRY_JUICE = registerWithTab("cherry_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.CHERRY_JUICE), true, false));
    public static final Supplier<Item> RASPBERRY_JUICE = registerWithTab("raspberry_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.RASPBERRY_JUICE), true, false));
    public static final Supplier<Item> RED_GRAPE_JUICE = registerWithTab("red_grape_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.GRAPE_JUICE), true, false));
    public static final Supplier<Item> GREEN_GRAPE_JUICE = registerWithTab("green_grape_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.GRAPE_JUICE), true, false));

    // Brewin and Chewin Tankards
//    public static final Supplier<Item> TANKARD_OF_MEAD = ModList.get().isLoaded("brewinandchewin")
//            ? registerWithTab("tankard_of_mead",
//            () -> new BoozeItem(()-> HHModFluids.MEAD.get(), drinkItem().food(HHFoodValues.MEAD).stacksTo(16).craftRemainder(BnCItems.TANKARD)))
//            : null;
//    public static final Supplier<Item> TANKARD_OF_BLUEBERRY_WINE = ModList.get().isLoaded("brewinandchewin")
//            ? registerWithTab("tankard_of_blueberry_wine",
//            () -> new BoozeItem(()-> HHModFluids.BLUEBERRY_WINE.get(), drinkItem().food(HHFoodValues.BLUEBERRY_WINE).stacksTo(16).craftRemainder(BnCItems.TANKARD)))
//            : null;
//    public static final Supplier<Item> TANKARD_OF_CHERRY_WINE = ModList.get().isLoaded("brewinandchewin")
//            ? registerWithTab("tankard_of_cherry_wine",
//            () -> new BoozeItem(()-> HHModFluids.CHERRY_WINE.get(), drinkItem().food(HHFoodValues.CHERRY_WINE).stacksTo(16).craftRemainder(BnCItems.TANKARD)))
//            : null;
//    public static final Supplier<Item> TANKARD_OF_RASPBERRY_WINE = ModList.get().isLoaded("brewinandchewin")
//            ? registerWithTab("tankard_of_raspberry_wine",
//            () -> new BoozeItem(()-> HHModFluids.RASPBERRY_WINE.get(), drinkItem().food(HHFoodValues.RASPBERRY_WINE).stacksTo(16).craftRemainder(BnCItems.TANKARD)))
//            : null;
//    public static final Supplier<Item> TANKARD_OF_GREEN_GRAPE_WINE = ModList.get().isLoaded("brewinandchewin")
//            ? registerWithTab("tankard_of_green_grape_wine",
//            () -> new BoozeItem(()-> HHModFluids.GREEN_GRAPE_WINE.get(), drinkItem().food(HHFoodValues.GREEN_GRAPE_WINE).stacksTo(16).craftRemainder(BnCItems.TANKARD)))
//            : null;
//    public static final Supplier<Item> TANKARD_OF_RED_GRAPE_WINE = ModList.get().isLoaded("brewinandchewin")
//            ? registerWithTab("tankard_of_red_grape_wine",
//            () -> new BoozeItem(()-> HHModFluids.RED_GRAPE_WINE.get(), drinkItem().food(HHFoodValues.RED_GRAPE_WINE).stacksTo(16).craftRemainder(BnCItems.TANKARD)))
//            : null;

    // Jar Items
    public static final Supplier<Item> JAR = registerWithTab("jar",
            () -> new BlockItem(HHModBlocks.JAR.get(), basicItem()));
    public static final Supplier<Item> BLUEBERRY_JAM = registerWithTab("blueberry_jam",
            () -> new BlockItem(HHModBlocks.BLUEBERRY_JAM.get(), jarItem(HHFoodValues.BLUEBERRY_JAM)));
    public static final Supplier<Item> CHERRY_JAM = registerWithTab("cherry_jam",
            () -> new BlockItem(HHModBlocks.CHERRY_JAM.get(), jarItem(HHFoodValues.BLUEBERRY_JAM)));
    public static final Supplier<Item> RASPBERRY_JAM = registerWithTab("raspberry_jam",
            () -> new BlockItem(HHModBlocks.RASPBERRY_JAM.get(), jarItem(HHFoodValues.RASPBERRY_JAM)));
    public static final Supplier<Item> GRAPE_JAM = registerWithTab("grape_jam",
            () -> new BlockItem(HHModBlocks.GRAPE_JAM.get(), jarItem(HHFoodValues.GRAPE_JAM)));
    public static final Supplier<Item> APPLE_JAM = registerWithTab("apple_jam",
            () -> new BlockItem(HHModBlocks.APPLE_JAM.get(), jarItem(HHFoodValues.APPLE_JAM)));
    public static final Supplier<Item> SWEET_BERRY_JAM = registerWithTab("sweet_berry_jam",
            () -> new BlockItem(HHModBlocks.SWEET_BERRY_JAM.get(), jarItem(HHFoodValues.SWEET_BERRY_JAM)));
    public static final Supplier<Item> GLOW_BERRY_JAM = registerWithTab("glow_berry_jam",
            () -> new BlockItem(HHModBlocks.GLOW_BERRY_JAM.get(), jarItem(HHFoodValues.GLOW_BERRY_JAM)));
    public static final Supplier<Item> MELON_JAM = registerWithTab("melon_jam",
            () -> new BlockItem(HHModBlocks.MELON_JAM.get(), jarItem(HHFoodValues.MELON_JAM)));
    public static final Supplier<Item> PEANUT_BUTTER = registerWithTab("peanut_butter",
            () -> new BlockItem(HHModBlocks.PEANUT_BUTTER.get(), jarItem(HHFoodValues.PEANUT_BUTTER)));
    public static final Supplier<Item> PICKLED_BEETROOTS = registerWithTab("pickled_beetroots",
            () -> new BlockItem(HHModBlocks.PICKLED_BEETROOTS.get(), jarItem(HHFoodValues.PICKLED_BEETROOTS)));
    public static final Supplier<Item> PICKLED_CABBAGE = registerWithTab("pickled_cabbage",
            () -> new BlockItem(HHModBlocks.PICKLED_CABBAGE.get(), jarItem(HHFoodValues.PICKLED_CABBAGE)));
    public static final Supplier<Item> PICKLED_CARROTS = registerWithTab("pickled_carrots",
            () -> new BlockItem(HHModBlocks.PICKLED_CARROTS.get(), jarItem(HHFoodValues.PICKLED_CARROTS)));
    public static final Supplier<Item> PICKLED_ONIONS = registerWithTab("pickled_onions",
            () -> new BlockItem(HHModBlocks.PICKLED_ONIONS.get(), jarItem(HHFoodValues.PICKLED_ONIONS)));
    public static final Supplier<Item> PICKLED_POTATOES = registerWithTab("pickled_potatoes",
            () -> new BlockItem(HHModBlocks.PICKLED_POTATOES.get(), jarItem(HHFoodValues.PICKLED_POTATOES)));

    // Sweets
    public static final Supplier<Item> CARAMEL = registerWithTab("caramel",
            () -> new Item(foodItem(HHFoodValues.CARAMEL)));
    public static final Supplier<Item> CARAMEL_APPLE = registerWithTab("caramel_apple",
            () -> new Item(foodItem(HHFoodValues.CARAMEL_APPLE)));
    public static final Supplier<Item> CHOCOLATE_BAR = registerWithTab("chocolate_bar",
            () -> new Item(foodItem(HHFoodValues.CHOCOLATE_BAR)));
    public static final Supplier<Item> COTTON_CANDY = registerWithTab("cotton_candy",
            () -> new Item(foodItem(HHFoodValues.COTTON_CANDY)));
    public static final Supplier<Item> BLUEBERRY_MUFFIN = registerWithTab("blueberry_muffin",
            () -> new Item(foodItem(HHFoodValues.BLUEBERRY_MUFFIN)));
    public static final Supplier<Item> PEANUT_BUTTER_COOKIE = registerWithTab("peanut_butter_cookie",
            () -> new Item(foodItem(HHFoodValues.PEANUT_BUTTER_COOKIE)));
    public static final Supplier<Item> TRAIL_MIX = registerWithTab("trail_mix",
            () -> new Item(foodItem(HHFoodValues.TRAIL_MIX)));

    public static final Supplier<Item> MARSHMALLOW_STICK = registerWithTab("marshmallow_stick",
            () -> new MarshmallowStickItem(foodItem(HHFoodValues.MARSHMALLOW_STICK).stacksTo(1)));
    public static final Supplier<Item> ROASTED_MARSHMALLOW_STICK = registerWithTab("roasted_marshmallow_stick",
            () -> new MarshmallowStickItem(foodItem(HHFoodValues.ROASTED_MARSHMALLOW_STICK).stacksTo(1)));
    public static final Supplier<Item> CHARRED_MARSHMALLOW_STICK = registerWithTab("charred_marshmallow_stick",
            () -> new MarshmallowStickItem(foodItem(HHFoodValues.CHARRED_MARSHMALLOW_STICK).stacksTo(1)));

    public static final Supplier<Item> SMORE = registerWithTab("smore",
            () -> new MarshmallowStickItem(foodItem(HHFoodValues.SMORE)));

    // Sap and Syrup
    public static final Supplier<Item> SAP_BUCKET = registerWithTab("sap_bucket",
            () -> new Item(basicItem().stacksTo(1)));
    public static final Supplier<Item> SYRUP_BOTTLE = registerWithTab("syrup_bottle",
            () -> new Item(basicItem().craftRemainder(Items.GLASS_BOTTLE)));

    // Pies
    public static final Supplier<Item> BLUEBERRY_PIE = registerWithTab("blueberry_pie",
            () -> new BlockItem(HHModBlocks.BLUEBERRY_PIE.get(), basicItem()));
    public static final Supplier<Item> BLUEBERRY_PIE_SLICE = registerWithTab("blueberry_pie_slice",
            () -> new Item(foodItem(HHFoodValues.BLUEBERRY_PIE_SLICE)));
    public static final Supplier<Item> RASPBERRY_PIE = registerWithTab("raspberry_pie",
            () -> new BlockItem(HHModBlocks.RASPBERRY_PIE.get(), basicItem()));
    public static final Supplier<Item> RASPBERRY_PIE_SLICE = registerWithTab("raspberry_pie_slice",
            () -> new Item(foodItem(HHFoodValues.RASPBERRY_PIE_SLICE)));
    public static final Supplier<Item> GRAPE_PIE = registerWithTab("grape_pie",
            () -> new BlockItem(HHModBlocks.GRAPE_PIE.get(), basicItem()));
    public static final Supplier<Item> GRAPE_PIE_SLICE = registerWithTab("grape_pie_slice",
            () -> new Item(foodItem(HHFoodValues.GRAPE_PIE_SLICE)));
    public static final Supplier<Item> CHICKEN_POT_PIE = registerWithTab("chicken_pot_pie",
            () -> new BlockItem(HHModBlocks.CHICKEN_POT_PIE.get(), basicItem()));
    public static final Supplier<Item> CHICKEN_POT_PIE_SLICE = registerWithTab("chicken_pot_pie_slice",
            () -> new Item(foodItem(HHFoodValues.CHICKEN_POT_PIE_SLICE)));
    public static final Supplier<Item> CARROT_CAKE = registerWithTab("carrot_cake",
            () -> new BlockItem(HHModBlocks.CARROT_CAKE.get(), basicItem()));
    public static final Supplier<Item> CARROT_CAKE_SLICE = registerWithTab("carrot_cake_slice",
            () -> new Item(foodItem(HHFoodValues.CARROT_CAKE_SLICE)));


    // Ingredients
    public static final Supplier<Item> COOKING_OIL = registerWithTab("cooking_oil",
            () -> new BucketItem(HHModFluids.COOKING_OIL.get(), basicItem().craftRemainder(Items.GLASS_BOTTLE)));

    public static final Supplier<Item> UNRIPE_CHEDDAR_CHEESE_WHEEL = registerWithTab("unripe_cheddar_cheese_wheel",
            () -> new BlockItem(HHModBlocks.UNRIPE_CHEDDAR_CHEESE_WHEEL.get(), basicItem()));
    public static final Supplier<Item> CHEDDAR_CHEESE_WHEEL = registerWithTab("cheddar_cheese_wheel",
            () -> new BlockItem(HHModBlocks.CHEDDAR_CHEESE_WHEEL.get(), basicItem()));
    public static final Supplier<Item> CHEDDAR_CHEESE_SLICE = registerWithTab("cheddar_cheese_slice",
            () -> new Item(foodItem(HHFoodValues.CHEDDAR_CHEESE_SLICE)));

    public static final Supplier<Item> UNRIPE_GOAT_CHEESE_WHEEL = registerWithTab("unripe_goat_cheese_wheel",
            () -> new BlockItem(HHModBlocks.UNRIPE_GOAT_CHEESE_WHEEL.get(), basicItem()));
    public static final Supplier<Item> GOAT_CHEESE_WHEEL = registerWithTab("goat_cheese_wheel",
            () -> new BlockItem(HHModBlocks.GOAT_CHEESE_WHEEL.get(), basicItem()));
    public static final Supplier<Item> GOAT_CHEESE_SLICE = registerWithTab("goat_cheese_slice",
            () -> new Item(foodItem(HHFoodValues.GOAT_CHEESE_SLICE)));

    public static final Supplier<Item> RAW_SAUSAGE = registerWithTab("raw_sausage",
            () -> new Item(foodItem(HHFoodValues.RAW_SAUSAGE)));
    public static final Supplier<Item> COOKED_SAUSAGE = registerWithTab("cooked_sausage",
            () -> new Item(foodItem(HHFoodValues.COOKED_SAUSAGE)));
    public static final Supplier<Item> SKEWERED_SAUSAGE = registerWithTab("skewered_sausage",
            () -> new Item(foodItem(HHFoodValues.SKEWERED_SAUSAGE)));
    public static final Supplier<Item> JERKY = registerWithTab("jerky",
            () -> new Item(foodItem(HHFoodValues.JERKY)));
    public static final Supplier<Item> BATTER = registerWithTab("batter",
            () -> new Item(basicItem().craftRemainder(Items.BOWL)));
    public static final Supplier<Item> SALT = registerWithTab("salt",
            () -> new Item(basicItem()));
    public static final Supplier<Item> RAISINS = registerWithTab("raisins",
            () -> new Item(foodItem(HHFoodValues.RAISINS)));
    public static final Supplier<Item> SUNFLOWER_SEEDS = registerWithTab("sunflower_seeds",
            () -> new Item(foodItem(HHFoodValues.SUNFLOWER_SEEDS)));

    // Meals
    public static final Supplier<Item> ONION_SOUP = registerWithTab("onion_soup",
            () -> new Item(bowlFoodItem(HHFoodValues.ONION_SOUP)));
    public static final Supplier<Item> MACARONI_AND_CHEESE = registerWithTab("macaroni_and_cheese",
            () -> new Item(bowlFoodItem(HHFoodValues.MACARONI_AND_CHEESE)));
    public static final Supplier<Item> MASHED_POTATOES = registerWithTab("mashed_potatoes",
            () -> new Item(bowlFoodItem(HHFoodValues.MASHED_POTATOES)));
    public static final Supplier<Item> PEANUT_BUTTER_AND_JELLY_SANDWICH = registerWithTab("peanut_butter_and_jelly_sandwich",
            () -> new Item(foodItem(HHFoodValues.PEANUT_BUTTER_AND_JELLY_SANDWICH)));
    public static final Supplier<Item> WAFFLE = registerWithTab("waffle",
            () -> new Item(foodItem(HHFoodValues.WAFFLE)));
    public static final Supplier<Item> BISCUITS_AND_GRAVY = registerWithTab("biscuits_and_gravy",
            () -> new Item(bowlFoodItem(HHFoodValues.BISCUITS_AND_GRAVY)));
}