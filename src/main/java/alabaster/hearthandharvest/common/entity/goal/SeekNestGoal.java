package alabaster.hearthandharvest.common.entity.goal;

import alabaster.hearthandharvest.common.registry.HHModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SeekNestGoal extends MoveToBlockGoal {
    private final Chicken chicken;
    private int eggCounter;

    public SeekNestGoal(Chicken chicken, double speed) {
        super(chicken, speed, 16);
        this.chicken = chicken;
    }

    @Override
    public boolean canUse() {
        return canEggBeLaid() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return canEggBeLaid() && super.canContinueToUse();
    }

    @Override
    protected int nextStartTick(PathfinderMob mob) {
        return 40;
    }

    @Override
    public void start() {
        super.start();
        this.eggCounter = this.adjustedTickDelay(40);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isReachedTarget() && canEggBeLaid()) {
            eggCounter--;

            if (eggCounter <= 0) {
                BlockPos nestPos = this.blockPos.above();

                layEggIntoNest(nestPos);

                chicken.playSound(SoundEvents.CHICKEN_EGG, 1.0F,
                        (chicken.getRandom().nextFloat() - chicken.getRandom().nextFloat()) * 0.2F + 1F
                );
            }
        }
    }

    private void layEggIntoNest(BlockPos pos) {
        if (!(chicken.level() instanceof ServerLevel server))
            return;

        // Create a vanilla egg
        ItemStack egg = new ItemStack(Items.EGG);

        // Centered above the nest
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.25;
        double z = pos.getZ() + 0.5;

        ItemEntity eggEntity = new ItemEntity(server, x, y, z, egg);

        eggEntity.setDeltaMovement(0, 0, 0);

        server.addFreshEntity(eggEntity);
    }

    private boolean canEggBeLaid() {
        return !chicken.isBaby() && chicken.eggTime < 100; // can tune threshold
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.is(HHModBlocks.NEST.get());
    }
}
