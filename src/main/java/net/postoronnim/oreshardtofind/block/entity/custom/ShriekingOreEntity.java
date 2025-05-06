package net.postoronnim.oreshardtofind.block.entity.custom;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.GameEventListener;
import net.postoronnim.oreshardtofind.block.custom.ShriekingOre;
import net.postoronnim.oreshardtofind.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.OptionalInt;

public class ShriekingOreEntity extends BlockEntity implements GameEventListener.Holder<Vibrations.VibrationListener>, Vibrations {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int field_38750 = 10;
    private static final int WARDEN_SPAWN_TRIES = 20;
    private static final int WARDEN_SPAWN_HORIZONTAL_RANGE = 5;
    private static final int WARDEN_SPAWN_VERTICAL_RANGE = 6;
    private static final int DARKNESS_RANGE = 40;
    private static final int SHRIEK_DELAY = 90;
    private static final Int2ObjectMap<SoundEvent> WARNING_SOUNDS = Util.make(new Int2ObjectOpenHashMap<>(), warningSounds -> {
        warningSounds.put(1, SoundEvents.ENTITY_WARDEN_NEARBY_CLOSE);
        warningSounds.put(2, SoundEvents.ENTITY_WARDEN_NEARBY_CLOSER);
        warningSounds.put(3, SoundEvents.ENTITY_WARDEN_NEARBY_CLOSEST);
        warningSounds.put(4, SoundEvents.ENTITY_WARDEN_LISTENING_ANGRY);
    });
    private int warningLevel;
    private final Vibrations.Callback vibrationCallback = new ShriekingOreEntity.VibrationCallback();
    private Vibrations.ListenerData vibrationListenerData = new Vibrations.ListenerData();
    private final Vibrations.VibrationListener vibrationListener = new Vibrations.VibrationListener(this);

