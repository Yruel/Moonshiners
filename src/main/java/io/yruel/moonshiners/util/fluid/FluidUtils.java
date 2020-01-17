package io.yruel.moonshiners.util.fluid;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class FluidUtils {
    public static FluidActionResult interactWithFluidHandler(@Nonnull ItemStack stack, IFluidHandler fluidHandler, EntityPlayer player) {
        if (stack.isEmpty() || fluidHandler == null || player == null) {
            return FluidActionResult.FAILURE;
        }

        IItemHandler inventory = new InvWrapper(player.inventory);

        FluidActionResult fillResult = FluidUtil.tryFillContainerAndStow(stack, fluidHandler, inventory, Integer.MAX_VALUE, player, true);
        if (fillResult.isSuccess()) {
            return fillResult;
        } else {
            return FluidUtil.tryEmptyContainerAndStow(stack, fluidHandler, inventory, Integer.MAX_VALUE, player, true);
        }
    }
}
