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

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MoonshinersModels {

    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {

        // Item Models
        /*register(MoonshinersItems.ingotCopper);
        register(MoonshinersItems.itemPickaxeCopper);
        register(MoonshinersItems.itemCopperAxe);
        register(MoonshinersItems.itemCopperHoe);
        register(MoonshinersItems.itemCopperSpate);
        register(MoonshinersItems.itemCopperSword);*/

        // Block Models
        /*register(Item.getItemFromBlock(MoonshinersBlocks.blockCopper));
        register(Item.getItemFromBlock(MoonshinersBlocks.oreCopper));
        register(Item.getItemFromBlock(MoonshinersBlocks.fermenter));*/
    }

    private static void register(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
