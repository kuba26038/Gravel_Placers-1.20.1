package com.thekuba.item;

import com.thekuba.gravel_placers.GravelPlacers;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> MOD_ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GravelPlacers.MOD_ID);

    public static final RegistryObject<Item> RAW_ELECTRUM_NUGGET = MOD_ITEMS.register("raw_electrum_nugget",
            () -> new Item(new Item.Properties()));

    //to break this down let's write the next one like this:
    public static final RegistryObject<Item> RAW_PLATINUM_NUGGET =
            MOD_ITEMS.register(
                    "raw_platinum_nugget",
                    () -> new Item(new Item.Properties())
            );
    //as i can see, it is a variable. right?? unless methods can also be public static final
    //what the hell is RegistryObject????
    // ok i think it's a. a class?? because methods and variables are the same thing?
    // it has the equals sign so it must be a variable right?
    // i just learned that instances of a class are objects. that's what they were called. that's super useful right
    //are objects variables???

    public static void registerBus(IEventBus eventBus) {
        MOD_ITEMS.register(eventBus);
    }

}
