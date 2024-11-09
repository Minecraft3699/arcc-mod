package net.mc3699.arcc.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class EntityTargetGoal extends TargetGoal {

    private final Mob mob;
    private final EntityType<?> targetType;
    private final TargetingConditions targetingConditions;
    private LivingEntity target;


    public EntityTargetGoal(Mob mob, EntityType<?> targetType) {
        super(mob, false);
        this.mob = mob;
        this.targetType = targetType;
        this.targetingConditions = TargetingConditions.forCombat().selector(livingEntity -> livingEntity.getType() == this.targetType);
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
