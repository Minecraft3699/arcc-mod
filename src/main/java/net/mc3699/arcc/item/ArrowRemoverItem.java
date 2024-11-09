package net.mc3699.arcc.item;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ArrowRemoverItem extends Item {
    public ArrowRemoverItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
            if(!pLevel.isClientSide() && pTimeCharged < 280)
            {

                if(pLivingEntity.getPersistentData().contains("trackerChannel"))
                {
                    pLivingEntity.level().playSound(null, pLivingEntity.blockPosition(), SoundEvents.ARROW_HIT, SoundSource.PLAYERS, 2.0f, 1.5f);
                    ItemStack arrowStack = new ItemStack(ModItems.TRACKING_ARROW.get(), 1);
                    CompoundTag arrowTags = arrowStack.getOrCreateTag();
                    arrowTags.putString("trackerID", pLivingEntity.getPersistentData().getString("trackerChannel"));
                    ItemEntity arrowItemDrop = new ItemEntity(
                            pLevel,
                            pLivingEntity.position().x,
                            pLivingEntity.position().y + 0.5,
                            pLivingEntity.position().z,
                            arrowStack
                    );

                    arrowItemDrop.setPickUpDelay(10);
                    pLevel.addFreshEntity(arrowItemDrop);
                    pLivingEntity.getPersistentData().remove("trackerChannel");
                    pLivingEntity.getPersistentData().remove("trackerTimer");

                    pStack.hurtAndBreak(1, pLivingEntity, livingEntity -> {
                        livingEntity.broadcastBreakEvent(livingEntity.getUsedItemHand());
                    });
                }
            }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        Level pLevel = pTarget.level();

        if(!pLevel.isClientSide())
        {
            if(pTarget.getPersistentData().contains("trackerChannel"))
            {
                pTarget.level().playSound(null, pTarget.blockPosition(), SoundEvents.ARROW_HIT, SoundSource.PLAYERS, 2.0f, 1.5f);
                ItemStack arrowStack = new ItemStack(ModItems.TRACKING_ARROW.get(), 1);
                CompoundTag arrowTags = arrowStack.getOrCreateTag();
                arrowTags.putString("trackerID", pTarget.getPersistentData().getString("trackerChannel"));
                ItemEntity arrowItemDrop = new ItemEntity(
                        pLevel,
                        pTarget.position().x,
                        pTarget.position().y + 0.5,
                        pTarget.position().z,
                        arrowStack
                );

                arrowItemDrop.setPickUpDelay(10);
                pLevel.addFreshEntity(arrowItemDrop);
                pTarget.getPersistentData().remove("trackerChannel");
                pTarget.getPersistentData().remove("trackerTimer");

                pStack.hurtAndBreak(1, pTarget, livingEntity -> {
                    livingEntity.broadcastBreakEvent(livingEntity.getUsedItemHand());
                });
                return true;
            }
        }
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 300;
    }
}
