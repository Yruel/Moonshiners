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

import io.yruel.moonshiners.block.item.ItemBlockPlanksVariants;
import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import io.yruel.moonshiners.init.MoonshinersTabs;
import io.yruel.moonshiners.util.enums.TreeType;
import io.yruel.moonshiners.util.interfaces.IMetaName;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
public class CustomBlockPlanks extends Block implements IMetaName {
    public static final PropertyEnum<TreeType> VARIANT = PropertyEnum.create("variant", TreeType.class);

    public CustomBlockPlanks(String name) {
        super(Material.WOOD);

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(MoonshinersTabs.tab);

        setHardness(2.0F);
        setResistance(5.0F);
        setSoundType(SoundType.WOOD);

        setDefaultState(this.getBlockState().getBaseState().withProperty(VARIANT, TreeType.JUNIPER));

        MoonshinersBlocks.BLOCKS.add(this);
        MoonshinersItems.ITEMS.add(new ItemBlockPlanksVariants(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (TreeType treeType : TreeType.values()) {
            items.add(new ItemStack(this, 1, treeType.getMeta()));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMeta();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMeta();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, TreeType.byMetadata(meta));
    }

    @Override
    @ParametersAreNonnullByDefault
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return TreeType.values()[stack.getItemDamage()].getName();
    }
}
