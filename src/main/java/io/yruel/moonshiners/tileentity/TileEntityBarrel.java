package io.yruel.moonshiners.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TileEntityBarrel extends TileEntity {
   /* private int cookTime;
    private int totalCookTime = 200;*/

    private FluidTank tank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            markDirty();
        }
    };

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTagCompound = super.getUpdateTag();
        NBTTagCompound tankNBT = new NBTTagCompound();
        tank.writeToNBT(tankNBT);
        nbtTagCompound.setTag("tank", tankNBT);
        return nbtTagCompound;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        tank.readFromNBT(pkt.getNbtCompound().getCompoundTag("tank"));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readRestoreableFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeRestoreableToNBT(compound);
        return super.writeToNBT(compound);
    }

    public void readRestoreableFromNBT(NBTTagCompound compound) {
        tank.readFromNBT(compound.getCompoundTag("tank"));
    }

    public void writeRestoreableToNBT(NBTTagCompound compound) {
        NBTTagCompound tankNBT = new NBTTagCompound();
        tank.writeToNBT(tankNBT);
        compound.setTag("tank", tankNBT);
    }

    public FluidTank getTank() {
        return tank;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        return super.getCapability(capability, facing);
    }
}
