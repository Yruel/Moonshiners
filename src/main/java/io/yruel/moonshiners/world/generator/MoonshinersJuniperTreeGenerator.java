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

import io.yruel.moonshiners.block.CustomBlockLog;
import io.yruel.moonshiners.block.CustomBlockSapling;
import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.util.enums.TreeType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import javax.annotation.Nonnull;
import java.util.Random;

public class MoonshinersJuniperTreeGenerator extends WorldGenAbstractTree {

    public static final IBlockState LOG = MoonshinersBlocks.BLOCK_LOG.getDefaultState().withProperty(CustomBlockLog.VARIANT, TreeType.JUNIPER);
    public static final IBlockState LEAF = MoonshinersBlocks.BLOCK_LEAVES.getDefaultState().withProperty(CustomBlockLog.VARIANT, TreeType.JUNIPER);

    private int minHeight;

    public MoonshinersJuniperTreeGenerator() {
        super(false);
        this.minHeight = 9;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position) {
        int height = this.minHeight;
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= world.getHeight()) {
            for (int y = position.getY(); y <= position.getY() + 1 + height; y++) {
                int k = 1;
                if (y == position.getY()) k = 0;
                if (y >= position.getY() + 1 + height - 2) k = 2;

                BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

                for (int x = position.getX() - k; x <= position.getX() + k && flag; x++) {
                    for (int z = position.getZ() - k; z <= position.getZ() + k && flag; z++) {
                        if (y >= 0 && y < world.getHeight()) {
                            if (!this.isReplaceable(world, mutable.setPos(x, y, z))) {
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
                IBlockState state = world.getBlockState(position.down());

                if (state.getBlock().canSustainPlant(state, world, position.down(), EnumFacing.UP, (CustomBlockSapling) MoonshinersBlocks.BLOCK_SAPLING) && position.getY() < world.getHeight() - height - 1) {
                    state.getBlock().onPlantGrow(state, world, position.down(), position);

                    for (int y = position.getY() - 8 + height; y <= position.getY() + height; y++) {
                        if (y == position.getY() + 1) {
                            this.setBlockAndNotifyAdequately(world, position.add(1, 1, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 1, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 1, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 1, -1), LEAF);
                        }
                        if (y == position.getY() + 2) {
                            this.setBlockAndNotifyAdequately(world, position.add(1, 2, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(2, 2, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 2, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-2, 2, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 2, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 2, 2), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 2, -1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 2, -2), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(1, 2, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(1, 2, -1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 2, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 2, -1), LEAF);
                        }
                        if (y == position.getY() + 3) {
                            this.setBlockAndNotifyAdequately(world, position.add(1, 3, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(2, 3, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 3, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-2, 3, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 3, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 3, 2), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 3, -1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 3, -2), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(1, 3, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(1, 3, -1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 3, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 3, -1), LEAF);
                        }
                        if (y == position.getY() + 4) {
                            this.setBlockAndNotifyAdequately(world, position.add(1, 4, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(2, 4, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 4, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-2, 4, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 4, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 4, 2), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 4, -1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 4, -2), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(1, 4, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(1, 4, -1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 4, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 4, -1), LEAF);
                        }
                        if (y == position.getY() + 5) {
                            this.setBlockAndNotifyAdequately(world, position.add(1, 5, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 5, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 5, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 5, -1), LEAF);
                        }
                        if (y == position.getY() + 6) {
                            this.setBlockAndNotifyAdequately(world, position.add(1, 6, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 6, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 6, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 6, -1), LEAF);
                        }
                        if (y == position.getY() + 7) {
                            this.setBlockAndNotifyAdequately(world, position.add(1, 7, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(-1, 7, 0), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 7, 1), LEAF);
                            this.setBlockAndNotifyAdequately(world, position.add(0, 7, -1), LEAF);
                        }
                        if (y == position.getY() + 8) {
                            this.setBlockAndNotifyAdequately(world, position.add(0, 8, 0), LEAF);
                        }
                    }

                    /*for (int y = position.getY() - 8 + height; y <= position.getY() + height; y++) {
                        int b1 = y - (position.getY() + height);
                        int b2 = 1 - b1 / 4;

                        for (int x = position.getX() - b2; x <= position.getX() + b2; x++) {
                            int b3 = x - position.getX();

                            for (int z = position.getZ() - b2; z <= position.getZ() + b2; z++) {
                                int b4 = z - position.getZ();

                                if (Math.abs(b3) != b2 || Math.abs(b4) != b2 || rand.nextInt(2) != 0 && b1 != 0) {
                                    BlockPos blockPos = new BlockPos(x, y, z);
                                    state = world.getBlockState(blockPos);

                                    if (state.getBlock().isAir(state, world, blockPos) || state.getBlock().isLeaves(state, world, blockPos) || state.getMaterial() == Material.VINE) {
                                        this.setBlockAndNotifyAdequately(world, blockPos, LEAF);
                                    }
                                }
                            }
                        }
                    }*/

                    for (int logHeight = 0; logHeight < height - 1; logHeight++) {
                        BlockPos up = position.up(logHeight);
                        state = world.getBlockState(up);

                        if (state.getBlock().isAir(state, world, up) || state.getBlock().isLeaves(state, world, up) || state.getMaterial() == Material.VINE) {
                            this.setBlockAndNotifyAdequately(world, position.up(logHeight), LOG);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
