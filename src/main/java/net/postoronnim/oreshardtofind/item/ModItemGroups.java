package net.postoronnim.oreshardtofind.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup ORESHARDTOFIND_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(OresHardToFind.MOD_ID, "oreshardtofind_items_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.GOLD_SAND_BLOCK))
                    .displayName(Text.translatable("itemgroup.oreshardtofind.oreshardtofind_items_group"))
                    .entries((displayContext, entries) -> {
                        //blocks
                        entries.add(ModBlocks.GOLD_SAND_BLOCK);
                        entries.add(ModBlocks.GOLD_GRAVEL_BLOCK);
                        entries.add(ModBlocks.GOLD_CLAY_BLOCK);
                        entries.add(ModBlocks.SHRIEKING_DIAMOND_ORE);
                        entries.add(ModBlocks.SUS_DEEPSLATE_IRON_ORE);
                        entries.add(ModBlocks.MAGNETITE_GRAVEL_BLOCK);
                        entries.add(ModBlocks.MAGNETITE_SAND_BLOCK);
                        entries.add(ModBlocks.MAGNETITE_BLOCK);
                        entries.add(ModBlocks.UNCHARGED_MAGNET);
                        entries.add(ModBlocks.CHARGED_MAGNET);
                        entries.add(ModBlocks.CALCITE_LAPIS_ORE);

                        //items
                        entries.add(ModItems.MAGNETITE_SHARD);
                        entries.add(ModItems.COPPER_HELMET);
                        entries.add(ModItems.COPPER_CHESTPLATE);
                        entries.add(ModItems.COPPER_LEGGINGS);
                        entries.add(ModItems.COPPER_BOOTS);
                        entries.add(ModItems.COPPER_SWORD);
                        entries.add(ModItems.COPPER_PICKAXE);
                        entries.add(ModItems.COPPER_SHOVEL);
                        entries.add(ModItems.COPPER_AXE);
                        entries.add(ModItems.COPPER_HOE);
                        entries.add(ModItems.COPPER_MAGNETOMETER);
                        entries.add(ModItems.IRON_MAGNETOMETER);
                        entries.add(ModItems.NETHERITE_MAGNETOMETER);
                        entries.add(ModItems.LAPIS_COMPASS);

                        //spawn eggs
                        entries.add(ModItems.IRON_ORE_GOLEM_SPAWN_EGG);
                    }).build());

    public static void registerItemGroups() {
        OresHardToFind.LOGGER.info("Registering Item Groups for " + OresHardToFind.MOD_ID);
    }
}
