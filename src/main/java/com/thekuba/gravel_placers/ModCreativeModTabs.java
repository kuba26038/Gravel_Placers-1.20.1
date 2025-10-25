package com.thekuba.gravel_placers;

import com.thekuba.gravel_placers.block.ModBlocks;
import com.thekuba.gravel_placers.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GravelPlacers.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PLACER_TAB = CREATIVE_MODE_TABS.register("placer_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.GRAVEL_PLACER.get()))
                    .title(Component.translatable("creativetab.placer_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.GRAVEL_PLACER.get());
                        pOutput.accept(ModItems.RAW_ELECTRUM_NUGGET.get());
                        pOutput.accept(ModItems.RAW_PLATINUM_NUGGET.get());
                    })
                    .build());

    public static void registerBus(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}