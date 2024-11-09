package net.mc3699.arcc.events;

import net.mc3699.arcc.arcc;
import net.mc3699.arcc.entity.client.MissileEntityModel;
import net.mc3699.arcc.entity.client.ModModelLayers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = arcc.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ModModelLayers.MISSILE_LAYER, MissileEntityModel::createBodyLayer);
    }
}
