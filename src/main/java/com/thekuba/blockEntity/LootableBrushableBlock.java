package com.thekuba.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LootableBrushableBlock extends BrushableBlock {
    private final String lootTableId;

    public LootableBrushableBlock(Block turnsInto,
                                  BlockBehaviour.Properties properties,
                                  SoundEvent brushSound,
                                  SoundEvent brushCompletedSound,
                                  String lootTableId) {
        super(turnsInto, properties, brushSound, brushCompletedSound);
        this.lootTableId = lootTableId;
    }

    //stuff here is just defining the existence of the kind of block that uses the Lootable entity defined elsewhere
    //like i said in there, this thing is the same as the BrushableBlock but it holds within itself a LootTable
    //i wonder if i could define the loottable later?

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LootableBrushableBlockEntity(pos, state, lootTableId);
    }// spawn a new block entity when this block is created into the world i th;ink
}