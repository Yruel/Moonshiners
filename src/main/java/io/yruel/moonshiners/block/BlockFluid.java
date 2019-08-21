package io.yruel.moonshiners.block;

import io.yruel.moonshiners.init.MoonshinersBlocks;
import io.yruel.moonshiners.init.MoonshinersItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.Objects;

@MethodsReturnNonnullByDefault
public class BlockFluid extends BlockFluidClassic {
    public BlockFluid(String name, Fluid fluid, Material material) {
        super(fluid, material);
        setUnlocalizedName(name);
        setRegistryName(name);

        MoonshinersBlocks.BLOCKS.add(this);
        MoonshinersItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
