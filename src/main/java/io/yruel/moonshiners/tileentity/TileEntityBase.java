package io.yruel.moonshiners.tileentity;

import io.netty.util.internal.MathUtil;
import io.yruel.moonshiners.fluid.FluidTankBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBase extends TileEntity implements ITickable {

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

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            drainItem();
            fillItem();
        }
    }

    private void drainItem() {
        if (tank.getFreeSpace() <= 0) {
            return;
        }
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                handler.drain(tank.fill(handler.drain(MAX_TRANSFER, false), true), true);
                inventory.setStackInSlot(SLOT_INPUT, handler.getContainer());
            }
        }
    }

    private void fillItem() {
        if (tank.getFluidAmount() <= 0) {
            return;
        }
        ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                tank.drain(handler.fill(tank.drain(MAX_TRANSFER, false), true), true);
                inventory.setStackInSlot(SLOT_OUTPUT, handler.getContainer());
            }
        }
    }

    public static int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    public static int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    public int getComparatorLevel() {
        return tank.getFluidAmount() > 0 ? tank.getCapacity() - tank.getFluidAmount() > 0 ? 1 + (int) ((15 - 1) * ((tank.getFluidAmount() - 1) / (tank.getCapacity() - 1))) : 15 : 0;
    }
}
