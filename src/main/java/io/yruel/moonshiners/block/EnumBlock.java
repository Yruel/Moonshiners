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


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class EnumBlock<E extends Enum<E> & EnumBlock.IEnumMeta & IStringSerializable> extends Block {

    public final PropertyEnum<E> prop;
    private final E[] values;

    protected static PropertyEnum<?> tmp;

    public EnumBlock(Material material, PropertyEnum<E> prop, Class<E> clazz) {
        super(preInit(material, prop));
        this.prop = prop;
        values = clazz.getEnumConstants();
    }

    private static Material preInit(Material material, PropertyEnum<?> property) {
        tmp = property;
        return material;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (E type : values) {
            items.add(new ItemStack(this, 1, type.getMeta()));
        }
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        if (prop == null) {
            return new BlockStateContainer(this, tmp);
        }
        return new BlockStateContainer(this, prop);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(prop, fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(prop).getMeta();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    protected E fromMeta(int meta) {
        if (meta < 0 || meta >= values.length) {
            meta = 0;
        }
        return values[meta];
    }

    public interface IEnumMeta {
        int getMeta();
    }
}
