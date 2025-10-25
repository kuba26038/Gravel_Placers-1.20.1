package com.thekuba.gravel_placers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LongEatingItem extends Item {


    public LongEatingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        if (pStack.getItem().isEdible()) {
            return 64;
        } else {
            return 0;
        }
    }

}
