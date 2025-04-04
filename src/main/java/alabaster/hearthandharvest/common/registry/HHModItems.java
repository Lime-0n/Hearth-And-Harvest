package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.HHFoodValues;
import alabaster.hearthandharvest.common.item.MarshmallowStickItem;
import alabaster.hearthandharvest.common.item.UniversalFeedItem;
import alabaster.hearthandharvest.common.item.WateringCanItem;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;
import vectorwing.farmersdelight.common.registry.ModMaterials;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class HHModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, HearthAndHarvest.MODID);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static Supplier<Item> registerWithTab(String name, Supplier<Item> supplier) {
        Supplier<Item> block = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(block);
        return block;
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
    public static final Supplier<Item> JAR = registerWithTab("jar",
            () -> new BlockItem(HHModBlocks.JAR.get(), basicItem()));

    // Crops
    public static final Supplier<Item> BLUEBERRIES = registerWithTab("blueberries",
            () -> new ItemNameBlockItem(HHModBlocks.BLUEBERRY_BUSH.get(), foodItem(HHFoodValues.BLUEBERRIES)));
    public static final Supplier<Item> CHERRY = registerWithTab("cherry",
            () -> new Item(foodItem(HHFoodValues.CHERRY)));
    public static final Supplier<Item> RASPBERRY = registerWithTab("raspberry",
            () -> new ItemNameBlockItem(HHModBlocks.RASPBERRY_BUSH.get(), foodItem(HHFoodValues.RASPBERRY)));
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

    // Wild Crops
    public static final Supplier<Item> WILD_RED_GRAPES = registerWithTab("wild_red_grapes",
            () -> new BlockItem(HHModBlocks.WILD_RED_GRAPES.get(), basicItem()));
    public static final Supplier<Item> WILD_GREEN_GRAPES = registerWithTab("wild_green_grapes",
            () -> new BlockItem(HHModBlocks.WILD_GREEN_GRAPES.get(), basicItem()));
    
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
    public static final Supplier<Item> RED_GRAPE_WINE_CRATE = registerWithTab("red_grape_wine_crate",
            () -> new BlockItem(HHModBlocks.RED_GRAPE_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> GREEN_GRAPE_WINE_CRATE = registerWithTab("green_grape_wine_crate",
            () -> new BlockItem(HHModBlocks.GREEN_GRAPE_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> RASPBERRY_WINE_CRATE = registerWithTab("raspberry_wine_crate",
            () -> new BlockItem(HHModBlocks.RASPBERRY_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> BLUEBERRY_WINE_CRATE = registerWithTab("blueberry_wine_crate",
            () -> new BlockItem(HHModBlocks.BLUEBERRY_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> CHERRY_WINE_CRATE = registerWithTab("cherry_wine_crate",
            () -> new BlockItem(HHModBlocks.CHERRY_WINE_CRATE.get(), basicItem()));
    public static final Supplier<Item> MEAD_CRATE = registerWithTab("mead_crate",
            () -> new BlockItem(HHModBlocks.MEAD_CRATE.get(), basicItem()));
    public static final Supplier<Item> WATER_CRATE = registerWithTab("water_crate",
            () -> new BlockItem(HHModBlocks.WATER_CRATE.get(), basicItem()));
    public static final Supplier<Item> HONEY_CRATE = registerWithTab("honey_crate",
            () -> new BlockItem(HHModBlocks.HONEY_CRATE.get(), basicItem()));
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
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.MEAD), false, false));
    public static final Supplier<Item> BLUEBERRY_WINE = registerWithTab("blueberry_wine",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.WINE), false, false));
    public static final Supplier<Item> CHERRY_WINE = registerWithTab("cherry_wine",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.WINE), false, false));
    public static final Supplier<Item> RASPBERRY_WINE = registerWithTab("raspberry_wine",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.WINE), false, false));
    public static final Supplier<Item> RED_GRAPE_WINE = registerWithTab("red_grape_wine",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.WINE), false, false));
    public static final Supplier<Item> GREEN_GRAPE_WINE = registerWithTab("green_grape_wine",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.WINE), false, false));
    public static final Supplier<Item> GOAT_MILK_BOTTLE = registerWithTab("goat_milk_bottle",
            () -> new MilkBottleItem(drinkItem().food(HHFoodValues.GOAT_MILK_BOTTLE)));
    public static final Supplier<Item> BLUEBERRY_JUICE = registerWithTab("blueberry_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.BLUEBERRY_JUICE), false, false));
    public static final Supplier<Item> CHERRY_JUICE = registerWithTab("cherry_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.CHERRY_JUICE), false, false));
    public static final Supplier<Item> RASPBERRY_JUICE = registerWithTab("raspberry_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.RASPBERRY_JUICE), false, false));
    public static final Supplier<Item> RED_GRAPE_JUICE = registerWithTab("red_grape_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.GRAPE_JUICE), false, false));
    public static final Supplier<Item> GREEN_GRAPE_JUICE = registerWithTab("green_grape_juice",
            () -> new DrinkableItem(drinkItem().food(HHFoodValues.GRAPE_JUICE), false, false));

    // Jams
    public static final Supplier<Item> BLUEBERRY_JAM = registerWithTab("blueberry_jam",
            () -> new Item(jarItem(HHFoodValues.BLUEBERRY_JAM)));
    public static final Supplier<Item> RASPBERRY_JAM = registerWithTab("raspberry_jam",
            () -> new Item(jarItem(HHFoodValues.RASPBERRY_JAM)));
    public static final Supplier<Item> GRAPE_JAM = registerWithTab("grape_jam",
            () -> new Item(jarItem(HHFoodValues.GRAPE_JAM)));
    public static final Supplier<Item> APPLE_JAM = registerWithTab("apple_jam",
            () -> new Item(jarItem(HHFoodValues.APPLE_JAM)));
    public static final Supplier<Item> SWEET_BERRY_JAM = registerWithTab("sweet_berry_jam",
            () -> new Item(jarItem(HHFoodValues.SWEET_BERRY_JAM)));
    public static final Supplier<Item> GLOW_BERRY_JAM = registerWithTab("glow_berry_jam",
            () -> new Item(jarItem(HHFoodValues.GLOW_BERRY_JAM)));
    public static final Supplier<Item> MELON_JAM = registerWithTab("melon_jam",
            () -> new Item(jarItem(HHFoodValues.MELON_JAM)));

    // Pickled Vegetables
    public static final Supplier<Item> PICKLED_BEETROOTS = registerWithTab("pickled_beetroots",
            () -> new Item(jarItem(HHFoodValues.PICKLED_BEETROOTS)));
    public static final Supplier<Item> PICKLED_CABBAGE = registerWithTab("pickled_cabbage",
            () -> new Item(jarItem(HHFoodValues.PICKLED_CABBAGE)));
    public static final Supplier<Item> PICKLED_CARROTS = registerWithTab("pickled_carrots",
            () -> new Item(jarItem(HHFoodValues.PICKLED_CARROTS)));
    public static final Supplier<Item> PICKLED_ONIONS = registerWithTab("pickled_onions",
            () -> new Item(jarItem(HHFoodValues.PICKLED_ONIONS)));
    public static final Supplier<Item> PICKLED_POTATOES = registerWithTab("pickled_potatoes",
            () -> new Item(jarItem(HHFoodValues.PICKLED_POTATOES)));

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

    // Sap and Syrup
    public static final Supplier<Item> SAP_BUCKET = registerWithTab("sap_bucket",
            () -> new Item(basicItem()));
    public static final Supplier<Item> SYRUP_BOTTLE = registerWithTab("syrup_bottle",
            () -> new Item(basicItem()));

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
    public static final Supplier<Item> PEANUT_BUTTER = registerWithTab("peanut_butter",
            () -> new Item(jarItem(HHFoodValues.PEANUT_BUTTER)));

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
            () -> new Item(basicItem()));
    public static final Supplier<Item> SALT = registerWithTab("salt",
            () -> new Item(basicItem()));
    public static final Supplier<Item> RAISINS = registerWithTab("raisins",
            () -> new Item(foodItem(HHFoodValues.RAISINS)));
    public static final Supplier<Item> SUNFLOWER_SEEDS = registerWithTab("sunflower_seeds",
            () -> new Item(foodItem(HHFoodValues.SUNFLOWER_SEEDS)));

    // Meals
    public static final Supplier<Item> ONION_SOUP = registerWithTab("onion_soup",
            () -> new Item(foodItem(HHFoodValues.ONION_SOUP)));
    public static final Supplier<Item> MACARONI_AND_CHEESE = registerWithTab("macaroni_and_cheese",
            () -> new Item(foodItem(HHFoodValues.MACARONI_AND_CHEESE)));
    public static final Supplier<Item> MASHED_POTATOES = registerWithTab("mashed_potatoes",
            () -> new Item(foodItem(HHFoodValues.MASHED_POTATOES)));
    public static final Supplier<Item> PEANUT_BUTTER_AND_JELLY_SANDWICH = registerWithTab("peanut_butter_and_jelly_sandwich",
            () -> new Item(foodItem(HHFoodValues.PEANUT_BUTTER_AND_JELLY_SANDWICH)));
    public static final Supplier<Item> WAFFLE = registerWithTab("waffle",
            () -> new Item(foodItem(HHFoodValues.WAFFLE)));
}