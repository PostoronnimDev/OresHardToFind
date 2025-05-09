package net.postoronnim.oreshardtofind.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.postoronnim.oreshardtofind.OresHardToFind;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> COPPER_MATERIAL = registerArmorMaterial("copper",
            ()-> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.BODY, 4);
            }), 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.COPPER_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(OresHardToFind.MOD_ID, "copper")),
                            new ArmorMaterial.Layer(Identifier.of(OresHardToFind.MOD_ID, "copper_exposed")),
                            new ArmorMaterial.Layer(Identifier.of(OresHardToFind.MOD_ID, "copper_weathered")),
                            new ArmorMaterial.Layer(Identifier.of(OresHardToFind.MOD_ID, "copper_oxidized"))), 0, 0));

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(OresHardToFind.MOD_ID, name), material.get());
    }

    public static void registerArmorMaterials() {
        OresHardToFind.LOGGER.info("Registering Armor Materials for " + OresHardToFind.MOD_ID);
    }
}
