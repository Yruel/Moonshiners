package io.yruel.moonshiners.block;

import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Objects;

public class BlockBarleyPlant extends BlockCrops {

    public BlockBarleyPlant(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);

        MoonshinersBlocks.BLOCKS.add(this);
        MoonshinersItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(getRegistryName())));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (this.isMaxAge(state)) {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MoonshinersItems.BARLEY_SEED, 2)));
                worldIn.setBlockState(pos, this.withAge(0));
                return true;
            }
        }

        return false;
    }

    @Override
    protected Item getSeed() {
        return MoonshinersItems.BARLEY_SEED;
    }

    @Override
    protected Item getCrop() {
        return MoonshinersItems.BARLEY_SEED;
    }
}
