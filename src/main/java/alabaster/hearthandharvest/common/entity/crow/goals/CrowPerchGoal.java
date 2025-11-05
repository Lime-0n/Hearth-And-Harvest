package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;

import java.util.EnumSet;

public class CrowPerchGoal extends Goal {
    private final CrowEntity crow;
    private BlockPos perchPos;

    public CrowPerchGoal(CrowEntity crow) {
        this.crow = crow;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (crow.isOrderedToSit() || crow.isPassenger() || crow.isTame()) return false;
        if (!crow.canPerch()) return false;
        if (crow.getRandom().nextInt(80) != 0) return false;

        perchPos = findNearbyPerch();
        return perchPos != null;
    }

    @Override
    public void start() {
        if (perchPos != null) {
            crow.getMoveControl().setWantedPosition(perchPos.getX() + 0.5D, perchPos.getY() + 1.0D, perchPos.getZ() + 0.5D, 1.0D);
            crow.perchCooldown(200 + crow.getRandom().nextInt(200));
        }
    }

    @Override
    public boolean canContinueToUse() {
        return perchPos != null && crow.distanceToSqr(perchPos.getX(), perchPos.getY(), perchPos.getZ()) > 2.0D;
    }

    private BlockPos findNearbyPerch() {
        BlockPos base = crow.blockPosition();
        for (int i = 0; i < 12; i++) {
            BlockPos pos = base.offset(
                    crow.getRandom().nextInt(8) - 4,
                    crow.getRandom().nextInt(4) - 2,
                    crow.getRandom().nextInt(8) - 4
            );
            Block block = crow.level().getBlockState(pos).getBlock();
            if (block.defaultBlockState().is(BlockTags.FENCES) || block.defaultBlockState().is(BlockTags.FENCE_GATES) || block.defaultBlockState().is(BlockTags.LEAVES)) {
                return pos;
            }
        }
        return null;
    }
}

