package io.yruel.moonshiners.item;

import io.yruel.moonshiners.init.MoonshinersItems;
import io.yruel.moonshiners.init.MoonshinersTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ItemCustomFood extends ItemFood {

    public ItemCustomFood(String name, int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(MoonshinersTabs.tab);

        MoonshinersItems.ITEMS.add(this);
    }
}
