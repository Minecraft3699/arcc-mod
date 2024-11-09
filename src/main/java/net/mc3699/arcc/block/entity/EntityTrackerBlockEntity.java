package net.mc3699.arcc.block.entity;

import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.capabilities.ModCapabilities;
import net.mc3699.arcc.peripheral.CellularAPPeripheral;
import net.mc3699.arcc.peripheral.EntityTrackerPeripheral;
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

public class EntityTrackerBlockEntity extends BlockEntity {


    String trackerAssignmentID;
    private final EntityTrackerPeripheral peripheral;
    private final LazyOptional peripheralLazyOptional;

    public EntityTrackerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ENTITY_TRACKER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.peripheral = new EntityTrackerPeripheral(this);
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

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putString("tracker_assignment", trackerAssignmentID);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        trackerAssignmentID = pTag.getString("tracker_assignment");
        super.load(pTag);
    }

    public EntityTrackerPeripheral getPeripheral() {
        return this.peripheral;
    }
}
