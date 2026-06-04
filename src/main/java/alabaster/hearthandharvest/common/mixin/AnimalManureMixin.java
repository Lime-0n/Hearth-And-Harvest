package alabaster.hearthandharvest.common.mixin;

import alabaster.hearthandharvest.common.registry.HHModItems;
import alabaster.hearthandharvest.common.registry.HHModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class AnimalManureMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void tickManureDrop(CallbackInfo ci) {
        if (!((Object) this instanceof Animal animal)) return;
        if (animal.level().isClientSide) return;
        if (animal.isBaby()) return;
        if (animal.tickCount % 20 != 0) return;
        if (animal.getRandom().nextInt(300) != 0) return;

        animal.spawnAtLocation(HHModItems.MANURE.get());
        animal.level().playSound(null, animal.getX(), animal.getY(), animal.getZ(),
                HHModSounds.FART.get(), SoundSource.NEUTRAL, 0.5f, 0.5f + animal.getRandom().nextFloat() * 1.5f);
    }
}
