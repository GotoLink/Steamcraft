package steamcraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class ItemKettle extends Item
{
    public ItemKettle(int i,int j)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(j);
    }
    @Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		if(itemstack.getItem().itemID == mod_Steamcraft.hotKettle.itemID && itemstack.getItemDamage() < itemstack.getItem().getMaxDamage()-5){
			if(getStackPosition(entityplayer.inventory, mod_Steamcraft.emptyTeacup) > -1){
				entityplayer.triggerAchievement(mod_Steamcraft.ach_TimeForACuppa);
				entityplayer.inventory.setInventorySlotContents(getStackPosition(entityplayer.inventory, mod_Steamcraft.emptyTeacup), new ItemStack(mod_Steamcraft.fullTeacup, 1));
				itemstack.damageItem((itemstack.getItem().getMaxDamage()/3) - 1, entityplayer);
			}
			if(itemstack.getItemDamage() >= itemstack.getItem().getMaxDamage()-5){
				itemstack = new ItemStack(mod_Steamcraft.emptyKettle, 1, itemstack.getItemDamage());
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
