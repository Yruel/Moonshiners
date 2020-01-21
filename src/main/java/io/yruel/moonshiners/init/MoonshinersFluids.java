package io.yruel.moonshiners.init;

import io.yruel.moonshiners.fluid.CustomFluid;
import io.yruel.moonshiners.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class MoonshinersFluids {

    public static final List<Fluid> FLUIDS = new ArrayList<>();

    public static final Fluid FLUID_POTATO_MASH = new CustomFluid("potato_mash", new ResourceLocation(Reference.ID + ":blocks/potato_mash_still"), new ResourceLocation(Reference.ID + ":blocks/potato_mash_flow"));
    public static final Fluid FLUID_FERMENTED_POTATO_MASH = new CustomFluid("fermented_potato_mash", new ResourceLocation(Reference.ID + ":blocks/potato_mash_still"), new ResourceLocation(Reference.ID + ":blocks/potato_mash_flow"));
}
