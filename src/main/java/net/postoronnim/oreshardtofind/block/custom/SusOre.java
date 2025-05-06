package net.postoronnim.oreshardtofind.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.postoronnim.oreshardtofind.entity.ModEntities;
import net.postoronnim.oreshardtofind.entity.custom.IronOreGolemEntity;

public class SusOre extends Block {
    public SusOre(Settings settings) {
        super(settings.pistonBehavior(PistonBehavior.DESTROY));
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!isMinedWithSilkTouch(player)) {
            summonEntity(world, pos);
        }
        return super.onBreak(world, pos, state, player);
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
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        summonEntity(world, pos);
    }

    public void summonEntity(World world, BlockPos pos) {
        if (!world.isClient) { // Only run on the server side
            IronOreGolemEntity ironOreGolem = new IronOreGolemEntity(ModEntities.IRON_ORE_GOLEM, world);
            ironOreGolem.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5); // Center the mob on the block
            world.spawnEntity(ironOreGolem); // Spawn the mob
        }
    }
}
