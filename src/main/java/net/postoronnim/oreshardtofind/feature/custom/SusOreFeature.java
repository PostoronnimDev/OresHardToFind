package net.postoronnim.oreshardtofind.feature.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.block.ModBlocks;

public class SusOreFeature extends Feature<OreFeatureConfig> {

    public SusOreFeature() {
        super(OreFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {

        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        OreFeatureConfig config = context.getConfig();

        int replaced = 0;

        for (BlockPos checkPos : BlockPos.iterate(
                origin.add(-4, -4, -4),
                origin.add(4, 4, 4)))
        {
            if (world.getBlockState(checkPos).isOf(Blocks.DEEPSLATE_IRON_ORE) && random.nextBoolean()) {
                world.setBlockState(checkPos, ModBlocks.SUS_DEEPSLATE_IRON_ORE.getDefaultState(), Block.NOTIFY_ALL);
                replaced++;
            }
        }

        if (replaced > 0) {
            OresHardToFind.LOGGER.info("replaced: " + replaced);
            return true;
        } else {
            return false;
        }
    }

    private boolean shouldPlace(StructureWorldAccess world, BlockPos origin, OreFeatureConfig config, Random random) {
        return config.targets.getFirst().target.test(world.getBlockState(origin), random);
    }
}
