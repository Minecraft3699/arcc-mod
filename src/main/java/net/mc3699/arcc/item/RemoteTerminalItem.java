package net.mc3699.arcc.item;
import dan200.computercraft.ComputerCraft;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.ComputerCraftTags;
import dan200.computercraft.client.gui.ComputerScreen;
import dan200.computercraft.core.apis.ComputerAccess;
import dan200.computercraft.impl.ComputerCraftAPIImpl;
import dan200.computercraft.impl.ComputerCraftAPIService;
import dan200.computercraft.shared.ModRegistry;
import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import dan200.computercraft.shared.computer.blocks.ComputerBlock;
import dan200.computercraft.shared.computer.blocks.ComputerBlockEntity;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.computer.core.ServerComputerRegistry;
import dan200.computercraft.shared.computer.inventory.ComputerMenuWithoutInventory;
import dan200.computercraft.shared.computer.menu.ComputerMenu;
import dan200.computercraft.shared.network.container.ComputerContainerData;
import net.mc3699.arcc.network.ModNetworking;
import net.mc3699.arcc.network.OpenComputerGuiPacket;
import net.mc3699.arcc.network.RemoteBlockClickPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RemoteTerminalItem extends Item {

    public RemoteTerminalItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {


        if(pStack.hasTag())
        {
            int computerID = pStack.getTag().getInt("computer_id");
            pTooltipComponents.add(Component.literal("§6Controlling: "+computerID));
            pTooltipComponents.add(Component.literal("§7Right click an [Advanced Computer] to link!"));
        } else {
            pTooltipComponents.add(Component.literal("§6No computer linked"));
            pTooltipComponents.add(Component.literal("§7Right click an [Advanced Computer] to link!"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        BlockPos clickedBlock = pContext.getClickedPos();
        ItemStack heldItem = pContext.getItemInHand();

        if(pContext.getLevel().getBlockEntity(clickedBlock) instanceof ComputerBlockEntity)
        {
            ComputerBlockEntity clickedComputer = (ComputerBlockEntity) pContext.getLevel().getBlockEntity(clickedBlock);
            int clickedComputerID = clickedComputer.getComputerID();
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Computer ID: "+clickedComputerID));
            heldItem.getOrCreateTag().putInt("computer_id", clickedComputerID);

            heldItem.getOrCreateTag().putInt("computer_x", clickedBlock.getX());
            heldItem.getOrCreateTag().putInt("computer_y", clickedBlock.getY());
            heldItem.getOrCreateTag().putInt("computer_z", clickedBlock.getZ());

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide())
        {
            ItemStack heldItem = pPlayer.getItemInHand(pUsedHand);
            int computerX = heldItem.getOrCreateTag().getInt("computer_x");
            int computerY = heldItem.getOrCreateTag().getInt("computer_y");
            int computerZ = heldItem.getOrCreateTag().getInt("computer_z");
            //Minecraft.getInstance().player.sendSystemMessage(Component.literal("X:"+computerX+" Y:"+computerY+" Z:"+computerZ));

            BlockPos computerPos = new BlockPos(computerX,computerY,computerZ);
            AbstractComputerBlockEntity targetComputer = (AbstractComputerBlockEntity) pLevel.getBlockEntity(computerPos);

            ServerComputer serverComputer = targetComputer.createServerComputer();
            serverComputer.turnOn();

            ItemStack termItem = new ItemStack(ModItems.REMOTE_TERMINAL.get(), 1);
            (new ComputerContainerData(serverComputer, termItem)).open(pPlayer, targetComputer);
            return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), true);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
