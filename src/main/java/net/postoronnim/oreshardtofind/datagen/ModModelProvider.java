package net.postoronnim.oreshardtofind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.item.ModItems;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.GOLD_GRAVEL_BLOCK);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.GOLD_SAND_BLOCK);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.GOLD_CLAY_BLOCK);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.MAGNETITE_GRAVEL_BLOCK);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.MAGNETITE_SAND_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHRIEKING_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SUS_DEEPSLATE_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAGNETITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.UNCHARGED_MAGNET);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHARGED_MAGNET);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CALCITE_LAPIS_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.IRON_ORE_GOLEM_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));

        itemModelGenerator.register(ModItems.MAGNETITE_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_MAGNETOMETER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.IRON_MAGNETOMETER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.NETHERITE_MAGNETOMETER, Models.HANDHELD);

        itemModelGenerator.registerCompass(ModItems.LAPIS_COMPASS);

//        ModelPredicateProviderRegistry.register(ModItems.LAPIS_COMPASS, new CompassAnglePredicateProvider(ModItems.LAPIS_COMPASS.));

//        itemModelGenerator.register(ModItems.COPPER_SWORD, Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_PICKAXE, Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_SHOVEL, Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_AXE, Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_HOE, Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_SWORD, "_exposed", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_PICKAXE, "_exposed", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_SHOVEL, "_exposed", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_AXE, "_exposed", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_HOE, "_exposed", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_SWORD, "_weathered", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_PICKAXE, "_weathered", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_SHOVEL, "_weathered", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_AXE, "_weathered", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_HOE, "_weathered", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_SWORD, "_oxidized", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_PICKAXE, "_oxidized", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_SHOVEL, "_oxidized", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_AXE, "_oxidized", Models.HANDHELD);
//        itemModelGenerator.register(ModItems.COPPER_HOE, "_oxidized", Models.HANDHELD);

//        itemModelGenerator.registerArmor(((ArmorItem) ModItems.COPPER_HELMET));
//        itemModelGenerator.registerArmor(((ArmorItem) ModItems.COPPER_CHESTPLATE));
//        itemModelGenerator.registerArmor(((ArmorItem) ModItems.COPPER_LEGGINGS));
//        itemModelGenerator.registerArmor(((ArmorItem) ModItems.COPPER_BOOTS));
    }
}
