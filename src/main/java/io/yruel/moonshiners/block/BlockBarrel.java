package io.yruel.moonshiners.block;

import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class BlockBarrel extends BlockBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockBarrel(String name, Material material, float hardness, float resistance) {
        super(name, material, hardness, resistance);
        setSoundType(SoundType.WOOD);
        setDefaultState(this.getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null) {
            NBTTagCompound nbt = tagCompound.getCompoundTag("tank");
            FluidStack fluidStack = null;
            if (!nbt.hasKey("Empty")) {
                fluidStack = FluidStack.loadFluidStackFromNBT(nbt);
            }
            if (fluidStack == null) {
                addInformationLocalized(tooltip, "message.moonshiners.tank", "empty");
            } else {
                String name = fluidStack.getLocalizedName();
                addInformationLocalized(tooltip, "message.moonshiners.tank",  name + " (" + fluidStack.amount + ")");
            }
        }
    }

    private void addInformationLocalized(List<String> tooltip, String key, Object... parameters) {
        String translated = I18n.format(key, parameters);
        translated = Pattern.compile("@", Pattern.LITERAL).matcher(translated).replaceAll("\u00a7");
        Collections.addAll(tooltip, StringUtils.split(translated, "\n"));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TileEntityBarrel) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound tagCompound = new NBTTagCompound();
            ((TileEntityBarrel) tileEntity).writeRestoreableToNBT(tagCompound);

            stack.setTagCompound(tagCompound);
            drops.add(stack);
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) {
            return true;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityBarrel) {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound != null) {
                ((TileEntityBarrel) tileEntity).readRestoreableFromNBT(compound);
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityBarrel();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing);
        }
        return true;
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
        return new BlockStateContainer(this, FACING);
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
