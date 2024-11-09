package net.mc3699.arcc.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class EntryMissileEntity extends Projectile {

    protected EntryMissileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public void tick() {
        level().addParticle(ParticleTypes.END_ROD, position().x, position().y, position().z, 0,0.6,0);
        level().addParticle(ParticleTypes.FLAME, position().x,position().y + 0.5, position().z, 0,0.3,0);

        move(MoverType.SELF, new Vec3(0,-2.5,0));

        if(onGround())
        {
            if(!level().isClientSide())
            {
                level().explode(null,position().x,position().y,position().z, 15, Level.ExplosionInteraction.BLOCK);
                LightningBolt newLightning = EntityType.LIGHTNING_BOLT.create(level());
                newLightning.moveTo(position().x,position().y,position().z);
                level().addFreshEntity(newLightning);
                kill();
            }
        }

        super.tick();



    }



    @Override
    protected void onHitBlock(BlockHitResult pResult) {


    }

    @Override
    protected void defineSynchedData() {

    }
}
