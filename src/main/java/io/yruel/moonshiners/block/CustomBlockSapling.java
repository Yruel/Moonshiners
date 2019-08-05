/*
 * Copyright (c) 2019 Yruel (Alexander Stüben)
 *
 * This file is part of Moonshiners
 *
 * Moonshiners is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Moonshiners is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moonshiners.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.yruel.moonshiners.block;

import io.yruel.moonshiners.block.item.ItemBlockTreeVariants;
import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import io.yruel.moonshiners.init.MoonshinersTabs;
import io.yruel.moonshiners.util.enums.TreeType;
import io.yruel.moonshiners.util.interfaces.IMetaName;
import io.yruel.moonshiners.world.generator.MoonshinersJuniperTreeGenerator;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import sun.reflect.generics.tree.Tree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;

@MethodsReturnNonnullByDefault
public class CustomBlockSapling extends BlockBush implements IGrowable, IMetaName {

    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public static final PropertyEnum<TreeType> VARIANT = PropertyEnum.create("variant", TreeType.class);

    public CustomBlockSapling(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(MoonshinersTabs.tab);
        setSoundType(SoundType.PLANT);

        this.setDefaultState(this.getBlockState().getBaseState().withProperty(VARIANT, TreeType.JUNIPER).withProperty(STAGE, 0));

        MoonshinersBlocks.BLOCKS.add(this);
        MoonshinersItems.ITEMS.add(new ItemBlockTreeVariants(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (TreeType treeType: TreeType.values()) {
            items.add(new ItemStack(this, 1, treeType.getMeta()));
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMeta();
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return TreeType.values()[stack.getItemDamage()].getName();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, TreeType.byMetadata(0)).withProperty(STAGE, (meta & 8) >> 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(VARIANT).getMeta();
        i = i | state.getValue(STAGE) << 3;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT, STAGE);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return (double) worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (state.getValue(STAGE) == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else {
            this.generateTree(worldIn, rand, pos, state);
        }
    }

    @Override
    public void updateTick(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, pos, state, rand);

            if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(world, rand, pos, state);
            }
        }
    }

    public void generateTree(World world, Random random, BlockPos pos, IBlockState state) {
        if(!TerrainGen.saplingGrowTree(world, random, pos)) return;
        WorldGenerator generator = random.nextInt(10) == 0 ? new WorldGenBigTree(false) : new WorldGenTrees(false);
        int i = 0;
        int j = 0;
        boolean flag = false;

        switch (state.getValue(VARIANT)) {
            case JUNIPER:
                label68:

                for (i = 0; i >= -1; --i) {
                    for (j = 0; j >= -1; --j) {
                        if (this.isTwoByTwoOfType(world, pos, i, j, TreeType.JUNIPER)) {
                            // generator = new MoonshinersMegaJuniperTreeGenerator();
                            flag = true;
                            break label68;
                        }
                    }
                }

                if (!flag) {
                    i = 0;
                    j = 0;
                    generator = new MoonshinersJuniperTreeGenerator();
                }
                break;
        }

        IBlockState blockState = Blocks.AIR.getDefaultState();
        if (flag) {
            world.setBlockState(pos.add(i, 0, j), blockState, 4);
            world.setBlockState(pos.add(i + 1, 0, j), blockState, 4);
            world.setBlockState(pos.add(i, 0, j + 1), blockState, 4);
            world.setBlockState(pos.add(i + 1, 0, j + 1), blockState, 4);
        } else {
            world.setBlockState(pos, blockState, 4);
        }

        if (!generator.generate(world, random, pos.add(i, 0, j))) {
            if (flag) {
                world.setBlockState(pos.add(i, 0, j), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j), state, 4);
                world.setBlockState(pos.add(i, 0, j + 1), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            } else {
                world.setBlockState(pos, blockState, 4);
            }
        }

        generator.generate(world, random, pos);
    }

    private boolean isTwoByTwoOfType(World world, BlockPos pos, int i, int j, TreeType type) {
        return this.isTypeAt(world, pos.add(i, 0, j), type) && this.isTypeAt(world, pos.add(i + 1, 0, j), type) && this.isTypeAt(world, pos.add(i, 0, j + 1), type) && this.isTypeAt(world, pos.add(i + 1, 0, j + 1), type);
    }

    public boolean isTypeAt(World world, BlockPos pos, TreeType type) {
        IBlockState iBlockState = world.getBlockState(pos);
        return iBlockState.getBlock() == this && iBlockState.getValue(VARIANT) == type;
    }
}
