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

package io.yruel.moonshiners.block;

import io.yruel.moonshiners.block.base.BaseBlock;
import io.yruel.moonshiners.init.MoonshinersTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCopper extends BaseBlock {
    public BlockCopper() {
        super(Material.IRON, 5.0F, 10.0F);
        setCreativeTab(MoonshinersTabs.tab);
        setSoundType(SoundType.METAL);
    }
}
