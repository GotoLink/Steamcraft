package steamcraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;

public class ItemKettle extends Item {
	public ItemKettle() {
		super();
		setMaxStackSize(1);
		setMaxDamage(300);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (itemstack.getItem() == getHot() && itemstack.getItemDamage() < itemstack.getMaxDamage() - 5) {
			if (getStackPosition(entityplayer.inventory, ItemTeacup.getEmpty()) > -1) {
				entityplayer.triggerAchievement(HandlerRegistry.getAchievement("timeforacuppa"));
				entityplayer.inventory.setInventorySlotContents(getStackPosition(entityplayer.inventory, ItemTeacup.getEmpty()), new ItemStack(ItemTeacup.getFull(), 1));
				itemstack.damageItem((itemstack.getMaxDamage() / 3) - 1, entityplayer);
			}
			if (itemstack.getItemDamage() >= itemstack.getMaxDamage() - 5) {
				itemstack = new ItemStack(getEmpty(), 1, itemstack.getItemDamage());
			}
		}
		return itemstack;
	}

	public static Item getEmpty() {
		return HandlerRegistry.getItem("steamcraft:kettleempty").get();
	}

	public static Item getHot() {
		return HandlerRegistry.getItem("steamcraft:kettleHot").get();
	}

	public static int getStackPosition(InventoryPlayer inventory, Item item) {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null && item == inventory.getStackInSlot(i).getItem()) {
				return i;
			}
		}
		return -1;
	}
}
