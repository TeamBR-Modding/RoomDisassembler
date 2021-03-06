package com.dyonovan.roomdisassembler.common.blocks;

import com.dyonovan.roomdisassembler.RoomDisassembler;
import com.dyonovan.roomdisassembler.client.renderers.BlockDisassemblerRenderer;
import com.dyonovan.roomdisassembler.common.tileentities.TileDisassembler;
import com.dyonovan.roomdisassembler.managers.GuiManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDisassembler extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon top, side, bottom;

    public BlockDisassembler() {
        super(Material.iron);

        this.setCreativeTab(RoomDisassembler.tabRoomDisassembler);
        this.setHardness(2.0F);
        this.setBlockName("blockRoomDisassembler");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.side = iconregister.registerIcon("roomdisassembler:rd_side");
        this.top = iconregister.registerIcon("roomdisassembler:rd_top");
        this.bottom = iconregister.registerIcon("roomdisassembler:rd_bottom");
        this.blockIcon = side;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case 0:
                return this.top;
            case 1:
                return this.bottom;
            default:
                return this.side;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileDisassembler();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta,
                                    float hitX, float hitY, float hitZ) {

        TileDisassembler tile = (TileDisassembler) world.getTileEntity(x, y, z);
        if (tile == null) return false;

        if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.apple) {
            player.openGui(RoomDisassembler.instance, GuiManager.ROOM_DISASSEMBLER_ID, world, x, y, z);
            return true;
        }

        switch (meta) {
            case 0: //Bottom
                if (player.isSneaking()) {
                    tile.loc1.travel(0, 1, 0);
                } else {
                    tile.loc1.travel(0, -1, 0);
                }
                break;
            case 1: //Top
                if (player.isSneaking()) {
                    tile.loc2.travel(0, 1, 0);
                } else {
                    tile.loc2.travel(0, -1, 0);
                }
                break;
            case 2:  //North Side
                if (player.isSneaking()) {
                    tile.loc1.travel(0, 0, -1);
                } else {
                    tile.loc1.travel(0, 0, 1);
                }
                break;
            case 3: //South Side
                if (player.isSneaking()) {
                    tile.loc2.travel(0, 0, -1);
                } else {
                    tile.loc2.travel(0, 0, 1);
                }
                break;
            case 4: //West Side
                if (player.isSneaking()) {
                    tile.loc1.travel(-1, 0, 0);
                } else {
                    tile.loc1.travel(1, 0, 0);
                }
                break;
            case 5: //East Side
                if (player.isSneaking()) {
                    tile.loc2.travel(-1, 0, 0);
                } else {
                    tile.loc2.travel(1, 0, 0);
                }
                break;
            default:
                sendChatMsg("Opps, something happened!", world, player);
        }

        return true;
    }

    private void sendChatMsg(String msg, World world, EntityPlayer player) {
        if (world.isRemote) {
            player.addChatComponentMessage(new ChatComponentText(msg));
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return BlockDisassemblerRenderer.renderID;
    }
}
