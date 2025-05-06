package net.postoronnim.oreshardtofind.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.block.entity.custom.BrushableOreEntity;
import net.postoronnim.oreshardtofind.block.entity.custom.ShriekingOreEntity;

public class ModBlockEntities {
    public static BlockEntityType<BrushableOreEntity> BRUSHABLE_ORE_ENTITY;
    public static BlockEntityType<ShriekingOreEntity> SHRIEKING_ORE_ENTITY;

    public static void registerModBlockEntities() {

        BRUSHABLE_ORE_ENTITY =
                Registry.register(
                        Registries.BLOCK_ENTITY_TYPE,
                        Identifier.of(OresHardToFind.MOD_ID, "brushable_ore_entity"),
                        BlockEntityType.Builder.create(
                                BrushableOreEntity::new,
                                ModBlocks.GOLD_SAND_BLOCK,
                                ModBlocks.GOLD_GRAVEL_BLOCK,
                                ModBlocks.GOLD_CLAY_BLOCK,
                                ModBlocks.MAGNETITE_GRAVEL_BLOCK,
                                ModBlocks.MAGNETITE_SAND_BLOCK
                        ).build()
                );

        SHRIEKING_ORE_ENTITY =
                Registry.register(
                        Registries.BLOCK_ENTITY_TYPE,
                        Identifier.of(OresHardToFind.MOD_ID, "shrieking_ore_entity"),
                        BlockEntityType.Builder.create(
                                ShriekingOreEntity::new,
                                ModBlocks.SHRIEKING_DIAMOND_ORE
                        ).build()
                );

        OresHardToFind.LOGGER.info("Registering Mod Block Entities for " + OresHardToFind.MOD_ID);
    }
}
