package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.block.WildCropBlock;

public class HHModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, HearthAndHarvest.MODID);

    // Workstations
    public static final RegistryObject<Block> TREE_TAPPER = BLOCKS.register("tree_tapper",
            () -> new TreeTapperBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F).sound(SoundType.WOOD).randomTicks()));
    public static final RegistryObject<Block> CASK = BLOCKS.register("cask",
            () -> new CaskBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F).sound(SoundType.WOOD).randomTicks()));
    public static final RegistryObject<Block> JUG = BLOCKS.register("jug",
            () -> new JugBlock(Block.Properties.copy(Blocks.IRON_BARS).strength(2.0F, 3.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> SAP_CAULDRON = BLOCKS.register("sap_cauldron",
            () -> new SapCauldronBlock(Block.Properties.copy(Blocks.CAULDRON).strength(2.0F, 3.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> COUNTER = BLOCKS.register("counter",
            () -> new Block(Block.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> DRAWER = BLOCKS.register("drawer",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> BASIN = BLOCKS.register("basin",
            () -> new BasinBlock(Block.Properties.copy(Blocks.BRICKS).randomTicks()));

    // Half-Cabinets
    public static final RegistryObject<Block> OAK_HALF_CABINET = BLOCKS.register("oak_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> SPRUCE_HALF_CABINET = BLOCKS.register("spruce_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> BIRCH_HALF_CABINET = BLOCKS.register("birch_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> JUNGLE_HALF_CABINET = BLOCKS.register("jungle_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> ACACIA_HALF_CABINET = BLOCKS.register("acacia_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> DARK_OAK_HALF_CABINET = BLOCKS.register("dark_oak_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> MANGROVE_HALF_CABINET = BLOCKS.register("mangrove_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> CHERRY_HALF_CABINET = BLOCKS.register("cherry_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.CHERRY_WOOD)));
    public static final RegistryObject<Block> BAMBOO_HALF_CABINET = BLOCKS.register("bamboo_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.BAMBOO_WOOD)));
    public static final RegistryObject<Block> CRIMSON_HALF_CABINET = BLOCKS.register("crimson_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.NETHER_WOOD)));
    public static final RegistryObject<Block> WARPED_HALF_CABINET = BLOCKS.register("warped_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.NETHER_WOOD)));

    // Half-Cabinets
    public static final RegistryObject<Block> OAK_WINE_RACK = BLOCKS.register("oak_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> SPRUCE_WINE_RACK = BLOCKS.register("spruce_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> BIRCH_WINE_RACK = BLOCKS.register("birch_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> JUNGLE_WINE_RACK = BLOCKS.register("jungle_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> ACACIA_WINE_RACK = BLOCKS.register("acacia_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> DARK_OAK_WINE_RACK = BLOCKS.register("dark_oak_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> MANGROVE_WINE_RACK = BLOCKS.register("mangrove_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> CHERRY_WINE_RACK = BLOCKS.register("cherry_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.CHERRY_WOOD)));
    public static final RegistryObject<Block> BAMBOO_WINE_RACK = BLOCKS.register("bamboo_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.BAMBOO_WOOD)));
    public static final RegistryObject<Block> CRIMSON_WINE_RACK = BLOCKS.register("crimson_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.NETHER_WOOD)));
    public static final RegistryObject<Block> WARPED_WINE_RACK = BLOCKS.register("warped_wine_rack",
            () -> new WineRackBlock(Block.Properties.copy(Blocks.BARREL).sound(SoundType.NETHER_WOOD)));

    // Crops
    public static final RegistryObject<Block> RASPBERRY_BUSH = BLOCKS.register("raspberry_bush",
            () -> new RaspberryBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)));
    public static final RegistryObject<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush",
            () -> new BlueberryBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)));
    public static final RegistryObject<Block> BUDDING_RED_GRAPE_CROP = BLOCKS.register("budding_red_grapes",
            () -> new BuddingRedGrapeBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> RED_GRAPE_CROP = BLOCKS.register("red_grapes",
            () -> new RedGrapeVineBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> BUDDING_GREEN_GRAPE_CROP = BLOCKS.register("budding_green_grapes",
            () -> new BuddingGreenGrapeBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> GREEN_GRAPE_CROP = BLOCKS.register("green_grapes",
            () -> new GreenGrapeVineBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> PEANUT_CROP = BLOCKS.register("peanuts",
            () -> new PeanutBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> COTTON_CROP = BLOCKS.register("cotton",
            () -> new CottonBlock(Block.Properties.copy(Blocks.WHEAT)));

    // Wild Crops
    public static final RegistryObject<Block> WILD_RED_GRAPES = BLOCKS.register("wild_red_grapes",
            () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 10, Block.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<Block> WILD_GREEN_GRAPES = BLOCKS.register("wild_green_grapes",
            () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 10, Block.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<Block> WILD_COTTON = BLOCKS.register("wild_cotton",
            () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 10, Block.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<Block> WILD_PEANUTS = BLOCKS.register("wild_peanuts",
            () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 10, Block.Properties.copy(Blocks.TALL_GRASS)));

    // Crates

    public static final RegistryObject<Block> BLUEBERRY_CRATE = BLOCKS.register("blueberry_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHERRY_CRATE = BLOCKS.register("cherry_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RED_GRAPE_CRATE = BLOCKS.register("red_grape_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GREEN_GRAPE_CRATE = BLOCKS.register("green_grape_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RASPBERRY_CRATE = BLOCKS.register("raspberry_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PEANUT_CRATE = BLOCKS.register("peanut_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> APPLE_CRATE = BLOCKS.register("apple_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GOLDEN_APPLE_CRATE = BLOCKS.register("golden_apple_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GOLDEN_CARROT_CRATE = BLOCKS.register("golden_carrot_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_POTATO_CRATE = BLOCKS.register("poisonous_potato_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GLOW_BERRY_CRATE = BLOCKS.register("glow_berry_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> 10)));
    public static final RegistryObject<Block> SWEET_BERRY_CRATE = BLOCKS.register("sweet_berry_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    
    // Bags
    public static final RegistryObject<Block> SALT_BAG = BLOCKS.register("salt_bag",
            () -> new Block(Block.Properties.copy(Blocks.GRAVEL)));
    public static final RegistryObject<Block> SUGAR_BAG = BLOCKS.register("sugar_bag",
            () -> new Block(Block.Properties.copy(Blocks.GRAVEL)));
    public static final RegistryObject<Block> COCOA_BEAN_BAG = BLOCKS.register("cocoa_bean_bag",
            () -> new Block(Block.Properties.copy(Blocks.GRAVEL)));
    public static final RegistryObject<Block> GUNPOWDER_BAG = BLOCKS.register("gunpowder_bag",
            () -> new Block(Block.Properties.copy(Blocks.GRAVEL)));
    
    // Misc
    public static final RegistryObject<Block> COTTON_BALE = BLOCKS.register("cotton_bale",
            () -> new HayBlock(Block.Properties.copy(Blocks.WHITE_WOOL)));
    public static final RegistryObject<Block> SPOOL = BLOCKS.register("spool",
            () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.WHITE_WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> ROPE_COIL = BLOCKS.register("rope_coil",
            () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.WHITE_WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOL)));

    // Half-Slab Crates
    public static final RegistryObject<Block> EGG_CRATE = BLOCKS.register("egg_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TURTLE_EGG_CRATE = BLOCKS.register("turtle_egg_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MILK_CRATE = BLOCKS.register("milk_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GOAT_MILK_CRATE = BLOCKS.register("goat_milk_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RED_GRAPE_WINE_CRATE = BLOCKS.register("red_grape_wine_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GREEN_GRAPE_WINE_CRATE = BLOCKS.register("green_grape_wine_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RASPBERRY_WINE_CRATE = BLOCKS.register("raspberry_wine_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BLUEBERRY_WINE_CRATE = BLOCKS.register("blueberry_wine_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHERRY_WINE_CRATE = BLOCKS.register("cherry_wine_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SWEET_BERRY_WINE_CRATE = BLOCKS.register("sweet_berry_wine_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MEAD_CRATE = BLOCKS.register("mead_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WATER_CRATE = BLOCKS.register("water_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_CRATE = BLOCKS.register("honey_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SYRUP_CRATE = BLOCKS.register("syrup_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BROWN_MUSHROOM_CRATE = BLOCKS.register("brown_mushroom_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RED_MUSHROOM_CRATE = BLOCKS.register("red_mushroom_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CRIMSON_FUNGUS_CRATE = BLOCKS.register("crimson_fungus_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WARPED_FUNGUS_CRATE = BLOCKS.register("warped_fungus_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    // Pies
    public static final RegistryObject<Block> RASPBERRY_PIE = BLOCKS.register("raspberry_pie",
            () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), HHModItems.RASPBERRY_PIE_SLICE));
    public static final RegistryObject<Block> BLUEBERRY_PIE = BLOCKS.register("blueberry_pie",
            () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), HHModItems.BLUEBERRY_PIE_SLICE));
    public static final RegistryObject<Block> GRAPE_PIE = BLOCKS.register("grape_pie",
            () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), HHModItems.GRAPE_PIE_SLICE));
    public static final RegistryObject<Block> PEANUT_BUTTER_PIE = BLOCKS.register("peanut_butter_pie",
            () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), HHModItems.PEANUT_BUTTER_PIE_SLICE));
    public static final RegistryObject<Block> CHICKEN_POT_PIE = BLOCKS.register("chicken_pot_pie",
            () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), HHModItems.CHICKEN_POT_PIE_SLICE));
    public static final RegistryObject<Block> CARROT_CAKE = BLOCKS.register("carrot_cake",
            () -> new SliceableCakeBlock(Block.Properties.copy(Blocks.CAKE),  HHModItems.CARROT_CAKE_SLICE));

    public static final RegistryObject<Block> JAR = BLOCKS.register("jar",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> BLUEBERRY_JAM = BLOCKS.register("blueberry_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> CHERRY_JAM = BLOCKS.register("cherry_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> GRAPE_JAM = BLOCKS.register("grape_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> RASPBERRY_JAM = BLOCKS.register("raspberry_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> APPLE_JAM = BLOCKS.register("apple_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> SWEET_BERRY_JAM = BLOCKS.register("sweet_berry_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> GLOW_BERRY_JAM = BLOCKS.register("glow_berry_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).lightLevel(state -> 8).noOcclusion()));
    public static final RegistryObject<Block> MELON_JAM = BLOCKS.register("melon_jam",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> PEANUT_BUTTER = BLOCKS.register("peanut_butter",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> PICKLED_BEETROOTS = BLOCKS.register("pickled_beetroots",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> PICKLED_CABBAGE = BLOCKS.register("pickled_cabbage",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> PICKLED_CARROTS = BLOCKS.register("pickled_carrots",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> PICKLED_ONIONS = BLOCKS.register("pickled_onions",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> PICKLED_POTATOES = BLOCKS.register("pickled_potatoes",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(0.5F, 2.0F).sound(SoundType.GLASS).noOcclusion()));

    // Cheese
    public static final RegistryObject<Block> UNRIPE_CHEDDAR_CHEESE_WHEEL = BLOCKS.register("unripe_cheddar_cheese_wheel",
            () -> new UnripeCheeseWheelBlock(HHModBlocks.CHEDDAR_CHEESE_WHEEL, Block.Properties.copy(Blocks.CAKE)));
    public static final RegistryObject<Block> CHEDDAR_CHEESE_WHEEL = BLOCKS.register("cheddar_cheese_wheel",
            () -> new CheeseWheelBlock(HHModItems.CHEDDAR_CHEESE_SLICE, Block.Properties.copy(Blocks.CAKE)));

    public static final RegistryObject<Block> UNRIPE_GOAT_CHEESE_WHEEL = BLOCKS.register("unripe_goat_cheese_wheel",
            () -> new UnripeCheeseWheelBlock(HHModBlocks.GOAT_CHEESE_WHEEL, Block.Properties.copy(Blocks.CAKE)));
    public static final RegistryObject<Block> GOAT_CHEESE_WHEEL = BLOCKS.register("goat_cheese_wheel",
            () -> new CheeseWheelBlock(HHModItems.GOAT_CHEESE_SLICE, Block.Properties.copy(Blocks.CAKE)));
}