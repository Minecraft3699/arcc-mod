package net.mc3699.arcc.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class EntityNameTargetGoal extends TargetGoal {

    private final Mob mob;
    private final String targetName;
    private final TargetingConditions targetingConditions;
    private LivingEntity target;


    public EntityNameTargetGoal(Mob mob, String targetName) {
        super(mob, false);
        this.mob = mob;
        this.targetName = targetName;
        this.targetingConditions = TargetingConditions.forCombat().selector(livingEntity -> livingEntity.getName().equals(Component.literal(targetName)));
    }


    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    @Override
    public boolean canUse() {

        this.target = this.mob.level().getNearestEntity(
                LivingEntity.class,
                targetingConditions,
                this.mob,
                this.mob.getX(),
                this.mob.getY(),
                this.mob.getZ(),
                this.mob.getBoundingBox().inflate(32)
        );
        return this.target != null;
    }
}
