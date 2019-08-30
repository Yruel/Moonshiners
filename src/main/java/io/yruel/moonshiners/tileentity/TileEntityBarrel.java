package io.yruel.moonshiners.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityBarrel extends TileEntity {
    /*private IFluidHandler fluidHandler;
    private  FluidTank tank = new FluidTank(16000);*/

    private ItemStackHandler handler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityBarrel.this.markDirty();
        }
    };

    /*public FluidTank getTank() {
        return tank;
    }*/

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return true;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(handler);
        return super.getCapability(capability, facing);
    }
}
