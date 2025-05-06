package net.postoronnim.oreshardtofind.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class CopperMagnetometerItem extends Item {
    public final int RANGE = 64;
    public static final int COOLDOWN_TICKS = 60;

    public CopperMagnetometerItem(Settings settings) {
        super(settings.maxDamage(32)); // 64 scans
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
                pos -> world.getBlockState(pos).isIn(BlockTags.IRON_ORES)).orElse(null);
    }

    private void emitParticles(ServerWorld serverWorld, PlayerEntity player, BlockPos target) {
        Random random = serverWorld.getRandom();

        Vec3d vec3d = player.getPos().add(0, 1, 0);
        Vec3d vec3d2 = new Vec3d(target.getX() + 0.5f, target.getY() + 0.5f, target.getZ() + 0.5f).subtract(vec3d);
        Vec3d vec3d3 = vec3d2.normalize();
        int i = 7;

        float distance = (float) vec3d2.length();
        float maxOffset = MathHelper.clamp((distance - 8) / 56f, 0f, 1f);

        float pitch = 1.5f - (distance / RANGE) * 1f;
        serverWorld.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_COPPER_STEP,
                SoundCategory.PLAYERS, 0.7f, pitch);

        Vec3d offset = new Vec3d(
                (random.nextFloat() - 0.5f) * maxOffset,
                (random.nextFloat() - 0.5f) * maxOffset,
                (random.nextFloat() - 0.5f) * maxOffset
        );

        Vec3d targetWithOffset = vec3d3.add(offset).normalize();

        for (int j = 1; j < i; j++) {
            Vec3d vec3d4 = vec3d.add(targetWithOffset.multiply(j));
            serverWorld.spawnParticles(new DustParticleEffect(new Vector3f(0.3f, 0.5f, 0.9f), 1f), vec3d4.x, vec3d4.y, vec3d4.z, 4, 0.2, 0.2, 0.2, 0.0);
        }
    }
}
