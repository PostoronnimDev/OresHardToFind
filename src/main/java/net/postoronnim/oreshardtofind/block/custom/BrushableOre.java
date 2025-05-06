package net.postoronnim.oreshardtofind.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrushableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.postoronnim.oreshardtofind.block.entity.ModBlockEntities;
import net.postoronnim.oreshardtofind.block.entity.custom.BrushableOreEntity;

public class BrushableOre extends BrushableBlock {

    public RegistryKey<LootTable> lootTable;

    public BrushableOre(Block baseBlock, SoundEvent brushingSound, SoundEvent brushingCompleteSound, Settings settings, RegistryKey<LootTable> lootTable) {
        super(baseBlock, brushingSound, brushingCompleteSound, settings);
        this.lootTable = lootTable;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        BrushableOreEntity entity =  ModBlockEntities.BRUSHABLE_ORE_ENTITY.instantiate(pos, state);
        assert entity != null;
        entity.setLootTable(lootTable, 0L);
        return entity;
    }
}
