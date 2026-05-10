package alabaster.hearthandharvest.integration.everycompat;

import alabaster.hearthandharvest.common.block.BottleRackBlock;
import alabaster.hearthandharvest.common.block.HalfCabinetBlock;
import alabaster.hearthandharvest.common.registry.HHModBlockEntities;
import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

public class EveryCompatModule extends SimpleModule {

    private static final String FD = "farmersdelight";
    private static final String HH = "hearthandharvest";

    public final SimpleEntrySet<WoodType, HalfCabinetBlock> halfCabinets;
    public final SimpleEntrySet<WoodType, BottleRackBlock> bottleRacks;

    public EveryCompatModule(String modId) {
        super(modId, "hnh");

        halfCabinets = SimpleEntrySet.builder(
                        WoodType.class,
                        "half_cabinet",
                        () -> (HalfCabinetBlock) HHModBlocks.OAK_HALF_CABINET.get(),
                        () -> VanillaWoodTypes.OAK,
                        w -> new HalfCabinetBlock(BlockBehaviour.Properties.ofFullCopy(w.planks))
                )
                .addTile(ModBlockEntityTypes.CABINET)
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_side"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_top"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(FD, "block/oak_cabinet_front"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(FD, "block/oak_cabinet_front_open"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(FD, "block/oak_cabinet_side"))
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
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_side"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(HH, "block/oak_half_cabinet_top"))
                .addTexture(ResourceLocation.fromNamespaceAndPath(FD, "block/oak_cabinet_side"))
                .build();
        addEntry(bottleRacks);
    }
}