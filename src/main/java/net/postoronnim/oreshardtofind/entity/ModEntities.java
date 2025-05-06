package net.postoronnim.oreshardtofind.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.entity.custom.IronOreGolemEntity;

public class ModEntities {

    public static final EntityType<IronOreGolemEntity> IRON_ORE_GOLEM = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(OresHardToFind.MOD_ID, "iron_ore_golem"),
            EntityType.Builder.create(IronOreGolemEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.75f, 1f).build());

    public static void registerModEntities() {
        OresHardToFind.LOGGER.info("Register Mod Entities for " + OresHardToFind.MOD_ID);
    }
}
