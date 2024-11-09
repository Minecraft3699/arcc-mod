package net.mc3699.arcc.network;

import net.mc3699.arcc.block.entity.CellularAPBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PagerButtonPressPacket {
    private final BlockPos pos;
    private final int buttonID;

    public PagerButtonPressPacket(BlockPos pos, int buttonID)
    {
        this.pos = pos;
        this.buttonID = buttonID;
    }

    public static void encode(PagerButtonPressPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.buttonID);
    }

    public static PagerButtonPressPacket decode(FriendlyByteBuf buf) {
        return new PagerButtonPressPacket(buf.readBlockPos(), buf.readInt());
    }

    public static void handle(PagerButtonPressPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ServerLevel serverLevel = player.getServer().getLevel(player.level().dimension());
                BlockEntity cellBlockEntity = serverLevel.getBlockEntity(msg.pos);

                if (cellBlockEntity instanceof CellularAPBlockEntity cellAPPeripheral) {
                    cellAPPeripheral.getPeripheral().sendEventToConnectedComputers(
                            "pager_button_press", msg.buttonID
                    );
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
