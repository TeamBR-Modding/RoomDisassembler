package com.dyonovan.roomdisassembler.client.gui;

import com.dyonovan.brlib.client.gui.GuiBase;
import com.dyonovan.roomdisassembler.common.container.ContainerDisassembler;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDisassembler extends GuiBase<ContainerDisassembler> {

    public GuiDisassembler(InventoryPlayer invPlayer) {
        super(new ContainerDisassembler(invPlayer), 175, 165, "inventory.disassembler.title");
    }

    @Override
    public void addComponents() {

    }
}
