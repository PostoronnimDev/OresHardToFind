package net.postoronnim.oreshardtofind.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.feature.ModFeatures;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> BRUSHABLE_GOLD_WATER = registerKey("brushable_gold_water");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SHRIEKING_DIAMOND_ORE = registerKey("shrieking_diamond_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SUS_IRON_ORE = registerKey("sus_iron_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> REDSTONE_CORE_VEIN = registerKey("redstone_core_vein");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAPIS_GEODE = registerKey("lapis_geode");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAGNETITE_BRUSHABLE_ORE_WATER = registerKey("magnetite_brushable_ore_water");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAGNETITE_GRAVEL_ORE = registerKey("magnetite_gravel_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest sandReplaceables = new BlockMatchRuleTest(Blocks.SAND);
        RuleTest gravelReplaceables = new BlockMatchRuleTest(Blocks.GRAVEL);
        RuleTest clayReplaceables = new BlockMatchRuleTest(Blocks.CLAY);
        RuleTest deepslateIronOreReplaceables = new BlockMatchRuleTest(Blocks.DEEPSLATE_IRON_ORE);

        List<OreFeatureConfig.Target> overworldShriekingDiamondOre =
                List.of(OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.SHRIEKING_DIAMOND_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldGoldOres =
                List.of(OreFeatureConfig.createTarget(sandReplaceables, ModBlocks.GOLD_SAND_BLOCK.getDefaultState()),
                        OreFeatureConfig.createTarget(gravelReplaceables, ModBlocks.GOLD_GRAVEL_BLOCK.getDefaultState()),
                        OreFeatureConfig.createTarget(clayReplaceables, ModBlocks.GOLD_CLAY_BLOCK.getDefaultState()));
        List<OreFeatureConfig.Target> overworldBrushableMagnetiteOre =
                List.of(OreFeatureConfig.createTarget(gravelReplaceables, ModBlocks.MAGNETITE_GRAVEL_BLOCK.getDefaultState()),
                        OreFeatureConfig.createTarget(sandReplaceables, ModBlocks.MAGNETITE_SAND_BLOCK.getDefaultState()));
        List<OreFeatureConfig.Target> overworldMagnetiteGravelOre =
                List.of(OreFeatureConfig.createTarget(sandReplaceables, ModBlocks.MAGNETITE_GRAVEL_BLOCK.getDefaultState()));

        List<OreFeatureConfig.Target> overworldSusIronOre =
                List.of(OreFeatureConfig.createTarget(deepslateIronOreReplaceables, ModBlocks.SUS_DEEPSLATE_IRON_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overWorldRedstoneOre =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, Blocks.REDSTONE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, Blocks.DEEPSLATE_REDSTONE_ORE.getDefaultState()));

        register(context, SHRIEKING_DIAMOND_ORE, ModFeatures.SHRIEKING_ORE_FEATURE, new OreFeatureConfig(overworldShriekingDiamondOre, 4));
        register(context, BRUSHABLE_GOLD_WATER, ModFeatures.WATER_ADJACENT_GOLD_FEATURE, new OreFeatureConfig(overworldGoldOres, 5));
        register(context, MAGNETITE_BRUSHABLE_ORE_WATER, ModFeatures.WATER_ADJACENT_GOLD_FEATURE, new OreFeatureConfig(overworldBrushableMagnetiteOre, 5));
        register(context, MAGNETITE_GRAVEL_ORE, Feature.ORE, new OreFeatureConfig(overworldMagnetiteGravelOre, 5));
        register(context, SUS_IRON_ORE, ModFeatures.SUS_ORE_FEATURE, new OreFeatureConfig(overworldSusIronOre, 1));
        register(context, REDSTONE_CORE_VEIN, ModFeatures.REDSTONE_CORE_VEIN_FEATURE, new OreFeatureConfig(overWorldRedstoneOre, 16));
        register(
                context,
                LAPIS_GEODE,
                Feature.GEODE,
                new GeodeFeatureConfig(
                        new GeodeLayerConfig(
                                BlockStateProvider.of(Blocks.AIR),
                                BlockStateProvider.of(Blocks.CALCITE),
                                BlockStateProvider.of(ModBlocks.CALCITE_LAPIS_ORE),
                                BlockStateProvider.of(Blocks.CALCITE),
                                BlockStateProvider.of(Blocks.SMOOTH_BASALT),
                                List.of(
                                        Blocks.AIR.getDefaultState()
                                ),
                                BlockTags.FEATURES_CANNOT_REPLACE,
                                BlockTags.GEODE_INVALID_BLOCKS
                        ),
                        new GeodeLayerThicknessConfig(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackConfig(0.95, 2.0, 2),
                        0.35,
                        0.083,
                        true,
                        UniformIntProvider.create(4, 6),
                        UniformIntProvider.create(3, 4),
                        UniformIntProvider.create(1, 2),
                        -16,
                        16,
                        0.05,
                        1
                )
        );
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(OresHardToFind.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
