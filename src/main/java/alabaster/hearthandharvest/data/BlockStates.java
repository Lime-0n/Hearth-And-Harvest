package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.*;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.PieBlock;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class BlockStates extends BlockStateProvider
{
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public BlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, HearthAndHarvest.MODID, existingFileHelper);
    }

    private String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "block/" + path);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        this.halfCabinetBlock(HHModBlocks.OAK_HALF_CABINET.get(), "oak");
        this.halfCabinetBlock(HHModBlocks.BIRCH_HALF_CABINET.get(), "birch");
        this.halfCabinetBlock(HHModBlocks.SPRUCE_HALF_CABINET.get(), "spruce");
        this.halfCabinetBlock(HHModBlocks.JUNGLE_HALF_CABINET.get(), "jungle");
        this.halfCabinetBlock(HHModBlocks.ACACIA_HALF_CABINET.get(), "acacia");
        this.halfCabinetBlock(HHModBlocks.DARK_OAK_HALF_CABINET.get(), "dark_oak");
        this.halfCabinetBlock(HHModBlocks.MANGROVE_HALF_CABINET.get(), "mangrove");
        this.halfCabinetBlock(HHModBlocks.CHERRY_HALF_CABINET.get(), "cherry");
        this.halfCabinetBlock(HHModBlocks.BAMBOO_HALF_CABINET.get(), "bamboo");
        this.halfCabinetBlock(HHModBlocks.CRIMSON_HALF_CABINET.get(), "crimson");
        this.halfCabinetBlock(HHModBlocks.WARPED_HALF_CABINET.get(), "warped");

        this.wineRackBlock(HHModBlocks.OAK_WINE_RACK.get(), "oak");
        this.wineRackBlock(HHModBlocks.BIRCH_WINE_RACK.get(), "birch");
        this.wineRackBlock(HHModBlocks.SPRUCE_WINE_RACK.get(), "spruce");
        this.wineRackBlock(HHModBlocks.JUNGLE_WINE_RACK.get(), "jungle");
        this.wineRackBlock(HHModBlocks.ACACIA_WINE_RACK.get(), "acacia");
        this.wineRackBlock(HHModBlocks.DARK_OAK_WINE_RACK.get(), "dark_oak");
        this.wineRackBlock(HHModBlocks.MANGROVE_WINE_RACK.get(), "mangrove");
        this.wineRackBlock(HHModBlocks.CHERRY_WINE_RACK.get(), "cherry");
        this.wineRackBlock(HHModBlocks.BAMBOO_WINE_RACK.get(), "bamboo");
        this.wineRackBlock(HHModBlocks.CRIMSON_WINE_RACK.get(), "crimson");
        this.wineRackBlock(HHModBlocks.WARPED_WINE_RACK.get(), "warped");
        
        this.crateBlock(HHModBlocks.CHERRY_CRATE.get(), "cherry");
        this.crateBlock(HHModBlocks.BLUEBERRY_CRATE.get(), "blueberry");
        this.crateBlock(HHModBlocks.RASPBERRY_CRATE.get(), "raspberry");
        this.crateBlock(HHModBlocks.RED_GRAPE_CRATE.get(), "red_grape");
        this.crateBlock(HHModBlocks.GREEN_GRAPE_CRATE.get(), "green_grape");
        this.crateBlock(HHModBlocks.PEANUT_CRATE.get(), "peanut");

        this.crateBlock(HHModBlocks.APPLE_CRATE.get(), "apple");
        this.crateBlock(HHModBlocks.GOLDEN_APPLE_CRATE.get(), "golden_apple");
        this.crateBlock(HHModBlocks.GOLDEN_CARROT_CRATE.get(), "golden_carrot");
        this.crateBlock(HHModBlocks.POISONOUS_POTATO_CRATE.get(), "poisonous_potato");
        this.crateBlock(HHModBlocks.GLOW_BERRY_CRATE.get(), "glow_berry");
        this.crateBlock(HHModBlocks.SWEET_BERRY_CRATE.get(), "sweet_berry");

        this.customStageBlock(HHModBlocks.RASPBERRY_BUSH.get(), resourceBlock("crop_cross"), "cross", RaspberryBushBlock.AGE, Arrays.asList(0, 1, 2, 3, 3));
        this.customStageBlock(HHModBlocks.BLUEBERRY_BUSH.get(), resourceBlock("crop_cross"), "cross", BlueberryBushBlock.AGE, Arrays.asList(0, 1, 2, 3, 3));
        this.customStageBlock(HHModBlocks.BUDDING_RED_GRAPE_CROP.get(), resourceBlock("crop_cross"), "cross", BuddingRedGrapeBlock.AGE, Arrays.asList(0, 1, 2, 3, 3));
        this.customStageBlock(HHModBlocks.BUDDING_GREEN_GRAPE_CROP.get(), resourceBlock("crop_cross"), "cross", BuddingRedGrapeBlock.AGE, Arrays.asList(0, 1, 2, 3, 3));
        this.customStageBlock(HHModBlocks.PEANUT_CROP.get(), mcLoc("crop"), "crop", PeanutBlock.AGE, Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3));
        this.customStageBlock(HHModBlocks.COTTON_CROP.get(), resourceBlock("crop_cross"), "cross", CottonBlock.AGE, Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3));

        this.pieBlock(HHModBlocks.RASPBERRY_PIE.get());
        this.pieBlock(HHModBlocks.BLUEBERRY_PIE.get());
        this.pieBlock(HHModBlocks.GRAPE_PIE.get());
        this.pieBlock(HHModBlocks.CHICKEN_POT_PIE.get());

        this.axisBlock((RotatedPillarBlock) HHModBlocks.ROPE_COIL.get());
        this.axisBlock((RotatedPillarBlock) HHModBlocks.COTTON_BALE.get());
        this.axisBlock((RotatedPillarBlock) HHModBlocks.SPOOL.get());

        this.bagBlock(HHModBlocks.SALT_BAG.get(), "salt");
        this.bagBlock(HHModBlocks.SUGAR_BAG.get(), "sugar");
        this.bagBlock(HHModBlocks.COCOA_BEAN_BAG.get(), "cocoa_bean");
        this.bagBlock(HHModBlocks.GUNPOWDER_BAG.get(), "gunpowder");

        this.wildCropBlock(HHModBlocks.WILD_RED_GRAPES.get());
        this.wildCropBlock(HHModBlocks.WILD_GREEN_GRAPES.get());
        this.wildCropBlock(HHModBlocks.WILD_COTTON.get());
        this.wildCropBlock(HHModBlocks.WILD_PEANUTS.get());

        this.jarBlock(HHModBlocks.BLUEBERRY_JAM_JAR.get(), "blueberry_jam");
        this.jarBlock(HHModBlocks.CHERRY_JAM_JAR.get(), "cherry_jam");
        this.jarBlock(HHModBlocks.GRAPE_JAM_JAR.get(), "grape_jam");
        this.jarBlock(HHModBlocks.RASPBERRY_JAM_JAR.get(), "raspberry_jam");
        this.jarBlock(HHModBlocks.APPLE_JAM_JAR.get(), "apple_jam");
        this.jarBlock(HHModBlocks.SWEET_BERRY_JAM_JAR.get(), "sweet_berry_jam");
        this.jarBlock(HHModBlocks.GLOW_BERRY_JAM_JAR.get(), "glow_berry_jam");
        this.jarBlock(HHModBlocks.MELON_JAM_JAR.get(), "melon_jam");
        this.jarBlock(HHModBlocks.PEANUT_BUTTER_JAR.get(), "peanut_butter");
        this.jarBlock(HHModBlocks.PICKLED_BEETROOT_JAR.get(), "pickled_beetroot");
        this.jarBlock(HHModBlocks.PICKLED_CABBAGE_JAR.get(), "pickled_cabbage");
        this.jarBlock(HHModBlocks.PICKLED_CARROT_JAR.get(), "pickled_carrot");
        this.jarBlock(HHModBlocks.PICKLED_ONION_JAR.get(), "pickled_onion");
        this.jarBlock(HHModBlocks.PICKLED_POTATO_JAR.get(), "pickled_potato");

    }

    public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    int ageSuffix = state.getValue(ageProperty);
                    String stageName = blockName(block) + "_stage";
                    stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size(), ageSuffix));
                    if (parent == null) {
                        return ConfiguredModel.builder()
                                .modelFile(models().cross(stageName, resourceBlock(stageName)).renderType("cutout")).build();
                    }
                    return ConfiguredModel.builder()
                            .modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName)).renderType("cutout")).build();
                }, ignored);
    }

    public void halfCabinetBlock(Block block, String woodType) {
        this.horizontalBlock(block, state -> {
            String suffix = state.getValue(CabinetBlock.OPEN) ? "_open" : "";
            String modelName = woodType + "_half_cabinet" + suffix;

            return models().getBuilder(modelName)
                    .parent(existingModel("half_cabinet"))
                    .texture("front", resourceBlock(woodType + "_cabinet_front" + suffix))
                    .texture("side", resourceBlock(woodType + "_half_cabinet_side"))
                    .texture("top", resourceBlock(woodType + "_half_cabinet_top"))
                    .texture("back", resourceBlock(woodType + "_cabinet_side"));
        });
    }

    public void wineRackBlock(Block block, String woodType) {
        this.horizontalBlock(block, state -> {
            String modelName = woodType + "_wine_rack";

            return models().getBuilder(modelName)
                    .parent(existingModel("wine_rack"))
                    .texture("side", resourceBlock(woodType + "_cabinet_side"))
                    .texture("rack_side", resourceBlock(woodType + "_wine_rack_side"))
                    .texture("rack_top", resourceBlock(woodType + "_wine_rack_top"));
        });
    }

    public void wildCropBlock(Block block) {
        this.wildCropBlock(block, false);
    }

    public void wildCropBlock(Block block, boolean isBushCrop) {
        if (isBushCrop) {
            this.simpleBlock(block, models().singleTexture(blockName(block), resourceBlock("bush_crop"), "crop", resourceBlock(blockName(block))).renderType("cutout"));
        } else {
            this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))).renderType("cutout"));
        }
    }

    public void crateBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block),
                        resourceBlock(cropName + "_crate_side"),
                        resourceBlock("crate_bottom"),
                        resourceBlock(cropName + "_crate_top")));
    }

    public void bagBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cube(blockName(block),
                        resourceBlock("bag_bottom"),
                        resourceBlock(cropName + "_bag_top"),
                        resourceBlock("bag_side_tied"),
                        resourceBlock("bag_side_tied"),
                        resourceBlock("bag_side"),
                        resourceBlock("bag_side"))
                        .texture("particle", resourceBlock(cropName + "_bag_top")
                ));
    }

    public void pieBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                            int bites = state.getValue(PieBlock.BITES);
                            String suffix = bites > 0 ? "_slice" + bites : "";
                            return ConfiguredModel.builder()
                                    .modelFile(existingModel(blockName(block) + suffix))
                                    .rotationY(((int) state.getValue(PieBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                                    .build();
                        }
                );
    }

    public void jarBlock(Block block, String jarType) {
        String baseModelPath = HearthAndHarvest.MODID + ":block/" + jarType + "_jar";
        String modelTexture = HearthAndHarvest.MODID + ":block/" + jarType + "_jar";

        for (int i = 1; i <= 4; i++) {
            String modelName = jarType + "_jar_" + i;
            String parentModel = HearthAndHarvest.MODID + ":block/generic_jar_" + i;

            // Generate model file
            models().withExistingParent(modelName, parentModel)
                    .texture("lid", modelTexture);
        }

        // Generate blockstate JSON with multipart rotations
        for (int i = 1; i <= 4; i++) {
            String modelPath = HearthAndHarvest.MODID + ":block/" + jarType + "_jar_" + i;

            ModelFile model = models().getExistingFile(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, jarType + "_jar_" + i));

            for (Direction dir : Direction.Plane.HORIZONTAL) {
                getMultipartBuilder(block)
                        .part()
                        .modelFile(model)
                        .rotationY(getYRotation(dir))
                        .addModel()
                        .condition(HorizontalDirectionalBlock.FACING, dir)
                        .condition(JarBlock.JARS, i, 4);
            }
        }
    }

    private int getYRotation(Direction direction) {
        return switch (direction) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }

    private String buildJarsCondition(int minLevel) {
        StringBuilder builder = new StringBuilder();
        for (int j = minLevel; j <= 4; j++) {
            if (builder.length() > 0) builder.append("|");
            builder.append(j);
        }
        return builder.toString();
    }

}