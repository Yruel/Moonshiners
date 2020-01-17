package io.yruel.moonshiners.util.handler;

import io.yruel.moonshiners.container.ContainerBarrel;
import io.yruel.moonshiners.container.ContainerCopperFurnace;
import io.yruel.moonshiners.gui.GuiBarrel;
import io.yruel.moonshiners.gui.GuiCopperFurnace;
import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import io.yruel.moonshiners.tileentity.TileEntityCopperFurnace;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.lwjgl.Sys;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Reference.GUI_COPPER_FURNACE) return new ContainerCopperFurnace(player.inventory, (TileEntityCopperFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        else if (ID == Reference.GUI_BARREL) return new ContainerBarrel(player.inventory, (TileEntityBarrel) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Reference.GUI_COPPER_FURNACE) return new GuiCopperFurnace(player.inventory, (TileEntityCopperFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        else if (ID == Reference.GUI_BARREL) {
            System.out.println(((TileEntityBarrel) world.getTileEntity(new BlockPos(x, y, z))).getClientFluidInAmount());
            return new GuiBarrel(player.inventory, (TileEntityBarrel) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
