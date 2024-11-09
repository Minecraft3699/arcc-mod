package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.block.entity.EntityTrackerBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EntityTrackerPeripheral implements IPeripheral {

    public final EntityTrackerBlockEntity blockEntity;

    public EntityTrackerPeripheral(EntityTrackerBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public String getType() {
        return "entity_tracker";
    }

    @LuaFunction
    public List<Map<String,Object>> getTrackedEntities(String trackerID)
    {
        ServerLevel level = Objects.requireNonNull(Objects.requireNonNull(blockEntity.getLevel()).getServer()).getLevel(blockEntity.getLevel().dimension());

        List<Entity> tracked = new ArrayList<>();

        for (Entity entity : level.getAllEntities())
        {
            if(entity.getPersistentData().contains("trackerChannel"))
            {
                if(entity.getPersistentData().getString("trackerChannel").equals(trackerID))
                {
                    tracked.add(entity);
                }
            }
        }

        List<Map<String, Object>> trackedEntityData = new ArrayList<>();

        for(Entity trackedEntity: tracked)
        {
            Map<String, Object> entityInf = new HashMap<>();
            Vec3 pos = trackedEntity.position();
            CompoundTag entityTag = trackedEntity.getPersistentData();


            entityInf.put("trackerBattery", entityTag.getInt("trackerTimer")/20);

            entityInf.put("x", pos.x);

            entityInf.put("y", pos.y);

            entityInf.put("z", pos.z);

            entityInf.put("name", trackedEntity.getName().getString());

            if(trackedEntity instanceof LivingEntity livingEntity)
            {
                entityInf.put("health",livingEntity.getHealth());
            } else {
                entityInf.put("health", -1);
            }

            trackedEntityData.add(entityInf);
        }



        return trackedEntityData;
    }

    @LuaFunction
    public void detonateEntities(String trackerID)
    {
        ServerLevel level = Objects.requireNonNull(Objects.requireNonNull(blockEntity.getLevel()).getServer()).getLevel(blockEntity.getLevel().dimension());

        for (Entity entity : level.getAllEntities())
        {
            if(entity.getPersistentData().contains("trackerChannel"))
            {
                if(entity.getPersistentData().getString("trackerChannel").equals(trackerID))
                {
                    Explosion explosion = level.explode(entity, entity.position().x, entity.position().y, entity.position().z, 2.0f, Level.ExplosionInteraction.NONE);
                    entity.hurt(entity.damageSources().explosion(explosion), 5);
                }
            }
        }
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }
}
