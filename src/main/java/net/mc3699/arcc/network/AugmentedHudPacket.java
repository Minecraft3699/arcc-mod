package net.mc3699.arcc.network;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mc3699.arcc.network.TextElement;
import net.mc3699.arcc.overlay.AugmentedHudOverlay;
import net.mc3699.arcc.overlay.AugmentedTerminalOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AugmentedHudPacket {

    private static AugmentedTerminalOverlay currentTerminal;

    private final String text;
    private final int x;
    private final int y;
    private int color;
    private int size;

    // Constructor for creating the packet to send from server to client
    public AugmentedHudPacket(String text, int x, int y, int color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }

    // Constructor for decoding the packet when received
    public AugmentedHudPacket(FriendlyByteBuf buf) {
        this.text = buf.readUtf(32767); // Read the text from the buffer (32767 is the max string length)
        this.x = buf.readInt();         // Read the X coordinate
        this.y = buf.readInt();         // Read the Y coordinate\
        this.color = buf.readInt();
        this.size = buf.readInt();
    }

    // Method for encoding the packet before sending it
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.text); // Write the text to the buffer
        buf.writeInt(this.x);    // Write the X coordinate
        buf.writeInt(this.y);    // Write the Y coordinate
        buf.writeInt(this.color);
        buf.writeInt(this.size);
    }

    // Handling the packet when received on the client
    public static void handle(AugmentedHudPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Run on the client thread to update the HUD
            Minecraft.getInstance().execute(() -> {
                // Add the text element to the HUD overlay on the client side
                AugmentedHudOverlay.addDisplayText(new TextElement(msg.text, msg.x, msg.y, msg.color, msg.size));
            });
        });
        ctx.get().setPacketHandled(true); // Mark the packet as handled
    }

}
