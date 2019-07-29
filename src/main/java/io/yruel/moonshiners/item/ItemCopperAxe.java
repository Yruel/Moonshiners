/*
 * Copyright (c) 2019 Yruel (Alexander Stüben)
 *
 * This file is part of Moonshiners
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

package io.yruel.moonshiners.item;

import io.yruel.moonshiners.init.MoonshinersTabs;
import io.yruel.moonshiners.item.base.BaseItemAxe;
import io.yruel.moonshiners.item.base.BaseItemPickaxe;

public class ItemCopperAxe extends BaseItemAxe {
    public ItemCopperAxe(ToolMaterial material) {
        super(material, 8F, -3.15F);
        setCreativeTab(MoonshinersTabs.tab);
    }
}
