package io.yruel.moonshiners.util.handler;

import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.util.Reference;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
public class RenderHandler {

    public static void registerCustomMeshesAndStates() {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(MoonshinersBlocks.BLOCK_POTATO_MASH), new ItemMeshDefinition() {
            @Override
            @ParametersAreNonnullByDefault
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(Reference.ID + ":potato_mash", "fluid");
            }
        });

        ModelLoader.setCustomStateMapper(MoonshinersBlocks.BLOCK_POTATO_MASH, new StateMapperBase() {
            @Override
            @ParametersAreNonnullByDefault
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(Reference.ID + ":potato_mash", "fluid");
            }
        });
    }
}
