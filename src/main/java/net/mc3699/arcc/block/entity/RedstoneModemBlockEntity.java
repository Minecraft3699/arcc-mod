package net.mc3699.arcc.block.entity;

import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.capabilities.ModCapabilities;
import net.mc3699.arcc.network.ModNetworking;
import net.mc3699.arcc.peripheral.ARTerminalPeripheral;
import net.mc3699.arcc.peripheral.RedstoneModemPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class RedstoneModemBlockEntity extends BlockEntity {
    private final Map<Direction, Boolean> redstoneOutputs = new EnumMap<>(Direction.class);
    private final RedstoneModemPeripheral peripheral;
    private final LazyOptional peripheralLazyOptional;

    public RedstoneModemBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AR_TERMINAL_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.peripheral = new RedstoneModemPeripheral(this);
        this.peripheralLazyOptional = LazyOptional.of(() -> this.peripheral);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ModCapabilities.PERIPHERAL_CAPABILITY)
        {
            return peripheralLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        peripheralLazyOptional.invalidate();
    }

    public int getRedstoneOutput(Direction direction)
    {
        return redstoneOutputs.getOrDefault(direction, false) ? 15 :0;
    }

    public void setRedstoneOutput(Direction direction, Boolean output)
    {
        redstoneOutputs.put(direction,output);
        if(level != null)
        {
            level.updateNeighborsAt(worldPosition, this.getBlockState().getBlock());
            level.updateNeighborsAt(worldPosition.relative(direction), this.getBlockState().getBlock());
        }
    }


    public RedstoneModemPeripheral getPeripheral() {
        return this.peripheral;
    }



}
