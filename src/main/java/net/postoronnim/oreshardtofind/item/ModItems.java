package net.postoronnim.oreshardtofind.item;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.entity.ModEntities;
import net.postoronnim.oreshardtofind.item.custom.CopperArmorItem;
import net.postoronnim.oreshardtofind.item.custom.CopperMagnetometerItem;
import net.postoronnim.oreshardtofind.item.custom.IronMagnetometerItem;
import net.postoronnim.oreshardtofind.item.custom.NetheriteMagnetometerItem;

public class ModItems {

    public static final Item IRON_ORE_GOLEM_SPAWN_EGG = registerItem("iron_ore_golem_spawn_egg",
            new SpawnEggItem(ModEntities.IRON_ORE_GOLEM, 0x3d3d43, 0xe2c0aa, new Item.Settings()));

    public static final Item MAGNETITE_SHARD = registerItem("magnetite_shard",
            new Item(new Item.Settings()));

    public static final Item COPPER_MAGNETOMETER = registerItem("copper_magnetometer",
            new CopperMagnetometerItem(new Item.Settings()));
    public static final Item IRON_MAGNETOMETER = registerItem("iron_magnetometer",
            new IronMagnetometerItem(new Item.Settings()));
    public static final Item NETHERITE_MAGNETOMETER = registerItem("netherite_magnetometer",
            new NetheriteMagnetometerItem(new Item.Settings()));

    public static final Item COPPER_HELMET = registerItem("copper_helmet",
            new CopperArmorItem(ModArmorMaterials.COPPER_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(33))));
    public static final Item COPPER_LEGGINGS = registerItem("copper_leggings",
                new CopperArmorItem(ModArmorMaterials.COPPER_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                        .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(33))));
    public static final Item COPPER_CHESTPLATE = registerItem("copper_chestplate",
                new CopperArmorItem(ModArmorMaterials.COPPER_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                        .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(33))));
    public static final Item COPPER_BOOTS = registerItem("copper_boots",
                new CopperArmorItem(ModArmorMaterials.COPPER_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                        .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(33))));

    public static final Item COPPER_SWORD = registerItem("copper_sword",
            new SwordItem(ModToolMaterials.COPPER, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.COPPER, 3, -2.0f))));
    public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe",
            new PickaxeItem(ModToolMaterials.COPPER, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.COPPER, 1, -2.4f))));
    public static final Item COPPER_SHOVEL = registerItem("copper_shovel",
            new ShovelItem(ModToolMaterials.COPPER, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.COPPER, 1.5f, -2.6f))));
    public static final Item COPPER_AXE = registerItem("copper_axe",
            new AxeItem(ModToolMaterials.COPPER, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.COPPER, 6, -2.8f))));
    public static final Item COPPER_HOE = registerItem("copper_hoe",
            new HoeItem(ModToolMaterials.COPPER, new Item.Settings()
                    .attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.COPPER, 0, -2.6f))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(OresHardToFind.MOD_ID, name), item);
    }

    public static void registerModItems() {
        OresHardToFind.LOGGER.info("Registering Mod Items for " + OresHardToFind.MOD_ID);
    }
}
