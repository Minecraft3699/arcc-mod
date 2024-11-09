package net.mc3699.arcc.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.util.Arrays;

public class AugmentedTerminalOverlay {
    private final int width;
    private final int height;
    private final char[][] screenBuffer;
    private final int[][] colorBuffer;
    private int cursorX = 0;
    private int cursorY = 0;
    private int textColor = 0xFFFFFF; // Default white color

    public AugmentedTerminalOverlay(int width, int height) {
        this.width = width;
        this.height = height;
        this.screenBuffer = new char[width][height];
        this.colorBuffer = new int[width][height];
        clear();
    }

    public void clear() {
        for (int x = 0; x < width; x++) {
            Arrays.fill(screenBuffer[x], ' ');
            Arrays.fill(colorBuffer[x], textColor);
        }
    }

    public void write(String text) {
        for (char c : text.toCharArray()) {
            if (cursorX >= width) {
                cursorX = 0;
                cursorY++;
            }
            if (cursorY >= height) {
                cursorY = height - 1; // Avoid overflow
            }
            screenBuffer[cursorX][cursorY] = c;
            colorBuffer[cursorX][cursorY] = textColor;
            cursorX++;
        }
    }

    public void setCursorPos(int x, int y) {
        this.cursorX = x;
        this.cursorY = y;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void render(PoseStack poseStack) {
        Font font = Minecraft.getInstance().font;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                char character = screenBuffer[x][y];
                int color = colorBuffer[x][y];
                font.drawInBatch(
                        String.valueOf(character),                               // The character to render
                        x * 6,                                                   // X position
                        y * 10,                                                  // Y position
                        color,                                                   // Text color (hexadecimal)
                        false,                                                   // Drop shadow
                        poseStack.last().pose(),                                 // Matrix stack
                        Minecraft.getInstance().renderBuffers().bufferSource(),  // Buffer source
                        Font.DisplayMode.NORMAL,                                 // Display mode
                        0,                                                       // See-through effect, usually 0
                        15728880                                                  // Packed light (this is a default value)
                );
            }
        }
        // Ensure the text is flushed to the screen
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
    }
}
