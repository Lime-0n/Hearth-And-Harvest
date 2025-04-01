package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HHModPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, HearthAndHarvest.MODID);

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }

    // Pungent Potion
    public static final DeferredHolder<Potion, Potion> PUNGENT_POTION = POTIONS.register("pungent_potion", () -> new Potion(new MobEffectInstance(HHModEffects.PUNGENT, 3600, 0)));
    // Long Pungent Potion
    public static final DeferredHolder<Potion, Potion> LONG_PUNGENT_POTION = POTIONS.register("long_pungent_potion", () -> new Potion(new MobEffectInstance(HHModEffects.PUNGENT, 9600, 0)));
    // Strong Pungent Potion
    public static final DeferredHolder<Potion, Potion> STRONG_PUNGENT_POTION = POTIONS.register("strong_pungent_potion", () -> new Potion(new MobEffectInstance(HHModEffects.PUNGENT, 1800, 1)));

    // Tempting Potion
    public static final DeferredHolder<Potion, Potion> TEMPTING_POTION = POTIONS.register("tempting_potion", () -> new Potion(new MobEffectInstance(HHModEffects.TEMPTING, 3600, 0)));
    // Long Tempting Potion
    public static final DeferredHolder<Potion, Potion> LONG_TEMPTING_POTION = POTIONS.register("long_tempting_potion", () -> new Potion(new MobEffectInstance(HHModEffects.TEMPTING, 9600, 0)));
    // Strong Tempting Potion
    public static final DeferredHolder<Potion, Potion> STRONG_TEMPTING_POTION = POTIONS.register("strong_tempting_potion", () -> new Potion(new MobEffectInstance(HHModEffects.TEMPTING, 1800, 1)));

}
