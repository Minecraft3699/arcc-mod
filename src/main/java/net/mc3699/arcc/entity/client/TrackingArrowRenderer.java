package net.mc3699.arcc.entity.client;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TrackingArrowRenderer extends ArrowRenderer {

    private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation("arcc", "textures/entity/projectile/tracking_arrow.png");

    public TrackingArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return ARROW_TEXTURE;
    }
}
