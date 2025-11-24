package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HHModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, HearthAndHarvest.MODID);

    // Crow
    public static final Supplier<SoundEvent> CROW_SQUAWK = SOUNDS.register("entities.crow.squawking",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "entities.crow.squawking")));

    public static final Supplier<SoundEvent> CROW_HURT = SOUNDS.register("entities.crow.hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "entities.crow.hurt")));

    public static final Supplier<SoundEvent> CROW_EAT = SOUNDS.register("entities.crow.eat",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "entities.crow.eat")));

    public static final Supplier<SoundEvent> CROW_STEP = SOUNDS.register("entities.crow.ste",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(HearthAndHarvest.MODID, "entities.crow.step")));
}
