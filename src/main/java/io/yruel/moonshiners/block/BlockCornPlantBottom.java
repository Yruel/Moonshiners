package io.yruel.moonshiners.block;

import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;

@MethodsReturnNonnullByDefault
public class BlockCornPlantBottom extends BlockCrops {

    public static final PropertyInteger CORN_AGE = PropertyInteger.create("age", 0, 3);
    private static final AxisAlignedBB[] CORN_BOTTOM_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.2D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.8D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

    public BlockCornPlantBottom(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);

        MoonshinersBlocks.BLOCKS.add(this);
        MoonshinersItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(getRegistryName())));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CORN_BOTTOM_AABB[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected PropertyInteger getAgeProperty() {
        return CORN_AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            IBlockState cornTop = worldIn.getBlockState(pos.up());
            if (cornTop.getBlock() == Blocks.AIR) return false;
            if (this.isMaxAge(state) && ((BlockCornPlantTop) cornTop.getBlock()).getAge(cornTop) >= ((BlockCornPlantTop) cornTop.getBlock()).getMaxAge()) {
                Random random = new Random();
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.CORN_SEED, random.nextInt(3) + 1)));
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.BARLEY, 1)));
                worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
                worldIn.setBlockState(pos, this.withAge(0));
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {

            float f = getGrowthChance(this, worldIn, pos);
            if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0))
            {
                int i = this.getAge(state);

                if (i < this.getMaxAge() - 1)
                {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
                // if the block's metadata is maxed out
                else
                {
                    // If the block above the bottom corn block is air, sets the block above to the corn crop top block
                    if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR)
                    {
                        worldIn.setBlockState(pos.up(), MoonshinersBlocks.PLANT_CORN_TOP.getDefaultState());
                        worldIn.setBlockState(pos, this.withAge(this.getMaxAge()), 2);
                    }
                    // If the block above the bottom corn block is a top corn block and isn't maxed out on metadata, +1 to the metadata of the block above
                    else if (worldIn.getBlockState(pos.up()).getBlock() == MoonshinersBlocks.PLANT_CORN_TOP)
                    {
                        IBlockState cornTop = worldIn.getBlockState(pos.up());
                        if (((BlockCornPlantTop) cornTop.getBlock()).getAge(cornTop) < ((BlockCornPlantTop) cornTop.getBlock()).getMaxAge())
                        {
                            IBlockState topState = worldIn.getBlockState(pos.up());
                            BlockCornPlantTop topBlock = (BlockCornPlantTop) worldIn.getBlockState(pos.up()).getBlock();
                            int topAge = topBlock.getAge(topState);
                            worldIn.setBlockState(pos.up(), topBlock.withAge(topAge + 1), 2);
                            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos.up(), topState, topState);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge() - 1;
        int excessAge = i - j;

        if (excessAge <= 0)
        {
            worldIn.setBlockState(pos, this.withAge(i), 2);
        }
        //if bonemeal goes over max age, add excess age to corn block top above
        else
        {
            //if block above is air, set it to cornTop
            if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR)
            {
                worldIn.setBlockState(pos.up(), MoonshinersBlocks.PLANT_CORN_TOP.getDefaultState());
                worldIn.setBlockState(pos, this.withAge(this.getMaxAge()), 2);
                //subtract 1 from excess age
                --excessAge;
            }
            //add excessAge to current cornTop age
            if (excessAge > 0 && worldIn.getBlockState(pos.up()).getBlock() == MoonshinersBlocks.PLANT_CORN_TOP)
            {
                IBlockState cornTopState = worldIn.getBlockState(pos.up());
                BlockCornPlantTop cornTopBlock = (BlockCornPlantTop) worldIn.getBlockState(pos.up()).getBlock();
                int cornTopAge = cornTopBlock.getAge(cornTopState);
                if (cornTopAge < cornTopBlock.getMaxAge())
                {
                    int newAge = cornTopAge + excessAge;
                    if (newAge > cornTopBlock.getMaxAge()) newAge = cornTopBlock.getMaxAge();
                    worldIn.setBlockState(pos.up(), cornTopBlock.withAge(newAge), 2);
                }
            }
        }
    }

    public static float getGrowthChance(@Nonnull Block blockIn, World worldIn, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.down();

        for (int i = -1; i <= 1; ++i)
        {
            for (int j = -1; j <= 1; ++j)
            {
                float f1 = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, blockpos.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)blockIn))
                {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(worldIn, blockpos.add(i, 0, j)))
                    {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();

        if (flag && flag1)
        {
            f /= 2.0F;
        }
        else
        {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();

            if (flag2)
            {
                f /= 2.0F;
            }
        }
        return f;
    }

    @Override
    protected Item getSeed() {
        return MoonshinersItems.CORN_SEED;
    }

    @Override
    protected Item getCrop() {
        return MoonshinersItems.BARLEY;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int age = getAge(state);
        Random random = new Random();
        if (age < getMaxAge()) {
            drops.add(new ItemStack(this.getSeed(), 1, 0));
        } else {
            drops.add(new ItemStack(this.getSeed(), random.nextInt(3) + 1, 0));
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        IBlockState upState = worldIn.getBlockState(pos.up());
        return this.getAge(state) <= this.getMaxAge() - 1 || (upState.getBlock() == Blocks.AIR || upState.getBlock() == MoonshinersBlocks.PLANT_CORN_TOP && !((BlockCornPlantTop) upState.getBlock()).isMaxAge(upState));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CORN_AGE);
    }
}
