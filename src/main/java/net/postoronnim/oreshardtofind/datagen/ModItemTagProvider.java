package net.postoronnim.oreshardtofind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.postoronnim.oreshardtofind.item.ModItems;
import net.postoronnim.oreshardtofind.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
//                .add(ModItems.COPPER_HELMET)
//                .add(ModItems.COPPER_CHESTPLATE)
//                .add(ModItems.COPPER_LEGGINGS)
//                .add(ModItems.COPPER_BOOTS);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.COPPER_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.COPPER_PICKAXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.COPPER_SHOVEL);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.COPPER_AXE);
        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.COPPER_HOE);
    }
}
