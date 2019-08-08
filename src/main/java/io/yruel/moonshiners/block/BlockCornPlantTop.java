package io.yruel.moonshiners.block;

import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

@MethodsReturnNonnullByDefault
public class BlockCornPlantTop extends BlockCornPlantBottom {

    public static final PropertyInteger CORN_TOP_AGE = PropertyInteger.create("age", 0, 3);

    public BlockCornPlantTop(String name) {
        super(name);
    }

    protected PropertyInteger getAgeProperty() {
        return CORN_TOP_AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == MoonshinersBlocks.PLANT_CORN_BOTTOM;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (this.isMaxAge(state)) {
                Random random = new Random();
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.CORN_SEED, random.nextInt(3) + 1)));
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.CORN, 1)));
                IBlockState cornBottom = worldIn.getBlockState(pos.down());
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                worldIn.setBlockState(pos.down(), ((BlockCornPlantBottom) cornBottom.getBlock()).withAge(0));

                return true;
            }
        }
        return false;
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (i > j) {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);
    }

    public float getGrowthChance(World par1World, int par2, int par3, int par4) {
        return 0.0F;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState cornBottom = worldIn.getBlockState(pos.down());
        return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && (cornBottom.getBlock() == MoonshinersBlocks.PLANT_CORN_BOTTOM && cornBottom.getBlock().getMetaFromState(cornBottom) >= ((BlockCornPlantBottom) cornBottom.getBlock()).getMaxAge() - 1);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        //set bottom block to air
        ((World) world).setBlockToAir(pos.down());
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        //if at max age
        if (age >= this.getMaxAge())
        {
            //drop 0-2 seeds
            for (int i = 0; i < 2 + fortune; ++i)
            {
                if (rand.nextInt(2 * getMaxAge()) <= age)
                {
                    drops.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
            //add 1-2 corn cobs
            drops.add(new ItemStack(this.getCrop(), 1, 0));
        }
        //if not ready to harvest, drops 1 corn seeds
        else
        {
            drops.add(new ItemStack(this.getSeed(), 1, 0));
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean isMaxAge(IBlockState state) {
        return super.isMaxAge(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CORN_TOP_AGE);
    }
}
