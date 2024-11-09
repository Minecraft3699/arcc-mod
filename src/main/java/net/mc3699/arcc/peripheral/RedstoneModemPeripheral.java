package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.block.entity.RedstoneModemBlockEntity;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RedstoneModemPeripheral implements IPeripheral
{

    public final RedstoneModemBlockEntity blockEntity;

    public RedstoneModemPeripheral(RedstoneModemBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }


    private Direction getDirFromSide(String side)
    {
        BlockState state = blockEntity.getBlockState();
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

        switch(side.toLowerCase())
        {
            case "front":
                return facing;
            case "back":
                return facing.getOpposite();
            case "left":
                return facing.getCounterClockWise();
            case "right":
                return facing.getClockWise();
            case "top":
                return Direction.UP;
            case "bottom":
                return Direction.DOWN;
            default:
                return null;
        }
    }

    @LuaFunction
    public void setOutput(String side, boolean state) throws LuaException
    {
        Direction emitterSide = getDirFromSide(side);
        if(emitterSide == null)
        {
            throw new LuaException("Invalid side: "+side);
        }
        blockEntity.setRedstoneOutput(emitterSide, state);
    }

    @Override
    public String getType() {
        return "rs_modem";
    }



    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }
}
