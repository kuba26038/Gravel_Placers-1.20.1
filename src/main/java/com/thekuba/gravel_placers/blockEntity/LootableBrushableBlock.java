package com.thekuba.gravel_placers.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
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
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {

        BlockEntity doohickey = ModBlockEntities.LOOTABLE_BRUSHABLE.get().create(pos, state);
        if (doohickey instanceof LootableBrushableBlockEntity lb) {
            lb.setLootTable(new ResourceLocation(lootTableId));
            lb.setChanged();
        }
        return doohickey;
    }// spawn a new block entity when this block is created into the world i th;ink

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state,
                            @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        if (!level.isClientSide) {
            BlockEntity thingamabob = level.getBlockEntity(pos);
            if (thingamabob instanceof LootableBrushableBlockEntity lb) {
                lb.setLootTable(new ResourceLocation(lootTableId));
                lb.setChanged();
            }
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }
    @Override
    public void tick(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        //FUCKS THIS KILL KILL KILL!
        //(coherent voice) overwriting this with an empty field makes it so the blockstate never reverts

        //it *should* be safe to do this, since the ticking logic originally didn't seem to do anything that important? maybe?
        //no wait fuck
        if (FallingBlock.isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, pPos, pState);
            fallingblockentity.disableDrop();
        }
        //ok now it should be fine
    }
}