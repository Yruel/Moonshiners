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

import io.yruel.moonshiners.enums.ToolMaterial;
import io.yruel.moonshiners.item.*;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class MoonshinersItems {
    public static final ItemCopperIngot ingotCopper = new ItemCopperIngot();
    public static final ItemCopperPickaxe itemPickaxeCopper = new ItemCopperPickaxe(ToolMaterial.COPPER);
    public static final ItemCopperAxe itemCopperAxe = new ItemCopperAxe(ToolMaterial.COPPER);
    public static final ItemCopperHoe itemCopperHoe = new ItemCopperHoe(ToolMaterial.COPPER);
    public static final ItemCopperSpate itemCopperSpate = new ItemCopperSpate(ToolMaterial.COPPER);
    public static final ItemCopperSword itemCopperSword = new ItemCopperSword(ToolMaterial.COPPER);

    public static void init() {
        setName(ingotCopper, "copper_ingot");
        setName(itemPickaxeCopper, "copper_pickaxe");
        setName(itemCopperAxe, "copper_axe");
        setName(itemCopperHoe, "copper_hoe");
        setName(itemCopperSpate, "copper_shovel");
        setName(itemCopperSword, "copper_sword");
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(ingotCopper);
        registry.register(itemPickaxeCopper);
        registry.register(itemCopperAxe);
        registry.register(itemCopperHoe);
        registry.register(itemCopperSpate);
        registry.register(itemCopperSword);
    }

    public static void setName(Item item, String name) {
        item.setRegistryName(new ResourceLocation(Reference.ID, name));
        item.setUnlocalizedName(name);
    }
}
