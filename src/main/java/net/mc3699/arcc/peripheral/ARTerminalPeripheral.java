package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.network.ModNetworking;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class ARTerminalPeripheral implements IPeripheral {

    private final BlockEntity blockEntity;
    private final ModNetworking networking;

    public ARTerminalPeripheral(BlockEntity blockEntity, ModNetworking networking) {
        this.blockEntity = blockEntity;
        this.networking = networking;
    }

    @Override
    public String getType() {
        return "ar_terminal";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }
}
