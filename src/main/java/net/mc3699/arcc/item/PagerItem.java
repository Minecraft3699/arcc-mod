package net.mc3699.arcc.item;

import dan200.computercraft.api.ForgeComputerCraftAPI;
import dan200.computercraft.impl.ComputerCraftAPIImpl;
import dan200.computercraft.shared.computer.core.ServerComputerRegistry;
import net.mc3699.arcc.client.ClientHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PagerItem extends Item {

    String messageLine1 = "";
    String messageLine2 = "";
    String messageLine3 = "";
    String messageLine4 = "";

    int CellX = 0;
    int CellY = 0;
    int CellZ = 0;

    int PhoneNumber = 0;
    int connectedComputer = -1;

    public String getMessageLine1() {
        return messageLine1;
    }

    public String getMessageLine2() {
        return messageLine2;
    }

    public String getMessageLine3() {
        return messageLine3;
    }

    public String getMessageLine4() {
        return messageLine4;
    }

    public int getCellX() {
        return CellX;
    }

    public int getCellY() {
        return CellY;
    }

    public int getCellZ() {
        return CellZ;
    }

    public int getConnectedComputer() {
        return connectedComputer;
    }

    public PagerItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        CompoundTag tag = pStack.getOrCreateTag();
        if(tag.contains("message_line_1")) {messageLine1 = tag.getString("message_line_1");}
        if(tag.contains("message_line_2")) {messageLine2 = tag.getString("message_line_2");}
        if(tag.contains("message_line_3")) {messageLine3 = tag.getString("message_line_3");}
        if(tag.contains("message_line_4")) {messageLine4 = tag.getString("message_line_4");}

        if(tag.contains("cell_x")) {CellX = tag.getInt("cell_x");}
        if(tag.contains("cell_y")) {CellY = tag.getInt("cell_y");}
        if(tag.contains("cell_z")) {CellZ = tag.getInt("cell_z");}

        if(tag.contains("phone_number")) {PhoneNumber = tag.getInt("phone_number");}
        if(tag.contains("computer_id")) {connectedComputer = tag.getInt("computer_id");}
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide())
        {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openPagerGUI(pPlayer, this));
        }        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openPagerGUI(pPlayer, this));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(pStack.hasTag())
        {
            int phoneNumber = pStack.getTag().getInt("phone_number");
            String message = pStack.getTag().getString("message_line_1");
            pTooltipComponents.add(Component.literal("§6Phone Number: "+phoneNumber));
            pTooltipComponents.add(Component.literal("Message: "+message+"..."));
        } else {
            pTooltipComponents.add(Component.literal("§aThis pager hasn't been set up yet."));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
