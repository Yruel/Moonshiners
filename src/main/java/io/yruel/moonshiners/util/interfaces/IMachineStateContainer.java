package io.yruel.moonshiners.util.interfaces;

import net.minecraftforge.fluids.FluidStack;

public interface IMachineStateContainer {
    void sync(int cookTime);

    void sync(int... fluids);

    void sync(FluidStack fluidStack);
}
