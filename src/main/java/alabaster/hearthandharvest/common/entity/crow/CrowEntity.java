package alabaster.hearthandharvest.common.entity.crow;

import alabaster.hearthandharvest.common.entity.crow.goals.*;
import alabaster.hearthandharvest.common.registry.HHModEntities;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrowEntity extends TamableAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int perchCooldown = 0;
    private Ingredient temptationItems;

    public CrowEntity(EntityType<? extends CrowEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FLYING_SPEED, .5D)
                .add(Attributes.MOVEMENT_SPEED, 1D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanFloat(false);
        nav.setCanOpenDoors(false);
        nav.setCanPassDoors(true);
        return nav;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new CrowPickUpShinyGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new CrowEatCropsGoal(this, 1.2D));
        this.goalSelector.addGoal(3, new CrowGiveGiftGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new CrowPerchGoal(this));
        this.goalSelector.addGoal(5, new CrowFlyRandomlyGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            this.setNoGravity(true);
            if (this.onGround() && this.getDeltaMovement().y < 0.0D) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D, 1.0D));
            }

            if (perchCooldown > 0) perchCooldown--;
        } else {
            setupAnimationStates();
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isFlying()) {
            this.moveRelative(0.05F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.91D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    private boolean wasFlying = false;
    private int flyingStableTicks = 0;

    private void setupAnimationStates() {
        if (this.level().isClientSide()) {
            boolean flying = this.isFlying();

            // Require a few consistent ticks before toggling to prevent flicker
            if (flying != wasFlying) {
                flyingStableTicks++;
                if (flyingStableTicks > 5) { // must stay same state for 5 ticks
                    wasFlying = flying;
                    flyingStableTicks = 0;

                    if (flying) {
                        if (this.idleAnimationState.isStarted()) this.idleAnimationState.stop();
                        if (!this.flyingAnimationState.isStarted()) this.flyingAnimationState.start(this.tickCount);
                    } else {
                        if (this.flyingAnimationState.isStarted()) this.flyingAnimationState.stop();
                        if (!this.idleAnimationState.isStarted()) this.idleAnimationState.start(this.tickCount);
                    }
                }
            } else {
                flyingStableTicks = 0;
            }
        }
    }

    private boolean isFlying() {
        return !onGround() && getDeltaMovement().lengthSqr() > 0.02;
    }

    public void perchCooldown(int ticks) {
        this.perchCooldown = ticks;
    }

    public boolean canPerch() {
        return perchCooldown <= 0 && this.onGround();
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(HHModTags.CROW_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return HHModEntities.CROW.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARROT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
    }
}
