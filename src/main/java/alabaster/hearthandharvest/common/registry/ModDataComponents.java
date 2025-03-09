package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(HearthAndHarvest.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ROASTING_TIME = DATA_COMPONENTS.registerComponentType(
            "roasting_time", (builder) -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
    );
}
