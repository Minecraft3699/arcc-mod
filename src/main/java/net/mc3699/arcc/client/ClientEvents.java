package net.mc3699.arcc.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.client.renderer.HUDBlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "arcc", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.HUD_BLOCK_ENIITY.get(), HUDBlockEntityRenderer::new);
    }

}
