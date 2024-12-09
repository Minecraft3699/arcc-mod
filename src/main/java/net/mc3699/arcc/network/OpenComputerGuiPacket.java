package net.mc3699.arcc.network;

import dan200.computercraft.shared.computer.blocks.ComputerBlockEntity;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.network.container.ComputerContainerData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class OpenComputerGuiPacket {
    private final BlockPos pos;

    public OpenComputerGuiPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(OpenComputerGuiPacket packet, FriendlyByteBuf buf) {
        buf.writeBlockPos(packet.pos);
    }

    public static OpenComputerGuiPacket decode(FriendlyByteBuf buf) {
        return new OpenComputerGuiPacket(buf.readBlockPos());
    }

    public static void handle(OpenComputerGuiPacket packet, @NotNull Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerLevel level = context.get().getSender().serverLevel();
            BlockEntity blockEntity = level.getBlockEntity(packet.pos);
            if (blockEntity instanceof ComputerBlockEntity computerBlock) {
                ServerComputer computer = computerBlock.getServerComputer();
                if (computer != null) {
                    //computerBlock.createMenu(computer.getID(), context.get().getSender().getInventory(), context.get().getSender());
                    (new ComputerContainerData(computer, null)).open(context.get().getSender(), null);
                    level.setBlock(packet.pos.above(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
