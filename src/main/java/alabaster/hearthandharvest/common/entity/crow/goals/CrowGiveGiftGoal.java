package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CrowGiveGiftGoal extends Goal {
    private final CrowEntity crow;
    private final double speed;
    private int cooldown = 0;

    public CrowGiveGiftGoal(CrowEntity crow, double speed) {
        this.crow = crow;
        this.speed = speed;
    }

    @Override
    public boolean canUse() {
        return crow.isTame() && crow.getOwner() instanceof Player && crow.tickCount % 20 == 0 && cooldown-- <= 0;
    }

    @Override
    public void start() {
        Player owner = (Player) crow.getOwner();
        if (owner == null || crow.distanceTo(owner) > 16.0F) return;
        crow.getNavigation().moveTo(owner, speed);
        if (crow.distanceTo(owner) < 2.0F && !crow.level().isClientSide()) {
            ItemStack gift = getGift(crow.level(), owner);
            crow.level().addFreshEntity(new ItemEntity(crow.level(), crow.getX(), crow.getY(), crow.getZ(), gift));
            cooldown = 20 * 60 * 5;
        }
    }

    private ItemStack getGift(Level level, Player player) {
        return new ItemStack(Items.STICK);
    }
}

