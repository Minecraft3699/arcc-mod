package net.mc3699.arcc.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEventBusEvents {

    @SubscribeEvent
    public void trackerTick(LivingEvent.LivingTickEvent event)
    {
        LivingEntity entity = event.getEntity();
        CompoundTag entityTag = entity.getPersistentData();

        if(entityTag.contains("trackerTimer"))
        {
            int timer = entityTag.getInt("trackerTimer");
            if(timer > 0)
            {
                entityTag.putInt("trackerTimer", timer - 1);
            } else {
                entityTag.remove("trackerTimer");
                entityTag.remove("trackerChannel");
            }
        }
    }
}
