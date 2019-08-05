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

package io.yruel.moonshiners.block.item;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Collection;

public class ItemBlockMeta extends ItemColored {

    protected IProperty mappingProperty;

    public ItemBlockMeta(Block block) {
        super(block, true);
    }

    @Override
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        if(mappingProperty == null) {
            return super.getUnlocalizedName(stack);
        }

        IBlockState state = block.getStateFromMeta(stack.getMetadata());
        String name = state.getValue(mappingProperty).toString().toLowerCase();
        return super.getUnlocalizedName(stack) + "." + name;
    }

    public static void setMappingProperty(Block block, IProperty property) {
        ((ItemBlockMeta) Item.getItemFromBlock(block)).mappingProperty = property;
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModels() {
        final Item item = this;
        final ResourceLocation loc = ForgeRegistries.BLOCKS.getKey(block);

        for (Comparable o: (Collection<Comparable>) mappingProperty.getAllowedValues()) {
            int meta = block.getMetaFromState(block.getDefaultState().withProperty(mappingProperty, o));
            String name = mappingProperty.getName(o);

            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(loc, mappingProperty.getName() + "=" + name));
        }
    }
}
