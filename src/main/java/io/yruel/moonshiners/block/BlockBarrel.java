package io.yruel.moonshiners.block;

import io.yruel.moonshiners.Moonshiners;
import io.yruel.moonshiners.init.MoonshinersFluids;
import io.yruel.moonshiners.init.MoonshinersItems;
import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import io.yruel.moonshiners.util.Reference;
import io.yruel.moonshiners.util.fluid.FluidUtils;
import io.yruel.moonshiners.util.interfaces.IRestorableTileEntity;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@MethodsReturnNonnullByDefault
public class BlockBarrel extends BlockBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool OPEN = PropertyBool.create("open");

    public BlockBarrel(String name) {
        super(name, Material.WOOD, 3.0F, 5.0F);
        setSoundType(SoundType.WOOD);
        setDefaultState(this.getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, true));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof IRestorableTileEntity) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound tagCompound = new NBTTagCompound();
            ((IRestorableTileEntity) tileEntity).writeRestorableFromNBT(tagCompound);
            stack.setTagCompound(tagCompound);
            drops.add(stack);
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) return true;
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        if (!state.getValue(OPEN)) {
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.COVER, 1)));
        }
        worldIn.setBlockToAir(pos);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("deprecation")
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.getItemDropped(state, RANDOM, 0));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            final TileEntityBarrel tileEntity = (TileEntityBarrel) worldIn.getTileEntity(pos);
            ItemStack item = playerIn.getHeldItem(hand);

            if (tileEntity != null && state.getValue(OPEN)) {
                if ((item.getItem() instanceof UniversalBucket && ((UniversalBucket) item.getItem()).getFluid(item).getFluid() == MoonshinersFluids.FLUID_POTATO_MASH) || item.getItem() == Items.BUCKET) {
                    IFluidHandler handler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.NORTH);
                    FluidActionResult res = FluidUtils.interactWithFluidHandler(item, handler, playerIn);
                    if (res.isSuccess()) {
                        playerIn.setHeldItem(hand, res.getResult());
                        return true;
                    }
                }
            }

            if(item.getItem() == MoonshinersItems.COVER && tileEntity != null && state.getValue(OPEN)) {
                item.shrink(1);
                worldIn.setBlockState(pos, state.withProperty(OPEN, false), 2);
                return true;
            } else if (tileEntity != null && !state.getValue(OPEN) && playerIn.isSneaking()) {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.COVER, 1)));
                worldIn.setBlockState(pos, state.withProperty(OPEN, true), 2);
                return true;
            }
            playerIn.openGui(Moonshiners.instance, Reference.GUI_BARREL, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof IRestorableTileEntity) {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound != null) {
                ((IRestorableTileEntity) tileEntity).readRestorableFromNBT(compound);
            }
        }
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

/*    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
        if (!state.getValue(OPEN)) {
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.COVER, 1)));
        }
    }*/

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityBarrel();
    }

    @Override
    @ParametersAreNonnullByDefault
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, OPEN);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);
        if (facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
}
