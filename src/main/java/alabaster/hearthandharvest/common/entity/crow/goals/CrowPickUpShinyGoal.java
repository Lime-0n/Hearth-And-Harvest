package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.Comparator;

public class CrowPickUpShinyGoal extends Goal {
    private final CrowEntity crow;
    private final double flySpeed;
    private ItemEntity targetItem;
    private int tickTimer;

    public CrowPickUpShinyGoal(CrowEntity crow, double flySpeed) {
        this.crow = crow;
        this.flySpeed = flySpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (crow.isPassenger() || crow.isOrderedToSit()) return false;
        if (crow.isTame() && crow.getRandom().nextInt(6) != 0) return false;

        List<ItemEntity> items = crow.level().getEntitiesOfClass(
                ItemEntity.class,
                crow.getBoundingBox().inflate(10.0D),
                e -> e.isAlive() && e.getItem().is(Items.IRON_NUGGET)
        );

        if (items.isEmpty()) return false;
        targetItem = items.stream()
                .min(Comparator.comparingDouble(crow::distanceToSqr))
                .orElse(null);

        return targetItem != null;
    }

    @Override
    public boolean canContinueToUse() {
        return targetItem != null && targetItem.isAlive() && tickTimer < 200;
    }

    @Override
    public void start() {
        tickTimer = 0;
    }

    @Override
    public void stop() {
        targetItem = null;
    }

    @Override
    public void tick() {
        tickTimer++;
        if (targetItem == null) return;

        Vec3 targetPos = targetItem.position().add(0, 0.4D, 0);
        double distance = crow.distanceToSqr(targetPos.x, targetPos.y, targetPos.z);

        crow.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, flySpeed);

        if (distance < 2.25D && !crow.level().isClientSide()) {
            handlePickup((ServerLevel) crow.level());
        }
    }

    private void handlePickup(ServerLevel level) {
        if (targetItem == null) return;

        targetItem.discard();
        level.broadcastEntityEvent(crow, (byte) 7);
        crow.playPickupSound();

        if (!crow.isTame()) {
            Player nearby = level.getNearestPlayer(crow, 5.0D);
            if (nearby != null && level.random.nextFloat() < 0.2F) {
                crow.tame(nearby);
                crow.showHappyParticles();
            }
        }
        else {
            LivingEntity owner = crow.getOwner();
            if (owner instanceof Player player) {
                ItemEntity gift = new ItemEntity(
                        level,
                        player.getX(),
                        player.getY() + 1.0D,
                        player.getZ(),
                        new ItemStack(Items.GOLD_NUGGET)
                );
                level.addFreshEntity(gift);
            }
        }

        targetItem = null;
    }
}
