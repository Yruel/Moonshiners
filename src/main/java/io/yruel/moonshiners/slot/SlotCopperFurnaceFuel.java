package io.yruel.moonshiners.slot;

import io.yruel.moonshiners.tileentity.TileEntityCopperFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCopperFurnaceFuel extends Slot {

    public SlotCopperFurnaceFuel(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return TileEntityCopperFurnace.isItemFuel(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return super.getItemStackLimit(stack);
    }
}
