package net.mc3699.arcc.entity;

import net.mc3699.arcc.item.ModItems;
import net.mc3699.arcc.item.TrackingArrowItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class TrackingArrowEntity extends AbstractArrow {

    String trackerID;

    public TrackingArrowEntity(EntityType<? extends AbstractArrow> pEntityType, LivingEntity pShooter, Level pLevel, String trackID) {
        super(pEntityType, pShooter, pLevel);
        trackerID = trackID;
    }

    public TrackingArrowEntity(EntityType<TrackingArrowEntity> trackingArrowEntityEntityType, Level level) {
        super(trackingArrowEntityEntityType, level);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        level().playSound(null, position().x,position().y,position().z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 3.0f, 1.0f);
        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(!level().isClientSide())
        {
            if(pResult.getEntity().isAlive())
            {
                if(!pResult.getEntity().getPersistentData().contains("trackerChannel") && !pResult.getEntity().getPersistentData().contains("trackerTimer"))
                {
                    pResult.getEntity().getPersistentData().putString("trackerChannel", trackerID);
                    pResult.getEntity().getPersistentData().putInt("trackerTimer", 3600*20);
                }
            }
            super.onHitEntity(pResult);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        ItemStack trackingArrowItemStack = new ItemStack(ModItems.TRACKING_ARROW.get(), 1);
        CompoundTag dropItemTags = trackingArrowItemStack.getOrCreateTag();
        dropItemTags.putString("trackerID", trackerID);
        return trackingArrowItemStack;
    }
}
