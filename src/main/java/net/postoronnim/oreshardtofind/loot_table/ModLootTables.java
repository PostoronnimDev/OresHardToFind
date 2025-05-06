package net.postoronnim.oreshardtofind.loot_table;

import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;

public class ModLootTables {

    public static final RegistryKey<LootTable> BRUSHABLE_GOLD_BLOCK_LOOT =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(OresHardToFind.MOD_ID, "archeology/brushable_gold_block"));

    public static final RegistryKey<LootTable> BRUSHABLE_MAGNETITE_BLOCK_LOOT =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(OresHardToFind.MOD_ID, "archeology/brushable_magnetite_block"));

    public static void registerModLootTables() {
        OresHardToFind.LOGGER.info("Registering Mod Loot Tables for " + OresHardToFind.MOD_ID);
    }
}
