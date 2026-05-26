package alabaster.hearthandharvest.common.event;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;

public class RabbitLitters {

    @SubscribeEvent
    public void onRabbitBreed(BabyEntitySpawnEvent event) {
        if (event.getParentA() instanceof Rabbit && event.getParentB() instanceof Rabbit) {
            Level world = event.getParentA().level();
            event.setCanceled(true);

            RandomSource random = event.getParentA().getRandom();
            int babyCount = 2 + random.nextInt(3);

            for (int i = 0; i < babyCount; i++) {
                Rabbit babyRabbit = EntityType.RABBIT.create(world);
                if (babyRabbit != null) {
                    babyRabbit.setPos(event.getParentA().getX(), event.getParentA().getY(), event.getParentA().getZ());
                    babyRabbit.setBaby(true);
                    world.addFreshEntity(babyRabbit);
                }
            }
        }
    }
}