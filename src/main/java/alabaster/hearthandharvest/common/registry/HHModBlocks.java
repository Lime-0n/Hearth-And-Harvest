package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
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
    public static final RegistryObject<Block> JAR = BLOCKS.register("jar",
            () -> new JarBlock(Block.Properties.copy(Blocks.GLASS).strength(2.0F, 3.0F).sound(SoundType.GLASS)));

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
            () -> new BuddingRedGrapeBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> GREEN_GRAPE_CROP = BLOCKS.register("green_grapes",
            () -> new RedGrapeVineBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> PEANUT_CROP = BLOCKS.register("peanuts",
            () -> new PeanutBlock(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> COTTON_CROP = BLOCKS.register("cotton",
            () -> new CottonBlock(Block.Properties.copy(Blocks.WHEAT)));

    // Wild Crops
    public static final RegistryObject<Block> WILD_RED_GRAPES = BLOCKS.register("wild_red_grapes",
            () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 10, Block.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<Block> WILD_GREEN_GRAPES = BLOCKS.register("wild_green_grapes",
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
    public static final RegistryObject<Block> MEAD_CRATE = BLOCKS.register("mead_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WATER_CRATE = BLOCKS.register("water_crate",
            () -> new SlabBlock(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_CRATE = BLOCKS.register("honey_crate",
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
    public static final RegistryObject<Block> CHICKEN_POT_PIE = BLOCKS.register("chicken_pot_pie",
            () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), HHModItems.CHICKEN_POT_PIE_SLICE));
    public static final RegistryObject<Block> CARROT_CAKE = BLOCKS.register("carrot_cake",
            () -> new SliceableCakeBlock(Block.Properties.copy(Blocks.CAKE),  HHModItems.CARROT_CAKE_SLICE));

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