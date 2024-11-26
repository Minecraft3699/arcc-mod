package net.mc3699.arcc.peripheral;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.mc3699.arcc.block.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;

public class ModPeripheralProviders implements IPeripheralProvider {
    @Override
    public LazyOptional<IPeripheral> getPeripheral(Level level, BlockPos blockPos, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof ARControllerBlockEntity)
        {
            return LazyOptional.of(() -> ((ARControllerBlockEntity) blockEntity).getPeripheral());
        }

        if(blockEntity instanceof ARTerminalBlockEntity)
        {
            return LazyOptional.of(() -> ((ARTerminalBlockEntity) blockEntity).getPeripheral());
        }

        if(blockEntity instanceof CellularAPBlockEntity)
        {
            return LazyOptional.of(() -> ((CellularAPBlockEntity) blockEntity).getPeripheral());
        }

        if(blockEntity instanceof EntityTrackerBlockEntity)
        {
            return LazyOptional.of( () -> ((EntityTrackerBlockEntity) blockEntity).getPeripheral());
        }

        if(blockEntity instanceof RedstoneModemBlockEntity)
        {
            return LazyOptional.of( () -> ((RedstoneModemBlockEntity) blockEntity).getPeripheral());
        }

        if(blockEntity instanceof EntityControllerBlockEntity)
        {
            return  LazyOptional.of( () -> ((EntityControllerBlockEntity) blockEntity).getPeripheral());
        }

        if(blockEntity instanceof HUDBlockEntity)
        {
            return LazyOptional.of(() -> ((HUDBlockEntity) blockEntity).getPeripheral());
        }


        return LazyOptional.empty();
    }
}
