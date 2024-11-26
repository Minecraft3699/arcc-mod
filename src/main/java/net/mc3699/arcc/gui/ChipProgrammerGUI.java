package net.mc3699.arcc.gui;

import dan200.computercraft.shared.platform.NetworkHandler;
import net.mc3699.arcc.arcc;
import net.mc3699.arcc.item.ChipProgrammerItem;
import net.mc3699.arcc.item.EntityChipItem;
import net.mc3699.arcc.item.ModItems;
import net.mc3699.arcc.network.ModNetworking;
import net.mc3699.arcc.network.SpawnChipItemPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChipProgrammerGUI extends Screen {
    public ChipProgrammerGUI(Component pTitle, Player player) {
        super(pTitle);
        this.player = player;
    }

    private Player player;
    private EditBox groupInputBox;
    private EditBox individualInputBox;

    private String groupInputID;
    private String individualInputID;


    @Override
    protected void init() {
        this.groupInputBox = new EditBox(this.font, this.width / 2 - 80, this.height / 2 - 17, 86, 11, Component.literal("Set Group: "));
        this.groupInputBox.setMaxLength(30);
        this.groupInputBox.setResponder(this::updateGID);
        this.addRenderableWidget(this.groupInputBox);

        this.individualInputBox = new EditBox(this.font, this.width / 2 - 80, this.height / 2 + 9, 86, 11, Component.literal("Set Individual"));
        this.individualInputBox.setMaxLength(30);
        this.individualInputBox.setResponder(this::updateIID);
        this.addRenderableWidget(this.individualInputBox);
    }



    private static final ResourceLocation BACKGROUND_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/programmer_gui.png");

    private static final ResourceLocation WRITE_BUTTON_TEXTURE =
            new ResourceLocation(arcc.MODID, "textures/gui/write_button.png");

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_TEXTURE, width / 2 - (176/2), height / 2 - 32, 0, 0,
                256, 256);

        this.groupInputBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.individualInputBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.drawString(this.font, Component.literal("Group ID"), width / 2 - 80, height/ 2 - 27, 0xAAAAAA, false);
        pGuiGraphics.drawString(this.font, Component.literal("Individual ID"), width / 2 - 80, height/ 2 - 1, 0xAAAAAA, false);

        this.addRenderableWidget(new ImageButton(width / 2 + 37, height / 2 + 16, 36, 27, 0, 0,
                27, WRITE_BUTTON_TEXTURE, 37, 55, this::handleWriteButton));

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }


    private void updateIID(String newText)
    {
        this.individualInputID = newText;
    }

    private void updateGID(String newText)
    {
        this.groupInputID = newText;
    }


    private void handleWriteButton(Button button) {
        if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ChipProgrammerItem)
        {
            if(individualInputID != null && groupInputID != null)
            {
                ModNetworking.INSTANCE_CHANNEL.sendToServer(new SpawnChipItemPacket(groupInputID,individualInputID));
                this.onClose();
            } else {
                player.level().playSound(null, player.getX(),player.getY(),player.getZ(), SoundEvents.NOTE_BLOCK_COW_BELL.get(), SoundSource.PLAYERS, 1, 1);
            }
        }
    }



    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
