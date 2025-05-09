package net.postoronnim.oreshardtofind.render;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.item.custom.CopperArmorItem;

import java.util.List;

public class CopperArmorRenderer implements ArmorRenderer {
    public ArmorEntityModel<LivingEntity> innerModel;
    public ArmorEntityModel<LivingEntity> outerModel;

    public CopperArmorRenderer() {

    }

    public void lazyInitModels() {
        if(innerModel == null || outerModel == null) {
            innerModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR));
            outerModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        lazyInitModels();
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.CHEST, light, contextModel, this.getModel(EquipmentSlot.CHEST));
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.LEGS, light, contextModel, this.getModel(EquipmentSlot.LEGS));
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.FEET, light, contextModel, this.getModel(EquipmentSlot.FEET));
        this.renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.HEAD, light, contextModel, this.getModel(EquipmentSlot.HEAD));
    }

    protected void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> contextModel, ArmorEntityModel<LivingEntity> model) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getSlotType() == armorSlot) {
                contextModel.copyBipedStateTo(model);
                this.setVisible(model, armorSlot);
                boolean bl = this.usesInnerModel(armorSlot);
                ArmorMaterial armorMaterial = armorItem.getMaterial().value();
                ArmorMaterial.Layer layer = getLayer(armorMaterial, itemStack);

                this.renderArmorParts(matrices, vertexConsumers, light, model, layer.getTexture(bl));
            }
        }
    }

    private static ArmorMaterial.Layer getLayer(ArmorMaterial armorMaterial, ItemStack itemStack) {
        List<ArmorMaterial.Layer> layers = armorMaterial.layers();

        if(itemStack.getItem() instanceof CopperArmorItem) {
            float durabilityRatio = (float) itemStack.getDamage() / itemStack.getMaxDamage();

            return durabilityRatio > 0.75f ?  layers.get(3):
                    durabilityRatio > 0.5f ? layers.get(2) :
                            durabilityRatio > 0.25f ? layers.get(1) :
                                    layers.get(0);
        }
        return armorMaterial.layers().getFirst();
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorEntityModel<LivingEntity> model, Identifier identifier) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(identifier));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    }

    protected void setVisible(ArmorEntityModel<LivingEntity> bipedModel, EquipmentSlot slot) {
        bipedModel.setVisible(false);
        switch (slot) {
            case HEAD:
                bipedModel.head.visible = true;
                bipedModel.hat.visible = true;
                break;
            case CHEST:
                bipedModel.body.visible = true;
                bipedModel.rightArm.visible = true;
                bipedModel.leftArm.visible = true;
                break;
            case LEGS:
                bipedModel.body.visible = true;
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
                break;
            case FEET:
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
        }
    }

    protected boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    private ArmorEntityModel<LivingEntity> getModel(EquipmentSlot slot) {
        return this.usesInnerModel(slot) ? this.innerModel : this.outerModel;
    }
}
