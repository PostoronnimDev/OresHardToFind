package net.postoronnim.oreshardtofind.feature.custom;

import net.minecraft.block.Block;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.block.custom.ShriekingOre;

public class ShriekingOreFeature extends OreFeature {

    public ShriekingOreFeature() {
        super(OreFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        boolean generated = super.generate(context);

        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        OreFeatureConfig oreFeatureConfig = context.getConfig();

        if (generated) {
            BlockPos.stream(context.getOrigin().add(-8, -8, -8), context.getOrigin().add(8, 8, 8))
                    .filter(pos -> structureWorldAccess.getBlockState(pos).getBlock() instanceof ShriekingOre)
                    .forEach(pos -> {
                        structureWorldAccess.setBlockState(pos, ModBlocks.SHRIEKING_DIAMOND_ORE.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, true), Block.NOTIFY_ALL);
                    });
        }

        return generated;
    }
}
