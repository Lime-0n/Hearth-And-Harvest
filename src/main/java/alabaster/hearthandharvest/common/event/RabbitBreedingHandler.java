package alabaster.hearthandharvest.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber
public class RabbitBreedingHandler {

    @SubscribeEvent
    public static void onCarrotRemoved(BlockEvent.NeighborNotifyEvent event) {
        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        // Check if the block was a fully grown carrot before being eaten
        if (state.isAir() && level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            AABB area = new AABB(pos).inflate(3); // 3-block radius around the eaten carrot
            level.getEntitiesOfClass(Rabbit.class, area)
                    .stream()
                    .filter(rabbit -> !rabbit.isBaby()) // Check if it's an adult rabbit
                    .limit(2)
                    .forEach(rabbit -> {
                        rabbit.setInLoveTime(600); // Set rabbit in love mode
                        // Add particle effects when the rabbit is in love mode
                        spawnLoveParticles(rabbit, level);
                        breedRabbit(rabbit, level); // Trigger actual breeding behavior
                    });
        }
    }

    // Method to spawn particle effects
    private static void spawnLoveParticles(Rabbit rabbit, ServerLevel level) {
        // Spawn heart particles around the rabbit
        level.sendParticles(ParticleTypes.HEART,
                rabbit.getX(), rabbit.getY() + 0.5, rabbit.getZ(), 5, 0.5, 0.5, 0.5, 0.1);
    }

    // Trigger the breeding behavior of rabbits
    private static void breedRabbit(Rabbit rabbit, ServerLevel level) {
        // Find another rabbit in love mode and breed them
        level.getEntitiesOfClass(Rabbit.class, rabbit.getBoundingBox().inflate(8)) // Looking for other rabbits within an 8-block radius
                .stream()
                .filter(otherRabbit -> otherRabbit != rabbit && !otherRabbit.isBaby() && otherRabbit.getInLoveTime() > 0) // Must not be the same rabbit, not a baby, and in love
                .findFirst()
                .ifPresent(otherRabbit -> {
                    // Set the rabbits to breed (create baby rabbit)
                    rabbit.setInLoveTime(0); // Set this rabbit out of love mode
                    otherRabbit.setInLoveTime(0); // Set the other rabbit out of love mode
                    // Create baby rabbit
                    Rabbit babyRabbit = rabbit.getBreedOffspring(level, otherRabbit); // Create offspring
                    level.addFreshEntity(babyRabbit); // Add baby rabbit to the world
                    // Additional particle effect for successful breeding
                    level.sendParticles(ParticleTypes.HEART, rabbit.getX(), rabbit.getY() + 0.5, rabbit.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
                });
    }
}
