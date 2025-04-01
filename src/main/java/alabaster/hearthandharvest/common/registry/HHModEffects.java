package alabaster.hearthandharvest.common.registry;

import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.effect.PungentEffect;
import alabaster.hearthandharvest.common.effect.TemptingEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HHModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, HearthAndHarvest.MODID);

    public static final RegistryObject<MobEffect> PUNGENT = EFFECTS.register("pungent", PungentEffect::new);
    public static final RegistryObject<MobEffect> TEMPTING = EFFECTS.register("tempting", TemptingEffect::new);
}
