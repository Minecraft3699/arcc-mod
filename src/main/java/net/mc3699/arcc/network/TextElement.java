package net.mc3699.arcc.network;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

public class TextElement {
    private final String text;
    private final int x;
    private final int y;
    private final int color;
    private final int size;

    public TextElement(String text, int x, int y, int color, int size) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }

    public String getText()
    {
        return text;
    }

    public int getX()
    {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor()
    {
        return color;
    }

    public int getSize()
    {
        return size;
    }

}
