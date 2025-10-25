package com.thekuba.gravel_placers.mixin;

import com.thekuba.gravel_placers.GravelPlacerDamageTypes;
import com.thekuba.gravel_placers.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity{

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    LivingEntity gravel_1_20_1$PlayerSelf = this;

    @Unique
    protected final RandomSource gravel_1_20_1$RNG = RandomSource.create();

    @Unique
    private static int gravel_1_20_1$WaitForTicks = 0;



    @Inject(method = "tick", at = @At("HEAD"))
    private void gravel$DamagePlayerEachBite(CallbackInfo ci) {

        if(this.useItem.is(ModItems.RAW_ELECTRUM_NUGGET.get()) && this.useItem.getUseAnimation() == UseAnim.EAT) {

                if(gravel_1_20_1$WaitForTicks < 20) {
                    gravel_1_20_1$WaitForTicks++; //SCUFFED AAA
                    return;
                }
                
                    playSound(SoundEvents.GRINDSTONE_USE, // you know what really grinds my gears?
                            0.3F + 0.3F * (float) gravel_1_20_1$RNG.nextInt(2),
                            (gravel_1_20_1$RNG.nextFloat() - gravel_1_20_1$RNG.nextFloat()) * 0.2F + 1.0F);

                    //TODO create a shorter and more snappy version of the grinding SFXes (SoundEffectsies)

                    gravel_1_20_1$PlayerSelf.hurt(gravel_1_20_1$PlayerSelf.damageSources().source(GravelPlacerDamageTypes.TEETH_GRIND), 1.0f); //harm.

        } else gravel_1_20_1$WaitForTicks = 0;
    }
}
