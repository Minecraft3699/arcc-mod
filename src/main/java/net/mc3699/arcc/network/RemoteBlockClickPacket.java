package net.mc3699.arcc.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3d;

import java.util.function.Supplier;

public class RemoteBlockClickPacket {
    private final BlockPos blockPos;
    private final InteractionHand hand;

    public RemoteBlockClickPacket(BlockPos blockPos, InteractionHand hand) {
        this.blockPos = blockPos;
        this.hand = hand;
    }

    public RemoteBlockClickPacket(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
        this.hand = buf.readEnum(InteractionHand.class);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeEnum(hand);
    }

    public static RemoteBlockClickPacket decode(FriendlyByteBuf buf) {
        return new RemoteBlockClickPacket(buf);
    }

    public static void handle(RemoteBlockClickPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                Level world = player.level();
                BlockHitResult hitResult = new BlockHitResult(player.position(), player.getDirection(), msg.blockPos, false);

                // Simulate the right-click action
                world.getBlockState(msg.blockPos).use(world, player, msg.hand, hitResult);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
