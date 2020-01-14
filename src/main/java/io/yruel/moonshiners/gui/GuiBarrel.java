package io.yruel.moonshiners.gui;

import io.yruel.moonshiners.container.ContainerBarrel;
import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiBarrel extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.ID + ":textures/gui/barrel.png");
    private final InventoryPlayer player;
    private final TileEntityBarrel tileEntity;

    protected Rectangle fluidBar = new Rectangle(36, 73, 16, 60);

    public GuiBarrel(InventoryPlayer player, TileEntityBarrel tileEntity) {
        super(new ContainerBarrel(player, tileEntity));
        this.player = player;
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.renderFluid();

        if (this.isPointInRegion(36, 73, 16, 60, mouseX, mouseY)) {
            List<String> inputFluid = new ArrayList<>();
            inputFluid.add(tileEntity.getTank(0).getFluidAmount() + " / " + tileEntity.getTank(0).getCapacity() + " MB");
            GuiUtils.drawHoveringText(inputFluid, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    private void renderFluid() {
        Fluid fluid = FluidRegistry.WATER;
        TextureAtlasSprite fluidTexture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        int fluidHeight = (int) ((double) (tileEntity.getTank(0).getFluidAmount()) / (double) (tileEntity.getTank(0).getCapacity()) * fluidBar.height);
        if (tileEntity.getTank(0).getFluidAmount() == 0) {
            fluidHeight = 0;
        }
        if (tileEntity.getTank(0).getFluidAmount() == tileEntity.getTank(0).getCapacity()) {
            fluidHeight = fluidBar.height;
        }
        this.drawTexturedModalRect(fluidBar.x + this.guiLeft, fluidBar.y - 1 + this.guiTop - fluidHeight, fluidTexture, fluidBar.width, fluidHeight);
    }
}
