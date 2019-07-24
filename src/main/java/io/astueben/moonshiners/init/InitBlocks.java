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

import io.astueben.moonshiners.blocks.Blocks;
import io.astueben.moonshiners.blocks.FermentMachine;
import io.astueben.moonshiners.blocks.Ore;
import io.astueben.moonshiners.blocks.base.BaseBlock;

public class InitBlocks {
    public static final BaseBlock oreCopper = new Ore("copper_ore");
    public static final BaseBlock blockCopper = new Blocks("copper_block");
    public static final BaseBlock fermentMachine = new FermentMachine("ferment_machine");
}
