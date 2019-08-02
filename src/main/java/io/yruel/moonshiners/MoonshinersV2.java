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

package io.yruel.moonshiners;

import io.yruel.moonshiners.proxy.CommonProxy;
import io.yruel.moonshiners.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION, /*dependencies = Reference.DEPENDENCIES,*/ acceptedMinecraftVersions = Reference.MINECRAFT_VERSION)
public class MoonshinersV2 {

    @Instance
    public static Moonshiners instace;

    @SidedProxy(clientSide = "io.yruel.moonshiners.proxy.ClientProxyV2", serverSide = "io.yruel.moonshiners.proxy.CommonProxyV2")
    public static CommonProxy proxy;
}
