package net.mc3699.arcc;

import com.mojang.logging.LogUtils;
import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.block.ModBlocks;
import net.mc3699.arcc.entity.ModEntities;
import net.mc3699.arcc.entity.client.MissileRenderer;
import net.mc3699.arcc.entity.client.TrackingArrowRenderer;
import net.mc3699.arcc.events.ModEventBusEvents;
import net.mc3699.arcc.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(arcc.MODID)
public class arcc
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "arcc";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public arcc()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(new ModEventBusEvents());

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModEntities.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.ENTRY_MISSILE_ENTITY.get(), MissileRenderer::new);
            EntityRenderers.register(ModEntities.TRACKING_ARROW.get(), TrackingArrowRenderer::new);
        }
    }
}
