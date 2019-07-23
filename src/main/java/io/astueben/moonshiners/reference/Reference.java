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

package io.astueben.moonshiners.reference;

public class Reference {
    public static final String MOD_ID = "moonshiners";
    public static final String MOD_VERSION = "0.0.1";
    public static final String MOD_NAME = "Moonshiners";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[10.13.4.1614,)";
    public static final String MOD_MINECRAFT_VERSION = "1.7.10";

    public static final String CLIENT_PROXY_CLASS = "io.astueben.moonshiners.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "io.astueben.moonshiners.proxy.ServerProxy";
    public static final String GUI_FACTORY_CLASS = "io.astueben.moonshiners.client.gui.GuiFactory";
}
