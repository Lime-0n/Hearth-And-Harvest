package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.GAME)
public class JuicedEventHandler {

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance incoming = event.getEffectInstance();

        if (incoming.getEffect().is(HHModEffects.JUICED.getKey())) return;
        if (incoming.getEffect().value().getCategory() != MobEffectCategory.BENEFICIAL) return;

        MobEffectInstance drunkInstance = entity.getActiveEffects().stream()
                .filter(e -> e.getEffect().is(HHModEffects.JUICED.getKey()))
                .findFirst()
                .orElse(null);

        if (drunkInstance == null || drunkInstance.getAmplifier() < 4) return;

        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }
}