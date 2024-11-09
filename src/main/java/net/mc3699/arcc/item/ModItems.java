package net.mc3699.arcc.item;

import net.mc3699.arcc.arcc;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, arcc.MODID);

    public static final RegistryObject<Item> AR_HEADSET = ITEMS.register("ar_headset",
            () -> new ARHeadset(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ZIP_DISK = ITEMS.register("zip_disk",
            () -> new ZipDiskItem(new Item.Properties()));

    public static final RegistryObject<Item> REMOTE_TERMINAL = ITEMS.register("remote_terminal",
            () -> new RemoteTerminalItem(new Item.Properties()));

    public static final RegistryObject<Item> PAGER = ITEMS.register("pager",
            () -> new PagerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TRACKING_ARROW = ITEMS.register("tracking_arrow",
            () -> new TrackingArrowItem(new Item.Properties().stacksTo(8).rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> ARROW_REMOVER = ITEMS.register("arrow_remover",
            () -> new ArrowRemoverItem(new Item.Properties().stacksTo(1).durability(4)));

    public static final RegistryObject<Item> REDNET_DIRECTOR = ITEMS.register("rednet_director",
            () -> new DirectorItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CHIP_PROGRAMMER = ITEMS.register("chip_programmer",
            () -> new ChipProgrammerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ENTITY_CHIP = ITEMS.register("entity_chip",
            () -> new EntityChipItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

