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

package io.yruel.moonshiners.proxy;

import io.yruel.moonshiners.generator.MoonshinersGenerator;
import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import io.yruel.moonshiners.init.MoonshinersSmelting;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void registerItemRenderer(Item item, int meta, String id) {}

    public void preInit(FMLPreInitializationEvent event) {
       /* MoonshinersBlocks.init();*/

        /*MinecraftForge.EVENT_BUS.register(MoonshinersItems.class);
        MinecraftForge.EVENT_BUS.register(MoonshinersBlocks.class);*/

        /*GameRegistry.registerWorldGenerator(new MoonshinersGenerator(), 0);*/
    }
    public void init(FMLInitializationEvent event) {
        MoonshinersSmelting.init();
    }
    public void postInit(FMLPostInitializationEvent event) {}
}
