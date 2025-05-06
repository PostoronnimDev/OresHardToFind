package net.postoronnim.oreshardtofind.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Colors;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.postoronnim.oreshardtofind.util.ModTags;
import org.joml.Vector3f;

public class IronMagnetometerItem extends Item {
    public final int RANGE = 64;
    public static final int COOLDOWN_TICKS = 60;

    public IronMagnetometerItem(Settings settings) {
        super(settings.maxDamage(64)); // 64 scans
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {

            if(user.getItemCooldownManager().isCoolingDown(this)) {
                return TypedActionResult.fail(stack);
            } else {
                user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
            }

            BlockPos ironPos = locateIronVein(world, user.getBlockPos(), RANGE);
            if (ironPos != null) {
                stack.damage(1, user, EquipmentSlot.MAINHAND);

                emitParticles(((ServerWorld) world), user, ironPos);
            } else {
                world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT,
                        SoundCategory.PLAYERS, 0.7f, 1f);
            }
        }
        return TypedActionResult.success(stack);
    }

    private BlockPos locateIronVein(World world, BlockPos center, int range) {
        return BlockPos.findClosest(center, range, range/2,
                pos -> world.getBlockState(pos).isIn(ModTags.Blocks.IRON_MAGNETOMETER_FINDABLE)).orElse(null);
    }

    private void emitParticles(ServerWorld serverWorld, PlayerEntity player, BlockPos target) {
        Random random = serverWorld.getRandom();

        Vec3d vec3d = player.getPos().add(0, 1, 0);
        Vec3d vec3d2 = new Vec3d(target.getX() + 0.5f, target.getY() + 0.5f, target.getZ() + 0.5f).subtract(vec3d);
        Vec3d vec3d3 = vec3d2.normalize();
        int i = 7;

        float distance = (float) vec3d2.length();
        float maxOffset = MathHelper.clamp((distance - 8) / 56f, 0f, 1f);

        Vec3d offset = new Vec3d(
                (random.nextFloat() - 0.5f) * maxOffset,
                (random.nextFloat() - 0.5f) * maxOffset,
                (random.nextFloat() - 0.5f) * maxOffset
        );

        Vec3d targetWithOffset = vec3d3.add(offset).normalize();

        Vector3f color;
        SoundEvent soundEvent;

        BlockState state = serverWorld.getBlockState(target).getBlock().getDefaultState();

        if (state.isIn(BlockTags.LAPIS_ORES)) {
           color = new Vector3f(0.1f, 0.3f, 0.7f);
           soundEvent = SoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP_ENCHANTED;
        } else if (state.isIn(BlockTags.IRON_ORES)) {
            color = new Vector3f(0.8f, 0.7f, 0.6f);
            soundEvent = SoundEvents.BLOCK_METAL_STEP;
        } else if (state.isIn(BlockTags.REDSTONE_ORES)) {
            color = new Vector3f(1f, 0f, 0f);
            soundEvent = SoundEvents.BLOCK_GLASS_STEP;
        } else {
            color = new Vector3f(0f, 0f, 0f);
            soundEvent = SoundEvents.BLOCK_COPPER_STEP;
        }
        soundEvent = SoundEvents.BLOCK_COPPER_STEP;

        float pitch = 1.5f - (distance / RANGE) * 1f;
        serverWorld.playSound(null, player.getBlockPos(), soundEvent,
                SoundCategory.PLAYERS, 0.7f, pitch);


        for (int j = 1; j < i; j++) {
            Vec3d vec3d4 = vec3d.add(targetWithOffset.multiply(j));
            serverWorld.spawnParticles(new DustParticleEffect(color, 1f), vec3d4.x, vec3d4.y, vec3d4.z, 4, 0.2, 0.2, 0.2, 0.0);
        }
    }
}
