package net.postoronnim.oreshardtofind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.SHRIEKING_DIAMOND_ORE,
                        ModBlocks.SUS_DEEPSLATE_IRON_ORE,
                        ModBlocks.MAGNETITE_BLOCK,
                        ModBlocks.UNCHARGED_MAGNET,
                        ModBlocks.CHARGED_MAGNET,
                        ModBlocks.CALCITE_LAPIS_ORE);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.GOLD_SAND_BLOCK,
                        ModBlocks.GOLD_GRAVEL_BLOCK,
                        ModBlocks.GOLD_CLAY_BLOCK,
                        ModBlocks.GOLD_GRAVEL_BLOCK);

//        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL)
//                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
//                .addTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .addTag(ModTags.Blocks.NEEDS_COPPER_TOOL);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SHRIEKING_DIAMOND_ORE)
                .add(ModBlocks.CALCITE_LAPIS_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_COPPER_TOOL)
                .add(Blocks.DEEPSLATE_IRON_ORE)
                .add(Blocks.IRON_ORE)
                .add(ModBlocks.SUS_DEEPSLATE_IRON_ORE)
                .add(ModBlocks.MAGNETITE_BLOCK)
                .add(ModBlocks.UNCHARGED_MAGNET)
                .add(ModBlocks.CHARGED_MAGNET);

        getOrCreateTagBuilder(BlockTags.GOLD_ORES)
                .add(ModBlocks.GOLD_SAND_BLOCK)
                .add(ModBlocks.GOLD_GRAVEL_BLOCK)
                .add(ModBlocks.GOLD_CLAY_BLOCK);

        getOrCreateTagBuilder(BlockTags.IRON_ORES)
                .add(ModBlocks.SUS_DEEPSLATE_IRON_ORE);

        getOrCreateTagBuilder(BlockTags.LAPIS_ORES)
                .add(ModBlocks.CALCITE_LAPIS_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.MAGNETITE_ORES)
                .add(ModBlocks.MAGNETITE_GRAVEL_BLOCK)
                .add(ModBlocks.MAGNETITE_SAND_BLOCK);

//        getOrCreateTagBuilder(ModTags.Blocks.IRON_MAGNETOMETER_FINDABLE)
//                .addTag(BlockTags.IRON_ORES)
//                .addTag(BlockTags.LAPIS_ORES)
//                .addTag(BlockTags.REDSTONE_ORES);
    }
}
