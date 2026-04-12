package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.common.registry.HHModEffects;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class EffectEvents {

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        // Cancel blindness and darkness if the entity currently has Clarity
        if ((event.getEffectInstance().getEffect().is(MobEffects.BLINDNESS)
                || event.getEffectInstance().getEffect().is(MobEffects.DARKNESS))
                && event.getEntity().hasEffect(HHModEffects.CLARITY)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        // When Clarity is applied, strip any active blindness and darkness
        if (event.getEffectInstance().getEffect().is(HHModEffects.CLARITY)) {
            event.getEntity().removeEffect(MobEffects.BLINDNESS);
            event.getEntity().removeEffect(MobEffects.DARKNESS);
        }
    }
}