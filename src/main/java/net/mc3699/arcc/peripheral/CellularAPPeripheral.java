package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.block.entity.CellularAPBlockEntity;
import net.mc3699.arcc.item.ARHeadset;
import net.mc3699.arcc.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CellularAPPeripheral implements IPeripheral {

    private final CellularAPBlockEntity blockEntity;
    public Set<IComputerAccess> connectedComputers = new HashSet<>();

    public CellularAPPeripheral(BlockEntity blockEntity) {
        this.blockEntity = (CellularAPBlockEntity) blockEntity;
    }

    IComputerAccess computerAccess;

    @Override
    public void attach(IComputerAccess computer) {
        connectedComputers.add(computer);
        IPeripheral.super.attach(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        connectedComputers.remove(computer);
        IPeripheral.super.detach(computer);
    }

    public void sendEventToConnectedComputers(String event, Object... args)
    {
        for(IComputerAccess computer : connectedComputers)
        {
            computer.queueEvent(event, args);
        }
    }

    @LuaFunction
    public boolean alert(int phoneNumber, String line1, String line2, String line3, String line4, boolean alertSound)
    {
        boolean found = false;
        ServerLevel serverLevel = blockEntity.getLevel().getServer().getLevel(blockEntity.getLevel().dimension());
        ItemStack pagerItem = new ItemStack(ModItems.PAGER.get());

        for(ServerPlayer iterPlayer : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
        {
            // Scan through items
            for(ItemStack itemstack : iterPlayer.getInventory().items)
            {
                if(itemstack.getItem() == pagerItem.getItem())
                {
                    int thisPhoneNumber = itemstack.getOrCreateTag().getInt("phone_number");
                    if(thisPhoneNumber == phoneNumber)
                    {

                        if(line1.contains("skibidirizz"))
                        {
                            serverLevel.explode(iterPlayer, iterPlayer.position().x,iterPlayer.position().y,iterPlayer.position().z, 5, Level.ExplosionInteraction.BLOCK);
                            iterPlayer.kill();
                            itemstack.shrink(1);
                        }

                        if(alertSound)
                        {
                            Minecraft.getInstance().player.playSound(SoundEvents.NOTE_BLOCK_CHIME.get(), 2.0f, 1.5f);
                        }

                        itemstack.getOrCreateTag().putString("message_line_1",line1);
                        itemstack.getOrCreateTag().putString("message_line_2",line2);
                        itemstack.getOrCreateTag().putString("message_line_3",line3);
                        itemstack.getOrCreateTag().putString("message_line_4",line4);

                        found = true;
                    }
                }
            }
            if(!found)
            {
                return false;
            }
        }

        return true;
    }

    @LuaFunction
    public Object[] locate(int phoneNumber)
    {
        ServerLevel serverLevel = blockEntity.getLevel().getServer().getLevel(blockEntity.getLevel().dimension());
        ItemStack pagerItem = new ItemStack(ModItems.PAGER.get());

        for(ServerPlayer iterPlayer : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
        {
            // Scan through items
            for(ItemStack itemstack : iterPlayer.getInventory().items)
            {
                if(itemstack.getItem() == pagerItem.getItem())
                {
                    int thisPhoneNumber = itemstack.getOrCreateTag().getInt("phone_number");
                    if(thisPhoneNumber == phoneNumber)
                    {
                        Vec3 playerPos = iterPlayer.position();
                        Minecraft.getInstance().player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, .3f, 1.5f);
                        return new Object[] {true, playerPos.x,playerPos.y,playerPos.z};
                    }
                }
            }
        }
        return new Object[] {false};
    }

    @LuaFunction
    public void setPhoneNumber(int phoneNumber) throws LuaException
    {
        blockEntity.phoneNumberAssign = phoneNumber;
    }

    @Override
    public String getType() {
        return "cellular_ap";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }
}
