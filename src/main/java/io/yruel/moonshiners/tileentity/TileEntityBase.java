package io.yruel.moonshiners.tileentity;

import io.yruel.moonshiners.fluid.FluidTankBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

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

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("tank")) {
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT(tank, null, compound.getTag("tank"));
        }
        if (compound.hasKey("inventory")) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, compound.getTag("inventory"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("tank", CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT(tank, null));
        compound.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));
        return compound;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T) tank : super.getCapability(capability, facing);
    }

    public int getFluidtoToDrain() {
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                FluidStack fluidStack = prop.getContents();
                return prop.getCapacity() - (fluidStack != null ? fluidStack.amount : 0);
            }
        }
        return 0;
    }

    public int getCapacityToDrain() {
        ItemStack stack = inventory.getStackInSlot(SLOT_INPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                return prop.getCapacity();
            }
        }
        return 0;
    }

    public int getFluidToFill() {
        ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                FluidStack fluidStack = prop.getContents();
                return fluidStack != null ? fluidStack.amount : 0;
            }
        }
        return 0;
    }

    public int getCapacityToFill() {
        ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (handler != null) {
                IFluidTankProperties prop = handler.getTankProperties()[0];
                return prop.getCapacity();
            }
        }
        return 0;
    }
}
