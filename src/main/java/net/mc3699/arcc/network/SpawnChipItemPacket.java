package net.mc3699.arcc.network;

import net.mc3699.arcc.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnChipItemPacket {

    private final String groupID;
    private final String indivID;

    public SpawnChipItemPacket(String groupID, String indivID) {
        this.groupID = groupID;
        this.indivID = indivID;
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeUtf(groupID);
        buf.writeUtf(indivID);
    }

    public static SpawnChipItemPacket decode(FriendlyByteBuf buf)
    {
        return new SpawnChipItemPacket(buf.readUtf(),buf.readUtf());
    }

    public void handle(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
           if(context.get().getSender() != null)
           {
               ServerPlayer player = context.get().getSender();
               assert player != null;
               player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
               ItemStack newChipItem = new ItemStack(ModItems.ENTITY_CHIP.get(), 1);
               newChipItem.getOrCreateTag().putString("controlChipGroup", this.groupID);
               newChipItem.getOrCreateTag().putString("controlChipMember", this.indivID);
               player.addItem(newChipItem);
           }
        });
    }



}
