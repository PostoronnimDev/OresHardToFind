package net.postoronnim.oreshardtofind.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.postoronnim.oreshardtofind.entity.custom.IronOreGolemEntity;

import java.util.Optional;

public class IronOreGolemBrain {
    private static final float WALK_SPEED = 0.5f;
    private static final float ATTACK_WALK_SPEED = 0.8f;
    private static final ImmutableList<SensorType<? extends Sensor<? super IronOreGolemEntity>>> SENSORS = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS
    );
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            //NearestLivingEntitiesSensor outputs
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,

            //HurtBySensor outputs
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,

            //NearestPlayersSensor outputs
            MemoryModuleType.NEAREST_PLAYERS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,

            MemoryModuleType.PATH,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN
            );

    public static void initialize(IronOreGolemEntity ironOreGolem, Random random) {
    }

    public static Brain.Profile<IronOreGolemEntity> createProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    public static Brain<?> create(IronOreGolemEntity ironOreGolem, Dynamic<?> dynamic) {
        Brain.Profile<IronOreGolemEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<IronOreGolemEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(brain, ironOreGolem);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<IronOreGolemEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new LookAroundTask(45, 90),
                        new MoveToTargetTask()
                )
        );
    }

    private static void addIdleActivities(Brain<IronOreGolemEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(2, new RandomTask<>(
                                ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                                ImmutableList.of(
                                        Pair.of(StrollTask.create(WALK_SPEED), 1),
                                        Pair.of(new WaitTask(20, 100), 1)
                                ))
                        ),
                        Pair.of(3, UpdateAttackTargetTask.create(IronOreGolemBrain::getPreferredTarget)),
                        Pair.of(4, new GoSleepTask())
                )
        );
    }

    private static void addFightActivities(Brain<IronOreGolemEntity> brain, IronOreGolemEntity ironOreGolem) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        new ResetAwakeTimeoutTask(),
                        RangedApproachTask.create(ATTACK_WALK_SPEED),
                        MeleeAttackTask.create(20),
                        ForgetAttackTargetTask.create()
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static Optional<? extends LivingEntity> getPreferredTarget(IronOreGolemEntity ironOreGolem) {
        Optional<PlayerEntity> optional = ironOreGolem.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);

        return optional;
    }

    public static void updateActivities(IronOreGolemEntity ironOreGolem) {
        Brain<?> brain = ironOreGolem.getBrain();
        brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static class GoSleepTask extends MultiTickTask<IronOreGolemEntity> {

        public GoSleepTask() {
            super(ImmutableMap.of());
        }

        protected boolean shouldRun(ServerWorld serverWorld, IronOreGolemEntity ironOreGolemEntity) {
            return ironOreGolemEntity.idleTime() >= 400 && !ironOreGolemEntity.willDespawn();
        }

        protected void run(ServerWorld serverWorld, IronOreGolemEntity ironOreGolemEntity, long l) {
            Vec3d pos = ironOreGolemEntity.getPos();
            BlockPos nearestBlockPos = new BlockPos(MathHelper.ceil(pos.x - 1), MathHelper.ceil(pos.y), MathHelper.ceil(pos.z - 1));
            BlockPos lookPos = new BlockPos(nearestBlockPos.getX(), nearestBlockPos.getY(), nearestBlockPos.getZ() + 1);

            ironOreGolemEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(nearestBlockPos, 0.5f, 0));
//            ironOreGolemEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(lookPos));

            ironOreGolemEntity.setWillDespawn(true);
        }
    }

    public static class ResetAwakeTimeoutTask extends SingleTickTask<IronOreGolemEntity> {
        @Override
        public boolean trigger(ServerWorld world, IronOreGolemEntity entity, long time) {
            entity.setIdleTime(0);
            return true;
        }
    }
}
