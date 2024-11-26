package net.mc3699.arcc.gui;

import net.mc3699.arcc.arcc;
import net.mc3699.arcc.item.TrackingArrowItem;
import net.mc3699.arcc.network.TextElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TrackingArrowGUI extends Screen {

    private EditBox idInputField;
    private Button confirmButton;

    private String inputIDString = "";

    private Object onConfirm;
    private Player interactingPlayer;

    private static final ResourceLocation BACKGROUND_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/tracking_arrow_gui.png");

    private static final ResourceLocation BUTTON_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/small_button.png");

    public TrackingArrowGUI(Component pTitle, Player player) {
        super(pTitle);
        interactingPlayer = player;
    }

    @Override
    protected void init() {
        this.minecraft.pauseGame(false);
        this.idInputField = new EditBox(this.font, this.width / 2 - 18, this.height / 2 + 26, 59, 9, Component.literal("Set ID: "));
        this.idInputField.setMaxLength(30);
        this.idInputField.setResponder(this::onTextChange);
        this.addRenderableWidget(this.idInputField);

        super.init();
    }

    private void onTextChange(String newText)
    {
        this.inputIDString = newText;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);


        pGuiGraphics.blit(BACKGROUND_TEXTURE, width / 2 - (176/2), height / 2 - 32, 0, 0,
                256, 256);

        this.addRenderableWidget(new ImageButton(width / 2 - 18, height / 2 + 38, 10, 5, 0, 0,
                5, BUTTON_TEXTURE, 10, 10, this::onConfirm));

        //this.idInputField.render(pGuiGraphics, pMouseX,pMouseY, pPartialTick);
        //this.confirmButton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics,pMouseX,pMouseY,pPartialTick);
    }

    private void onConfirm(Button button) {
        this.onClose();
        interactingPlayer.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().putString("trackerID", inputIDString);
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

