package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EffectEvents {

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();

        // Clarity
        MobEffectInstance incoming = event.getEffectInstance();

        if ((incoming.getEffect().is(MobEffects.BLINDNESS) || incoming.getEffect().is(MobEffects.DARKNESS))
                && entity.hasEffect(HHModEffects.CLARITY)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            return;
        }

        // Drunk
        if (incoming.getEffect().is(HHModEffects.DRUNK.getKey())) return;
        if (incoming.getEffect().value().getCategory() != MobEffectCategory.BENEFICIAL) return;

        MobEffectInstance drunkInstance = entity.getActiveEffects().stream()
                .filter(e -> e.getEffect().is(HHModEffects.DRUNK.getKey()))
                .findFirst()
                .orElse(null);

        if (drunkInstance != null && drunkInstance.getAmplifier() >= 4) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        if (event.getEffectInstance().getEffect().is(HHModEffects.CLARITY)) {
            event.getEntity().removeEffect(MobEffects.BLINDNESS);
            event.getEntity().removeEffect(MobEffects.DARKNESS);
        }
    }
}