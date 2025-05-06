package net.postoronnim.oreshardtofind.feature.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.block.ModBlocks;

public class RedstoneCoreVeinFeature extends OreFeature {

    public RedstoneCoreVeinFeature() {
        super(OreFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {

        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        OreFeatureConfig config = context.getConfig();

        if (super.generate(context)) {
            if(!world.getBlockState(origin).isAir()) {
                world.setBlockState(origin, Blocks.REDSTONE_BLOCK.getDefaultState(), Block.NOTIFY_ALL);
            }
            return true;
        }
        return false;
    }
}
