package io.yruel.moonshiners.util.network;

import io.netty.buffer.ByteBuf;
import io.yruel.moonshiners.Moonshiners;
import io.yruel.moonshiners.util.interfaces.IMachineStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncMachineFluid implements IMessage {

    private FluidStack fluid;

    public PacketSyncMachineFluid() {
    }

    public PacketSyncMachineFluid(FluidStack fluid) {
        if (fluid != null) {
            this.fluid = fluid;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.fluid = FluidStack.loadFluidStackFromNBT(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, fluid.writeToNBT(new NBTTagCompound()));
    }

    public static class Handler implements IMessageHandler<PacketSyncMachineFluid, IMessage> {

        @Override
        public IMessage onMessage(PacketSyncMachineFluid message, MessageContext ctx) {
            Moonshiners.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSyncMachineFluid message, MessageContext ctx) {
            EntityPlayer player = Moonshiners.proxy.getClientPlayer();
            if (player.openContainer instanceof IMachineStateContainer) {
                ((IMachineStateContainer) player.openContainer).sync(message.fluid);
            }
        }
    }
}
