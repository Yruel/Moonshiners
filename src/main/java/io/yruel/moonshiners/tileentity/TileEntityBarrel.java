package io.yruel.moonshiners.tileentity;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelFluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class TileEntityBarrel extends TileEntity implements ITickable {

    private FluidTank inputTank = new FluidTank(new FluidStack(FluidRegistry.WATER, 2000),2000) {
        @Override
        protected void onContentsChanged() {
            markDirty();
        }
    };

    private FluidTank outputTank = new FluidTank(2000) {
        @Override
        protected void onContentsChanged() {
            markDirty();
        }
    };

    private IFluidHandler fluidHandlerInput = new IFluidHandler() {
        @Override
        public IFluidTankProperties[] getTankProperties() {
            return FluidTankProperties.convert(new FluidTankInfo[] {
                    inputTank.getInfo()
            });
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            return inputTank.fill(resource, doFill);
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            return inputTank.drain(resource, doDrain);
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            return inputTank.drain(maxDrain, doDrain);
        }
    };

    private IFluidHandler fluidHandlerOutput = new IFluidHandler() {
        @Override
        public IFluidTankProperties[] getTankProperties() {
            return FluidTankProperties.convert(new FluidTankInfo[] {outputTank.getInfo()});
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            return outputTank.fill(resource, doFill);
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            return outputTank.drain(resource, doDrain);
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            return outputTank.drain(maxDrain, doDrain);
        }
    };

    @Override
    public void update() {
        if (this.inputTank.getFluidAmount() > 0) {
            this.getTank(0).drain(1, true);
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return true;
    }

    public FluidTank getTank(int index) {
        if (index == 0) {
            return inputTank;
        } else {
            return outputTank;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.NORTH) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandlerInput);
            } else if (facing == EnumFacing.SOUTH) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandlerOutput);
            }
        }
        return super.getCapability(capability, null);
    }

    public boolean isUsableByPlayer(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inputTank", inputTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("outputTank", outputTank.writeToNBT(new NBTTagCompound()));
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inputTank.readFromNBT(compound.getCompoundTag("inputTank"));
        outputTank.readFromNBT(compound.getCompoundTag("outputTank"));
    }
}
