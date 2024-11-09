package net.mc3699.arcc.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mc3699.arcc.arcc;
import net.mc3699.arcc.entity.EntryMissileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MissileRenderer extends EntityRenderer<EntryMissileEntity> {


    public MissileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(EntryMissileEntity pEntity) {
        return new ResourceLocation(arcc.MODID, "textures/entity/missile_texture.png");
    }

    @Override
    public void render(EntryMissileEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
