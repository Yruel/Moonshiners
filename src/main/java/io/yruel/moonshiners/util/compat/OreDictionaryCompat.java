package io.yruel.moonshiners.util.compat;

import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCompat {
    public static void registerOres() {
        /*OreDictionary.registerOre("ingotCopper", new ItemStack(MoonshinersItems.COPPER_INGOT));
        OreDictionary.registerOre("cropWheat", MoonshinersItems.BARLEY);*/
        OreDictionary.registerOre("plankWood", new ItemStack(MoonshinersBlocks.BLOCK_PLANKS, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(MoonshinersBlocks.BLOCK_PLANKS, 1, 1));
        OreDictionary.registerOre("treeSapling", new ItemStack(MoonshinersBlocks.BLOCK_SAPLING, 1, 0));
        OreDictionary.registerOre("treeSapling", new ItemStack(MoonshinersBlocks.BLOCK_SAPLING, 1, 1));
        OreDictionary.registerOre("logWood", new ItemStack(MoonshinersBlocks.BLOCK_LOG, 1, 0));
        OreDictionary.registerOre("logWood", new ItemStack(MoonshinersBlocks.BLOCK_LOG, 1, 1));
        OreDictionary.registerOre("treeLeaves", new ItemStack(MoonshinersBlocks.BLOCK_LEAVES, 1, 0));
        OreDictionary.registerOre("treeLeaves", new ItemStack(MoonshinersBlocks.BLOCK_LEAVES, 1, 1));
    }
}
