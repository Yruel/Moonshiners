package io.yruel.moonshiners.gui;

import io.yruel.moonshiners.container.ContainerBarrel;
import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiBarrel extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.ID + ":textures/gui/barrel.png");
    private final TileEntityBarrel tileEntity;
    private final InventoryPlayer player;

    protected Rectangle fluidBar = new Rectangle(71, 12, 16, 60);

    public GuiBarrel(InventoryPlayer player, TileEntityBarrel tileEntity) {
        super(new ContainerBarrel(player, tileEntity));
        this.tileEntity = tileEntity;
        this.player = player;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString("Barrel", 8, 6, 4210752);
        int percentage =(int) ((double) tileEntity.getClientCookTime() / 600.0 * 100);
        this.fontRenderer.drawString(percentage + " %", 105, 40, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.renderFluid();

        if (this.isPointInRegion(fluidBar.x, fluidBar.y, fluidBar.width, fluidBar.height, mouseX, mouseY)) {
            List<String> inputFluid = new ArrayList<>();
            if (tileEntity.getClientFluid() != null) {
                inputFluid.add(tileEntity.getClientFluid().getLocalizedName());
            } else {
                inputFluid.add("<Empty>");
            }

            inputFluid.add(tileEntity.getClientFluidAmount() + " / " + tileEntity.tank.getCapacity() + " MB");
            GuiUtils.drawHoveringText(inputFluid, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    private void renderFluid() {
        Fluid fluid;
        if (tileEntity.getClientFluid() != null) {
            fluid = tileEntity.getClientFluid().getFluid();
        } else {
            fluid = FluidRegistry.WATER;
        }
        TextureAtlasSprite fluidTexture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        int fluidHeightIn = (int) ((double) (tileEntity.getClientFluidAmount()) / (double) (tileEntity.tank.getCapacity()) * fluidBar.height);
        if (tileEntity.getClientFluidAmount() == 0) {
            fluidHeightIn = 0;
        }
        if (tileEntity.getClientFluidAmount() == tileEntity.tank.getCapacity()) {
            fluidHeightIn = fluidBar.height;
        }
        this.drawTexturedModalRect(this.guiLeft + fluidBar.x, this.guiTop + fluidBar.y + (fluidBar.height - fluidHeightIn), fluidTexture, fluidBar.width, fluidHeightIn);
    }
}
