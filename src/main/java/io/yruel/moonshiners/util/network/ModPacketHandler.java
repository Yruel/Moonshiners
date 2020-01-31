package io.yruel.moonshiners.util.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModPacketHandler {
    public static SimpleNetworkWrapper INSTANCE;

    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessage(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        INSTANCE.registerMessage(PacketSyncMachineState.Handler.class, PacketSyncMachineState.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketSyncMachineFluid.Handler.class, PacketSyncMachineFluid.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketSyncMachineCookTime.Handler.class, PacketSyncMachineCookTime.class, nextID(), Side.CLIENT);
    }
}
