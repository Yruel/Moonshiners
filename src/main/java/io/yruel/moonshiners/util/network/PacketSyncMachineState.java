package io.yruel.moonshiners.util.network;

import io.netty.buffer.ByteBuf;
import io.yruel.moonshiners.Moonshiners;
import io.yruel.moonshiners.util.interfaces.IMachineStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class PacketSyncMachineState implements IMessage {

    private int[] fluids;
    private int dimension;

    public PacketSyncMachineState() {}

    public PacketSyncMachineState(int... fluids) {
        if (fluids != null) {
            this.fluids = fluids;
            this.dimension = fluids.length;
        } else {
            this.dimension = 0;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dimension);

        for (int i = 0; i < dimension; i++) {
            buf.writeInt(fluids[i]);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dimension = buf.readInt();

        if (dimension != 0)
            fluids = new int [dimension];
        else
            fluids = null;

        for (int i = 0; i < dimension; i++) {
            fluids[i] = buf.readInt();
        }
    }

    public static class Handler implements IMessageHandler<PacketSyncMachineState, IMessage> {

        @Override
        public IMessage onMessage(PacketSyncMachineState message, MessageContext ctx) {
            Moonshiners.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSyncMachineState message, MessageContext ctx) {
            EntityPlayer player = Moonshiners.proxy.getClientPlayer();
            if (player.openContainer instanceof IMachineStateContainer) {
                ((IMachineStateContainer) player.openContainer).sync(message.fluids);
            }
        }
    }
}
