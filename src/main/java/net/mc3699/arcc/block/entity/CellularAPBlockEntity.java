package net.mc3699.arcc.block.entity;

import dan200.computercraft.api.peripheral.IComputerAccess;
import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.capabilities.ModCapabilities;
import net.mc3699.arcc.network.ModNetworking;
import net.mc3699.arcc.peripheral.ARTerminalPeripheral;
import net.mc3699.arcc.peripheral.CellularAPPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class CellularAPBlockEntity extends BlockEntity {


    private final CellularAPPeripheral peripheral;
    private final LazyOptional peripheralLazyOptional;
    public int phoneNumberAssign;

    public CellularAPBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CELLULAR_AP_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.peripheral = new CellularAPPeripheral(this);
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

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("phone_assignment", phoneNumberAssign);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        phoneNumberAssign = pTag.getInt("phone_assignment");
        super.load(pTag);
    }

    public CellularAPPeripheral getPeripheral() {
        return this.peripheral;
    }
}
