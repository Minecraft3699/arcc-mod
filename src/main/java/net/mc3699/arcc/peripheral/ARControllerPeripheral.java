package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.block.entity.ARControllerBlockEntity;
import net.mc3699.arcc.item.ARHeadset;
import net.mc3699.arcc.network.ModNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

public class ARControllerPeripheral implements IPeripheral
{
    private final ModNetworking network;
    private final ARControllerBlockEntity blockEntity;

    public ARControllerPeripheral(ARControllerBlockEntity blockEntity,ModNetworking network) {
        this.blockEntity = blockEntity;
        this.network = network;
    }


    @LuaFunction
    public final boolean addText(String text, int x, int y, int color)
    {
        ServerPlayer player = null;

        for(ServerPlayer iterPlayer : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
        {
            if(iterPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ARHeadset)
            {
                CompoundTag headsetTag = iterPlayer.getItemBySlot(EquipmentSlot.HEAD).getTag();
                String testID = headsetTag.getString("controller_id");
                if(testID.equals(blockEntity.controllerID))
                {
                    player = iterPlayer;
                    break;
                }
            }
        }

        if(player != null)
        {
            network.sendTextToPlayer(player, text, x, y, color);
            return true;
        } else {
            return false;
        }
    }

    @LuaFunction
    public final boolean clearDisplay()
    {
        ServerPlayer player = null;

        for(ServerPlayer iterPlayer : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
        {
                if(iterPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ARHeadset)
                {
                    CompoundTag headsetTag = iterPlayer.getItemBySlot(EquipmentSlot.HEAD).getTag();
                    String testID = headsetTag.getString("controller_id");
                    if(testID.equals(blockEntity.controllerID))
                    {
                        player = iterPlayer;
                        break;
                    }
                }
        }


        if(player != null)
        {
            network.clearHudTextForPlayer(player);
            return true;
        } else {
            return false;
        }
    }

    @LuaFunction
    public final void setID(String id)
    {
        blockEntity.setInterfaceID(id);
    }

    @LuaFunction
    public final Object[] getPlayerData()
    {
        ServerPlayer player = null;

        for(ServerPlayer iterPlayer : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
        {
            if(iterPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ARHeadset)
            {
                CompoundTag headsetTag = iterPlayer.getItemBySlot(EquipmentSlot.HEAD).getTag();
                String testID = headsetTag.getString("controller_id");
                if(testID.equals(blockEntity.controllerID))
                {
                    player = iterPlayer;
                    break;
                }
            }
        }

        if(player != null)
        {
            return new Object[] {
                    true,
                    player.position().x,
                    player.position().y,
                    player.position().z,
                    (player.getYRot() + 360) % 360,
                    player.getXRot()
            };
        } else {
            return new Object[] {false};
        }
    }

    @Override
    public String getType() {
        return "ar_interface";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }
}
