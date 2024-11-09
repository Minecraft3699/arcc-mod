package net.mc3699.arcc.block;

import net.mc3699.arcc.arcc;
import net.mc3699.arcc.block.special.*;
import net.mc3699.arcc.item.ModItems;
import net.mc3699.arcc.network.ModNetworking;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, arcc.MODID);

    public static final RegistryObject<Block> AUGMENTED_CONTROLLER = registerBlock("ar_controller",
            () -> new ARControllerBlock(BlockBehaviour.Properties.copy(Blocks.STONE), ModNetworking.getInstance()));

    public static final RegistryObject<Block> AUGMENTED_TERMINAL = registerBlock("ar_terminal",
            () -> new ARTerminalBlock(BlockBehaviour.Properties.copy(Blocks.STONE), ModNetworking.getInstance()));

    public static final RegistryObject<Block> CELLULAR_AP = registerBlock("cellular_ap",
            () -> new CellularAPBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> ENTITY_TRACKER = registerBlock("entity_tracker",
            () -> new EntityTrackerBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> REDSTONE_MODEM = registerBlock("redstone_modem",
            () -> new RedstoneModemBlock(BlockBehaviour.Properties.copy(Blocks.OBSERVER)));

    public static final RegistryObject<Block> ENTITY_CONTROLLER = registerBlock("entity_controller",
            () -> new EntityControllerBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
