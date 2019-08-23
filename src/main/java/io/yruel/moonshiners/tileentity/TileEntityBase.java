package io.yruel.moonshiners.tileentity;

import io.yruel.moonshiners.fluid.FluidTankBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBase extends TileEntity {

    public static final int CAPACITY = 16_000;
    private static final int MAX_TRANSFER = 1000;
    private static final int SLOTS = 2, SLOT_INPUT = 0, SLOT_OUTPUT = 1;
    private static final int[] INPUT_SLOTS = {SLOT_INPUT, SLOT_OUTPUT}, OUTPUT_SLOTS = {SLOT_INPUT, SLOT_OUTPUT};

    public final FluidTankBase tank = new FluidTankBase(this, CAPACITY);
    public final ItemStackHandler inventory = new ItemStackHandler(SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityBase.this.markDirty();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        public boolean canInsert(ItemStack stack, int slot) {
            return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        }
    };


}
