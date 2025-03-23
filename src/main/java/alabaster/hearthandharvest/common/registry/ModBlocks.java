package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class ModBlocks {

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

    // Crops
    public static final Supplier<Block> RASPBERRY_BUSH = BLOCKS.register("raspberry_bush",
            () -> new RaspberryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final Supplier<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush",
            () -> new BlueberryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final Supplier<Block> BUDDING_GRAPE_CROP = BLOCKS.register("budding_grapes",
            () -> new BuddingGrapeBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> GRAPE_CROP = BLOCKS.register("grapes",
            () -> new GrapeVineBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> PEANUT_CROP = BLOCKS.register("peanuts",
            () -> new PeanutBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final Supplier<Block> COTTON_CROP = BLOCKS.register("cotton",
            () -> new CottonBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));

    // Crates
    public static final Supplier<Block> RASPBERRY_CRATE = BLOCKS.register("raspberry_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> BLUEBERRY_CRATE = BLOCKS.register("blueberry_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GRAPE_CRATE = BLOCKS.register("grape_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> PEANUT_CRATE = BLOCKS.register("peanut_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> APPLE_CRATE = BLOCKS.register("apple_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GOLDEN_APPLE_CRATE = BLOCKS.register("golden_apple_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> GOLDEN_CARROT_CRATE = BLOCKS.register("golden_carrot_crate",
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
    public static final Supplier<Block> MEAD_CRATE = BLOCKS.register("mead_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> WINE_CRATE = BLOCKS.register("wine_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> WATER_CRATE = BLOCKS.register("water_crate",
            () -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> HONEY_CRATE = BLOCKS.register("honey_crate",
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
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), ModItems.RASPBERRY_PIE_SLICE));
    public static final Supplier<Block> BLUEBERRY_PIE = BLOCKS.register("blueberry_pie",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), ModItems.BLUEBERRY_PIE_SLICE));
    public static final Supplier<Block> GRAPE_PIE = BLOCKS.register("grape_pie",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), ModItems.GRAPE_PIE_SLICE));
    public static final Supplier<Block> CHICKEN_POT_PIE = BLOCKS.register("chicken_pot_pie",
            () -> new PieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), ModItems.CHICKEN_POT_PIE_SLICE));
    public static final Supplier<Block> CARROT_CAKE = BLOCKS.register("carrot_cake",
            () -> new SliceableCakeBlock(Block.Properties.ofFullCopy(Blocks.CAKE),  ModItems.CARROT_CAKE_SLICE));

    // Cheese
    public static final Supplier<Block> UNRIPE_CHEDDAR_CHEESE_WHEEL = BLOCKS.register("unripe_cheddar_cheese_wheel",
            () -> new UnripeCheeseWheelBlock(ModBlocks.CHEDDAR_CHEESE_WHEEL, Block.Properties.ofFullCopy(Blocks.CAKE)));
    public static final Supplier<Block> CHEDDAR_CHEESE_WHEEL = BLOCKS.register("cheddar_cheese_wheel",
            () -> new CheeseWheelBlock(ModItems.CHEDDAR_CHEESE_SLICE, Block.Properties.ofFullCopy(Blocks.CAKE)));;

    public static final Supplier<Block> UNRIPE_GOAT_CHEESE_WHEEL = BLOCKS.register("unripe_goat_cheese_wheel",
            () -> new UnripeCheeseWheelBlock(ModBlocks.GOAT_CHEESE_WHEEL, Block.Properties.ofFullCopy(Blocks.CAKE)));
    public static final Supplier<Block> GOAT_CHEESE_WHEEL = BLOCKS.register("goat_cheese_wheel",
            () -> new CheeseWheelBlock(ModItems.GOAT_CHEESE_SLICE, Block.Properties.ofFullCopy(Blocks.CAKE)));;
}