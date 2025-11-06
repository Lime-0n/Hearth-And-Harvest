package alabaster.hearthandharvest.common.entity.crow.goals;

import alabaster.hearthandharvest.common.block.CornStalkBlock;
import alabaster.hearthandharvest.common.block.CornStalkBlock.CornSection;
import alabaster.hearthandharvest.common.entity.crow.CrowEntity;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class CrowEatCropsGoal extends Goal {
    private final CrowEntity crow;
    private final double speed;
    private BlockPos targetCrop;
    private int eatingTimer;
    private boolean hovering;

    public CrowEatCropsGoal(CrowEntity crow, double speed) {
        this.crow = crow;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (crow.isTame() || crow.isOrderedToSit()) return false;

        List<BlockPos> nearby = BlockPos.betweenClosedStream(
                crow.blockPosition().offset(-8, -3, -8),
                crow.blockPosition().offset(8, 3, 8)
        ).map(BlockPos::immutable).toList();

        for (BlockPos pos : nearby) {
            BlockState state = crow.level().getBlockState(pos);
            if (isMatureCrop(state, pos)) {
                targetCrop = pos;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetCrop != null
                && !crow.isTame()
                && crow.level().isLoaded(targetCrop)
                && crow.distanceToSqr(Vec3.atCenterOf(targetCrop)) < 25.0D;
    }

    @Override
    public void start() {
        if (targetCrop != null) {
            hovering = false;
            eatingTimer = 0;
            moveTowardCrop();
        }
    }

    @Override
    public void stop() {
        targetCrop = null;
        hovering = false;
        eatingTimer = 0;
    }

    @Override
    public void tick() {
        if (targetCrop == null) return;

        double distSq = crow.distanceToSqr(Vec3.atCenterOf(targetCrop));

        if (distSq < 2.0D) {
            if (!hovering) {
                hovering = true;
                crow.getNavigation().stop();
            }

            eatingTimer++;
            crow.getLookControl().setLookAt(
                    targetCrop.getX() + 0.5D,
                    targetCrop.getY() + 0.5D,
                    targetCrop.getZ() + 0.5D
            );

            if (eatingTimer == 20 || eatingTimer == 40) {
                crow.playSound(SoundEvents.PARROT_EAT, 0.8F, 0.9F + crow.getRandom().nextFloat() * 0.2F);
            }

            if (eatingTimer > 45 && crow.level() instanceof ServerLevel server) {
                BlockState state = server.getBlockState(targetCrop);
                if (isMatureCrop(state, targetCrop)) {
                    destroyCrop(server, targetCrop, state);
                }
                stop();
            }

        } else {
            if (!hovering && (crow.getNavigation().isDone() || distSq > 9.0D)) {
                moveTowardCrop();
            }
        }
    }

    private void moveTowardCrop() {
        if (targetCrop != null) {
            crow.getNavigation().moveTo(
                    targetCrop.getX() + 0.5D,
                    targetCrop.getY() + 1.2D,
                    targetCrop.getZ() + 0.5D,
                    speed
            );
        }
    }

    private boolean isMatureCrop(BlockState state, BlockPos pos) {
        if (!state.is(HHModTags.CROW_EDIBLE_CROPS)) {
            return false;
        }
        return true;
    }

    private void destroyCrop(ServerLevel server, BlockPos pos, BlockState state) {
        Block block = state.getBlock();

        if (block.defaultBlockState().is(BlockTags.CROPS)) {
            server.destroyBlock(pos, true);
            server.levelEvent(2001, pos, Block.getId(state));
            crow.playSound(SoundEvents.PARROT_EAT, 0.9F, 1.0F);
        }
    }
}
