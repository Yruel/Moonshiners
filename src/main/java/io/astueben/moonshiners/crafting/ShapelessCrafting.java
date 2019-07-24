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
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapelessCrafting {

    public static void ShapelessRecipes() {
        // GameRegistry.addShapelessRecipe(new ItemStack(InitItems.ingotCopper, 9), InitBlocks.blockCopper);
    }

    public static void ShapelessOreDictionaryRecipes() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(InitItems.ingotCopper, 9), "blockCopper"));
    }
}
