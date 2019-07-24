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

package io.astueben.moonshiners.register;

import io.astueben.moonshiners.utility.LogHelper;
import net.minecraftforge.oredict.OreDictionary;

import static io.astueben.moonshiners.init.InitBlocks.*;
import static io.astueben.moonshiners.init.InitItems.*;

public class RegisterOreDictionary {

    public static void Blocks() {
        OreDictionary.registerOre("blockCopper", blockCopper);
        OreDictionary.registerOre("oreCopper", oreCopper);
    }

    public static void Items() {
        OreDictionary.registerOre("ingotCopper", ingotCopper);
    }

    public static void printOreDictionaryList() {
        String[] oreList = OreDictionary.getOreNames();
        for (int x = 0; x < oreList.length; x++) {
            LogHelper.info(oreList[x]);
        }
    }
}
