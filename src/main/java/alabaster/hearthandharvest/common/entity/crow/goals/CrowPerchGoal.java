package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class CrowPerchGoal extends Goal {
    private final CrowEntity crow;
    private BlockPos perchPos;
    private int hoverTime;

    public CrowPerchGoal(CrowEntity crow) {
        this.crow = crow;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (crow.isOrderedToSit() || crow.isPassenger() || crow.isTame()) return false;
        if (!crow.canPerch()) return false;

        if (crow.onGround()) return false;

        double chance = getNearbyPlayer() != null ? 0.2 : 0.05;
        if (crow.getRandom().nextDouble() > chance) return false;

        perchPos = findNearbyPerch();
        return perchPos != null;
    }

    @Override
    public void start() {
        if (perchPos != null) {
            crow.getMoveControl().setWantedPosition(
                    perchPos.getX() + 0.5D,
                    perchPos.getY() + 1.0D,
                    perchPos.getZ() + 0.5D,
                    0.8D
            );
            hoverTime = 0;
            crow.perchCooldown(100 + crow.getRandom().nextInt(100));
        }
    }

    @Override
    public boolean canContinueToUse() {
        return perchPos != null
                && crow.distanceToSqr(perchPos.getX(), perchPos.getY(), perchPos.getZ()) > 2.0D
                && hoverTime++ < 200;
    }

    @Override
    public void stop() {
        perchPos = null;
    }

    private BlockPos findNearbyPerch() {
        BlockPos base = crow.blockPosition();
        for (int i = 0; i < 16; i++) {
            BlockPos pos = base.offset(
                    crow.getRandom().nextInt(12) - 6,
                    crow.getRandom().nextInt(6) - 2,
                    crow.getRandom().nextInt(12) - 6
            );

            var state = crow.level().getBlockState(pos);
            if (state.is(BlockTags.FENCES) || state.is(BlockTags.FENCE_GATES) || state.is(BlockTags.LEAVES) || state.isSolid()) {
                if (crow.level().isEmptyBlock(pos.above())) {
                    return pos;
                }
            }
        }
        return null;
    }

    private Player getNearbyPlayer() {
        return crow.level().getNearestPlayer(crow, 12.0D);
    }
}