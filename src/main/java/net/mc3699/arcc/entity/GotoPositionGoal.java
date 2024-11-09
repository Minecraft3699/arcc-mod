package net.mc3699.arcc.entity;

import net.minecraft.core.Position;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;

public class GotoPositionGoal extends Goal {

    private final Mob mob;
    private final double targetX, targetY, targetZ;
    private final double speed;

    private Path path;

    public GotoPositionGoal(Mob mob, double targetX, double targetY, double targetZ, double speed) {
        this.mob = mob;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.speed = speed;
    }


    @Override
    public boolean canUse() {
        Position targetPos = new Position() {
            @Override
            public double x() {
                return targetX;
            }

            @Override
            public double y() {
                return targetY;
            }

            @Override
            public double z() {
                return targetZ;
            }
        };
        return !this.mob.position().closerThan(targetPos, 1.5D); // Within 2 blocks of the target
    }

    @Override
    public void start() {
        PathNavigation navigation = this.mob.getNavigation();
        this.path = navigation.createPath(targetX,targetY,targetZ,0);
        navigation.moveTo(path,speed);
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }
}
