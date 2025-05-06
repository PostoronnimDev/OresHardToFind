package net.postoronnim.oreshardtofind.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.entity.custom.IronOreGolemEntity;

public class IronOreGolemRenderer extends MobEntityRenderer<IronOreGolemEntity, IronOreGolemModel<IronOreGolemEntity>> {
    public IronOreGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new IronOreGolemModel<>(context.getPart(IronOreGolemModel.IRON_ORE_GOLEM)), 0.75f);
    }

    @Override
    public Identifier getTexture(IronOreGolemEntity entity) {
        return Identifier.of(OresHardToFind.MOD_ID,  "textures/entity/iron_ore_golem/iron_ore_golem.png");
    }

    @Override
    public void render(IronOreGolemEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
