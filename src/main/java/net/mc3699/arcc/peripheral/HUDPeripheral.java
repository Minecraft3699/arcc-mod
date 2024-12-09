package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.block.entity.HUDBlockEntity;
import org.jetbrains.annotations.Nullable;

public class HUDPeripheral implements IPeripheral {

    public final HUDBlockEntity blockEntity;

    public HUDPeripheral(HUDBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public String getType() {
        return "hud";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return false;
    }

    @LuaFunction
    public void addText(String text, double x, double y, int color)
    {
        this.blockEntity.addTextElement(text, (float) x, (float) y, color);
    }

    @LuaFunction
    public void clear()
    {
        this.blockEntity.clearElements();
    }

}
