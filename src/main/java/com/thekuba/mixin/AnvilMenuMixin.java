package com.thekuba.mixin;


import com.thekuba.item.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {


    public AnvilMenuMixin(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId, pPlayerInventory, pAccess);
    }

    @Inject(method = "mayPickup", cancellable = true, at = @At("HEAD"))
    private void gravel$allowZeroCost(Player player, boolean hasStack, CallbackInfoReturnable<Boolean> cir) {



        //through classic  science allows us to reference the original class' private and protected methods (this.someshit for an instance)
        AnvilMenu self = (AnvilMenu)(Object)this;

        if (this.inputSlots.getItem(1).is(ModItems.RAW_PLATINUM_NUGGET.get())) {
        int cost = self.getCost();

        //the only change from the original is that it also allows 0 to be applied
        boolean canTake = (player.getAbilities().instabuild || player.experienceLevel >= cost) && cost >= 0;

        //the callback allows us to have something be returned in place of the original thing
        //iirc mixins pointed at head go before anything within the code, so this should by all means be... like... fine, i think.
        //just to make sure, i'll allow this only for my dipshit of a son item
        cir.setReturnValue(canTake);
        }
    }






}