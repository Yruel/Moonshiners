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

package io.astueben.moonshiners.init;

import io.astueben.moonshiners.enums.ToolMaterial;
import io.astueben.moonshiners.item.*;
import io.astueben.moonshiners.item.base.*;

public class InitItems {
    // ingots
    public static final BaseItem ingotCopper = new Ingot("copper_ingot");

    // swords
    public static final BaseItemSword swordCopper = new Sword("copper_sword", ToolMaterial.toolMaterialCopper);

    // axes
    public static final BaseItemAxe axeCopper = new Axe("copper_axe", ToolMaterial.toolMaterialCopper);

    // spades
    public static final BaseItemSpade spadeCopper = new Spade("copper_shovel", ToolMaterial.toolMaterialCopper);

    // pickaxes
    public static final BaseItemPickaxe pickaxeCopper = new Pickaxe("copper_pickaxe", ToolMaterial.toolMaterialCopper);

    // hoes
    public static final BaseItemHoe hoeCopper = new Hoe("copper_hoe", ToolMaterial.toolMaterialCopper);

}
