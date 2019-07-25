/*
 * Copyright (c) 2019 Alexander Stüben
 *
 * This file is part of Moonshiners.
 *
 * Moonshiners is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Moonshiners is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moonshiners.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.astueben.moonshiners.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FermentMachine extends Blocks {
    public FermentMachine(String unlocalizedName) {
        super(unlocalizedName, Material.iron, 5F, 10F);
    }

    @SideOnly(Side.CLIENT)
    public static IIcon frontIcon;

    @SideOnly(Side.CLIENT)
    public static IIcon mainIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        frontIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnLocalizedName(this.getUnlocalizedName()) + "_front"));
        mainIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnLocalizedName(this.getUnlocalizedName()) + "_main"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        if (side == 3) {
            return frontIcon;
        } else {
            return mainIcon;
        }
    }

/*    @Override
    @SideOnly(Side.CLIENT)
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);

        if(!world.isRemote) {
            Block direction0 = world.getBlock(x, y, z - 1);
            Block direction1 = world.getBlock(x, y, z + 1);
            Block direction2 = world.getBlock(x - 1, y, z);
            Block direction3 = world.getBlock(x + 1, y, z);

            byte side = 3;

            if(direction0.func_149730_j() && !direction1.func_149730_j()) {
                side = 3;
            }

            if(direction1.func_149730_j() && !direction0.func_149730_j()) {
                side = 2;
            }

            if(direction2.func_149730_j() && !direction3.func_149730_j()) {
                side = 5;
            }

            if(direction3.func_149730_j() && !direction2.func_149730_j()) {
                side = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, side, 2);
        }
    }*/

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        Block block0 = world.getBlock(x, y, z - 1);
        Block block1 = world.getBlock(x, y, z + 1);
        Block block2 = world.getBlock(x - 1, y, z);
        Block block3 = world.getBlock(x + 1, y, z);

        byte side = 0;

        int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (direction == 0) {
            side = 2;
        }

        if (direction == 1) {
            side = 5;
        }

        if (direction == 2) {
            side = 3;
        }

        if (direction == 3) {
            side = 4;
        }

        if (block0 != this && block1 != this && block2 != this && block3 != this) {
            world.setBlockMetadataWithNotify(x, y, z, side, 3);
        } else {
            if ((block0 == this || block1 == this) && (side == 4 || side == 5)) {
                if (block0 == this) {
                    world.setBlockMetadataWithNotify(x, y, z - 1, side, 3);
                } else {
                    world.setBlockMetadataWithNotify(x, y, z + 1, side, 3);
                }
                world.setBlockMetadataWithNotify(x, y, z, side, 3);
            }

            if ((block2 == this || block3 == this) && (side == 2 || side == 3)) {
                if (block2 == this) {
                    world.setBlockMetadataWithNotify(x - 1, y, z, side, 3);
                } else {
                    world.setBlockMetadataWithNotify(x + 1, y, z, side, 3);
                }
                world.setBlockMetadataWithNotify(x, y, z, side, 3);
            }
        }
    }
}
