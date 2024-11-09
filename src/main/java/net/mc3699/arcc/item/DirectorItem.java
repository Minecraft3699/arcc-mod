package net.mc3699.arcc.item;

import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;

public class DirectorItem extends CompassItem {
    public DirectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return false;
    }
}
