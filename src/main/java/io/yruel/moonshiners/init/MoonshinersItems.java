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

import io.yruel.moonshiners.armor.ArmorBase;
import io.yruel.moonshiners.item.*;
import io.yruel.moonshiners.tool.*;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MoonshinersItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    // Materials
    public static final ToolMaterial TOOL_COPPER = EnumHelper.addToolMaterial("tool_copper", 2, 200, 5.0F, 1.5F, 10);
    public static final ArmorMaterial ARMOR_COPPER = EnumHelper.addArmorMaterial("armor_copper", Reference.ID + ":copper", 13, new int[] {2, 5, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    // Items
    public static final Item COPPER_INGOT = new ItemBase("copper_ingot");

    // Tools
    public static final Item COPPER_AXE = new ToolAxe("copper_axe", TOOL_COPPER, 8F, -3.15F);
    public static final Item COPPER_HOE = new ToolHoe("copper_hoe", TOOL_COPPER);
    public static final Item COPPER_PICKAXE = new ToolPickaxe("copper_pickaxe", TOOL_COPPER);
    public static final Item COPPER_SHOVEL = new ToolShovel("copper_shovel", TOOL_COPPER);
    public static final Item COPPER_SWORD = new ToolSword("copper_sword", TOOL_COPPER);

    // Armor
    public static final Item COPPER_HELMET = new ArmorBase("copper_helmet", ARMOR_COPPER, 1, EntityEquipmentSlot.HEAD);
    public static final Item COPPER_CHESTPLATE = new ArmorBase("copper_chestplate", ARMOR_COPPER, 1, EntityEquipmentSlot.CHEST);
    public static final Item COPPER_LEGGINGS = new ArmorBase("copper_leggings", ARMOR_COPPER, 2,EntityEquipmentSlot.LEGS);
    public static final Item COPPER_BOOTS = new ArmorBase("copper_boots", ARMOR_COPPER, 2, EntityEquipmentSlot.FEET);


    /*public static final ItemCopperIngot ingotCopper = new ItemCopperIngot();
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
    }*/
}
