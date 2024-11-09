package net.mc3699.arcc.item;

import net.mc3699.arcc.client.ClientHooks;
import net.mc3699.arcc.entity.ModEntities;
import net.mc3699.arcc.entity.TrackingArrowEntity;
import net.mc3699.arcc.gui.TrackingArrowGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TrackingArrowItem extends ArrowItem {

    public TrackingArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        CompoundTag tag = pStack.getOrCreateTag();
        tag.putString("trackerID", "Default");
        pStack.setTag(tag);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String trackerChannelString = "DEFAULT";

        if(pStack.hasTag())
        {
            if(pStack.getTag().contains("trackerID"))
            {
                trackerChannelString = pStack.getTag().getString("trackerID");
            }

            pTooltipComponents.add(Component.literal("§4Tracker Channel: §f"+trackerChannelString));
        } else {
            pTooltipComponents.add(Component.literal("§4[Right Click] to set channel."));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide())
        {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openTrackingArrowGUI(pPlayer));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public @NotNull AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        return new TrackingArrowEntity(ModEntities.TRACKING_ARROW.get(), pShooter, pLevel, pStack.getTag().getString("trackerID"));
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        return false;
    }

}
