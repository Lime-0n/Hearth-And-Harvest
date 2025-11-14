package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HHModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, HearthAndHarvest.MODID);

    public static final Supplier<EntityType<CrowEntity>> CROW =
            ENTITY_TYPES.register("crow",
                    () -> EntityType.Builder.of(CrowEntity::new, MobCategory.AMBIENT).sized(0.4f, 0.5f).build("crow"));
}
