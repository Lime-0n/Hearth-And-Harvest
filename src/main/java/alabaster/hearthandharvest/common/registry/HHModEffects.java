package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.effect.PungentEffect;
import alabaster.hearthandharvest.common.effect.TemptingEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HHModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, HearthAndHarvest.MODID);

    public static final Holder<MobEffect> PUNGENT = EFFECTS.register("pungent", PungentEffect::new);
    public static final Holder<MobEffect> TEMPTING = EFFECTS.register("tempting", TemptingEffect::new);
}
