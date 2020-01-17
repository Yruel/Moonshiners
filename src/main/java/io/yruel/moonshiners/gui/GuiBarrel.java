package io.yruel.moonshiners.gui;

import io.yruel.moonshiners.container.ContainerBarrel;
import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiBarrel extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.ID + ":textures/gui/barrel.png");
    private final InventoryPlayer player;
    private final TileEntityBarrel tileEntity;

    protected Rectangle fluidBar = new Rectangle(36, 12, 16, 60);

    public GuiBarrel(InventoryPlayer player, TileEntityBarrel tileEntity) {
        super(new ContainerBarrel(player, tileEntity));
        this.player = player;
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.renderFluid();

        if (this.isPointInRegion(fluidBar.x, fluidBar.y, fluidBar.width, fluidBar.height, mouseX, mouseY)) {
            List<String> inputFluid = new ArrayList<>();
            //inputFluid.add(Objects.requireNonNull(tileEntity.inputTank.getFluid()).amount + " / " + tileEntity.inputTank.getCapacity() + " MB");
            inputFluid.add(tileEntity.getClientFluidInAmount() + " / " + tileEntity.inputTank.getCapacity() + " MB");
            GuiUtils.drawHoveringText(inputFluid, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    private void renderFluid() {
        Fluid fluid = FluidRegistry.WATER;
        TextureAtlasSprite fluidTexture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        int fluidHeight = (int) ((double) (tileEntity.getClientFluidInAmount()) / (double) (tileEntity.inputTank.getCapacity()) * fluidBar.height);
        if (tileEntity.getClientFluidInAmount() == 0) {
            fluidHeight = 0;
        }
        if (tileEntity.getClientFluidInAmount() == tileEntity.inputTank.getCapacity()) {
            fluidHeight = fluidBar.height;
        }
        this.drawTexturedModalRect(this.guiLeft + fluidBar.x, this.guiTop + fluidBar.y + (fluidBar.height - fluidHeight), fluidTexture, fluidBar.width, fluidHeight);
    }
}
