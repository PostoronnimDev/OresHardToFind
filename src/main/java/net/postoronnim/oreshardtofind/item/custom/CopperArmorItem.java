package net.postoronnim.oreshardtofind.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class CopperArmorItem extends ArmorItem {
    private final int DAMAGE_INTERVAL = 400;
    private int ticksToDamage = DAMAGE_INTERVAL;

    public CopperArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);

    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            if(((PlayerEntity) entity).getEquippedStack(getSlotType()) == stack) {
                ticksToDamage--;
            }

            if (ticksToDamage <= 0) {
                stack.damage(1, (LivingEntity) entity, getSlotType());
                ticksToDamage = DAMAGE_INTERVAL;
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
