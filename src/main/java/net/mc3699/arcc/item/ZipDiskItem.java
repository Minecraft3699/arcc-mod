package net.mc3699.arcc.item;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.filesystem.Mount;
import dan200.computercraft.api.media.IMedia;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ZipDiskItem extends Item implements IMedia {
    public ZipDiskItem(Properties pProperties) {
        super(pProperties);
    }

    public static int getDiskID(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt != null && nbt.contains("DiskId") ? nbt.getInt("DiskId") : -1;
    }

    private static void setDiskID(ItemStack stack, int id) {
        if (id >= 0) {
            stack.getOrCreateTag().putInt("DiskId", id);
        }
    }

    @Override
    public @Nullable String getLabel(ItemStack itemStack) {
        return "zip_disk";
    }

    @Override
    public @Nullable Mount createDataMount(ItemStack stack, ServerLevel level) {
        int diskID = getDiskID(stack);
        if (diskID < 0) {
            diskID = ComputerCraftAPI.createUniqueNumberedSaveDir(level.getServer(), "zip_disk");
            setDiskID(stack, diskID);
        }

        return ComputerCraftAPI.createSaveDirMount(level.getServer(), "zip_disk/" + diskID, 250000000);
    }
}
