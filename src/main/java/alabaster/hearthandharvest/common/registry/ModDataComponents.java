package alabaster.hearthandharvest.common.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "hearthandharvest");

    // Register a component to hold the cook time (in seconds)
    public static final Supplier<DataComponentType<Integer>> COOK_TIME =
            DATA_COMPONENTS.registerComponentType("cook_time", builder ->
                    builder.persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
            );

    public static final Supplier<DataComponentType<Integer>> WATER_LEVEL =
            DATA_COMPONENTS.registerComponentType("water_level", builder ->
                    builder.persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
            );

    public static final Supplier<DataComponentType<Integer>> BONEMEAL_LEVEL =
            DATA_COMPONENTS.registerComponentType("bonemeal_level", builder ->
                    builder.persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
            );
}
