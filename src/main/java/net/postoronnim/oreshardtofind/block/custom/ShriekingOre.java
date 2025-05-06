package net.postoronnim.oreshardtofind.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.Vibrations;
import net.postoronnim.oreshardtofind.block.entity.ModBlockEntities;
import net.postoronnim.oreshardtofind.block.entity.custom.ShriekingOreEntity;
import org.jetbrains.annotations.Nullable;

public class ShriekingOre extends BlockWithEntity {
    public static final MapCodec<SculkShriekerBlock> CODEC = createCodec(SculkShriekerBlock::new);
    public static final BooleanProperty SHRIEKING = Properties.SHRIEKING;
    public static final BooleanProperty CAN_SUMMON = Properties.CAN_SUMMON;

    @Override
    public MapCodec<SculkShriekerBlock> getCodec() {
        return CODEC;
    }

    public ShriekingOre(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SHRIEKING, false).with(CAN_SUMMON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHRIEKING);
        builder.add(CAN_SUMMON);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // Play shriek sound when broken
        if (world instanceof ServerWorld serverWorld) {
            ServerPlayerEntity serverPlayerEntity = ShriekingOreEntity.findResponsiblePlayerFromEntity(player);
            if (serverPlayerEntity != null && !isMinedWithSilkTouch(player)) {
                serverWorld.getBlockEntity(pos, ModBlockEntities.SHRIEKING_ORE_ENTITY).ifPresent(blockEntity -> blockEntity.shriek(serverWorld, serverPlayerEntity));
            }
        }

        super.onBreak(world, pos, state, player);
        return state;
    }

    private boolean isMinedWithSilkTouch(PlayerEntity player) {
        if (player != null) {
            ItemStack mainHandStack = player.getMainHandStack();
            // Check if the held item has Silk Touch
            return EnchantmentHelper.getLevel(player.getWorld()
                    .getRegistryManager()
                    .get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.SILK_TOUCH)
                    .orElse(null), mainHandStack) > 0;
        }
        return false;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world instanceof ServerWorld serverWorld && (Boolean)state.get(SHRIEKING) && !state.isOf(newState.getBlock())) {
            serverWorld.getBlockEntity(pos, ModBlockEntities.SHRIEKING_ORE_ENTITY).ifPresent(blockEntity -> blockEntity.warn(serverWorld));
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Boolean)state.get(SHRIEKING)) {
            world.setBlockState(pos, state.with(SHRIEKING, false), Block.NOTIFY_ALL);
            world.getBlockEntity(pos, ModBlockEntities.SHRIEKING_ORE_ENTITY).ifPresent(blockEntity -> blockEntity.warn(world));
        }
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.SHRIEKING_ORE_ENTITY.instantiate(pos, state);
    }

    @Override
    protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (dropExperience) {
            this.dropExperienceWhenMined(world, pos, tool, UniformIntProvider.create(3, 7));
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient
                ? BlockWithEntity.validateTicker(
                type,
                ModBlockEntities.SHRIEKING_ORE_ENTITY,
                (worldx, pos, statex, blockEntity) -> Vibrations.Ticker.tick(worldx, blockEntity.getVibrationListenerData(), blockEntity.getVibrationCallback())
        )
                : null;
    }
}
