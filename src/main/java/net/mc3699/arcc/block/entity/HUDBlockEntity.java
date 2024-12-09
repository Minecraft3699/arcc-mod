package net.mc3699.arcc.block.entity;

import net.mc3699.arcc.block.ModBlockEntities;
import net.mc3699.arcc.capabilities.ModCapabilities;
import net.mc3699.arcc.client.renderer.HUDTextElement;
import net.mc3699.arcc.peripheral.HUDPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
        this.elementList.add(new HUDTextElement(x, y, color, text));
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public void clearElements()
    {
        this.elementList.clear();
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public List<HUDTextElement> getElementList() {
        return this.elementList;
    }

    public HUDPeripheral getPeripheral() {
        return this.peripheral;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        ListTag elementListTag = new ListTag();
        for(HUDTextElement element : elementList)
        {
            CompoundTag elementTag = new CompoundTag();
            elementTag.putString("text",element.getText());
            elementTag.putFloat("x",element.getXPos());
            elementTag.putFloat("y",element.getYPos());
            elementTag.putInt("color",element.getColor());
            elementListTag.add(elementTag);
        }

        pTag.put("textelements",elementListTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        this.elementList.clear();

        if(pTag.contains("textelements", ListTag.TAG_LIST))
        {
            ListTag textListTag = pTag.getList("textelements", ListTag.TAG_COMPOUND);
            for(int i = 0; i < textListTag.size(); i++)
            {
                CompoundTag elementTag = textListTag.getCompound(i);
                HUDTextElement element = new HUDTextElement(
                        elementTag.getInt("x"),
                        elementTag.getInt("y"),
                        elementTag.getInt("color"),
                        elementTag.getString("text"));
                this.elementList.add(element);
            }
        }
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
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
