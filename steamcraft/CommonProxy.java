package steamcraft;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import steamcraft.items.ItemFirearm;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler,ITickHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity ent = world.getBlockTileEntity(x, y, z);
		if(ent!=null)
			switch(ID)
			{
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity ent = world.getBlockTileEntity(x, y, z);
		if(ent!=null)
			switch(ID)
			{
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			}
		return null;
	}

	public void registerRenderers() {
		
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		onPlayerTick((EntityPlayer)tickData[0]);
	}

	private void onPlayerTick(EntityPlayer entityPlayer) {
		 //Boots = 0
        //Legs = 1
        //Chest = 2
        //Helmet = 3
		ItemStack heldItem = entityPlayer.getCurrentEquippedItem();
        ItemStack helmetSlot = entityPlayer.inventory.armorInventory[3];
		ItemStack chestSlot = entityPlayer.inventory.armorInventory[2];
		ItemStack legSlot = entityPlayer.inventory.armorInventory[1];
		ItemStack bootSlot = entityPlayer.inventory.armorInventory[0];
		
		if(heldItem != null){
			if(heldItem.getItem() instanceof ItemFirearm && heldItem.getItemDamage() > 0){
				if(heldItem.getItemDamage() < heldItem.getMaxDamage()-1){
					heldItem.setItemDamage(heldItem.getItemDamage()-1);
				}
				if(heldItem.getItemDamage() == heldItem.getMaxDamage()-1){
					ItemFirearm heldFirearm = (ItemFirearm)heldItem.getItem();
					if(heldFirearm.getAmmoA() != heldFirearm.getAmmoB() && getStackPosition(entityPlayer.inventory, heldFirearm.getAmmoA()) > -1 && getStackPosition(entityPlayer.inventory, heldFirearm.getAmmoB()) > -1){
						if(entityPlayer.inventory.consumeInventoryItem(heldFirearm.getAmmoA().itemID) && entityPlayer.inventory.consumeInventoryItem(heldFirearm.getAmmoB().itemID))
				        {
							heldItem.setItemDamage(heldItem.getItemDamage()-1);
						}
					}
					if(heldFirearm.getAmmoA() == heldFirearm.getAmmoB() && entityPlayer.inventory.consumeInventoryItem(heldFirearm.getAmmoA().itemID)){
						heldItem.setItemDamage(heldItem.getItemDamage()-1);
					}
				}
			}
		}
        //Check to see if the currently worn item in slot 3(head slot) is a leather helmet
		if(helmetSlot != null){
			renderOverlay(helmetSlot);
		}
		
		if(chestSlot == null)
        {
			entityPlayer.getEntityData().setShort("Aqualung",(short) 0);
        }
		if(entityPlayer.getEntityData().getShort("Aqualung") > 0 && entityPlayer.isInsideOfMaterial(Material.water) && entityPlayer.isEntityAlive()){
			entityPlayer.getEntityData().setShort("Aqualung",(short) entityPlayer.decreaseAirSupply(entityPlayer.getEntityData().getShort("Aqualung")));
			entityPlayer.setAir(300);
			renderThings(entityPlayer);
		}
		//GL11.glDisable(3042 /*GL_BLEND*/);
		
		if(chestSlot != null){
			if(chestSlot.itemID == mod_Steamcraft.aqualung.itemID)
			{
				if(!entityPlayer.isInsideOfMaterial(Material.water)){
					entityPlayer.getEntityData().setShort("Aqualung",(short) 600);
				}
				else if(entityPlayer.getAir() == 0){
					chestSlot.damageItem(1, entityPlayer);
				}
			}else{
				entityPlayer.getEntityData().setShort("Aqualung",(short) 0);
			}
		}
				
		if(bootSlot != null){
			if(!entityPlayer.isInsideOfMaterial(Material.water) && !entityPlayer.isInWater() && entityPlayer.onGround && bootSlot.itemID == mod_Steamcraft.rollerSkates.itemID)
                   {
				 //  if(!setBaseSpeed){
		//minecraft.thePlayer.motionX *= 1.200000824D;
		//minecraft.thePlayer.motionZ *= 1.200000824D;
		//setBaseSpeed = true;
		//}
		//if(!minecraft.thePlayer.isJumping){
		//minecraft.thePlayer.moveEntity(minecraft.thePlayer.motionX*1.2D, 0.0D, minecraft.thePlayer.motionZ*1.2D);
		//}
		//minecraft.thePlayer.setPosition(minecraft.thePlayer.posX+minecraft.thePlayer.motionX*2D, minecraft.thePlayer.posY, minecraft.thePlayer.posZ+minecraft.thePlayer.motionZ*2D);
        	entityPlayer.moveEntityWithHeading(entityPlayer.moveStrafing, entityPlayer.moveForward*0.8F);
			entityPlayer.stepHeight = 0.0F;
     //   }else{
	//	setBaseSpeed = false;
                   }
		}
		if(bootSlot == null || bootSlot.itemID != mod_Steamcraft.rollerSkates.itemID){
			entityPlayer.stepHeight = 0.5F;
		}
		if(legSlot != null){
			if(legSlot.itemID == mod_Steamcraft.legBraces.itemID){
		//if(minecraft.thePlayer.fallDistance <= 10F){
		//minecraft.thePlayer.fallDistance = 0.0F;
		//}else{
				boolean setFall = false;
				if(entityPlayer.fallDistance > 0.0F && !setFall){
					entityPlayer.fallDistance -= 0.5F;
					setFall = true;
				}else if(entityPlayer.fallDistance <= 0.0F){
					setFall = false;
				}
		//}
			}
		}
	}
	
	public void renderThings(EntityPlayer entityPlayer) {
		
	}

	public void renderOverlay(ItemStack helmetSlot) {
		
	}

	public int getStackPosition(InventoryPlayer inventory, Item item)
	{
		for(int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if(inventory.getStackInSlot(i) != null && item == inventory.getStackInSlot(i).getItem())
			{
				return i;
			}
		}
		return -1;
	}
	
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return null;
	}

}
