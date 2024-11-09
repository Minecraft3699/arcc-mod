package net.mc3699.arcc.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EntityChipItem extends Item {

    public EntityChipItem(Properties pProperties) {
        super(pProperties);
    }



    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {


        if(pTarget instanceof PathfinderMob)
        {
            if(pStack.hasTag())
            {
                assert pStack.getTag() != null;
                if(pStack.getTag().contains("controlChipGroup") && pStack.getTag().contains("controlChipMember"))
                {
                    pTarget.getPersistentData().putString("controlChipGroup", pStack.getTag().getString("controlChipGroup"));
                    pTarget.getPersistentData().putString("controlChipMember", pStack.getTag().getString("controlChipMember"));
                    pStack.shrink(1);
                }
            }
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String controlGroupString = "NO DATA";
        String controlMemberString = "NO DATA";

        if(pStack.hasTag())
        {
            if(pStack.getTag().contains("controlChipGroup"))
            {
                controlGroupString = pStack.getTag().getString("controlChipGroup");
            }

            if(pStack.getTag().contains("controlChipMember"))
            {
                controlMemberString = pStack.getTag().getString("controlChipMember");
            }

            pTooltipComponents.add(Component.literal("§5 Group ID: §f"+controlGroupString));
            pTooltipComponents.add(Component.literal("§5 Individual ID: §f"+controlMemberString));
        } else {
            pTooltipComponents.add(Component.literal("§4Configuration Error, please re-craft."));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }



}
