package com.thekuba.forgeHooks;

import com.thekuba.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "gravel_placers", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public abstract class AnvilScreenExtension {

    //turned on and off by AdditionalAnvilLogic
    public static boolean render = true;

    //TODO don't do anything you'd later regret.


    //some Classic   Science called a "reflection"
    //from what i gather, it works by first getting the reference to the thang we want, and then because the "reflection" to it is local,
    // we simply change the access level of the local var
    //the error handing is in place in order to not try and trigger this in the wrong moment?
    //              the

    //i've read that it's slightly slow, so maybe i should keep an eye on this
    //yeah i kinda copied this one ngl
    //TODO keep an eye on this
    private static int getImageInt(AbstractContainerScreen<?> screen, String name, int fallback) {
        try {
            java.lang.reflect.Field f = AbstractContainerScreen.class.getDeclaredField(name);
            f.setAccessible(true);
            return f.getInt(screen);
        } catch (ReflectiveOperationException e) {
            return fallback;
        }
    }

    @SubscribeEvent
    public static void onAnvilRender(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof AnvilScreen anvilScreen) || anvilScreen.getMenu().getCost() > 0 || !render) return;


        // compute GUI origin (same math vanilla uses)
        int guiWidth  = getImageInt(anvilScreen, "imageWidth", 176);
        int guiHeight = getImageInt(anvilScreen, "imageHeight", 166); //hehe

        int left = (anvilScreen.width - guiWidth)   / 2; //lmao
        int top  = (anvilScreen.height - guiHeight) / 2;

        if (!anvilScreen.getMenu().getSlot(0).hasItem() ||
            !anvilScreen.getMenu().getSlot(1).getItem().is(ModItems.RAW_PLATINUM_NUGGET.get())) return; //it looks nice doesn't it HA

        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        int cost = anvilScreen.getMenu().getCost();
        if (cost < 0) return; // nothing to draw

        int color = 8453920; // geen
        Component component;

        if (cost >= 40 && (player == null || !player.getAbilities().instabuild)) {
            component = Component.translatable("container.repair.expensive");
            color = 16736352; //not geen (red)
        }
        else {
            component = Component.translatable("container.repair.cost", cost);
            if (player != null && !anvilScreen.getMenu().getSlot(2).mayPickup(player)) {// if the player cannot pick up the thang, because of woke (lack of exp)
                color = 16736352;// also not geen
            }
        }

        // some math (Classic   Sceience)
        //also copied :3
        int k = guiWidth - 8 - mc.font.width(component) - 2;
        int x1 = left + (k - 2);
        int y1 = top + 67;
        int x2 = left + (guiWidth - 8);
        int y2 = top + 79;

        //draw the thang
        guiGraphics.fill(x1, y1, x2, y2, 1325400064);// color is some gray
        guiGraphics.drawString(mc.font, component, left + k, top + 69, color);
    }
}
