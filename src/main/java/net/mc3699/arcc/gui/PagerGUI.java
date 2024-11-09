package net.mc3699.arcc.gui;

import dan200.computercraft.api.peripheral.IComputerAccess;
import net.mc3699.arcc.arcc;
import net.mc3699.arcc.block.entity.CellularAPBlockEntity;
import net.mc3699.arcc.item.PagerItem;
import net.mc3699.arcc.network.ModNetworking;
import net.mc3699.arcc.network.PagerButtonPressPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import dan200.computercraft.shared.computer.core.ServerComputerRegistry;

import java.awt.*;
import java.util.Objects;

public class PagerGUI extends Screen {

    public PagerGUI(Component pTitle, Player localPlayer, PagerItem pagerItem) {
        super(pTitle);
        pager = pagerItem;
    }

    private PagerItem pager;
    private ServerPlayer player;

    private static final ResourceLocation BACKGROUND_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/pager_gui_new.png");
    private static final ResourceLocation RED_BUTTON_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/red_button.png");
    private static final ResourceLocation ORANGE_BUTTON_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/orange_button.png");
    private static final ResourceLocation BLUE_BUTTON_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/blue_button.png");



    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_TEXTURE, width / 2 - (176/2), height / 2 - 32, 0, 0,
                256, 256);
        
        // Red Button
        this.addRenderableWidget(new ImageButton(width / 2 - 80, height / 2 + 29, 23, 14, 0, 0,
                14, RED_BUTTON_TEXTURE, 23, 14, this::handleRedButton));

        // Orange Button
        this.addRenderableWidget(new ImageButton(width / 2 - 54, height / 2 + 29, 23, 14, 0, 0,
                14, ORANGE_BUTTON_TEXTURE, 23, 14, this::handleOrangeButton));

        // Blue Button
        this.addRenderableWidget(new ImageButton(width / 2 - 28, height / 2 + 29, 23, 14, 0, 0,
                14, BLUE_BUTTON_TEXTURE, 23, 14, this::handleBlueButton));


        // Format message line
        String mLine1 = pager.getMessageLine1();
        String mLine2 = pager.getMessageLine2();
        String mLine3 = pager.getMessageLine3();
        String mLine4 = pager.getMessageLine4();

        if(mLine1.length() > 26) { mLine1 = mLine1.substring(0,26); }
        if(mLine2.length() > 26) { mLine2 = mLine2.substring(0,26); }
        if(mLine3.length() > 26) { mLine3 = mLine3.substring(0,26); }
        if(mLine4.length() > 26) { mLine4 = mLine4.substring(0,26); }

        // Message Lines
        pGuiGraphics.drawString(this.font, Component.literal(mLine1), width / 2 - 78, height / 2 - 21, 0xAAFFAA, false);
        pGuiGraphics.drawString(this.font, Component.literal(mLine2), width / 2 - 78, height / 2 - 11, 0xAAFFAA, false);
        pGuiGraphics.drawString(this.font, Component.literal(mLine3), width / 2 - 78, height / 2, 0xAAFFAA, false);
        pGuiGraphics.drawString(this.font, Component.literal(mLine4), width / 2 - 78, height / 2 + 11, 0xAAFFAA, false);

        // Phone Number
        pGuiGraphics.drawString(this.font, Component.literal("#: "+Integer.toString(pager.getPhoneNumber())),
                width / 2 + 2, height / 2 + 32, 0xFFFFFF, false);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    private void handleRedButton(Button button) {
        ModNetworking.INSTANCE_CHANNEL.sendToServer(new PagerButtonPressPacket(new BlockPos(pager.getCellX(),pager.getCellY(),pager.getCellZ()), 1));
    }

    private void handleOrangeButton(Button button) {
        ModNetworking.INSTANCE_CHANNEL.sendToServer(new PagerButtonPressPacket(new BlockPos(pager.getCellX(),pager.getCellY(),pager.getCellZ()), 2));
    }

    private void handleBlueButton(Button button) {
        ModNetworking.INSTANCE_CHANNEL.sendToServer(new PagerButtonPressPacket(new BlockPos(pager.getCellX(),pager.getCellY(),pager.getCellZ()), 3));
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
