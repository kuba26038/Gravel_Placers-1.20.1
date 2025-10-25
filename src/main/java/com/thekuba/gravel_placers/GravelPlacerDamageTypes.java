package com.thekuba.gravel_placers;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class GravelPlacerDamageTypes {
    public static final ResourceKey<DamageType> TEETH_GRIND = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(GravelPlacers.MOD_ID,"teeth_grind"));

    public static void bootstrap(BootstapContext<DamageType> ctx) {
        ctx.register(TEETH_GRIND, new DamageType(
                "gravel_placers.teeth_grind",
                DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                0f
        ));
    }


}// on that yoinky spolnkjgkuy
//on that yelunky spelunky


