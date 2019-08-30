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

package io.yruel.moonshiners.util;

public class Reference {
    public static final String ID = "moonshiners";
    public static final String NAME = "Moonshiners";
    public static final String VERSION = "0.0.1";
    public static final String DEPENDENCIES = "required-after:Forge@[14.23.5.2838,)";
    public static final String MINECRAFT_VERSION = "1.12.2";
    public static final String CLIENT_PROXY_CLASS = "io.yruel.moonshiners.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "io.yruel.moonshiners.proxy.CommonProxy";
    public static final int GUI_COPPER_FURNACE = 0;
    public static final int GUI_BARREL = 1;
}
