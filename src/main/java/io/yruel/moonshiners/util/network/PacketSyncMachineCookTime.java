package io.yruel.moonshiners.util.network;

import io.netty.buffer.ByteBuf;
import io.yruel.moonshiners.Moonshiners;
import io.yruel.moonshiners.util.interfaces.IMachineStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncMachineCookTime implements IMessage {

    private int cookTime;

    public PacketSyncMachineCookTime() {}

    public PacketSyncMachineCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.cookTime = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.cookTime);
    }

    public static class Handler implements IMessageHandler<PacketSyncMachineCookTime, IMessage> {

        @Override
        public IMessage onMessage(PacketSyncMachineCookTime message, MessageContext ctx) {
            Moonshiners.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSyncMachineCookTime message, MessageContext ctx) {
            EntityPlayer player = Moonshiners.proxy.getClientPlayer();
            if (player.openContainer instanceof IMachineStateContainer) {
                ((IMachineStateContainer) player.openContainer).sync(message.cookTime);
            }
        }
    }
}
