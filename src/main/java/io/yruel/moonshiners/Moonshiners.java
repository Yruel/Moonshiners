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

package io.yruel.moonshiners;

import io.yruel.moonshiners.init.MoonshinersSmelting;
import io.yruel.moonshiners.proxy.CommonProxy;
import io.yruel.moonshiners.util.Reference;
import io.yruel.moonshiners.util.compat.OreDictionaryCompat;
import io.yruel.moonshiners.util.handler.GuiHandler;
import io.yruel.moonshiners.util.handler.RegistryHandler;
import io.yruel.moonshiners.util.handler.RenderHandler;
import io.yruel.moonshiners.util.network.ModPacketHandler;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION, /*dependencies = Reference.DEPENDENCIES,*/ acceptedMinecraftVersions = Reference.MINECRAFT_VERSION)
public class Moonshiners {

    @Instance
    public static Moonshiners instance;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        ModPacketHandler.registerMessage(Reference.ID);
        RegistryHandler.onFluidRegister();
        RegistryHandler.onGeneratorRegister();
        RenderHandler.registerCustomMeshesAndStates();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        MoonshinersSmelting.init();
        OreDictionaryCompat.registerOres();
        NetworkRegistry.INSTANCE.registerGuiHandler(Moonshiners.instance, new GuiHandler());
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {}
}
