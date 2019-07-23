/*
 * Copyright (c) 2019 Alexander Stüben
 *
 * This file is part of Moonshiners
 *
 * Moonshiners is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mooshiners is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moonshiners.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.astueben.moonshiners;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import io.astueben.moonshiners.crafting.ShapedCrafting;
import io.astueben.moonshiners.crafting.ShapelessCrafting;
import io.astueben.moonshiners.crafting.Smelting;
import io.astueben.moonshiners.handler.ConfigurationHandler;
import io.astueben.moonshiners.proxy.IProxy;
import io.astueben.moonshiners.reference.Reference;
import io.astueben.moonshiners.register.RegisterBlocks;
import io.astueben.moonshiners.register.RegisterGenerators;
import io.astueben.moonshiners.register.RegisterItems;
import io.astueben.moonshiners.utility.LogHelper;
import net.minecraft.init.Blocks;

import java.util.Random;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.MOD_DEPENDENCIES, acceptedMinecraftVersions = Reference.MOD_MINECRAFT_VERSION, canBeDeactivated = true, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Moonshiners
{
    @Mod.Instance(Reference.MOD_ID)
    public static Moonshiners instance = new Moonshiners();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        LogHelper.info("preInitialization started");

        LogHelper.info("reading config file");
        String configDir = event.getModConfigurationDirectory().toString();
        ConfigurationHandler.init(configDir);
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

        //TODO: implement UpdateCheck

        LogHelper.info("registering items");
        RegisterItems.Ingots();

        LogHelper.info("registering tools");
        RegisterItems.Swords();
        RegisterItems.Spades();
        RegisterItems.Axes();
        RegisterItems.Pickaxes();
        RegisterItems.Hoes();

        LogHelper.info("registering blocks");
        RegisterBlocks.Ores();
        RegisterBlocks.Blocks();

        LogHelper.info("registering ore generators");
        RegisterGenerators.oreGen();

        LogHelper.info("preInitialization complete");
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {

        LogHelper.info("initialization started");

        LogHelper.info("registering smelting recipes");
        Smelting.smelting();

        LogHelper.info("registering shapeless crafting recipes");
        ShapelessCrafting.shapelessCrafting();

        LogHelper.info("registering shaped crafting recipes");
        ShapedCrafting.shapedCrafting();

        LogHelper.info("initialization complete");
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        LogHelper.info("postInitialization complete");
    }
}
