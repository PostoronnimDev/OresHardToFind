package net.postoronnim.oreshardtofind;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.postoronnim.oreshardtofind.entity.ModEntities;
import net.postoronnim.oreshardtofind.entity.client.IronOreGolemModel;
import net.postoronnim.oreshardtofind.entity.client.IronOreGolemRenderer;
import net.postoronnim.oreshardtofind.item.ModItems;
import net.postoronnim.oreshardtofind.render.CopperArmorRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OresHardToFindClient implements ClientModInitializer {
    public static final String MOD_ID = "oreshardtofind";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(IronOreGolemModel.IRON_ORE_GOLEM, IronOreGolemModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.IRON_ORE_GOLEM, IronOreGolemRenderer::new);
        ArmorRenderer.register(new CopperArmorRenderer(),
                ModItems.COPPER_HELMET,
                ModItems.COPPER_CHESTPLATE,
                ModItems.COPPER_LEGGINGS,
                ModItems.COPPER_BOOTS);
    }
}
