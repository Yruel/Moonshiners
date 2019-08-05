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

package io.yruel.moonshiners.proxy;

import io.yruel.moonshiners.MoonshinersV2;
import io.yruel.moonshiners.block.item.ItemBlockMeta;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxyV2 extends CommonProxyV2 {
    @Override
    protected void registerModels() {
        registerItemBlockMeta(MoonshinersV2.planks);
    }

    public static void registerItemBlockMeta(Block block) {
        if (block != null) {
            Item item = Item.getItemFromBlock(block);

            if(item instanceof ItemBlockMeta) {
                ((ItemBlockMeta) item).registerItemModels();
            }
        }
    }
}
