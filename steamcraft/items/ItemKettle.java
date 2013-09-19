package steamcraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class ItemKettle extends Item
{
    public ItemKettle(int i)
    {
        super(i);
        setMaxStackSize(1);
        setMaxDamage(300);
    }
    @Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		if(itemstack.getItem().itemID == Steamcraft.hotKettle.itemID && itemstack.getItemDamage() < itemstack.getItem().getMaxDamage()-5){
			if(getStackPosition(entityplayer.inventory, Steamcraft.emptyTeacup) > -1){
				entityplayer.triggerAchievement(Steamcraft.ach_TimeForACuppa);
				entityplayer.inventory.setInventorySlotContents(getStackPosition(entityplayer.inventory, Steamcraft.emptyTeacup), new ItemStack(Steamcraft.fullTeacup, 1));
				itemstack.damageItem((itemstack.getItem().getMaxDamage()/3) - 1, entityplayer);
			}
			if(itemstack.getItemDamage() >= itemstack.getItem().getMaxDamage()-5){
				itemstack = new ItemStack(Steamcraft.emptyKettle, 1, itemstack.getItemDamage());
			}
		}
		return itemstack;
	}
	
	public int getStackPosition(InventoryPlayer inventory, Item item){
		for(int i = 0; i < inventory.getSizeInventory(); i++){
			if(inventory.getStackInSlot(i) != null && item == inventory.getStackInSlot(i).getItem()){
				return i;
			}
		}
		return -1;
	}
}
