package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.block.WildCropBlock;

import java.util.function.Supplier;

public class HHModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, HearthAndHarvest.MODID);

    // Workstations
    public static final Supplier<Block> TREE_TAPPER = BLOCKS.register("tree_tapper",
            () -> new TreeTapperBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F).sound(SoundType.WOOD).randomTicks()));
    public static final Supplier<Block> CASK = BLOCKS.register("cask",
            () -> new CaskBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F).sound(SoundType.WOOD).randomTicks()));
    public static final Supplier<Block> JUG = BLOCKS.register("jug",
            () -> new JugBlock(Block.Properties.ofFullCopy(Blocks.IRON_BARS).strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final Supplier<Block> JAR = BLOCKS.register("jar",
            () -> new JarBlock(Block.Properties.ofFullCopy(Blocks.GLASS).strength(2.0F, 3.0F).sound(SoundType.GLASS)));

    public static final Supplier<Block> SAP_CAULDRON = BLOCKS.register("sap_cauldron",
            () -> new SapCauldronBlock(Block.Properties.ofFullCopy(Blocks.CAULDRON).strength(2.0F, 3.0F).sound(SoundType.METAL).randomTicks()));

    public static final Supplier<Block> COUNTER = BLOCKS.register("counter",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final Supplier<Block> DRAWER = BLOCKS.register("drawer",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final Supplier<Block> BASIN = BLOCKS.register("basin",
            () -> new BasinBlock(Block.Properties.ofFullCopy(Blocks.BRICKS).randomTicks()));

    // Half-Cabinets
    public static final Supplier<Block> OAK_HALF_CABINET = BLOCKS.register("oak_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final Supplier<Block> SPRUCE_HALF_CABINET = BLOCKS.register("spruce_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final Supplier<Block> BIRCH_HALF_CABINET = BLOCKS.register("birch_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final Supplier<Block> JUNGLE_HALF_CABINET = BLOCKS.register("jungle_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final Supplier<Block> ACACIA_HALF_CABINET = BLOCKS.register("acacia_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final Supplier<Block> DARK_OAK_HALF_CABINET = BLOCKS.register("dark_oak_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final Supplier<Block> MANGROVE_HALF_CABINET = BLOCKS.register("mangrove_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));
    public static final Supplier<Block> CHERRY_HALF_CABINET = BLOCKS.register("cherry_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL).sound(SoundType.CHERRY_WOOD)));
    public static final Supplier<Block> BAMBOO_HALF_CABINET = BLOCKS.register("bamboo_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL).sound(SoundType.BAMBOO_WOOD)));
    public static final Supplier<Block> CRIMSON_HALF_CABINET = BLOCKS.register("crimson_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL).sound(SoundType.NETHER_WOOD)));
    public static final Supplier<Block> WARPED_HALF_CABINET = BLOCKS.register("warped_half_cabinet",
            () -> new HalfCabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL).sound(SoundType.NETHER_WOOD)));

    // Wild Crops
    public static final Supplier<Block> WILD_RED_GRAPES = BLOCKS.register("wild_red_grapes",
            () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 10, Block.Properties.ofFullCopy(Blocks.TALL_GRASS)));
    public static final Supplier<Block> WILD_GREEN_GRAPES = BLOCKS.register("wild_green_grapes",
            () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 10, Block.Properties.ofFullCopy(Blocks.TALL_GRASS)));

    // Crops
    public static final Supplier<Block> RASPBERRY_BUSH = BLOCKS.register("raspberry_bush",
            () -> new RaspberryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final Supplier<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush",
            () -> new BlueberryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final Supplier<Block> BUDDING_RED_GRAPE_CROP = BLOCKS.register("budding_red_grapes",
            () -> new BuddingRedGrapeBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> RED_GRAPE_CROP = BLOCKS.register("red_grapes",
            () -> new RedGrapeVineBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> BUDDING_GREEN_GRAPE_CROP = BLOCKS.register("budding_green_grapes",
            () -> new BuddingGreenGrapeBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> GREEN_GRAPE_CROP = BLOCKS.register("green_grapes",
            () -> new GreenGrapeVineBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> PEANUT_CROP = BLOCKS.register("peanuts",
            () -> new PeanutBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> COTTON_CROP = BLOCKS.register("cotton",
            () -> new CottonBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));

    // Crates

    public static final Supplier<Block> BLUEBERRY_CRATE = BLOCKS.register("blueberry_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> CHERRY_CRATE = BLOCKS.register("cherry_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> RED_GRAPE_CRATE = BLOCKS.register("red_grape_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GREEN_GRAPE_CRATE = BLOCKS.register("green_grape_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> RASPBERRY_CRATE = BLOCKS.register("raspberry_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> PEANUT_CRATE = BLOCKS.register("peanut_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> APPLE_CRATE = BLOCKS.register("apple_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GOLDEN_APPLE_CRATE = BLOCKS.register("golden_apple_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GOLDEN_CARROT_CRATE = BLOCKS.register("golden_carrot_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> POISONOUS_POTATO_CRATE = BLOCKS.register("poisonous_potato_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GLOW_BERRY_CRATE = BLOCKS.register("glow_berry_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel(state -> 10)));
    public static final Supplier<Block> SWEET_BERRY_CRATE = BLOCKS.register("sweet_berry_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    
    // Bags
    public static final Supplier<Block> SALT_BAG = BLOCKS.register("salt_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.GRAVEL)));
    public static final Supplier<Block> SUGAR_BAG = BLOCKS.register("sugar_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.GRAVEL)));
    public static final Supplier<Block> COCOA_BEAN_BAG = BLOCKS.register("cocoa_bean_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.GRAVEL)));
    public static final Supplier<Block> GUNPOWDER_BAG = BLOCKS.register("gunpowder_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.GRAVEL)));
    
    // Misc
    public static final Supplier<Block> COTTON_BALE = BLOCKS.register("cotton_bale",
            () -> new HayBlock(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final Supplier<Block> SPOOL = BLOCKS.register("spool",
            () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOL)));
    public static final Supplier<Block> ROPE_COIL = BLOCKS.register("rope_coil",
            () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOL)));

    // Half-Slab Crates
    public static final Supplier<Block> EGG_CRATE = BLOCKS.register("egg_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> TURTLE_EGG_CRATE = BLOCKS.register("turtle_egg_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> MILK_CRATE = BLOCKS.register("milk_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GOAT_MILK_CRATE = BLOCKS.register("goat_milk_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> RED_GRAPE_WINE_CRATE = BLOCKS.register("red_grape_wine_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GREEN_GRAPE_WINE_CRATE = BLOCKS.register("green_grape_wine_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> RASPBERRY_WINE_CRATE = BLOCKS.register("raspberry_wine_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> BLUEBERRY_WINE_CRATE = BLOCKS.register("blueberry_wine_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> CHERRY_WINE_CRATE = BLOCKS.register("cherry_wine_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> MEAD_CRATE = BLOCKS.register("mead_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> WATER_CRATE = BLOCKS.register("water_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> HONEY_CRATE = BLOCKS.register("honey_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> SYRUP_CRATE = BLOCKS.register("syrup_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> BROWN_MUSHROOM_CRATE = BLOCKS.register("brown_mushroom_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> RED_MUSHROOM_CRATE = BLOCKS.register("red_mushroom_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> CRIMSON_FUNGUS_CRATE = BLOCKS.register("crimson_fungus_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> WARPED_FUNGUS_CRATE = BLOCKS.register("warped_fungus_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    // Pies
    public static final Supplier<Block> RASPBERRY_PIE = BLOCKS.register("raspberry_pie",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), HHModItems.RASPBERRY_PIE_SLICE));
    public static final Supplier<Block> BLUEBERRY_PIE = BLOCKS.register("blueberry_pie",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), HHModItems.BLUEBERRY_PIE_SLICE));
    public static final Supplier<Block> GRAPE_PIE = BLOCKS.register("grape_pie",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), HHModItems.GRAPE_PIE_SLICE));
    public static final Supplier<Block> CHICKEN_POT_PIE = BLOCKS.register("chicken_pot_pie",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), HHModItems.CHICKEN_POT_PIE_SLICE));
    public static final Supplier<Block> CARROT_CAKE = BLOCKS.register("carrot_cake",
            () -> new SliceableCakeBlock(Block.Properties.ofFullCopy(Blocks.CAKE),  HHModItems.CARROT_CAKE_SLICE));

    // Cheese
    public static final Supplier<Block> UNRIPE_CHEDDAR_CHEESE_WHEEL = BLOCKS.register("unripe_cheddar_cheese_wheel",
            () -> new UnripeCheeseWheelBlock(HHModBlocks.CHEDDAR_CHEESE_WHEEL, Block.Properties.ofFullCopy(Blocks.CAKE)));
    public static final Supplier<Block> CHEDDAR_CHEESE_WHEEL = BLOCKS.register("cheddar_cheese_wheel",
            () -> new CheeseWheelBlock(HHModItems.CHEDDAR_CHEESE_SLICE, Block.Properties.ofFullCopy(Blocks.CAKE)));;

    public static final Supplier<Block> UNRIPE_GOAT_CHEESE_WHEEL = BLOCKS.register("unripe_goat_cheese_wheel",
            () -> new UnripeCheeseWheelBlock(HHModBlocks.GOAT_CHEESE_WHEEL, Block.Properties.ofFullCopy(Blocks.CAKE)));
    public static final Supplier<Block> GOAT_CHEESE_WHEEL = BLOCKS.register("goat_cheese_wheel",
            () -> new CheeseWheelBlock(HHModItems.GOAT_CHEESE_SLICE, Block.Properties.ofFullCopy(Blocks.CAKE)));;
}