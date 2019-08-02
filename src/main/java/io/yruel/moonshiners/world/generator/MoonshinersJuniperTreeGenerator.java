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

package io.yruel.moonshiners.world.generator;

import io.yruel.moonshiners.block.CustomBlockLeaves;
import io.yruel.moonshiners.block.CustomBlockLog;
import io.yruel.moonshiners.block.CustomBlockSapling;
import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.util.enums.TreeType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class MoonshinersJuniperTreeGenerator extends WorldGenAbstractTree {

    public static final IBlockState LOG = MoonshinersBlocks.BLOCK_LOG.getDefaultState().withProperty(CustomBlockLog.VARIANT, TreeType.JUNIPER);
    public static final IBlockState LEAVES = MoonshinersBlocks.BLOCK_LEAVES.getDefaultState().withProperty(CustomBlockLeaves.VARIANT, TreeType.JUNIPER);

    private int minHeight;

    public MoonshinersJuniperTreeGenerator() {
        super(false);
        this.minHeight = 12;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        int height = this.minHeight + rand.nextInt(3);
        boolean flag = true;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int yPos = y; yPos <= y + 1 + height; yPos++) {
            int b0 = 2;
            if(yPos == y) b0 = 1;
            if (yPos >= y + 1 + height) b0 = 2;

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for (int xPos = x - b0; xPos <= x + b0 && flag; xPos++) {
                for (int zPos = z - b0; zPos <= z + b0 && flag; zPos++) {
                    if (yPos >= 0 && yPos < world.getHeight()) {
                        if (!this.isReplaceable(world, new BlockPos(xPos, yPos, zPos))) {
                            flag = false;
                        }
                    } else {
                        flag = false;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        } else {
            BlockPos down = pos.down();
            IBlockState state = world.getBlockState(down);
            boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, (CustomBlockSapling) MoonshinersBlocks.BLOCK_SAPLING);

            if (isSoil && y < world.getHeight() - height - 1) {
                state.getBlock().onPlantGrow(state, world, down, pos);
                for (int yPos = y - 3 + height; yPos <= y + height; yPos++) {
                    int b1 = yPos - (y + height);
                    int b2 = 1 - b1 / 2;

                    for (int xPos = x - b2; xPos <= x + b2; xPos++) {
                        int b3 = xPos - x;
                        for (int zPos = z - b2; zPos <= z + b2; zPos++) {
                            int b4 = zPos - z;
                            if (Math.abs(b3) != b2 || Math.abs(b4) != b2 || rand.nextInt(2) != 0 && b1 != 0) {
                                BlockPos treePos = new BlockPos(xPos, yPos, zPos);
                                IBlockState treeState = world.getBlockState(treePos);
                                if (treeState.getBlock().isAir(treeState, world, treePos) || treeState.getBlock().isLeaves(treeState, world, treePos)) {
                                    this.setBlockAndNotifyAdequately(world, treePos, LEAVES);
                                    this.setBlockAndNotifyAdequately(world, treePos.add(0, -0.25 * height, 0), LEAVES);
                                    this.setBlockAndNotifyAdequately(world, treePos.add(0, -0.5 * height, 0), LEAVES);
                                }
                            }
                        }
                    }
                }

                for (int logHeight = 0; logHeight < height; logHeight++) {
                    BlockPos up = pos.up(logHeight);
                    IBlockState logState = world.getBlockState(up);

                    if (logState.getBlock().isAir(logState, world, up) || logState.getBlock().isLeaves(logState, world, up)){
                        this.setBlockAndNotifyAdequately(world, pos.up(logHeight), LOG);
                    }
                }

                return true;
            }
        }

        return true;
    }
}
