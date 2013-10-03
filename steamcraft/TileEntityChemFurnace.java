package steamcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import steamcraft.blocks.BlockMainFurnace;

public class TileEntityChemFurnace extends TileEntityFurnace
{
    public int currentItemBurnTimea;
	public int currentItemBurnTimeb;
    public TileEntityChemFurnace()
    {
        furnaceItemStacks = new ItemStack[4];
        setGuiDisplayName("Chemical Furnace");
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        currentItemBurnTimea = getItemBurnTime(furnaceItemStacks[1]);
		currentItemBurnTimeb = getItemBurnTime(furnaceItemStacks[3]);
    }
    
    @Override
    public int getCookProgressScaled(int i)
    {
        return (furnaceCookTime * i) / 100;
    }
    @Override
    public int getBurnTimeRemainingScaled(int i)
    {
        if(currentItemBurnTimea == 0)
        {
            currentItemBurnTimea = 100;
        }
		if(currentItemBurnTimeb == 0)
        {
            currentItemBurnTimeb = 100;
        }
        return (furnaceBurnTime * i) / (currentItemBurnTimea + currentItemBurnTimeb);
    }

    @Override
    public void updateEntity()
    {
        boolean flag = furnaceBurnTime > 0;
        boolean flag1 = false;
        if(furnaceBurnTime > 0)
        {
            furnaceBurnTime--;
        }
        if(!worldObj.isRemote)
        {
            if(furnaceBurnTime == 0 && canSmelt())
            {
            	if(furnaceItemStacks[1] != null && furnaceItemStacks[3] != null){
            		if(furnaceItemStacks[1].itemID != furnaceItemStacks[3].itemID){
            			currentItemBurnTimea = getItemBurnTime(furnaceItemStacks[1]);
            			currentItemBurnTimeb = getItemBurnTime(furnaceItemStacks[3]);
            			if(currentItemBurnTimea > 0 && currentItemBurnTimeb > 0)
            			{
            				furnaceBurnTime = currentItemBurnTimea + currentItemBurnTimeb;
            				flag1 = true;
            				if(furnaceItemStacks[1] != null && furnaceItemStacks[3] != null)
            				{
            					furnaceItemStacks[1].stackSize--;
        						furnaceItemStacks[3].stackSize--;
            					if(furnaceItemStacks[1].stackSize == 0)
            					{
            						furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItemStack(furnaceItemStacks[1]);
            					} 
            					if(furnaceItemStacks[3].stackSize == 0)
            					{
            						furnaceItemStacks[3] = furnaceItemStacks[3].getItem().getContainerItemStack(furnaceItemStacks[3]);
            					}
                    		}
            			}
            		}
            	}
			}
            if(isBurning() && canSmelt())
            {
                furnaceCookTime++;
                if(furnaceCookTime >= 100)
                {
                    furnaceCookTime = 0;
                    smeltItem();
                    flag1 = true;
                }
            } else
            {
                furnaceCookTime = 0;
            }
            if(flag != (furnaceBurnTime > 0))
            {
                flag1 = true;
                BlockMainFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord, Steamcraft.chemOvenActive.blockID, Steamcraft.chemOvenIdle.blockID, false);
            }
		}
        if (flag1)
        {
            this.onInventoryChanged();
        }
    }
    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
    	if(par1 == 1 || par1 == 3)
    		return fuels.containsKey(par2ItemStack);
    	else
    		return super.isItemValidForSlot(par1, par2ItemStack);
    }
    public static Map<ItemStack,Integer> fuels = new HashMap();
    {
    	fuels.put(new ItemStack(Item.sugar),20);
    	fuels.put(new ItemStack(Item.gunpowder), 100);
    	fuels.put(new ItemStack(Steamcraft.material,1,2), 200);
    	fuels.put(new ItemStack(Steamcraft.material,1,1),1000);	
    	fuels.put(new ItemStack(Steamcraft.material,1,7), 1600);
    	fuels.put(new ItemStack(Item.glowstone), 3200);
    }
}
