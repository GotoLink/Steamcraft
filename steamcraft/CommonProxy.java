package steamcraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.inventories.ContainerChemFurnace;
import steamcraft.inventories.ContainerNukeFurnace;
import steamcraft.inventories.ContainerSteamFurnace;
import steamcraft.inventories.GuiChemFurnace;
import steamcraft.inventories.GuiNukeFurnace;
import steamcraft.inventories.GuiSteamFurnace;
import steamcraft.items.ItemSCArmor;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity ent = world.func_147438_o(x, y, z);
		if (ent != null)
			switch (ID) {
			case 0:
				if (ent instanceof TileEntitySteamFurnace)
					return new GuiSteamFurnace(player.inventory, (TileEntitySteamFurnace) ent);
				break;
			case 1:
				if (ent instanceof TileEntityChemFurnace)
					return new GuiChemFurnace(player.inventory, (TileEntityChemFurnace) ent);
				break;
			case 2:
				if (ent instanceof TileEntityNukeFurnace)
					return new GuiNukeFurnace(player.inventory, (TileEntityNukeFurnace) ent);
				break;
			}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity ent = world.func_147438_o(x, y, z);
		if (ent != null)
			switch (ID) {
			case 0:
				if (ent instanceof TileEntitySteamFurnace)
					return new ContainerSteamFurnace(player.inventory, (TileEntitySteamFurnace) ent);
				break;
			case 1:
				if (ent instanceof TileEntityChemFurnace)
					return new ContainerChemFurnace(player.inventory, (TileEntityChemFurnace) ent);
				break;
			case 2:
				if (ent instanceof TileEntityNukeFurnace)
					return new ContainerNukeFurnace(player.inventory, (TileEntityNukeFurnace) ent);
				break;
			}
		return null;
	}

	public void registerRenderers() {
	}

	@SubscribeEvent
	public void tickPlayer(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.END)
		    onPlayerTick(event.player);
	}

	protected void onPlayerTick(EntityPlayer entityPlayer) {
		ItemStack chestSlot = entityPlayer.inventory.armorItemInSlot(2);
		ItemStack bootSlot = entityPlayer.inventory.armorItemInSlot(0);
		if (chestSlot == null || chestSlot.getItem() != ItemSCArmor.getAqualung()) {
			entityPlayer.getEntityData().setShort("Aqualung", (short) 0);
		}
		if ((bootSlot == null || bootSlot.getItem() != ItemSCArmor.getSkates()) && entityPlayer.stepHeight == 0.0F) {
			entityPlayer.stepHeight = 0.5F;
		}
	}
}
