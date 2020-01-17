package io.yruel.moonshiners.tileentity;

import io.yruel.moonshiners.util.interfaces.IRestorableTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TileEntityBarrel extends TileEntity implements ITickable, IRestorableTileEntity {

    public FluidTank inputTank = new FluidTank(2000);
    private int clientAmountIn = -1;

    public FluidTank outputTank = new FluidTank(2000);
    private int clientAmountOut = -1;



    @Override
    public void update() {
     /*   if (this.inputTank.getFluidAmount() > 0) {
            this.getTank(0).drain(1, true);
        }*/
    }

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
    public void readRestorableFromNBT(NBTTagCompound compound) {
        this.inputTank.readFromNBT(compound.getCompoundTag("inputTank"));
        this.outputTank.readFromNBT(compound.getCompoundTag("outputTank"));
    }

    @Override
    public void writeRestorableFromNBT(NBTTagCompound compound) {
        compound.setTag("inputTank", inputTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("outputTank", outputTank.writeToNBT(new NBTTagCompound()));
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
        if (inputTank != null && inputTank.getFluid() != null && compound.hasKey("inputTank")) {
            inputTank.readFromNBT(compound.getCompoundTag("inputTank"));
        }
        if (this.inputTank != null) {
            this.inputTank.setTileEntity(this);
        }
        if (outputTank != null && outputTank.getFluid() != null && compound.hasKey("outputTank")) {
            inputTank.readFromNBT(compound.getCompoundTag("outputTank"));
        }
        if (this.outputTank != null) {
            this.outputTank.setTileEntity(this);
        }
    }

    public int getClientFluidInAmount() {
        return clientAmountIn;
    }

    public void setClientFluidInAmount(int clientAmountFluid) {
        this.clientAmountIn = clientAmountFluid;
    }

    public int getFluidInAmount() {
        return inputTank.getFluidAmount();
    }

    public int getClientFluidOutAmount() {
        return clientAmountOut;
    }

    public void setClientFluidOutAmount(int clientAmountFluid) {
        this.clientAmountOut = clientAmountFluid;
    }

    public int getFluidOutAmount() {
        return outputTank.getFluidAmount();
    }
}
