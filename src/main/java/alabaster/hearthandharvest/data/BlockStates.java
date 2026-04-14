package alabaster.hearthandharvest.data;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.*;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
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

        this.bottleRackBlock(HHModBlocks.OAK_BOTTLE_RACK.get(), "oak");
        this.bottleRackBlock(HHModBlocks.BIRCH_BOTTLE_RACK.get(), "birch");
        this.bottleRackBlock(HHModBlocks.SPRUCE_BOTTLE_RACK.get(), "spruce");
        this.bottleRackBlock(HHModBlocks.JUNGLE_BOTTLE_RACK.get(), "jungle");
        this.bottleRackBlock(HHModBlocks.ACACIA_BOTTLE_RACK.get(), "acacia");
        this.bottleRackBlock(HHModBlocks.DARK_OAK_BOTTLE_RACK.get(), "dark_oak");
        this.bottleRackBlock(HHModBlocks.MANGROVE_BOTTLE_RACK.get(), "mangrove");
        this.bottleRackBlock(HHModBlocks.CHERRY_BOTTLE_RACK.get(), "cherry");
        this.bottleRackBlock(HHModBlocks.BAMBOO_BOTTLE_RACK.get(), "bamboo");
        this.bottleRackBlock(HHModBlocks.CRIMSON_BOTTLE_RACK.get(), "crimson");
        this.bottleRackBlock(HHModBlocks.WARPED_BOTTLE_RACK.get(), "warped");
        
        this.crateBlock(HHModBlocks.CHERRY_CRATE.get(), "cherry");
        this.crateBlock(HHModBlocks.BLUEBERRY_CRATE.get(), "blueberry");
        this.crateBlock(HHModBlocks.RASPBERRY_CRATE.get(), "raspberry");
        this.crateBlock(HHModBlocks.RED_GRAPE_CRATE.get(), "red_grape");
        this.crateBlock(HHModBlocks.GREEN_GRAPE_CRATE.get(), "green_grape");
        this.crateBlock(HHModBlocks.PEANUT_CRATE.get(), "peanut");
        this.crateBlock(HHModBlocks.CORN_CRATE.get(), "corn");
        this.crateBlock(HHModBlocks.APPLE_CRATE.get(), "apple");
        this.crateBlock(HHModBlocks.GOLDEN_APPLE_CRATE.get(), "golden_apple");
        this.crateBlock(HHModBlocks.GOLDEN_CARROT_CRATE.get(), "golden_carrot");
        this.crateBlock(HHModBlocks.POISONOUS_POTATO_CRATE.get(), "poisonous_potato");
        this.crateBlock(HHModBlocks.ROTTEN_TOMATO_CRATE.get(), "rotten_tomato");
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
        this.pieBlock(HHModBlocks.PEANUT_BUTTER_PIE.get());
        this.pieBlock(HHModBlocks.CHICKEN_POT_PIE.get());

        this.axisBlock((RotatedPillarBlock) HHModBlocks.ROPE_COIL.get());
        this.axisBlock((RotatedPillarBlock) HHModBlocks.COTTON_BALE.get());
        this.axisBlock((RotatedPillarBlock) HHModBlocks.SPOOL.get());
        this.axisBlock((RotatedPillarBlock) HHModBlocks.STICK_BRUSH.get());

        this.bagBlock(HHModBlocks.SALT_BAG.get(), "salt");
        this.bagBlock(HHModBlocks.SUGAR_BAG.get(), "sugar");
        this.bagBlock(HHModBlocks.COCOA_BEAN_BAG.get(), "cocoa_bean");
        this.bagBlock(HHModBlocks.GUNPOWDER_BAG.get(), "gunpowder");
        this.bagBlock(HHModBlocks.CORN_KERNEL_BAG.get(), "corn_kernel");

        this.simpleBlock(HHModBlocks.CHARCOAL_BLOCK.get());

        this.wildCropBlock(HHModBlocks.WILD_RED_GRAPES.get());
        this.wildCropBlock(HHModBlocks.WILD_GREEN_GRAPES.get());
        this.wildCropBlock(HHModBlocks.WILD_COTTON.get());
        this.wildCropBlock(HHModBlocks.WILD_PEANUTS.get());

        this.wildCropBlock(HHModBlocks.YELLOW_MUM.get());
        this.wildCropBlock(HHModBlocks.ORANGE_MUM.get());
        this.wildCropBlock(HHModBlocks.RED_MUM.get());
        this.wildCropBlock(HHModBlocks.BLUE_MUM.get());
        this.wildCropBlock(HHModBlocks.LIGHT_BLUE_MUM.get());
        this.wildCropBlock(HHModBlocks.PURPLE_MUM.get());
        this.wildCropBlock(HHModBlocks.PINK_MUM.get());
        this.wildCropBlock(HHModBlocks.WHITE_MUM.get());

        this.pottedFlower(HHModBlocks.POTTED_YELLOW_MUM.get(), HHModBlocks.YELLOW_MUM.get());
        this.pottedFlower(HHModBlocks.POTTED_ORANGE_MUM.get(), HHModBlocks.ORANGE_MUM.get());
        this.pottedFlower(HHModBlocks.POTTED_RED_MUM.get(), HHModBlocks.RED_MUM.get());
        this.pottedFlower(HHModBlocks.POTTED_BLUE_MUM.get(), HHModBlocks.BLUE_MUM.get());
        this.pottedFlower(HHModBlocks.POTTED_LIGHT_BLUE_MUM.get(), HHModBlocks.LIGHT_BLUE_MUM.get());
        this.pottedFlower(HHModBlocks.POTTED_PURPLE_MUM.get(), HHModBlocks.PURPLE_MUM.get());
        this.pottedFlower(HHModBlocks.POTTED_PINK_MUM.get(), HHModBlocks.PINK_MUM.get());
        this.pottedFlower(HHModBlocks.POTTED_WHITE_MUM.get(), HHModBlocks.WHITE_MUM.get());

        this.jarBlock(HHModBlocks.JAR.get(), "jar");
        this.jarBlock(HHModBlocks.BLUEBERRY_JAM.get(), "blueberry_jam");
        this.jarBlock(HHModBlocks.CHERRY_JAM.get(), "cherry_jam");
        this.jarBlock(HHModBlocks.GRAPE_JAM.get(), "grape_jam");
        this.jarBlock(HHModBlocks.RASPBERRY_JAM.get(), "raspberry_jam");
        this.jarBlock(HHModBlocks.APPLE_JAM.get(), "apple_jam");
        this.jarBlock(HHModBlocks.SWEET_BERRY_JAM.get(), "sweet_berry_jam");
        this.jarBlock(HHModBlocks.GLOW_BERRY_JAM.get(), "glow_berry_jam");
        this.jarBlock(HHModBlocks.MELON_JAM.get(), "melon_jam");
        this.jarBlock(HHModBlocks.PEANUT_BUTTER.get(), "peanut_butter");
        this.jarBlock(HHModBlocks.PICKLED_BEETROOTS.get(), "pickled_beetroots");
        this.jarBlock(HHModBlocks.PICKLED_CABBAGE.get(), "pickled_cabbage");
        this.jarBlock(HHModBlocks.PICKLED_CARROTS.get(), "pickled_carrots");
        this.jarBlock(HHModBlocks.PICKLED_ONIONS.get(), "pickled_onions");
        this.jarBlock(HHModBlocks.PICKLED_POTATOES.get(), "pickled_potatoes");

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

    public void bottleRackBlock(Block block, String woodType) {
        this.horizontalBlock(block, state -> {
            String modelName = woodType + "_bottle_rack";

            return models().getBuilder(modelName)
                    .parent(existingModel("bottle_rack"))
                    .texture("side", resourceBlock(woodType + "_cabinet_side"))
                    .texture("rack_side", resourceBlock(woodType + "_half_cabinet_side"))
                    .texture("rack_top", resourceBlock(woodType + "_half_cabinet_top"));
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

    public void pottedFlower(Block pottedBlock, Block flowerBlock) {
        simpleBlock(pottedBlock,
                models().withExistingParent(blockName(pottedBlock), mcLoc("block/flower_pot_cross"))
                        .texture("plant", resourceBlock(blockName(flowerBlock)))
                        .renderType("cutout"));
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
        simpleBlock(block, models().getExistingFile(resourceBlock(jarType)));
    }

}