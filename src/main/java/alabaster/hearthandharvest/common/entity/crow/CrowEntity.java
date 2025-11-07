package alabaster.hearthandharvest.common.entity.crow;

import alabaster.hearthandharvest.common.entity.crow.goals.*;
import alabaster.hearthandharvest.common.registry.HHModEntities;
import alabaster.hearthandharvest.common.tag.HHModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrowEntity extends TamableAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    private int tameProgress = 0;
    private int perchCooldown = 0;

    public CrowEntity(EntityType<? extends CrowEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 3, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.5D)
                .add(Attributes.FLYING_SPEED, 0.4D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D);
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
        this.goalSelector.addGoal(1, new CrowAttackTargetGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new CrowFleePlayerGoal(this, 3.0D));
        this.goalSelector.addGoal(3, new CrowPickUpShinyGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new CrowEatCropsGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new CrowPerchGoal(this));
        this.goalSelector.addGoal(6, new CrowFlyRandomlyGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && this.isTame()) {
            LivingEntity owner = this.getOwner();

            if (owner instanceof Player player) {
                // Only if player not already has a crow on shoulder
                if (!player.hasPassenger(this) && this.distanceTo(player) < 1.5D && this.onGround()) {
                    tryToSitOnShoulder(player);
                }
            }
        }

        if (!this.isOrderedToSit()
                && !this.onGround()
                && this.getNavigation().isDone()) {

            // gently fall towards ground
            this.setDeltaMovement(
                    this.getDeltaMovement().x * 0.8,
                    -0.15,
                    this.getDeltaMovement().z * 0.8
            );
        }

        if (!this.level().isClientSide()) {
            boolean flying = !this.onGround();
            this.setNoGravity(flying);

            // Sitting for tame crows
            if (this.isOrderedToSit()) {
                if (!this.onGround()) {
                    Vec3 motion = this.getDeltaMovement();
                    this.setDeltaMovement(motion.x * 0.8D, -0.2D, motion.z * 0.8D);
                } else {
                    this.setDeltaMovement(Vec3.ZERO);
                }
                return;
            }

            // Existing behavior for wild/flying crows
            if (this.onGround()) {
                Player player = this.level().getNearestPlayer(this, 10.0D);
                if (player != null) {
                    this.getLookControl().setLookAt(player, 30.0F, 30.0F);
                }

                Player closePlayer = this.level().getNearestPlayer(this, 10.0D);
                if (closePlayer != null && this.distanceTo(closePlayer) < 3.0D && this.getRandom().nextInt(5) == 0) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5D, 0));
                }
            }

            if (perchCooldown > 0) perchCooldown--;
        } else {
            setupAnimationStates();
        }
    }

    public boolean tryToSitOnShoulder(Player player) {
        if (this.isPassenger() || this.isOrderedToSit()) return false;

        // Make sure player has free shoulder slot
        if (player.getShoulderEntityLeft().isEmpty() && player.getShoulderEntityRight().isEmpty()) {
            this.playSound(SoundEvents.PARROT_FLY, 0.2F, 1.0F);

           CompoundTag tag = this.saveWithoutId(new CompoundTag());
            tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(this.getType()).toString());

            if (player.setEntityOnShoulder(tag)) {
                this.discard();
                return true;
            }
        }

        return false;
    }

    public void increaseTameProgress(Player player) {
        tameProgress++;
        if (tameProgress >= 3) { // 5 shiny pickups to tame
            this.tame(player);
            this.showHappyParticles();
        }
    }


    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Feed to tame
        if (!this.isTame() && this.isFood(stack)) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            if (this.random.nextInt(3) == 0) {
                this.tame(player);
                this.showHappyParticles();
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }
            return InteractionResult.SUCCESS;
        }

        // Sit or shoulder perch if tamed
        if (this.isTame() && this.getOwner() == player) {
            if (player.isShiftKeyDown()) {
                this.setOrderedToSit(!this.isOrderedToSit());
                return InteractionResult.SUCCESS;
            } else {
                if (this.tryToSitOnShoulder(player)) {
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.mobInteract(player, hand);
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

    private void setupAnimationStates() {
        if (!this.level().isClientSide()) return;

        boolean flying = isFlying();

        if (flying && !flyingAnimationState.isStarted()) {
            idleAnimationState.stop();
            flyingAnimationState.start(this.tickCount);
        } else if (!flying && !idleAnimationState.isStarted()) {
            flyingAnimationState.stop();
            idleAnimationState.start(this.tickCount);
        }
    }

    public boolean isFlying() {
        return !this.onGround() && !this.getNavigation().isInProgress();
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

    public void playPickupSound() {
        this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.4F, 1.2F);
    }

    public void showHappyParticles() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(
                    ParticleTypes.HEART, this.getX(), this.getY() + 0.8, this.getZ(), 3, 0.2, 0.2, 0.2, 0.0
            );
        }
    }

    public void showUnhappyParticles() {
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(
                    ParticleTypes.SMOKE, this.getX(), this.getY() + 0.8, this.getZ(), 3, 0.2, 0.2, 0.2, 0.0
            );
        }
    }
}
