package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.Config;
import alabaster.hearthandharvest.HearthAndHarvest;
import alabaster.hearthandharvest.common.registry.HHModDataComponents;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = HearthAndHarvest.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SaltedEffectEvents {

    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        var stack = event.getItem();
        if (!stack.has(HHModDataComponents.SALTED.get())) return;

        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food == null) return;

        int bonusHunger = Math.max(1, Math.round(food.nutrition() * Config.SALTED_HUNGER_BONUS.get().floatValue()));
        player.getFoodData().eat(bonusHunger, 0f);

        float saturationGranted = food.nutrition() * food.saturation() * 2.0f;
        float newSaturation = Math.max(0f, player.getFoodData().getSaturationLevel() - saturationGranted * Config.SALTED_SATURATION_PENALTY.get().floatValue());
        player.getFoodData().setSaturation(newSaturation);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        var player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!player.getMainHandItem().isEmpty()) return;
        if (!event.getLevel().getBlockState(event.getPos()).is(HHModTags.SALT_BLOCKS)) return;

        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, true));
        event.setCanceled(true);
    }
}