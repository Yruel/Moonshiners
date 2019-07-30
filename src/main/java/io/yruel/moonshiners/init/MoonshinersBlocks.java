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

import io.yruel.moonshiners.block.BlockCopper;
import io.yruel.moonshiners.block.Fermenter;
import io.yruel.moonshiners.block.OreCopper;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class MoonshinersBlocks {
    public static final BlockCopper blockCopper = new BlockCopper();
    public static final OreCopper oreCopper = new OreCopper();
    public static final Fermenter fermenter = new Fermenter();

    public static void init() {
        setName(blockCopper, "copper_block");
        setName(oreCopper, "copper_ore");
        setName(fermenter, "fermenter");
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(blockCopper);
        registry.register(oreCopper);
        registry.register(fermenter);
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(new ItemBlock(blockCopper).setRegistryName(blockCopper.getRegistryName()));
        registry.register(new ItemBlock(oreCopper).setRegistryName(oreCopper.getRegistryName()));
        registry.register(new ItemBlock(fermenter).setRegistryName(fermenter.getRegistryName()));
    }

    public static void setName(Block block, String name) {
        block.setRegistryName(new ResourceLocation(Reference.ID, name));
        block.setUnlocalizedName(name);
    }
}
