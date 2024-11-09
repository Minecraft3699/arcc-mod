package net.mc3699.arcc.gui;

import net.mc3699.arcc.item.TrackingArrowItem;
import net.mc3699.arcc.network.TextElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TrackingArrowGUI extends Screen {

    private EditBox idInputField;
    private Button confirmButton;

    private String inputIDString = "";

    private Object onConfirm;
    private Player interactingPlayer;

    public TrackingArrowGUI(Component pTitle, Player player) {
        super(pTitle);
        interactingPlayer = player;
    }

    @Override
    protected void init() {
        this.minecraft.pauseGame(false);
        this.idInputField = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 20, 200, 20, Component.literal("Set ID: "));
        this.idInputField.setMaxLength(30);
        this.idInputField.setResponder(this::onTextChange);
        this.addRenderableWidget(this.idInputField);

        this.confirmButton = Button.builder(Component.literal("Confirm"), pButton -> this.onConfirm())
                .bounds(this.width / 2 -50, this.height / 2 + 10, 100, 20)
                .build();
        this.addRenderableWidget(this.confirmButton);

        super.init();
    }

    private void onTextChange(String newText)
    {
        this.inputIDString = newText;
    }

    private void onConfirm()
    {
        this.onClose();
        interactingPlayer.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().putString("trackerID", inputIDString);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        this.idInputField.render(pGuiGraphics, pMouseX,pMouseY, pPartialTick);
        this.confirmButton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics,pMouseX,pMouseY,pPartialTick);
    }

    @Override
    public void tick() {
        super.tick();
        this.idInputField.tick();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

