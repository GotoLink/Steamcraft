package steamcraft.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import steamcraft.TileEntitySteamFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSteamFurnace extends Container {
	public ContainerSteamFurnace(InventoryPlayer inventoryplayer, TileEntitySteamFurnace tileentitysteamfurnace) {
		cookTime = 0;
		burnTime = 0;
		itemBurnTime = 0;
		waterLevel = 0;
		furnace = tileentitysteamfurnace;
		addSlotToContainer(new Slot(tileentitysteamfurnace, 0, 56, 17));
		addSlotToContainer(new Slot(tileentitysteamfurnace, 1, 56, 53));
		addSlotToContainer(new SlotFurnace(inventoryplayer.player, tileentitysteamfurnace, 2, 116, 35));
		addSlotToContainer(new Slot(tileentitysteamfurnace, 3, 28, 53));
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);
			if (cookTime != furnace.getCookTime()) {
				icrafting.sendProgressBarUpdate(this, 0, furnace.getCookTime());
				cookTime = furnace.getCookTime();
			}
			if (burnTime != furnace.getBurnTime()) {
				icrafting.sendProgressBarUpdate(this, 1, furnace.getBurnTime());
				burnTime = furnace.getBurnTime();
			}
			if (itemBurnTime != furnace.getItemBurnTime()) {
				icrafting.sendProgressBarUpdate(this, 2, furnace.getItemBurnTime());
				itemBurnTime = furnace.getItemBurnTime();
			}
			if (waterLevel != furnace.getWater()) {
				icrafting.sendProgressBarUpdate(this, 3, furnace.getWater());
				waterLevel = furnace.getWater();
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			furnace.setCookTime(j);
		}
		if (i == 1) {
			furnace.setBurnTime(j);
		}
		if (i == 2) {
			furnace.setItemBurnTime(j);
		}
		if (i == 3) {
			furnace.waterLevel = j;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return furnace.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i <= 3) {
				if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null) {
				if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
					return null;
				}
			} else if (TileEntityFurnace.isItemFuel(itemstack1)) {
				if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
					return null;
				}
			} else if (itemstack1.getItem() == Items.water_bucket) {
				if (!this.mergeItemStack(itemstack1, 3, 4, false)) {
					return null;
				}
			} else if (i >= 31 && i < 40) {
				if (!this.mergeItemStack(itemstack1, 4, 31, false)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 31, 40, true)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}

	private TileEntitySteamFurnace furnace;
	private int cookTime;
	private int burnTime;
	private int itemBurnTime;
	private int waterLevel;
}
