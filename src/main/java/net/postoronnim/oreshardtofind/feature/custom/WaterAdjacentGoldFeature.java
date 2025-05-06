package net.postoronnim.oreshardtofind.feature.custom;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class WaterAdjacentGoldFeature extends OreFeature {
    private static final int LAKE_SEARCH_RADIUS = 8;
    private static final int MIN_LAKE_BLOCKS = 3;

    public WaterAdjacentGoldFeature() {
        super(OreFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        OreFeatureConfig config = context.getConfig();

        // Random position in chunk within diamond levels
        int x = origin.getX();
        int z = origin.getZ();
        int y = origin.getY();

        BlockPos pos = new BlockPos(x, y, z);

        if (isNearLake(world, pos)) {
            super.generate(context);
        }
        return isNearLake(world, pos);

    }

    private boolean isNearLake(StructureWorldAccess world, BlockPos pos) {
        int lavaCount = 0;

        // Check in 3D area around the position
        for (BlockPos checkPos : BlockPos.iterate(
                pos.add(-LAKE_SEARCH_RADIUS, -5, -LAKE_SEARCH_RADIUS),
                pos.add(LAKE_SEARCH_RADIUS, 5, LAKE_SEARCH_RADIUS)))
        {
            if (world.getBlockState(checkPos).isOf(Blocks.WATER)) {
                lavaCount++;
                if (lavaCount >= MIN_LAKE_BLOCKS) {
                    return true;
                }
            }
        }

        return false;
    }
}
