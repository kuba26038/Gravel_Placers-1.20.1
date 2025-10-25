package com.thekuba.gravel_placers.blockEntity;


import com.thekuba.gravel_placers.block.ModBlocks;
import com.thekuba.gravel_placers.GravelPlacers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> MOD_BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GravelPlacers.MOD_ID);

    public static final RegistryObject<BlockEntityType<LootableBrushableBlockEntity>> LOOTABLE_BRUSHABLE =
            MOD_BLOCK_ENTITIES.register("lootable_brushable",
                    () -> BlockEntityType.Builder.of(
                            LootableBrushableBlockEntity::new,
                            ModBlocks.GRAVEL_PLACER.get()
                    ).build(null));
    //i still have no fucking idea why a lambda function is a parameter to a registryObject


                    //the only real difference between a normal BrushableBlockEntity and this Lootable one is that
                    //this thingy holds a loottable, which is normally given to the entity via NBT
                    //that was a no-go because of what i'm trying to do here, so yeah
                    //it's kinda scuffed and i feel like i barely understood what happened here but alright it's a start

    public static void registerBus(IEventBus eventBus) {
        MOD_BLOCK_ENTITIES.register(eventBus);
    }
}

