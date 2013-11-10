package steamcraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;

public class ItemKettle extends Item {
	public ItemKettle(int i) {
		super(i);
		setMaxStackSize(1);
		setMaxDamage(300);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (itemstack.getItem().itemID == getHot() && itemstack.getItemDamage() < itemstack.getMaxDamage() - 5) {
			if (getStackPosition(entityplayer.inventory, ItemTeacup.getEmpty()) > -1) {
				entityplayer.triggerAchievement(Steamcraft.achs.get("timeforacuppa"));
				entityplayer.inventory.setInventorySlotContents(getStackPosition(entityplayer.inventory, ItemTeacup.getEmpty()), new ItemStack(ItemTeacup.getFull(), 1));
				itemstack.damageItem((itemstack.getMaxDamage() / 3) - 1, entityplayer);
			}
			if (itemstack.getItemDamage() >= itemstack.getMaxDamage() - 5) {
				itemstack = new ItemStack(getEmpty(), 1, itemstack.getItemDamage());
			}
		}
		return itemstack;
	}

	public static int getEmpty() {
		return HandlerRegistry.getItem("steamcraft:kettleempty").getID();
	}

	public static int getHot() {
		return HandlerRegistry.getItem("steamcraft:kettleHot").getID();
	}

	public static int getStackPosition(InventoryPlayer inventory, Item item) {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null && item.itemID == inventory.getStackInSlot(i).getItem().itemID) {
				return i;
			}
		}
		return -1;
	}
}
