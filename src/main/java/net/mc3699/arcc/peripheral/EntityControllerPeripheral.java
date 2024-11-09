package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.block.entity.EntityControllerBlockEntity;
import net.mc3699.arcc.entity.EntityNameTargetGoal;
import net.mc3699.arcc.entity.EntityTargetGoal;
import net.mc3699.arcc.entity.GotoPositionGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EntityControllerPeripheral implements IPeripheral {

    public final EntityControllerBlockEntity blockEntity;

    public EntityControllerPeripheral(EntityControllerBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public String getType() {
        return "entity_controller";
    }


    private List<Entity> getEntities(String controllerID)
    {
        ServerLevel level = Objects.requireNonNull(Objects.requireNonNull(blockEntity.getLevel()).getServer()).getLevel(blockEntity.getLevel().dimension());

        List<Entity> tracked = new ArrayList<>();

        for (Entity entity : level.getAllEntities())
        {
            if(entity.getPersistentData().contains("controlChipGroup"))
            {
                if(entity.getPersistentData().getString("controlChipGroup").equals(controllerID))
                {
                    tracked.add(entity);
                }
            }
        }

        return tracked;
    }


    private void suspendMember(Entity entity)
    {
        if(entity instanceof PathfinderMob mob)
        {
            mob.setNoAi(true);
        }
    }


    @LuaFunction
    public List<Map<String,Object>> getEntityData(String controllerID)
    {
        List<Map<String,Object>> trackedEntityData = new ArrayList<>();

        List<Entity> tracked = getEntities(controllerID);

        for(Entity trackedEntity: tracked)
        {

            Map<String, Object> entityInf = new HashMap<>();
            Vec3 pos = trackedEntity.position();

            entityInf.put("x", pos.x);

            entityInf.put("y", pos.y);

            entityInf.put("z", pos.z);

            entityInf.put("name", trackedEntity.getName().getString());

            entityInf.put("memberID", trackedEntity.getPersistentData().getString("controlChipMember"));

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
    public void suspend(String controllerID)
    {
        List<Entity> tracked = getEntities(controllerID);
        tracked.forEach(entity -> {
            if(entity instanceof Mob target)
            {
                target.setNoAi(true);
            }
        });
    }

    @LuaFunction
    public void reanimate(String controllerID)
    {
        List<Entity> tracked = getEntities(controllerID);
        tracked.forEach(entity -> {
            if(entity instanceof Mob target)
            {
                target.setNoAi(false);
            }
        });
    }

    @LuaFunction
    public void addMeleeIntentByType(String controllerID, String targetMob, int priority)
    {


        EntityType<?> targetType;
        List<Entity> tracked = getEntities(controllerID);

        if(EntityType.byString(targetMob).isPresent())
        {
            targetType = EntityType.byString(targetMob).get();
        } else {
            return;
        }

        tracked.forEach(entity -> {
            if(entity instanceof PathfinderMob mob)
            {
                mob.targetSelector.addGoal(priority, new EntityTargetGoal(mob, targetType));
                mob.goalSelector.addGoal(priority,new MeleeAttackGoal(mob, 1, true));
            }
        });
    }

    @LuaFunction
    public void addMeleeIntentByName(String controllerID, String targetName, int priority)
    {;
        List<Entity> tracked = getEntities(controllerID);

        tracked.forEach(entity -> {
            if(entity instanceof PathfinderMob mob)
            {
                mob.targetSelector.addGoal(priority, new EntityNameTargetGoal(mob, targetName));
                mob.goalSelector.addGoal(priority,new MeleeAttackGoal(mob, 1, true));
            }
        });
    }

    @LuaFunction
    public void addMoveIntent(String controllerID, double x, double y, double z)
    {
        List<Entity> tracked = getEntities(controllerID);

        tracked.forEach(entity -> {
            if(entity instanceof PathfinderMob mob)
            {
                mob.goalSelector.addGoal(0, new GotoPositionGoal(mob, x,y,z, 1));
            }
        });
    }

    @LuaFunction
    public void removeIntents(String controllerID)
    {
        List<Entity> tracked = getEntities(controllerID);

        tracked.forEach(entity -> {
            if(entity instanceof PathfinderMob mob)
            {
                mob.goalSelector.removeAllGoals(Objects::nonNull);
            }
        });
    }

    @LuaFunction
    public void terminate(String controllerID)
    {
        List<Entity> tracked = getEntities(controllerID);
        tracked.forEach(Entity::kill);
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }
}
