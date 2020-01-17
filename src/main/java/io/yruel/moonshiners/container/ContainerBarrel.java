package io.yruel.moonshiners.container;

import io.yruel.moonshiners.tileentity.TileEntityBarrel;
import io.yruel.moonshiners.util.interfaces.IMachineStateContainer;
import io.yruel.moonshiners.util.network.PacketSyncMachineState;
import io.yruel.moonshiners.util.network.ModPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;

public class ContainerBarrel extends Container implements IMachineStateContainer {
    private final TileEntityBarrel tileEntity;

    public ContainerBarrel(InventoryPlayer player, TileEntityBarrel tileEntity) {
        this.tileEntity = tileEntity;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (!this.tileEntity.getWorld().isRemote) {
            if (tileEntity.getFluidInAmount() != tileEntity.getClientFluidInAmount() || tileEntity.getFluidOutAmount() != tileEntity.getClientFluidOutAmount()) {
                tileEntity.setClientFluidInAmount(tileEntity.getFluidInAmount());
                tileEntity.setClientFluidOutAmount(tileEntity.getFluidOutAmount());

                for (IContainerListener listener : listeners) {
                    if (listener instanceof EntityPlayerMP) {
                        EntityPlayerMP player = (EntityPlayerMP) listener;
                        ModPacketHandler.INSTANCE.sendTo(new PacketSyncMachineState(tileEntity.getFluidInAmount(), tileEntity.getFluidOutAmount()), player);
                    }
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public void sync(int... fluids) {
        this.tileEntity.setClientFluidInAmount(fluids[0]);
        this.tileEntity.setClientFluidOutAmount(fluids[1]);
    }
}
