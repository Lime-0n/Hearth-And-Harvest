package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.entity.CaskBlockEntity;
import alabaster.hearthandharvest.common.block.entity.JugBlockEntity;
import alabaster.hearthandharvest.common.block.entity.WineRackBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HHModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HearthAndHarvest.MODID);

    public static final RegistryObject<BlockEntityType<JugBlockEntity>> JUG = BLOCK_ENTITY_TYPES.register("jug_tile",
            () -> BlockEntityType.Builder.of(JugBlockEntity::new, HHModBlocks.JUG.get()).build(null));

    public static final RegistryObject<BlockEntityType<CaskBlockEntity>> CASK = BLOCK_ENTITY_TYPES.register("cask_tile",
            () -> BlockEntityType.Builder.of(CaskBlockEntity::new, HHModBlocks.CASK.get()).build(null));

    public static final Supplier<BlockEntityType<WineRackBlockEntity>> WINE_RACK = BLOCK_ENTITY_TYPES.register("wine_rack",
            () -> BlockEntityType.Builder.of(WineRackBlockEntity::new,
                            HHModBlocks.OAK_WINE_RACK.get(),
                            HHModBlocks.BIRCH_WINE_RACK.get(),
                            HHModBlocks.SPRUCE_WINE_RACK.get(),
                            HHModBlocks.JUNGLE_WINE_RACK.get(),
                            HHModBlocks.ACACIA_WINE_RACK.get(),
                            HHModBlocks.DARK_OAK_WINE_RACK.get(),
                            HHModBlocks.MANGROVE_WINE_RACK.get(),
                            HHModBlocks.BAMBOO_WINE_RACK.get(),
                            HHModBlocks.CHERRY_WINE_RACK.get(),
                            HHModBlocks.CRIMSON_WINE_RACK.get(),
                            HHModBlocks.WARPED_WINE_RACK.get())
                    .build(null));
}