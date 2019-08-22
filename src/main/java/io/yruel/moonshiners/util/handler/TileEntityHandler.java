package io.yruel.moonshiners.util.handler;

import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import io.yruel.moonshiners.tileentity.TileEntityCopperFurnace;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCopperFurnace.class, new ResourceLocation(Reference.ID + ":copper_furnace"));
        GameRegistry.registerTileEntity(TileEntityBarrel.class, new ResourceLocation(Reference.ID + ":barrel"));
    }
}
