package net.postoronnim.oreshardtofind.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.block.custom.BrushableOre;
import net.postoronnim.oreshardtofind.block.custom.ShriekingOre;
import net.postoronnim.oreshardtofind.block.custom.SusOre;
import net.postoronnim.oreshardtofind.block.custom.UnchargedBlock;
import net.postoronnim.oreshardtofind.loot_table.ModLootTables;

public class ModBlocks {

    public static final Block GOLD_SAND_BLOCK = registerBlock("gold_sand_block",
            new BrushableOre(Blocks.SAND,
                    SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
                    SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.PALE_YELLOW)
                            .instrument(NoteBlockInstrument.SNARE)
                            .strength(0.25F)
                            .sounds(BlockSoundGroup.SUSPICIOUS_SAND)
                            .pistonBehavior(PistonBehavior.DESTROY),
                    ModLootTables.BRUSHABLE_GOLD_BLOCK_LOOT));

    public static final Block GOLD_GRAVEL_BLOCK = registerBlock("gold_gravel_block",
            new BrushableOre(Blocks.GRAVEL,
                    SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL,
                    SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.STONE_GRAY)
                            .instrument(NoteBlockInstrument.SNARE)
                            .strength(0.25F)
                            .sounds(BlockSoundGroup.SUSPICIOUS_GRAVEL)
                            .pistonBehavior(PistonBehavior.DESTROY),
                    ModLootTables.BRUSHABLE_GOLD_BLOCK_LOOT));

    public static final Block GOLD_CLAY_BLOCK = registerBlock("gold_clay_block",
            new BrushableOre(Blocks.CLAY,
                    SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
                    SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.STONE_GRAY)
                            .instrument(NoteBlockInstrument.SNARE)
                            .strength(0.25F)
                            .sounds(BlockSoundGroup.SUSPICIOUS_SAND)
                            .pistonBehavior(PistonBehavior.DESTROY),
                    ModLootTables.BRUSHABLE_GOLD_BLOCK_LOOT));

    public static final Block MAGNETITE_GRAVEL_BLOCK = registerBlock("magnetite_gravel_block",
            new BrushableOre(Blocks.GRAVEL,
                    SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL,
                    SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.STONE_GRAY)
                            .instrument(NoteBlockInstrument.SNARE)
                            .strength(0.25f)
                            .sounds(BlockSoundGroup.SUSPICIOUS_GRAVEL)
                            .pistonBehavior(PistonBehavior.DESTROY),
                    ModLootTables.BRUSHABLE_MAGNETITE_BLOCK_LOOT));

    public static final Block MAGNETITE_SAND_BLOCK = registerBlock("magnetite_sand_block",
            new BrushableOre(Blocks.SAND,
                    SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
                    SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.PALE_YELLOW)
                            .instrument(NoteBlockInstrument.SNARE)
                            .strength(0.25f)
                            .sounds(BlockSoundGroup.SUSPICIOUS_SAND)
                            .pistonBehavior(PistonBehavior.DESTROY),
                    ModLootTables.BRUSHABLE_MAGNETITE_BLOCK_LOOT));

    public static final Block SHRIEKING_DIAMOND_ORE = registerBlock("shrieking_diamond_ore",
            new ShriekingOre(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLACK)
                    .strength(4f, 4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.SCULK_SHRIEKER)));

    public static final Block MAGNETITE_BLOCK = registerBlock("magnetite_block",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    public static final Block UNCHARGED_MAGNET = registerBlock("uncharged_magnet",
            new UnchargedBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block CHARGED_MAGNET = registerBlock("charged_magnet",
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(state -> 8).emissiveLighting(Blocks::always)));

    public static final Block CALCITE_LAPIS_ORE = registerBlock("calcite_lapis_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.copy(Blocks.LAPIS_ORE)));

    public static final Block SUS_DEEPSLATE_IRON_ORE = registerBlock("sus_deepslate_iron_ore", new SusOre(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(OresHardToFind.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(OresHardToFind.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        OresHardToFind.LOGGER.info("Registering Mod Blocks for " + OresHardToFind.MOD_ID);
    }
}
