package net.postoronnim.oreshardtofind.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.postoronnim.oreshardtofind.OresHardToFind;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> BRUSHABLE_GOLD_WATER_PLACED = registryKey("brushable_gold_water_placed");
    public static final RegistryKey<PlacedFeature> SHRIEKING_DIAMOND_ORE_PLACED = registryKey("shrieking_diamond_ore_placed");
    public static final RegistryKey<PlacedFeature> SUS_IRON_ORE_PLACED = registryKey("sus_iron_ore_placed");
    public static final RegistryKey<PlacedFeature> REDSTONE_CORE_VEIN_PLACED = registryKey("redstone_core_vein_placed");
    public static final RegistryKey<PlacedFeature> LAPIS_GEODE_PLACED = registryKey("lapis_geode_placed");
    public static final RegistryKey<PlacedFeature> MAGNETITE_BRUSHABLE_ORE_WATER_PLACED = registryKey("magnetite_brushable_ore_water_placed");
    public static final RegistryKey<PlacedFeature> MAGNETITE_GRAVEL_ORE_PLACED = registryKey("magnetite_gravel_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, BRUSHABLE_GOLD_WATER_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.BRUSHABLE_GOLD_WATER),
                ModOrePlacement.modifiersWithCount(18,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(40),
                                YOffset.fixed(80))));

        register(context, MAGNETITE_BRUSHABLE_ORE_WATER_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.MAGNETITE_BRUSHABLE_ORE_WATER),
                ModOrePlacement.modifiersWithCount(18,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(40),
                                YOffset.fixed(80))));

        register(context, MAGNETITE_GRAVEL_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.MAGNETITE_BRUSHABLE_ORE_WATER),
                ModOrePlacement.modifiersWithCount(14,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(64),
                                YOffset.fixed(140))));

        register(context, SHRIEKING_DIAMOND_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.SHRIEKING_DIAMOND_ORE),
                ModOrePlacement.modifiersWithCount(8, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));

        register(context, SUS_IRON_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.SUS_IRON_ORE),
                ModOrePlacement.modifiersWithCount(2, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-80), YOffset.fixed(20))));

        register(context, REDSTONE_CORE_VEIN_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.REDSTONE_CORE_VEIN),
                ModOrePlacement.modifiersWithCount(1, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-32), YOffset.aboveBottom(32))));

        register(context, LAPIS_GEODE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.LAPIS_GEODE),
                ModOrePlacement.modifiersWithRarity(24, HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.fixed(30))));
    }

    public static RegistryKey<PlacedFeature> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(OresHardToFind.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
