package net.postoronnim.oreshardtofind.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.postoronnim.oreshardtofind.block.entity.ModBlockEntities;
import net.postoronnim.oreshardtofind.loot_table.ModLootTables;

public class BrushableOreEntity extends BrushableBlockEntity {

    public BrushableOreEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public boolean supports(BlockState state) {
        return this.getType().supports(state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.BRUSHABLE_ORE_ENTITY; // Your custom type
    }
}
