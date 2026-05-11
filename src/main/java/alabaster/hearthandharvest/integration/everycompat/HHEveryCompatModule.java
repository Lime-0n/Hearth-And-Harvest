package alabaster.hearthandharvest.integration.everycompat;

import alabaster.hearthandharvest.common.block.BottleRackBlock;
import alabaster.hearthandharvest.common.block.HalfCabinetBlock;
import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import alabaster.hearthandharvest.common.registry.HHModCreativeTabs;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.every_compat.modules.farmersdelight.FarmersDelightModule;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import java.util.function.Consumer;

public class HHEveryCompatModule extends EveryCompatModule {

    private static final String HH = "hearthandharvest";

    public final SimpleEntrySet<WoodType, HalfCabinetBlock> halfCabinets;
    public final SimpleEntrySet<WoodType, BottleRackBlock> bottleRacks;

    public HHEveryCompatModule(String modId) {
        super(modId, "hnh");

        ResourceKey<CreativeModeTab> tab = HHModCreativeTabs.BLOCKS_TAB_KEY;

        halfCabinets = SimpleEntrySet.builder(
                        WoodType.class,
                        "half_cabinet",
                        () -> (HalfCabinetBlock) HHModBlocks.OAK_HALF_CABINET.get(),
                        () -> VanillaWoodTypes.OAK,
                        w -> new HalfCabinetBlock(BlockBehaviour.Properties.ofFullCopy(w.planks))
                )
                .addTile(ModBlockEntityTypes.CABINET)
                .setTabKey(tab)
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_side"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_top"))
                .addTextureM(
                        ResourceLocation.fromNamespaceAndPath(HH, "block/oak_cabinet_front"),
                        EveryCompat.res("block/fd/oak_cabinet_front_m"),
                        FarmersDelightModule.customPalette)
                .addTextureM(
                        ResourceLocation.fromNamespaceAndPath(HH, "block/oak_cabinet_front_open"),
                        EveryCompat.res("block/fd/oak_cabinet_front_m"),
                        FarmersDelightModule.customPalette)
                .addTexture(
                        ResourceLocation.fromNamespaceAndPath(HH, "block/oak_cabinet_side"),
                        FarmersDelightModule.customPalette)
                .build();
        addEntry(halfCabinets);

        bottleRacks = SimpleEntrySet.builder(
                        WoodType.class,
                        "bottle_rack",
                        () -> (BottleRackBlock) HHModBlocks.OAK_BOTTLE_RACK.get(),
                        () -> VanillaWoodTypes.OAK,
                        w -> new BottleRackBlock(BlockBehaviour.Properties.ofFullCopy(w.planks))
                )
                .addTile(HHModBlockEntities.BOTTLE_RACK)
                .setTabKey(tab)
                .defaultRecipe()
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_side"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_top"))
                .addTexture(
                        ResourceLocation.fromNamespaceAndPath(HH, "block/oak_cabinet_side"),
                        FarmersDelightModule.customPalette)
                .build();
        addEntry(bottleRacks);
    }

    @Override
    public void addDynamicServerResources(Consumer<ResourceGenTask> executor) {
        super.addDynamicServerResources(executor);

        executor.accept((manager, sink) -> {
            halfCabinets.blocks.forEach((woodType, halfCabBlock) -> {
                ResourceLocation fdCabinetId = ResourceLocation.fromNamespaceAndPath(
                        "everycomp", "fd/" + woodType.getAppendableId() + "_cabinet");

                var fdCabinetOpt = BuiltInRegistries.BLOCK.getOptional(fdCabinetId);
                if (fdCabinetOpt.isEmpty()) return;
                Block fdCabinet = fdCabinetOpt.get();

                sink.addRecipe(new RecipeHolder<>(
                        EveryCompat.res(shortenedId() + "/half_cabinet_from_cabinet/" + woodType.getAppendableId()),
                        new ShapelessRecipe("", CraftingBookCategory.MISC,
                                new ItemStack(halfCabBlock, 2),
                                NonNullList.of(Ingredient.EMPTY,
                                        Ingredient.of(fdCabinet),
                                        Ingredient.of(fdCabinet)))
                ));

                sink.addRecipe(new RecipeHolder<>(
                        EveryCompat.res(shortenedId() + "/cabinet_from_halves/" + woodType.getAppendableId()),
                        new ShapelessRecipe("", CraftingBookCategory.MISC,
                                new ItemStack(fdCabinet, 1),
                                NonNullList.of(Ingredient.EMPTY,
                                        Ingredient.of(halfCabBlock),
                                        Ingredient.of(halfCabBlock)))
                ));
            });
        });
    }
}