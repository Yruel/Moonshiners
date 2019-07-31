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

package io.yruel.moonshiners.util.enums;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.IStringSerializable;

@MethodsReturnNonnullByDefault
public enum PlankType implements IStringSerializable {

    JUNIPER(0, "juniper");

    private static final PlankType[] META_LOOKUP = new PlankType[values().length];
    private final int meta;
    private final String unlocalizedName;

    PlankType(int meta, String unlocalizedName) {
        this.meta = meta;
        this.unlocalizedName = unlocalizedName;
    }

    @Override
    public String getName() {
        return this.unlocalizedName;
    }

    public int getMeta() {
        return this.meta;
    }

    @Override
    public String toString() {
        return this.unlocalizedName;
    }

    public static PlankType byMetadata(int meta) {
        return META_LOOKUP[meta];
    }

    static {
        for (PlankType enumType : values()) {
            META_LOOKUP[enumType.getMeta()] = enumType;
        }
    }
}
