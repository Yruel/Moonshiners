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

package io.yruel.moonshiners.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public class CustomBlockPlanksV2 extends EnumBlock<CustomBlockPlanksV2.PlankType> {

    public static PropertyEnum<PlankType> TYPE = PropertyEnum.create("type", PlankType.class);

    public CustomBlockPlanksV2() {
        super(Material.WOOD, TYPE, PlankType.class);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        // this.setCreativeTab();
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
    }

    public enum PlankType implements IStringSerializable, EnumBlock.IEnumMeta {
        JUNIPER, MAPLE;

        public final int meta;

        PlankType() {
            this.meta = this.ordinal();
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta() {
            return this.meta;
        }
    }
}
