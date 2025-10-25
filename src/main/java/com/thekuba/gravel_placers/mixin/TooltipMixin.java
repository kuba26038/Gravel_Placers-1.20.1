package com.thekuba.gravel_placers.mixin;



import com.thekuba.gravel_placers.block.ModBlocks;
import com.thekuba.gravel_placers.item.ModItems;
import net.minecraft.ChatFormatting;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class TooltipMixin { // thank you ordana for your awesome work (stolen from spelunkery TooltipMixin class)

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    private void vanillaItemTooltips(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag isAdvanced, CallbackInfo ci) {

        if (stack.is(ModItems.RAW_ELECTRUM_NUGGET.get())) {
            tooltip.add(Component.translatable("tooltip.gravel_placers.raw_electrum_nugget").setStyle(Style.EMPTY.applyFormats(ChatFormatting.GRAY, ChatFormatting.ITALIC)));
        }

        if (stack.is(ModItems.RAW_PLATINUM_NUGGET.get())) {
            tooltip.add(Component.translatable("tooltip.gravel_placers.raw_platinum_nugget").setStyle(Style.EMPTY.applyFormats(ChatFormatting.GRAY, ChatFormatting.ITALIC)));
        }

        if (stack.is(ModBlocks.GRAVEL_PLACER.get().asItem())) {
            tooltip.add(Component.translatable("tooltip.gravel_placers.gravel_placer").setStyle(Style.EMPTY.applyFormats(ChatFormatting.GRAY, ChatFormatting.ITALIC)));
        }
    }
}