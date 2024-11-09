package net.mc3699.arcc.block.entity;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.capabilities.ModCapabilities;
import net.mc3699.arcc.network.ModNetworking;
import net.mc3699.arcc.peripheral.ARControllerPeripheral;
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

public class ARControllerBlockEntity extends BlockEntity {

    private final ARControllerPeripheral peripheral;
    public String controllerID;
    private LazyOptional<IPeripheral> peripheralLazyOptional;

    public ARControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AR_CONTROLLER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.peripheral = new ARControllerPeripheral(this, ModNetworking.getInstance());
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
        pTag.putString("ar_controller.id", controllerID);
        super.saveAdditional(pTag);
    }

    public void setInterfaceID(String interfaceID)
    {
        controllerID = interfaceID;
    }

    public String getInterfaceID()
    {
        return controllerID;
    }

    @Override
    public void load(CompoundTag pTag) {
        controllerID = pTag.getString("ar_controller.id");
        super.load(pTag);
    }

    public ARControllerPeripheral getPeripheral() {
        return this.peripheral;
    }
}
