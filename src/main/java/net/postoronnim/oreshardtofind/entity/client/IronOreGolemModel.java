package net.postoronnim.oreshardtofind.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.entity.custom.IronOreGolemEntity;

public class IronOreGolemModel<T extends IronOreGolemEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer IRON_ORE_GOLEM = new EntityModelLayer(Identifier.of(OresHardToFind.MOD_ID, "iron_ore_golem"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    public IronOreGolemModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.body = this.root.getChild("body");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 3.0F, 1.0F));

        ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(40, 32).cuboid(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new Dilation(0.0F))
                .uv(37, 35).cuboid(-3.0F, -1.5F, -4.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(37, 32).cuboid(1.0F, -1.5F, -4.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -2.5F, -1.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 8.0F, -1.0F));

        ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create().uv(0, 49).cuboid(-2.0F, 0.0F, -3.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 8.0F, 0.0F));

        ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create().uv(0, 49).mirrored().cuboid(-3.0F, 0.0F, -3.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, 8.0F, 0.0F));

        ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 11.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-10.0F, -4.0F, -1.0F));

        ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 11.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(10.0F, -4.0F, -1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(IronOreGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(IronOreGolemAnimations.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, IronOreGolemAnimations.IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAnimationState, IronOreGolemAnimations.ATTACK, ageInTicks, 1f);
        this.updateAnimation(entity.standAnimationState, IronOreGolemAnimations.STAND, ageInTicks, 1f);
        this.updateAnimation(entity.seatAnimationState, IronOreGolemAnimations.SEAT, ageInTicks, 1f);

    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0f, 25.0f);

        this.head.yaw = headYaw * 0.017453292f;
        this.head.pitch = headPitch * 0.017453292f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        root.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }
}
