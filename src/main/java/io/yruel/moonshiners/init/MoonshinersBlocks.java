/*
 * Copyright (c) 2019 Yruel (Alexander Stüben)
 *
 *  This file is part of Moonshiners
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

package io.yruel.moonshiners.init;

import io.yruel.moonshiners.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class MoonshinersBlocks {

    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block BLOCK_COPPER = new BlockBase("copper_block", Material.IRON, 5.0F, 10.0F);
    public static final Block BLOCK_ORE = new BlockOre("ore");

    public static final Block BLOCK_PLANKS = new CustomBlockPlanks("planks");
    public static final Block BLOCK_LOG = new CustomBlockLog("log");
    public static final Block BLOCK_LEAVES = new CustomBlockLeaves("leaves");
   /* public static final Block BLOCK_SAPLING = new CustomBlockSapling("sapling");*/
}
