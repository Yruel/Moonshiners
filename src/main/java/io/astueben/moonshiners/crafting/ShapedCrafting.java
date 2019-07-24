/*
 * Copyright (c) 2019 Alexander Stüben
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
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moonshiners.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.astueben.moonshiners.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import io.astueben.moonshiners.init.InitBlocks;
import io.astueben.moonshiners.init.InitItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedCrafting {
    public static void ShapedRecipes() {
        // GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(InitBlocks.blockCopper)),"III", "III", "III", 'I', InitItems.ingotCopper);
        // GameRegistry.addShapedRecipe(new ItemStack(InitItems.pickaxeCopper), "III", " S ", " S ", 'I', InitItems.ingotCopper, 'S', Items.stick);
        // GameRegistry.addShapedRecipe(new ItemStack(InitItems.axeCopper), "II ", "IS ", " S ", 'I', InitItems.ingotCopper, 'S', Items.stick);
        // GameRegistry.addShapedRecipe(new ItemStack(InitItems.hoeCopper), "II ", " S ", " S ", 'I', InitItems.ingotCopper, 'S', Items.stick);
        // GameRegistry.addShapedRecipe(new ItemStack(InitItems.spadeCopper), " I ", " S ", " S ", 'I', InitItems.ingotCopper, 'S', Items.stick);
        // GameRegistry.addShapedRecipe(new ItemStack(InitItems.swordCopper), " I ", " I ", " S ", 'I', InitItems.ingotCopper, 'S', Items.stick);
    }

    public static void ShapedOreDictionaryRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(InitBlocks.blockCopper)), "III", "III", "III", 'I', "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.pickaxeCopper), "III", " S ", " S ", 'I', "ingotCopper", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.axeCopper), "II ", "IS ", " S ", 'I', "ingotCopper", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.hoeCopper), "II ", " S ", " S ", 'I', "ingotCopper", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.spadeCopper), " I ", " S ", " S ", 'I', "ingotCopper", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.swordCopper), " I ", " I ", " S ", 'I', "ingotCopper", 'S', "stickWood"));
    }
}
