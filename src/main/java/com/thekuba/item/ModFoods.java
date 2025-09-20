package com.thekuba.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods { //praise be to kaupenjoe

    public static final FoodProperties RAW_ELECTRUM_NUGGET = new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(1.2f)

            .effect(() -> new MobEffectInstance(
                            MobEffects.MOVEMENT_SPEED,
                            600,
                            6),
                    1.0f)

            .build();
}
