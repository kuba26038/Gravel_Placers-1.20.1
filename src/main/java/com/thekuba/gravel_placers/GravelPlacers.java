package com.thekuba.gravel_placers;

import com.thekuba.gravel_placers.block.ModBlocks;
import com.thekuba.gravel_placers.blockEntity.ModBlockEntities;
import com.thekuba.gravel_placers.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(GravelPlacers.MOD_ID)
public class GravelPlacers {
    public static final String MOD_ID = "gravel_placers";


    public GravelPlacers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.registerBus(modEventBus);
        ModItems.registerBus(modEventBus);
        ModBlocks.registerBus(modEventBus);
        ModBlockEntities.registerBus(modEventBus);

        //ModLootModifiers.register(modEventBus);
        //ModRecipes.register(modEventBus);



        MinecraftForge.EVENT_BUS.register(this);
        //modEventBus.addListener(this::addCreative);
    }
}



