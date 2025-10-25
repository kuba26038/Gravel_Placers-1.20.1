package com.thekuba.gravel_placers.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LootableBrushableBlockEntity extends BrushableBlockEntity {
    private ResourceLocation customLootTable;

    //the beast is here the beast has arrived

    public LootableBrushableBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }


    public void setLootTable(ResourceLocation table) {
        this.customLootTable = table;
    }

    @Override //override (hijack) the class that uses the block's NBT to determine its contents
    // (it's fine since this is a separate class and the original one is kept the same)
    public void unpackLootTable(net.minecraft.world.entity.player.@NotNull Player player) {
        if (this.level instanceof ServerLevel serverLevel) {
            LootTable table = serverLevel.getServer().getLootData().getLootTable(this.customLootTable);
            // build the context
            LootParams.Builder params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(worldPosition)) // this is the block's position in the world iirc?
                    .withParameter(LootContextParams.BLOCK_STATE, getBlockState())
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player) //entity that caused the thing to happen?
                    .withOptionalParameter(LootContextParams.TOOL, player.getMainHandItem());


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

    //ok new plan: maybe iplement your own brushcount?
    //or i guess an access transformer could work too? though i wonder if it's safe...
    //maybe if i set the brushcount to the brushableblock upon world saving it'd be fine? hmm
    //without actually touching it

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag); //having this super here makes it still perform it's normal behavior...

        tag.putInt("TimesBrushed", this.brushCount);//...but with this line added, it saves the number of brushes upon quitting the world


        if (this.customLootTable != null)
            tag.putString("CustomLootTable", this.customLootTable.toString());
        }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("TimesBrushed"))
            this.brushCount = tag.getInt("TimesBrushed");


        if (tag.contains("CustomLootTable"))
            this.customLootTable = new ResourceLocation(tag.getString("CustomLootTable"));
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return ModBlockEntities.LOOTABLE_BRUSHABLE.get();
    }
}

