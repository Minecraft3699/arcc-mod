package net.mc3699.arcc.network;

import net.mc3699.arcc.network.TextElement;
import net.mc3699.arcc.overlay.AugmentedHudOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModNetworking {

    // The single instance for the Singleton pattern
    private static ModNetworking instance;

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("arcc", "network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    // Private constructor for Singleton pattern
    private ModNetworking() {
        // Register the messages when the instance is created
        register();
    }

    // Static method to return the Singleton instance
    public static ModNetworking getInstance() {
        if (instance == null) {
            instance = new ModNetworking();
        }
        return instance;
    }

    // Registration of messages
    public static void register() {
        INSTANCE_CHANNEL.registerMessage(0, TextPacket.class, TextPacket::encode, TextPacket::decode, TextPacket::handle);
        INSTANCE_CHANNEL.registerMessage(1, ClearTextPacket.class, ClearTextPacket::encode, ClearTextPacket::decode, ClearTextPacket::handle);
        INSTANCE_CHANNEL.registerMessage(2, RemoteBlockClickPacket.class, RemoteBlockClickPacket::encode,RemoteBlockClickPacket::decode, RemoteBlockClickPacket::handle);
        INSTANCE_CHANNEL.registerMessage(3, OpenComputerGuiPacket.class, OpenComputerGuiPacket::encode, OpenComputerGuiPacket::decode, OpenComputerGuiPacket::handle);
        INSTANCE_CHANNEL.registerMessage(4, PagerButtonPressPacket.class, PagerButtonPressPacket::encode, PagerButtonPressPacket::decode, PagerButtonPressPacket::handle);
        INSTANCE_CHANNEL.registerMessage(5, SpawnChipItemPacket.class, SpawnChipItemPacket::encode, SpawnChipItemPacket::decode, SpawnChipItemPacket::handle);
    }


    public void openComputerGUI(BlockPos pos)
    {
        INSTANCE_CHANNEL.sendToServer(new OpenComputerGuiPacket(pos));
    }

    // Method to send a text packet to the player
    public void sendTextToPlayer(ServerPlayer player, String text, int x, int y, int color) {
        INSTANCE_CHANNEL.sendTo(new TextPacket(text, x, y, color), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    // Method to clear HUD text for the player
    public void clearHudTextForPlayer(ServerPlayer player) {
        INSTANCE_CHANNEL.sendTo(new ClearTextPacket(), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    // Inner class TextPacket to add a new text element
    public static class TextPacket {
        private final String text;
        private final int x;
        private final int y;
        private int color;
        private int size;

        public TextPacket(String text, int x, int y, int color) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
            this.size = size;
        }

        public TextPacket(FriendlyByteBuf buf) {
            this.text = buf.readUtf(32767);
            this.x = buf.readInt();
            this.y = buf.readInt();
            this.color = buf.readInt();
            this.size = size;
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeUtf(text);
            buf.writeInt(x);
            buf.writeInt(y);
            buf.writeInt(color);
            buf.writeInt(size);
        }

        public static TextPacket decode(FriendlyByteBuf buf) {
            return new TextPacket(buf);
        }

        public static void handle(TextPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().execute(() -> {
                    AugmentedHudOverlay.addDisplayText(new TextElement(msg.text, msg.x, msg.y, msg.color, msg.size));
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }

    // Inner class ClearTextPacket to clear all text elements
    public static class ClearTextPacket {
        public ClearTextPacket() {}

        public ClearTextPacket(FriendlyByteBuf buf) {}

        public void encode(FriendlyByteBuf buf) {
            // No data to encode
        }

        public static ClearTextPacket decode(FriendlyByteBuf buf) {
            return new ClearTextPacket();
        }

        public static void handle(ClearTextPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().execute(() -> {
                    AugmentedHudOverlay.clearDisplay();
                });
            });
            ctx.get().setPacketHandled(true);
        }

    }

}
