package net.postoronnim.oreshardtofind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.postoronnim.oreshardtofind.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.MAGNETITE_BLOCK);
        addDrop(ModBlocks.UNCHARGED_MAGNET);
        addDrop(ModBlocks.CHARGED_MAGNET);
        addDrop(ModBlocks.SHRIEKING_DIAMOND_ORE, oreDrops(ModBlocks.SHRIEKING_DIAMOND_ORE, Items.DIAMOND));
        addDropWithSilkTouch(ModBlocks.SUS_DEEPSLATE_IRON_ORE, ModBlocks.SUS_DEEPSLATE_IRON_ORE);
        addDrop(ModBlocks.CALCITE_LAPIS_ORE, lapisOreDrops(ModBlocks.CALCITE_LAPIS_ORE));
    }
}
