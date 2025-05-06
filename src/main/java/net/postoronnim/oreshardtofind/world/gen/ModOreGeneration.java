package net.postoronnim.oreshardtofind.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.world.ModPlacedFeatures;

public class ModOreGeneration {

    public static void generateOres () {
        BiomeModification biomeModification =  BiomeModifications.create(Identifier.of(OresHardToFind.MOD_ID, "remove_vanilla_ores"))
                .add(ModificationPhase.REMOVALS,
                        BiomeSelectors.foundInOverworld(),
                        context -> {
                            // Remove iron ore features
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_IRON_SMALL
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                   OrePlacedFeatures.ORE_IRON_MIDDLE
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_IRON_UPPER
                            );

                            // Remove diamond ore features
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_DIAMOND
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_DIAMOND_BURIED
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_DIAMOND_LARGE
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_DIAMOND_MEDIUM
                            );

                            // Remove gold ore features
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_GOLD
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_GOLD_DELTAS
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_GOLD_EXTRA
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_GOLD_LOWER
                            );

                            // Remove lapis ore features
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_LAPIS
                            );

                             context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_LAPIS_BURIED
                            );

                            // Remove redstone ore features
                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_REDSTONE
                            );

                            context.getGenerationSettings().removeFeature(
                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                    OrePlacedFeatures.ORE_REDSTONE_LOWER
                            );

                        });

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DEEP_DARK), GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.SHRIEKING_DIAMOND_ORE_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.excludeByKey(
                    BiomeKeys.OCEAN,
                    BiomeKeys.COLD_OCEAN,
                    BiomeKeys.DEEP_COLD_OCEAN,
                    BiomeKeys.DEEP_OCEAN,
                    BiomeKeys.FROZEN_OCEAN,
                    BiomeKeys.DEEP_FROZEN_OCEAN,
                    BiomeKeys.WARM_OCEAN,
                    BiomeKeys.LUKEWARM_OCEAN,
                    BiomeKeys.DEEP_LUKEWARM_OCEAN),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.BRUSHABLE_GOLD_WATER_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.MAGNETITE_BRUSHABLE_ORE_WATER_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.MAGNETITE_GRAVEL_ORE_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.SUS_IRON_ORE_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.REDSTONE_CORE_VEIN_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.LAPIS_GEODE_PLACED);
    }

}
