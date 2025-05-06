package net.postoronnim.oreshardtofind.feature;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.feature.custom.RedstoneCoreVeinFeature;
import net.postoronnim.oreshardtofind.feature.custom.ShriekingOreFeature;
import net.postoronnim.oreshardtofind.feature.custom.SusOreFeature;
import net.postoronnim.oreshardtofind.feature.custom.WaterAdjacentGoldFeature;

public class ModFeatures {

    public static final WaterAdjacentGoldFeature WATER_ADJACENT_GOLD_FEATURE =
            new WaterAdjacentGoldFeature();

    public static final ShriekingOreFeature SHRIEKING_ORE_FEATURE =
            new ShriekingOreFeature();

    public static final SusOreFeature SUS_ORE_FEATURE =
            new SusOreFeature();

    public static final RedstoneCoreVeinFeature REDSTONE_CORE_VEIN_FEATURE =
            new RedstoneCoreVeinFeature();

    public static void register() {

        Registry.register(Registries.FEATURE,
                Identifier.of(OresHardToFind.MOD_ID, "water_adjacent_gold"),
                WATER_ADJACENT_GOLD_FEATURE);

        Registry.register(Registries.FEATURE,
                Identifier.of(OresHardToFind.MOD_ID, "shrieking_ore_feature"),
                SHRIEKING_ORE_FEATURE);

        Registry.register(Registries.FEATURE,
                Identifier.of(OresHardToFind.MOD_ID, "sus_ore_feature"),
                SUS_ORE_FEATURE);

        Registry.register(Registries.FEATURE,
                Identifier.of(OresHardToFind.MOD_ID, "redstone_core_vein_feature"),
                REDSTONE_CORE_VEIN_FEATURE);
    }
}
