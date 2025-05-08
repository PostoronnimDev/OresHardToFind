package net.postoronnim.oreshardtofind.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> STORMBRINGER = registerStatusEffect("stormbringer",
            new StormBringerEffect(StatusEffectCategory.NEUTRAL, 0xebebeb));


    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(OresHardToFind.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        OresHardToFind.LOGGER.info("Registering Mod Effects for " + OresHardToFind.MOD_ID);
    }
}
