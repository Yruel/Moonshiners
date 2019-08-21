package io.yruel.moonshiners.block;

import io.yruel.moonshiners.init.MoonshinersBlocks;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class BlockBarrel extends BlockBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 4);

    public BlockBarrel(String name, Material material, float hardness, float resistance) {
        super(name, material, hardness, resistance);
        setSoundType(SoundType.WOOD);
        setDefaultState(this.getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, 0));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) facing = EnumFacing.SOUTH;
            else if (facing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) facing = EnumFacing.NORTH;
            else if (facing == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) facing = EnumFacing.EAST;
            else if (facing == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) facing = EnumFacing.WEST;
            worldIn.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        System.out.println(worldIn.getBlockState(pos).getValue(LEVEL));
        if (playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.WATER_BUCKET && worldIn.getBlockState(pos).getValue(LEVEL) != 4) {
            playerIn.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BUCKET));
            int level = worldIn.getBlockState(pos).getValue(LEVEL) + 1;
            setState(level, worldIn, pos);
        }
        return true;
    }

    public static void setState(int level, World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        worldIn.setBlockState(pos, MoonshinersBlocks.BARREL.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(LEVEL, level), 3);
    }

    @Override
    @ParametersAreNonnullByDefault
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);
        if (facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
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
