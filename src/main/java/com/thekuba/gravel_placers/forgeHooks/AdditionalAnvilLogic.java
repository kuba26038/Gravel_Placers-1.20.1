package com.thekuba.gravel_placers.forgeHooks;


import com.thekuba.gravel_placers.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "gravel_placers")
public class AdditionalAnvilLogic {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack itemStack1 = event.getLeft();
        ItemStack itemStack2 = event.getRight();

        if (itemStack1.isEmpty() || itemStack2.isEmpty() || !(itemStack2.is(ModItems.RAW_PLATINUM_NUGGET.get()) && itemStack1.isDamageableItem())) return;


        ItemStack itemStackOutput = itemStack1.copy();

        //TODO missing logic: for some reason the code doesn't limit how many nuggets can be used at once. so, when shift-clicking, you could spend an entire stack of em on accident
        //and and and, if the tool has max durability, it can still take these thingies, even thhough it shouldn't

        //if an item's durability is crazy low (less than 8), default to increasing it's durability by 1
        int perNugget = Math.max(1, itemStackOutput.getMaxDamage() / 8);
        int used = 0; //ammount of plat nugs that has been used so far (in theory should never go above )

        //let's go for loop, all my years studying are paying off
        //there is no variable defined here, that's why there's a smiley face (;
        for (; used < itemStack2.getCount() && itemStackOutput.getDamageValue() > 0; used++) {

            //if the damage is lesserr than what would be normally repaired, repair to full instead of over-repairing (non-funny)
            int repair = Math.min(perNugget, itemStackOutput.getDamageValue());

            itemStackOutput.setDamageValue(itemStackOutput.getDamageValue() - repair);
        }

        //slight edit of vanilla's renaming logic implementation
        int extraCost = 0; //0 if no rename, 1 if rename
        String newName = event.getName();
        if (newName != null && !newName.isBlank()) {

            // apparently items can have custom hover names that only show up via nbt? didn't know that, cool
            if (!newName.equals(itemStack1.getHoverName().getString())) {
                //if item has different name than before this check: make the names same and set rename cost to 1
                itemStackOutput.setHoverName(net.minecraft.network.chat.Component.literal(newName));
                extraCost = 1;
            }
        } else if (itemStack1.hasCustomHoverName()) {
            //same as before, but for what i assume is "nbt name" which yep idk what that is but let's roll with it
            itemStackOutput.resetHoverName();
            extraCost = 1;
        }

        if (used == 0 && extraCost == 0) {
            event.setOutput(ItemStack.EMPTY);
            event.setCost(0);

            AnvilScreenExtension.render = false;
            return;
        } else AnvilScreenExtension.render = true; //little thing to turn on and off the thingy

        //TODO make it so that the anvil doesn't get damaged from this

        //send the kids to school (bus)
        event.setOutput(itemStackOutput);
        event.setMaterialCost(used);
        event.setCost(extraCost);
    }
}