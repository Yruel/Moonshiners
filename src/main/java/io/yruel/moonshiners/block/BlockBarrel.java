package io.yruel.moonshiners.block;

import io.yruel.moonshiners.fluid.FluidTankBase;
import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;

public class BlockBarrel extends BlockContainerBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockBarrel(String name, Material material, float hardness, float resistance) {
        super(name, material, hardness, resistance);
        setSoundType(SoundType.WOOD);
        setDefaultState(this.getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    /*public Item getItemBlock() {
        return new ItemBlockTank(this);
    }*/

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityBarrel();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityBarrel) {
                if (tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing) && FluidUtil.interactWithFluidHandler(playerIn, hand, tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))) {
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
                IFluidHandlerItem itemHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if (itemHandler instanceof FluidTankBase) {
                    FluidTankBase itemTank = (FluidTankBase) itemHandler;
                    TileEntity tile = worldIn.getTileEntity(pos);
                    if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
                        IFluidHandler tileHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                        if (tileHandler instanceof FluidTankBase) {
                            FluidTankBase tileTank = (FluidTankBase) tileHandler;
                            FluidStack fluid = itemTank.getFluid();
                            if (fluid != null) {
                                tileTank.setFluid(fluid.copy());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean dropInventory() {
        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        if (!drops.isEmpty()) {
            ItemStack stack = drops.get(0);
            if (!stack.isEmpty()) {
                fillStack(stack, world, pos, state);
            }
        }
    }

    private void fillStack(ItemStack stack, IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem itemHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (itemHandler instanceof FluidTankBase) {
                FluidTankBase itemTank = (FluidTankBase) itemHandler;
                TileEntity tile = worldIn.getTileEntity(pos);
                if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
                    IFluidHandler tileHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                    if (tileHandler instanceof FluidTankBase) {
                        FluidTankBase tileTank = (FluidTankBase) tileHandler;
                        itemTank.setFluid(tileTank.getFluid());
                    }
                }
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
