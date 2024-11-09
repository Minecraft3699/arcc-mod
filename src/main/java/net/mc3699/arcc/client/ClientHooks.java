package net.mc3699.arcc.client;

import net.mc3699.arcc.gui.ChipProgrammerGUI;
import net.mc3699.arcc.gui.PagerGUI;
import net.mc3699.arcc.gui.TrackingArrowGUI;
import net.mc3699.arcc.item.PagerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ClientHooks {

    public static void openTrackingArrowGUI(Player pPlayer)
    {
        if(Minecraft.getInstance().isRunning())
        {
            Minecraft.getInstance().setScreen(new TrackingArrowGUI(Component.literal("Tracking Arrow ID"), pPlayer));
        }
    }

    public static void openPagerGUI(Player pPlayer, PagerItem pagerItem)
    {
        Minecraft.getInstance().setScreen(new PagerGUI(Component.literal("Pager"), pPlayer, pagerItem));
    }

    public static void openChipProgrammer(Player player)
    {
        Minecraft.getInstance().setScreen(new ChipProgrammerGUI(Component.literal("Chip Programmer"), player));
    }
}
