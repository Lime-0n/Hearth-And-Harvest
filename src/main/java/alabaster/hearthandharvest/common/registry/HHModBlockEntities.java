package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.entity.CrateBlockEntity;
import alabaster.hearthandharvest.common.block.entity.CaskBlockEntity;
import alabaster.hearthandharvest.common.block.entity.JugBlockEntity;
import alabaster.hearthandharvest.common.block.entity.BottleRackBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import java.util.function.Supplier;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.MOD)
public class HHModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HearthAndHarvest.MODID);

    public static final Supplier<BlockEntityType<JugBlockEntity>> JUG = BLOCK_ENTITY_TYPES.register("jug_tile",
            () -> BlockEntityType.Builder.of(JugBlockEntity::new, HHModBlocks.JUG.get()).build(null));

    public static final Supplier<BlockEntityType<CaskBlockEntity>> CASK = BLOCK_ENTITY_TYPES.register("cask_tile",
            () -> BlockEntityType.Builder.of(CaskBlockEntity::new, HHModBlocks.CASK.get()).build(null));

    public static final Supplier<BlockEntityType<BottleRackBlockEntity>> BOTTLE_RACK = BLOCK_ENTITY_TYPES.register("bottle_rack",
            () -> BlockEntityType.Builder.of(BottleRackBlockEntity::new,
                            HHModBlocks.OAK_BOTTLE_RACK.get(),
                            HHModBlocks.BIRCH_BOTTLE_RACK.get(),
                            HHModBlocks.SPRUCE_BOTTLE_RACK.get(),
                            HHModBlocks.JUNGLE_BOTTLE_RACK.get(),
                            HHModBlocks.ACACIA_BOTTLE_RACK.get(),
                            HHModBlocks.DARK_OAK_BOTTLE_RACK.get(),
                            HHModBlocks.MANGROVE_BOTTLE_RACK.get(),
                            HHModBlocks.BAMBOO_BOTTLE_RACK.get(),
                            HHModBlocks.CHERRY_BOTTLE_RACK.get(),
                            HHModBlocks.CRIMSON_BOTTLE_RACK.get(),
                            HHModBlocks.WARPED_BOTTLE_RACK.get())
                    .build(null));

    public static final Supplier<BlockEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITY_TYPES.register("crate",
            () -> BlockEntityType.Builder.of(CrateBlockEntity::new, HHModBlocks.CRATE.get()).build(null));

    @SubscribeEvent
    public static void addCabinetsBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(ModBlockEntityTypes.CABINET.get(),
                HHModBlocks.DRAWER.get(),
                HHModBlocks.OAK_HALF_CABINET.get(),
                HHModBlocks.BIRCH_HALF_CABINET.get(),
                HHModBlocks.SPRUCE_HALF_CABINET.get(),
                HHModBlocks.JUNGLE_HALF_CABINET.get(),
                HHModBlocks.ACACIA_HALF_CABINET.get(),
                HHModBlocks.DARK_OAK_HALF_CABINET.get(),
                HHModBlocks.MANGROVE_HALF_CABINET.get(),
                HHModBlocks.BAMBOO_HALF_CABINET.get(),
                HHModBlocks.CHERRY_HALF_CABINET.get(),
                HHModBlocks.CRIMSON_HALF_CABINET.get(),
                HHModBlocks.WARPED_HALF_CABINET.get()
        );
    }

    static {
        BLOCK_ENTITY_TYPES.addAlias(ResourceLocation.fromNamespaceAndPath("hearthandharvest", "wine_rack"),
                ResourceLocation.fromNamespaceAndPath("hearthandharvest", "bottle_rack"));
    }
}