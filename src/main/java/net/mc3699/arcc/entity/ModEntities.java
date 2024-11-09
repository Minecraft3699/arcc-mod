package net.mc3699.arcc.entity;

import net.mc3699.arcc.arcc;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, arcc.MODID);

    public static RegistryObject<EntityType<EntryMissileEntity>> ENTRY_MISSILE_ENTITY =
            ENTITY_TYPES.register("entry_missile", () -> EntityType.Builder.of(EntryMissileEntity::new, MobCategory.MISC)
                    .sized(0.25f,0.25f).build("entry_missile"));

    public static RegistryObject<EntityType<TrackingArrowEntity>> TRACKING_ARROW =
            ENTITY_TYPES.register("tracking_arrow", () -> EntityType.Builder.<TrackingArrowEntity>of(TrackingArrowEntity::new, MobCategory.MISC)
                    .sized(.5f,.5f).build("tracking_arrow"));


    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }

}
