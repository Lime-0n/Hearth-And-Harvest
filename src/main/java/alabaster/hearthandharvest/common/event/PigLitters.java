package alabaster.hearthandharvest.common.event;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;

import java.util.Random;

public class PigLitters {

    @SubscribeEvent
    public void onPigBreed(BabyEntitySpawnEvent event) {
        if (event.getParentA() instanceof Pig && event.getParentB() instanceof Pig) {
            Level world = event.getParentA().level();

            event.setCanceled(true);

            Random random = new Random();
            int babyCount = 2 + random.nextInt(3); // 2 to 4 babies

            for (int i = 0; i < babyCount; i++) {
                Pig babyPig = EntityType.PIG.create(world);
                if (babyPig != null) {
                    babyPig.setPos(event.getParentA().getX(), event.getParentA().getY(), event.getParentA().getZ());
                    babyPig.setBaby(true);
                    world.addFreshEntity(babyPig);
                }
            }
        }
    }
}
