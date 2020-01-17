package io.yruel.moonshiners.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TileEntityBarrel extends TileEntity implements ITickable {

    public FluidTank inputTank = new FluidTank(2000);

    public FluidTank outputTank = new FluidTank(2000);

    /*private IFluidHandler fluidHandlerInput = new IFluidHandler() {
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
    };*/

    @Override
    public void update() {
     /*   if (this.inputTank.getFluidAmount() > 0) {
            this.getTank(0).drain(1, true);
        }*/
    }

/*    public FluidTank getTank(int index) {
        if (index == 0) {
            return inputTank;
        } else {
            return outputTank;
        }
    }*/

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return this.getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.NORTH) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputTank);
            } else if (facing == EnumFacing.SOUTH) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputTank);
            }
        }
        return super.getCapability(capability, facing);
    }

    public boolean isUsableByPlayer(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (this.inputTank != null && this.inputTank.getFluid() != null) {
            compound.setTag("fluidDataInput", inputTank.getFluid().writeToNBT(new NBTTagCompound()));
            compound.setTag("inputTank", inputTank.writeToNBT(new NBTTagCompound()));
        }
        if (this.outputTank != null && this.outputTank.getFluid() != null) {
            compound.setTag("fluidDataOutput", outputTank.getFluid().writeToNBT(new NBTTagCompound()));
            compound.setTag("outputTank", outputTank.writeToNBT(new NBTTagCompound()));
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("fluidDataInput")) {
            this.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluidDataInput")));
        }
        if (compound.hasKey("fluidDataOutput")) {
            this.outputTank.setFluid(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluidDataOutput")));
        }
        if (this.inputTank != null && inputTank.getFluid() != null && compound.hasKey("inputTank")) {
            inputTank.readFromNBT(compound.getCompoundTag("inputTank"));
        }
        if (this.outputTank != null && outputTank.getFluid() != null && compound.hasKey("outputTank")) {
            inputTank.readFromNBT(compound.getCompoundTag("outputTank"));
        }
    }
}
