package com.thekuba.block;

import com.thekuba.blockEntity.LootableBrushableBlock;
import com.thekuba.gravel_placers.GravelPlacers;
import com.thekuba.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;
import static net.minecraft.world.level.block.Blocks.GRAVEL;


public class ModBlocks {
    public static final DeferredRegister<Block> MOD_BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, GravelPlacers.MOD_ID);
    //DeferredRegister is used to register deferredly
    //ohhh actually, i think it just feeds the class information when it's called made here? that's so weird how it's done

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.MOD_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = MOD_BLOCKS.register(name, block);
        registerBlockItem(name,toReturn);
        return  toReturn;
    }

    public static final RegistryObject<Block> GRAVEL_PLACER = registerBlock("gravel_placer",
            () -> new LootableBrushableBlock(
                    GRAVEL,
                    BlockBehaviour.Properties.of()
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.SNARE)
                            .strength(1F)
                            .sound(SoundType.SUSPICIOUS_GRAVEL)
                            .pushReaction(PushReaction.NORMAL),
                    SoundEvents.BRUSH_GRAVEL,
                    SoundEvents.BRUSH_GRAVEL_COMPLETED,
                    "gravel_placers:archeology/gravel_placer" // <- loot table path
            )); //the lootablebrushableblock is the exact same as the brushableblock, but holds a loottable
                //it doesn't do anything by itself and needs other classes for help
                //the fact that this block uses a loottable is defined much sooner, but the path is defined only here
                //i guess it could be like... hardcoded even harder? into the definition of the block entity even?
                //but that would be kinda pointless, wouldn't it be...
                //TODO remember that this loottable needs to be non-silkable if it's in a state that's anything else than untouched


    //i still struggle to figure out what's going on here but this was even earlier. god damn is this hard to wrap my head around

    /*pipeline: to register a block and it's item, one must go through loops:
    -MOD_BLOCKS is created
    -registerBlockItem is created:
    -registerBlock is created: a function that takes the MOD_BLOCKS class instance(?) and uses
    it's function which... idk does stuff i'm tired man
    - register


    */
    public static void registerBus(IEventBus eventBus) {
        MOD_BLOCKS.register(eventBus);
    }
}
