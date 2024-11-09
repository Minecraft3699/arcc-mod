package net.mc3699.arcc.overlay;

import net.mc3699.arcc.item.ModItems;
import net.mc3699.arcc.network.TextElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "arcc", value = Dist.CLIENT)
public class AugmentedHudOverlay {

    private static AugmentedTerminalOverlay currentTerminal;

    private static final List<TextElement> displayElements = new ArrayList<>();

    private static boolean needsUpdate = true;

    public static void addDisplayText(TextElement textElement)
    {
        displayElements.add(textElement);
        needsUpdate = true;
    }

    public static void clearDisplay()
    {
        displayElements.clear();
        needsUpdate = true;
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.level != null) {

            ItemStack helmetItem = mc.player.getItemBySlot(EquipmentSlot.HEAD);

            if(helmetItem.getItem() == ModItems.AR_HEADSET.get())
            {
                    GuiGraphics guiGraphics = event.getGuiGraphics();
                    for (TextElement textElement : displayElements)
                    {
                        guiGraphics.drawString(
                                mc.font,
                                Component.literal(textElement.getText()),
                                textElement.getX(),
                                textElement.getY(),
                                textElement.getColor(),
                                false
                        );
                    }
                    needsUpdate = false;
            } else {
                //displayElements.clear();
                needsUpdate = true;
            }
            //guiGraphics.drawString(mc.font, Component.literal("This is a test"), 10, 10, 0xFFFFFF, false);
        }
    }


}
