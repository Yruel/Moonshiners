package io.yruel.moonshiners.container;


import io.yruel.moonshiners.tileentity.TileEntityCopperFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCopperFurnace extends Container {

    private final TileEntityCopperFurnace tileEntity;
    private int cookTime, totalCookTime, burnTime, currentBurnTime;

    public ContainerCopperFurnace(InventoryPlayer player, TileEntityCopperFurnace tileEntity) {
        this.tileEntity = tileEntity;

        this.addSlotToContainer(new Slot(tileEntity, 0, ))
    }
}
