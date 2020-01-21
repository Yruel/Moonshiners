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

package io.yruel.moonshiners.tab;

import io.yruel.moonshiners.init.MoonshinersFluids;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.ParametersAreNonnullByDefault;

public class CreativeTabMoonshiners extends CreativeTabs {
    public CreativeTabMoonshiners() {
        super("moonshiners");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.BAKED_POTATO);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        super.displayAllRelevantItems(items);
        items.add(FluidUtil.getFilledBucket(new FluidStack(MoonshinersFluids.FLUID_POTATO_MASH, 1000)));
        items.add(FluidUtil.getFilledBucket(new FluidStack(MoonshinersFluids.FLUID_FERMENTED_POTATO_MASH, 1000)));
    }
}
