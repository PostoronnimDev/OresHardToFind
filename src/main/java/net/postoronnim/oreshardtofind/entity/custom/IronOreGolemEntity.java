package net.postoronnim.oreshardtofind.entity.custom;

import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.entity.ai.IronOreGolemBrain;
import org.jetbrains.annotations.Nullable;

public class IronOreGolemEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState seatAnimationState = new AnimationState();
    private int timeToDespawn = 20;
    public static final TrackedData<Integer> IDLE_TIME = DataTracker.registerData(IronOreGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> WILL_DESPAWN = DataTracker.registerData(IronOreGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static class EntityBehaviorStatus {

    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(WILL_DESPAWN, false);
        builder.add(IDLE_TIME, 0);
    }

    public void setWillDespawn(boolean willDespawn) {
        this.dataTracker.set(WILL_DESPAWN, willDespawn); // Syncs to clients automatically
    }

    public void setIdleTime(int idleTime) {
        this.dataTracker.set(IDLE_TIME, idleTime);
    }

    public boolean willDespawn() {
        return this.dataTracker.get(WILL_DESPAWN);
    }

    public int idleTime() {
        return this.dataTracker.get(IDLE_TIME);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (WILL_DESPAWN.equals(data) && this.getWorld().isClient()) {
            boolean willDespawn = this.dataTracker.get(WILL_DESPAWN);
            if (willDespawn) {
                this.seatAnimationState.start(this.age); // Start sitting animation
            } else {
                this.seatAnimationState.stop(); // Stop sitting animation
            }
        }
        super.onTrackedDataSet(data);
    }

    public IronOreGolemEntity(EntityType<IronOreGolemEntity> type, World world) {
        super(type, world);
        if(this.getWorld().isClient()) {
            this.standAnimationState.start(this.age);
        }
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        IronOreGolemBrain.initialize(this, world.getRandom());
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected Brain.Profile<IronOreGolemEntity> createBrainProfile() {
        return IronOreGolemBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return IronOreGolemBrain.create(this, dynamic);
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("ironOreGolemBrain");
        Brain<?> brain = this.getBrain();
        ((Brain<IronOreGolemEntity>)brain).tick((ServerWorld)this.getWorld(), this);
        IronOreGolemBrain.updateActivities(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.willDespawn()) {
            if(timeToDespawn <= 0) {
                goSleep();
            }
            timeToDespawn--;
        }

        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.setIdleTime(this.idleTime() + 40);
            if (this.getWorld().isClient()){
                this.idleAnimationState.start(this.age);
            }
        } else {
            this.idleAnimationTimeout--;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.BLOCK_DEEPSLATE_HIT);
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        super.applyDamage(source, amount);
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            this.attackAnimationState.start(this.age);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
        this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 10.0F, this.getSoundPitch());
        SonicBoomTask.cooldown(this, 40);
        return super.tryAttack(target);
    }

    public void goSleep() {
        Vec3d pos = this.getPos();
        BlockPos blockPos = new BlockPos(MathHelper.ceil(pos.x - 1), MathHelper.ceil(pos.y), MathHelper.ceil(pos.z - 1));
        this.getWorld().setBlockState(blockPos, ModBlocks.SUS_DEEPSLATE_IRON_ORE.getDefaultState());
        this.getWorld().playSound(this, blockPos, SoundEvents.BLOCK_DEEPSLATE_BREAK, SoundCategory.BLOCKS, 10f, 1f);
        this.discard();
    }
}