    public ShriekingOreEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHRIEKING_ORE_ENTITY, pos, state);
    }

    @Override
    public Vibrations.ListenerData getVibrationListenerData() {
        return this.vibrationListenerData;
    }

    @Override
    public Vibrations.Callback getVibrationCallback() {
        return this.vibrationCallback;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("warning_level", NbtElement.NUMBER_TYPE)) {
            this.warningLevel = nbt.getInt("warning_level");
        }

        RegistryOps<NbtElement> registryOps = registryLookup.getOps(NbtOps.INSTANCE);
        if (nbt.contains("listener", NbtElement.COMPOUND_TYPE)) {
            Vibrations.ListenerData.CODEC
                    .parse(registryOps, nbt.getCompound("listener"))
                    .resultOrPartial(string -> LOGGER.error("Failed to parse vibration listener for Sculk Shrieker: '{}'", string))
                    .ifPresent(vibrationListener -> this.vibrationListenerData = vibrationListener);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("warning_level", this.warningLevel);
        RegistryOps<NbtElement> registryOps = registryLookup.getOps(NbtOps.INSTANCE);
        Vibrations.ListenerData.CODEC
                .encodeStart(registryOps, this.vibrationListenerData)
                .resultOrPartial(string -> LOGGER.error("Failed to encode vibration listener for Sculk Shrieker: '{}'", string))
                .ifPresent(nbtElement -> nbt.put("listener", nbtElement));
    }

    @Nullable
    public static ServerPlayerEntity findResponsiblePlayerFromEntity(@Nullable Entity entity) {
        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
            return serverPlayerEntity;
        } else if (entity != null && entity.getControllingPassenger() instanceof ServerPlayerEntity serverPlayerEntity) {
            return serverPlayerEntity;
        } else if (entity instanceof ProjectileEntity projectileEntity && projectileEntity.getOwner() instanceof ServerPlayerEntity serverPlayerEntity2) {
            return serverPlayerEntity2;
        } else {
            return entity instanceof ItemEntity itemEntity && itemEntity.getOwner() instanceof ServerPlayerEntity serverPlayerEntity2 ? serverPlayerEntity2 : null;
        }
    }

    public void shriek(ServerWorld world, @Nullable ServerPlayerEntity player) {
        if (player != null) {
            BlockState blockState = this.getCachedState();
            if (!(Boolean)blockState.get(ShriekingOre.SHRIEKING)) {
                this.warningLevel = 0;
                if (!this.canWarn(world) || this.trySyncWarningLevel(world, player)) {
                    this.shriek(world, (Entity)player);
                }
            }
        }
    }

    private boolean trySyncWarningLevel(ServerWorld world, ServerPlayerEntity player) {
        OptionalInt optionalInt = SculkShriekerWarningManager.warnNearbyPlayers(world, this.getPos(), player);
        optionalInt.ifPresent(warningLevel -> this.warningLevel = warningLevel);
        return optionalInt.isPresent();
    }

    private void shriek(ServerWorld world, @Nullable Entity entity) {
        BlockPos blockPos = this.getPos();
        BlockState blockState = this.getCachedState();
        world.setBlockState(blockPos, blockState.with(ShriekingOre.SHRIEKING, true), Block.NOTIFY_LISTENERS);
        world.scheduleBlockTick(blockPos, blockState.getBlock(), 90);
        world.syncWorldEvent(WorldEvents.SCULK_SHRIEKS, blockPos, 0);
        world.emitGameEvent(GameEvent.SHRIEK, blockPos, GameEvent.Emitter.of(entity));
    }

    private boolean canWarn(ServerWorld world) {
        return (Boolean)this.getCachedState().get(ShriekingOre.CAN_SUMMON)
                && world.getDifficulty() != Difficulty.PEACEFUL
                && world.getGameRules().getBoolean(GameRules.DO_WARDEN_SPAWNING);
    }

    public void warn(ServerWorld world) {
        if (this.canWarn(world) && this.warningLevel > 0) {
            if (!this.trySpawnWarden(world)) {
                this.playWarningSound(world);
            }

            WardenEntity.addDarknessToClosePlayers(world, Vec3d.ofCenter(this.getPos()), null, 40);
        }
    }

    private void playWarningSound(World world) {
        SoundEvent soundEvent = WARNING_SOUNDS.get(this.warningLevel);
        if (soundEvent != null) {
            BlockPos blockPos = this.getPos();
            int i = blockPos.getX() + MathHelper.nextBetween(world.random, -10, 10);
            int j = blockPos.getY() + MathHelper.nextBetween(world.random, -10, 10);
            int k = blockPos.getZ() + MathHelper.nextBetween(world.random, -10, 10);
            world.playSound(null, (double)i, (double)j, (double)k, soundEvent, SoundCategory.HOSTILE, 5.0F, 1.0F);
        }
    }

    private boolean trySpawnWarden(ServerWorld world) {
        return this.warningLevel < 4
                ? false
                : LargeEntitySpawnHelper.trySpawnAt(EntityType.WARDEN, SpawnReason.TRIGGERED, world, this.getPos(), 20, 5, 6, LargeEntitySpawnHelper.Requirements.WARDEN)
                .isPresent();
    }

    public Vibrations.VibrationListener getEventListener() {
        return this.vibrationListener;
    }

    class VibrationCallback implements Vibrations.Callback {
        private static final int RANGE = 8;
        private final PositionSource positionSource = new BlockPositionSource(ShriekingOreEntity.this.pos);

        public VibrationCallback() {
        }

        @Override
        public int getRange() {
            return 8;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public TagKey<GameEvent> getTag() {
            return GameEventTags.SHRIEKER_CAN_LISTEN;
        }

        @Override
        public boolean accepts(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter) {
            return !(Boolean)ShriekingOreEntity.this.getCachedState().get(ShriekingOre.SHRIEKING)
                    && ShriekingOreEntity.findResponsiblePlayerFromEntity(emitter.sourceEntity()) != null;
        }

        @Override
        public void accept(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
            world.playSound(
                    null,
                    pos,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE,
                    SoundCategory.BLOCKS,
                    7.0f,
                    1.0f
            );
        }

        @Override
        public void onListen() {
            ShriekingOreEntity.this.markDirty();
        }

        @Override
        public boolean requiresTickingChunksAround() {
            return true;
        }
    }
}
