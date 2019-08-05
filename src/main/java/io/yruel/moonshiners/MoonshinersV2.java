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

import com.google.common.eventbus.Subscribe;
import io.yruel.moonshiners.block.CustomBlockPlanksV2;
import io.yruel.moonshiners.block.EnumBlock;
import io.yruel.moonshiners.block.item.ItemBlockMeta;
import io.yruel.moonshiners.proxy.CommonProxyV2;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION, /*dependencies = Reference.DEPENDENCIES,*/ acceptedMinecraftVersions = Reference.MINECRAFT_VERSION)
public class MoonshinersV2 {

    @Instance
    public static MoonshinersV2 instace;

    @SidedProxy(clientSide = "io.yruel.moonshiners.proxy.ClientProxyV2", serverSide = "io.yruel.moonshiners.proxy.CommonProxyV2")
    public static CommonProxyV2 proxy;

    public static CustomBlockPlanksV2 planks;

    @SubscribeEvent
    public void registerBlocks(Register<Block> event) {

        IForgeRegistry<Block> registry = event.getRegistry();

        planks = registerBlock(registry, new CustomBlockPlanksV2(), "planks");
    }

    @SubscribeEvent
    public void registerItems(Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        planks = registerEnumItemBlock(registry, planks, "planks");
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    private static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T block, String name) {
        String prefixedName = String.format("%s.%s", Reference.ID, name);
        block.setUnlocalizedName(prefixedName);

        register(registry, block, name);
        return block;
    }

    private static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T thing, String name) {
        thing.setRegistryName(new ResourceLocation(Reference.ID, name));
        registry.register(thing);
        return thing;
    }

    private static  <T extends EnumBlock<?>> T registerEnumItemBlock(IForgeRegistry<Item> registry, T block, String name) {
        ItemBlock itemBlock = new ItemBlockMeta(block);

        String prefixedName = String.format("%s.%s", Reference.ID, name);
        itemBlock.setUnlocalizedName(prefixedName);

        register(registry, itemBlock, name);
        ItemBlockMeta.setMappingProperty(block, block.prop);
        return block;
    }
}
