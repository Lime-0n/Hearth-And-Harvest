package alabaster.hearthandharvest.common.event;

import alabaster.hearthandharvest.common.entity.goal.StayNearSaltGoal;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = "hearthandharvest", bus = EventBusSubscriber.Bus.GAME)
public class SaltBlockEvents {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof Animal animal) {
            animal.goalSelector.addGoal(4, new StayNearSaltGoal(animal));
        }
    }
}