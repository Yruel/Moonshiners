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

package io.yruel.moonshiners.util.handler;

import io.yruel.moonshiners.Moonshiners;
import io.yruel.moonshiners.block.*;
import io.yruel.moonshiners.block.item.*;
import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import io.yruel.moonshiners.util.enums.OreType;
import io.yruel.moonshiners.util.enums.TreeType;
import io.yruel.moonshiners.world.generator.CopperOreGenerator;
import io.yruel.moonshiners.world.generator.JuniperTreeWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(MoonshinersItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(MoonshinersBlocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item: MoonshinersItems.ITEMS) {
            if (item instanceof ItemBlockOreVariants) {
                for (int i = 0; i < OreType.values().length; i++) {
                    Moonshiners.proxy.registerItemVariantRenderer(item, i, OreType.values()[i].getName() + "_" + item.getUnlocalizedName().substring(5), "inventory");
                }
            } else if (item instanceof ItemBlockTreeVariants) {
                for (int i = 0; i < TreeType.values().length; i++) {
                    Moonshiners.proxy.registerItemVariantRenderer(item, i, TreeType.values()[i].getName() + "_" + item.getUnlocalizedName().substring(5), "inventory");
                }
            } else {
                Moonshiners.proxy.registerItemRenderer(item, 0, "inventory");
            }
        }

        for (Block block : MoonshinersBlocks.BLOCKS) {
            if (block instanceof BlockOre) {
                for (int i = 0; i < OreType.values().length; i++) {
                    Moonshiners.proxy.registerItemVariantRenderer(Item.getItemFromBlock(block), i, OreType.values()[i].getName() + "_" + block.getUnlocalizedName().substring(5), "inventory");
                }
            } else if (block instanceof CustomBlockPlanks || block instanceof CustomBlockLog || block instanceof CustomBlockLeaves || block instanceof CustomBlockSapling) {
                for (int i = 0; i < TreeType.values().length; i++) {
                    Moonshiners.proxy.registerItemVariantRenderer(Item.getItemFromBlock(block), i, TreeType.values()[i].getName() + "_" + block.getUnlocalizedName().substring(5), "inventory");
                }
            } else {
                Moonshiners.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
            }
        }
    }

    public static void otherRegistries() {
        GameRegistry.registerWorldGenerator(new CopperOreGenerator(), 0);
        GameRegistry.registerWorldGenerator(new JuniperTreeWorldGenerator(), 0);
    }
}
