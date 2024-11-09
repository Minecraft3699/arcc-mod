package net.mc3699.arcc.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.JTextComponent;
import java.util.List;

public class ARHeadset extends Item {

    public ARHeadset(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.hasTag())
        {
            String headsetID = pStack.getTag().getString("controller_id");
            pTooltipComponents.add(Component.literal("§6Assigned to: "+headsetID));
            pTooltipComponents.add(Component.literal("§7Right click an [AR Controller] to link!"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return armorType == EquipmentSlot.HEAD;
    }
}
