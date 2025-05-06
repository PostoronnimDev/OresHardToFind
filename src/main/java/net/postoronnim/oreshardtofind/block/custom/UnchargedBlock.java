package net.postoronnim.oreshardtofind.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.postoronnim.oreshardtofind.block.ModBlocks;

import java.util.List;

public class UnchargedBlock extends Block {

    public UnchargedBlock(Settings settings) {
        super(settings.ticksRandomly());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient && isUnderPoweredLightningRod(world, pos)) {
            world.setBlockState(pos, ModBlocks.CHARGED_MAGNET.getDefaultState());
            playChargingEffects(((ServerWorld) world), pos);
        }
    }

    private boolean isUnderPoweredLightningRod(World world, BlockPos pos) {
        BlockPos rodPos = pos.up();
        return world.getBlockState(rodPos).isOf(Blocks.LIGHTNING_ROD) &&
                world.isReceivingRedstonePower(rodPos);
    }

    private void playChargingEffects(ServerWorld world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE,
                SoundCategory.BLOCKS, 1.0f, 1.5f);


        world.spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
                10,
                0.2,
                0.2,
                0.2, 1f);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.oreshardtofind.uncharged_block.tooltip"));
    }
}
