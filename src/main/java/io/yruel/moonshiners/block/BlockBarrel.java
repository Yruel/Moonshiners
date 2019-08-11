package io.yruel.moonshiners.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockBarrel extends BlockBase {
    public BlockBarrel(String name, Material material, float hardness, float resistance) {
        super(name, material, hardness, resistance);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
