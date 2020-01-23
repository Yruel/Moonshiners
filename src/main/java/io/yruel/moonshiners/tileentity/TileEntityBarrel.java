package io.yruel.moonshiners.tileentity;

import io.yruel.moonshiners.util.interfaces.IRestorableTileEntity;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

public class TileEntityBarrel extends TileEntity implements ITickable, IRestorableTileEntity {

    public FluidTank tank = new FluidTank(4000);
    private int clientAmountIn = -1;
    private FluidStack clientFluid;

    @Override
    public void update() {
/*        if (this.inputTank.getFluidAmount() > 0 && this.inputTank.getFluid() != null) {
            this.outputTank.fill(new FluidStack(this.inputTank.getFluid().getFluid(), 1), true);
            this.inputTank.drain(1, true);
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
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
            }
        }
        return super.getCapability(capability, facing);
    }

    public boolean isUsableByPlayer(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        this.tank.readFromNBT(compound.getCompoundTag("inputTank"));
    }

    @Override
    public void writeRestorableFromNBT(NBTTagCompound compound) {
        compound.setTag("inputTank", tank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (this.tank != null && this.tank.getFluid() != null) {
            compound.setTag("fluidDataInput", tank.getFluid().writeToNBT(new NBTTagCompound()));
            compound.setTag("inputTank", tank.writeToNBT(new NBTTagCompound()));
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("fluidDataInput")) {
            this.tank.setFluid(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluidDataInput")));
        }
        if (tank != null && tank.getFluid() != null && compound.hasKey("inputTank")) {
            tank.readFromNBT(compound.getCompoundTag("inputTank"));
        }
        if (this.tank != null) {
            this.tank.setTileEntity(this);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState == newSate;
    }

    public int getClientFluidAmount() {
        return clientAmountIn;
    }

    public void setClientFluidAmount(int clientAmountFluid) {
        this.clientAmountIn = clientAmountFluid;
    }

    public int getFluidAmount() {
        return tank.getFluidAmount();
    }

    public FluidStack getClientFluid() {
        return clientFluid;
    }

    public void setClientFluid(FluidStack clientFluid) {
        this.clientFluid = clientFluid;
    }

    public FluidStack getFluid() {
        return tank.getFluid();
    }
}
