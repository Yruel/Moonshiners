package io.yruel.moonshiners.fluid;

import io.yruel.moonshiners.init.MoonshinersFluids;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class CustomFluid extends Fluid {
    public CustomFluid(String name, ResourceLocation still, ResourceLocation flow) {
        super(name, still, flow);
        this.setUnlocalizedName(name);

        MoonshinersFluids.FLUIDS.add(this);
    }
}
