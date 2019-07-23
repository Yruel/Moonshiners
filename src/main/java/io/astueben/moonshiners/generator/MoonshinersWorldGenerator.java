/*
 * Copyright (c) Alexander Stüben
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moonshiners.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.astueben.moonshiners.generator;

import cpw.mods.fml.common.IWorldGenerator;
import io.astueben.moonshiners.init.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class MoonshinersWorldGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
            case 0:
                generateOverworld(world, random, chunkX * 16, chunkZ * 16);
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
        }
        generateAll(world, random, chunkX * 16, chunkZ * 16);
    }

    private void generateNether(World world, Random random, int x, int z) {}
    private void generateOverworld(World world, Random random, int x, int z) {
        addOreSpawn(InitBlocks.oreCopper, world, random, x, z, 16, 16, 4, 10 , 77, 1, 70);
    }
    private void generateEnd(World world, Random random, int x, int z) {}
    private void generateAll(World world, Random random, int x, int z) {}

    public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int minVainSize, int maxVienSize, int chanceToSpawn, int minY, int maxY) {
        for (int i = 0; i < chanceToSpawn; i++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);

            (new WorldGenMinable(block, random.nextInt(maxVienSize - minVainSize + 1) + minVainSize)).generate(world, random, posX, posY, posZ);
        }
    }
}
