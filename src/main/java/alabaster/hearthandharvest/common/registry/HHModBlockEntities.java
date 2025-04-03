package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.block.entity.CaskBlockEntity;
import alabaster.hearthandharvest.common.block.entity.JugBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HHModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HearthAndHarvest.MODID);

    public static final RegistryObject<BlockEntityType<JugBlockEntity>> JUG = BLOCK_ENTITY_TYPES.register("jug_tile",
            () -> BlockEntityType.Builder.of(JugBlockEntity::new, HHModBlocks.JUG.get()).build(null));

    public static final RegistryObject<BlockEntityType<CaskBlockEntity>> CASK = BLOCK_ENTITY_TYPES.register("cask_tile",
            () -> BlockEntityType.Builder.of(CaskBlockEntity::new, HHModBlocks.CASK.get()).build(null));
}