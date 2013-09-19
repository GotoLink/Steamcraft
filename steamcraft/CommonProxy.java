package steamcraft;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.inventories.ContainerChemFurnace;
import steamcraft.inventories.ContainerNukeFurnace;
import steamcraft.inventories.ContainerSteamFurnace;
import steamcraft.inventories.GuiChemFurnace;
import steamcraft.inventories.GuiNukeFurnace;
import steamcraft.inventories.GuiSteamFurnace;
import steamcraft.items.ItemFirearm;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler,ITickHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity ent = world.getBlockTileEntity(x, y, z);
		if(ent!=null)
			switch(ID)
			{
			case 0:
				if(ent instanceof TileEntitySteamFurnace)
					return new ContainerSteamFurnace(player.inventory,(TileEntitySteamFurnace) ent);
				break;
			case 1:
				if(ent instanceof TileEntityChemFurnace)
					return new ContainerChemFurnace(player.inventory,(TileEntityChemFurnace) ent);
				break;
			case 2:
				if(ent instanceof TileEntityNukeFurnace)
					return new ContainerNukeFurnace(player.inventory,(TileEntityNukeFurnace) ent);
				break;
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity ent = world.getBlockTileEntity(x, y, z);
		if(ent!=null)
			switch(ID)
			{
			case 0:
				if(ent instanceof TileEntitySteamFurnace)
					return new GuiSteamFurnace(player.inventory,(TileEntitySteamFurnace) ent);
				break;
			case 1:
				if(ent instanceof TileEntityChemFurnace)
					return new GuiChemFurnace(player.inventory,(TileEntityChemFurnace) ent);
				break;
			case 2:
				if(ent instanceof TileEntityNukeFurnace)
					return new GuiNukeFurnace(player.inventory,(TileEntityNukeFurnace) ent);
				break;
			}
		return null;
	}

	public void registerRenderers() {
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		onPlayerTick((EntityPlayer)tickData[0]);
	}

	protected void onPlayerTick(EntityPlayer entityPlayer) {
		 //Boots = 0
        //Legs = 1
        //Chest = 2
        //Helmet = 3
		ItemStack heldItem = entityPlayer.getCurrentEquippedItem();
        ItemStack helmetSlot = entityPlayer.inventory.armorItemInSlot(3);
		ItemStack chestSlot = entityPlayer.inventory.armorItemInSlot(2);
		ItemStack legSlot = entityPlayer.inventory.armorItemInSlot(1);
		ItemStack bootSlot = entityPlayer.inventory.armorItemInSlot(0);
		
		if(heldItem != null){
			if(heldItem.getItem() instanceof ItemFirearm && heldItem.getItemDamage() > 0){
				if(heldItem.getItemDamage() < heldItem.getMaxDamage()-1){
					heldItem.setItemDamage(heldItem.getItemDamage()-1);
				}
				if(heldItem.getItemDamage() == heldItem.getMaxDamage()-1){
					ItemFirearm heldFirearm = (ItemFirearm)heldItem.getItem();
					if(heldFirearm.getAmmoA(heldItem) != heldFirearm.getAmmoB() && getStackPosition(entityPlayer.inventory, heldFirearm.getAmmoA(heldItem)) > -1 && getStackPosition(entityPlayer.inventory, heldFirearm.getAmmoB()) > -1){
						if(entityPlayer.inventory.consumeInventoryItem(heldFirearm.getAmmoA(heldItem).itemID) && entityPlayer.inventory.consumeInventoryItem(heldFirearm.getAmmoB().itemID))
				        {
							heldItem.setItemDamage(heldItem.getItemDamage()-1);
						}
					}
					if(heldFirearm.getAmmoA(heldItem) == heldFirearm.getAmmoB() && entityPlayer.inventory.consumeInventoryItem(heldFirearm.getAmmoA(heldItem).itemID)){
						heldItem.setItemDamage(heldItem.getItemDamage()-1);
					}
				}
			}
		}
		
		if(chestSlot == null || chestSlot.itemID != Steamcraft.aqualung.itemID)
        {
			entityPlayer.getEntityData().setShort("Aqualung",(short) 0);
        }
		else
		{
			if(!entityPlayer.isInsideOfMaterial(Material.water)){
				entityPlayer.getEntityData().setShort("Aqualung",(short) 600);
			}
			else if(entityPlayer.getAir() == 0){
				chestSlot.damageItem(1, entityPlayer);
			}
		}
		if(entityPlayer.getEntityData().getShort("Aqualung") > 0 && entityPlayer.isInsideOfMaterial(Material.water) && entityPlayer.isEntityAlive()){
			entityPlayer.getEntityData().setShort("Aqualung",(short) decreaseAirSupply(entityPlayer,entityPlayer.getEntityData().getShort("Aqualung")));
			entityPlayer.setAir(300);
		}
		//GL11.glDisable(3042 /*GL_BLEND*/);
			
		if(bootSlot == null || bootSlot.itemID != Steamcraft.rollerSkates.itemID){
			entityPlayer.stepHeight = 0.5F;
		}
		else{
			if(!entityPlayer.isInsideOfMaterial(Material.water) && !entityPlayer.isInWater() && entityPlayer.onGround)
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
		
		if(legSlot != null && legSlot.itemID == Steamcraft.legBraces.itemID){
		//if(minecraft.thePlayer.fallDistance <= 10F){
		//minecraft.thePlayer.fallDistance = 0.0F;
		//}else{
			if(entityPlayer.fallDistance > 0.0F){
				entityPlayer.fallDistance -= 0.5F;
			}
		}
	}

	protected int decreaseAirSupply(EntityPlayer entityPlayer, int par1)
    {
        int j = EnchantmentHelper.getRespiration(entityPlayer);
        return j > 0 && new Random().nextInt(j + 1) > 0 ? par1 : par1 - 1;
    }

	public int getStackPosition(InventoryPlayer inventory, ItemStack itemStack)
	{
		for(int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if(inventory.getStackInSlot(i) != null && itemStack.isItemEqual(inventory.getStackInSlot(i)))
			{
				return i;
			}
		}
		return -1;
	}
	
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return null;
	}

	public int registerArmor(String string) {
		return 0;
	}
}
