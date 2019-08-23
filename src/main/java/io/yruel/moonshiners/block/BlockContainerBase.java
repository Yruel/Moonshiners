package io.yruel.moonshiners.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockContainerBase extends BlockBase {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    BlockContainerBase(String name, Material material, float hardness, float resistance) {
        super(name, material, hardness, resistance);
        this.hasTileEntity = true;
        setDefaultState(hasFacing() ? getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH) : getBlockState().getBaseState());
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest ? true : super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        //
    }

    protected boolean dropInventory() {
        return false;
    }

    public boolean hasFacing() {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, hasFacing() ? new IProperty<?>[]{FACING} : new IProperty<?>[0]);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return hasFacing() ? getDefaultState().withProperty(FACING, EnumFacing.getFront(meta)) : getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return hasFacing() ? state.getValue(FACING).getIndex() : 0;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        if (hasComparatorInputOverride(blockState)) {
            TileEntity tile = worldIn.getTileEntity(pos);
            /*if (tile instanceof TileBase) {
                return ((TileBase) tile).getComparatorLevel();
            }*/
        }
        return super.getComparatorInputOverride(blockState, worldIn, pos);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tile = worldIn.getTileEntity(pos);
        return tile != null && tile.receiveClientEvent(id, param);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return hasFacing() ? getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()) : getDefaultState();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return hasFacing() ? state.withProperty(FACING, rot.rotate(state.getValue(FACING))) : state;
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return hasFacing() ? state.withRotation(mirrorIn.toRotation(state.getValue(FACING))) : state;
    }
}
