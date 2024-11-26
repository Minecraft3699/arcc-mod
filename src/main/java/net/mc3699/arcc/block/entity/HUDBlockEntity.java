package net.mc3699.arcc.block.entity;

import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.capabilities.ModCapabilities;
import net.mc3699.arcc.client.renderer.HUDTextElement;
import net.mc3699.arcc.peripheral.HUDPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HUDBlockEntity extends BlockEntity {

    private final List<HUDTextElement> elementList = new ArrayList<>();

    private final HUDPeripheral peripheral;
    private final LazyOptional peripheralLazyOptional;

    public HUDBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HUD_BLOCK_ENIITY.get(), pPos, pBlockState);
        this.peripheral = new HUDPeripheral(this);
        this.peripheralLazyOptional = LazyOptional.of(() -> this.peripheral);
    }

    public void addTextElement(String text, float x, float y, int color)
    {
        elementList.add(new HUDTextElement(x, y, color, text));
    }

    public void clearElements()
    {
        elementList.clear();
    }

    public List<HUDTextElement> getElementList() {
        return elementList;
    }

    public HUDPeripheral getPeripheral() {
        return peripheral;
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
}
