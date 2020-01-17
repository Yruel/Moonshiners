package io.yruel.moonshiners.util.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface IRestorableTileEntity {
    void readRestorableFromNBT(NBTTagCompound compound);
    void writeRestorableFromNBT(NBTTagCompound compound);
}
