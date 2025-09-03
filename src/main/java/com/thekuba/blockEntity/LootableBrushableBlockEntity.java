package com.thekuba.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class LootableBrushableBlockEntity extends BrushableBlockEntity {
    private final ResourceLocation lootTable;

    //the beast is here the beast has arrived

    public LootableBrushableBlockEntity(BlockPos pos, BlockState state, String lootTableId) {
        super(pos, state);
        this.lootTable = new ResourceLocation(lootTableId);
    }//by supering we get all the huh whuh?

    @Override //override (hijack) the class that uses the block's NBT to determine its contents
    public void unpackLootTable(net.minecraft.world.entity.player.Player player) {
        if (this.level instanceof ServerLevel serverLevel) {
            LootTable table = serverLevel.getServer().getLootData().getLootTable(this.lootTable);
            // build the context
            LootParams.Builder params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(worldPosition)) // this is the block's position in the world iirc?
                    .withParameter(LootContextParams.BLOCK_STATE, getBlockState())
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player) //entity that caused the thing to happen?
                    .withOptionalParameter(LootContextParams.TOOL, player != null ? player.getMainHandItem() : null);
                    //alt if statement, if player isn't null, true?- player.getMainHandItem else:- nothing

            //run table using the BLOCK param set
            var drops = table.getRandomItems(params.create(LootContextParamSets.BLOCK));

            //spawn the items, the addition causes it to be centered at the block's top, which looks more vanilla like and natural
            for (ItemStack stack : drops) {
                serverLevel.addFreshEntity(new ItemEntity(
                        serverLevel,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 1.0,
                        worldPosition.getZ() + 0.5,
                        stack
                ));
            }
        }
    }
    //stuff up there is crazy
    //
    @Override
    public void checkReset() {
        
    }
}