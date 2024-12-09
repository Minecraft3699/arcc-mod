package net.mc3699.arcc.block;

import net.mc3699.arcc.arcc;
import net.mc3699.arcc.block.entity.*;
import net.mc3699.arcc.block.special.CellularAPBlock;
import net.mc3699.arcc.block.special.HUDBlock;
import net.mc3699.arcc.peripheral.ARControllerPeripheral;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, arcc.MODID);

    public static RegistryObject<BlockEntityType<ARControllerBlockEntity>> AR_CONTROLLER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("ar_controller_block_entity",
                    () -> BlockEntityType.Builder.of(ARControllerBlockEntity::new, ModBlocks.AUGMENTED_CONTROLLER.get()).build(null));

    public static RegistryObject<BlockEntityType<ARTerminalBlockEntity>> AR_TERMINAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("ar_terminal_block_entity",
                    () -> BlockEntityType.Builder.of(ARTerminalBlockEntity::new, ModBlocks.AUGMENTED_TERMINAL.get()).build(null));

    public static RegistryObject<BlockEntityType<CellularAPBlockEntity>> CELLULAR_AP_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("cellular_ap_block_entity",
                    () -> BlockEntityType.Builder.of(CellularAPBlockEntity::new, ModBlocks.CELLULAR_AP.get()).build(null));

    public static RegistryObject<BlockEntityType<EntityTrackerBlockEntity>> ENTITY_TRACKER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("entity_tracker_block_entity",
                    () -> BlockEntityType.Builder.of(EntityTrackerBlockEntity::new, ModBlocks.ENTITY_TRACKER.get()).build(null));

    public static RegistryObject<BlockEntityType<EntityControllerBlockEntity>> ENTITY_CONTROLLER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("entity_controller_block_entity",
                    () -> BlockEntityType.Builder.of(EntityControllerBlockEntity::new, ModBlocks.ENTITY_CONTROLLER.get()).build(null));

    public static RegistryObject<BlockEntityType<HUDBlockEntity>> HUD_BLOCK_ENIITY =
            BLOCK_ENTITIES.register("hud_block_entity", () -> BlockEntityType.Builder.of(HUDBlockEntity::new, ModBlocks.HEADS_UP_DISPLAY.get()).build(null));

    public static RegistryObject<BlockEntityType<CameraBlockEntity>> CAMERA_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("camera_block_entity", () -> BlockEntityType.Builder.of(CameraBlockEntity::new, ModBlocks.CAMERA_BLOCK.get()).build(null));
}
