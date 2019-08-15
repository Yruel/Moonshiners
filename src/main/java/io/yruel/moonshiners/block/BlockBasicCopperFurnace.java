package io.yruel.moonshiners.block;

import io.yruel.moonshiners.Moonshiners;
import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.tileentity.TileEntityCopperFurnace;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBasicCopperFurnace extends BlockBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool BURNING = PropertyBool.create("burning");
    private final boolean isBurning;
    private static boolean keepInventory;

    public BlockBasicCopperFurnace(String name, boolean isBurning) {
        super(name, Material.IRON, 5.0F, 10.0F);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
        this.isBurning = isBurning;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MoonshinersBlocks.COPPER_FURNACE_BASIC);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState iBlockState = worldIn.getBlockState(pos.north());
        IBlockState iBlockState1 = worldIn.getBlockState(pos.south());
        IBlockState iBlockState2 = worldIn.getBlockState(pos.west());
        IBlockState iBlockState3 = worldIn.getBlockState(pos.east());

        EnumFacing enumFacing = state.getValue(FACING);

        if (enumFacing == EnumFacing.NORTH && iBlockState.isFullBlock() && !iBlockState1.isFullBlock()) enumFacing = EnumFacing.SOUTH;
        else if (enumFacing == EnumFacing.SOUTH && iBlockState1.isFullBlock() && !iBlockState.isFullBlock()) enumFacing = EnumFacing.NORTH;
        else if (enumFacing == EnumFacing.WEST && iBlockState2.isFullBlock() && !iBlockState3.isFullBlock()) enumFacing = EnumFacing.EAST;
        else if (enumFacing == EnumFacing.EAST && iBlockState3.isFullBlock() && !iBlockState2.isFullBlock()) enumFacing = EnumFacing.WEST;

        worldIn.setBlockState(pos, state.withProperty(FACING, enumFacing), 2);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(Moonshiners.instace, Reference.GUI_COPPER_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iBlockState = worldIn.getBlockState(pos);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active) {
            worldIn.setBlockState(pos, MoonshinersBlocks.COPPER_FURNACE_BASIC.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)).withProperty(BURNING, true), 3);
            worldIn.setBlockState(pos, MoonshinersBlocks.COPPER_FURNACE_BASIC.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)).withProperty(BURNING, true), 3);
        } else {
            worldIn.setBlockState(pos, MoonshinersBlocks.COPPER_FURNACE_BASIC.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)).withProperty(BURNING, false), 3);
            worldIn.setBlockState(pos, MoonshinersBlocks.COPPER_FURNACE_BASIC.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)).withProperty(BURNING, false), 3);
        }

        keepInventory = false;

        if (tileEntity != null) {
            tileEntity.validate();
            worldIn.setTileEntity(pos, tileEntity);
        }
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCopperFurnaceBasic();
    }
}
