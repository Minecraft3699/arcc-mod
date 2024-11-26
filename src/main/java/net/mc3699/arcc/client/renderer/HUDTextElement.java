package net.mc3699.arcc.client.renderer;

public class HUDTextElement {

    private float xPos;
    private float yPos;
    private int color;
    private String text;

    public HUDTextElement(float x, float y, int color, String text)
    {
        this.xPos = x;
        this.yPos = y;
        this.color = color;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public int getColor() {
        return color;
    }
}
