package net.mc3699.arcc.block.entity;

import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.capabilities.ModCapabilities;
import net.mc3699.arcc.peripheral.EntityControllerPeripheral;
import net.mc3699.arcc.peripheral.EntityTrackerPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityControllerBlockEntity extends BlockEntity {

    private final EntityControllerPeripheral peripheral;
    private final LazyOptional peripheralLazyOptional;

    public EntityControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ENTITY_CONTROLLER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.peripheral = new EntityControllerPeripheral(this);
        this.peripheralLazyOptional = LazyOptional.of(() -> peripheral);
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

    public EntityControllerPeripheral getPeripheral() {
        return this.peripheral;
    }
}
