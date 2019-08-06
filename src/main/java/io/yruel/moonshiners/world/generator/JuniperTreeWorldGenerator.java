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

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class JuniperTreeWorldGenerator implements IWorldGenerator {

    private final WorldGenerator JUNIPER = new JuniperTreeGenerator();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 1:
                break;
            case 0:
                runGenerator(JUNIPER, world, random, chunkX, chunkZ, 3, -1, 0, BiomeForest.class, BiomeTaiga.class);
            case -1:
        }
    }
    private void runGenerator(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, double chanceToSpawn, int minHeight, int maxHeight, Class<?>... classes) {
        if (chanceToSpawn < 1) {
            if (random.nextDouble() < chanceToSpawn) chanceToSpawn = 1;
            else chanceToSpawn = 0;
        }

        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chanceToSpawn; i++) {
            BlockPos pos = new BlockPos(chunkX * 16 + 10 + random.nextInt(15), minHeight + random.nextInt(heightDiff), chunkZ * 16 + 10 + random.nextInt(15));
            if (minHeight < 0 ) pos = world.getHeight(pos);
            Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
            if (classesList.contains(biome) || classes.length == 0) generator.generate(world, random, pos);
        }

    }
}
