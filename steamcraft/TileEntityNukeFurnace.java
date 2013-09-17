package steamcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import steamcraft.blocks.BlockNukeFurnace;

public class TileEntityNukeFurnace extends TileEntityFurnace
{
	public int furnaceHeat;
    public TileEntityNukeFurnace()
    {
    	setGuiDisplayName("Nuclear Reactor");
    }
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
		furnaceHeat = nbttagcompound.getShort("Heat");
    }
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("Heat", (short)furnaceHeat);
    }
    @Override
    public int getCookProgressScaled(int i)
    {
        return (furnaceCookTime * i) / 20;
    }
	
	public int getHeatScaled(int i)
    {
        return (furnaceHeat * i) / 2560;
    }
	@Override
    public int getBurnTimeRemainingScaled(int i)
    {
        if(currentItemBurnTime == 0)
        {
            currentItemBurnTime = 20;
        }
        return (furnaceBurnTime * i) / currentItemBurnTime;
    }
	
	public void addHeat(int i){
		furnaceHeat += i;
	}
	
	public int getHeat(){
		return furnaceHeat;
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
        	if (this.furnaceBurnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

                if (this.furnaceBurnTime > 0)
                {
                    flag1 = true;

                    if (this.furnaceItemStacks[1] != null)
                    {
                    	--this.furnaceItemStacks[1].stackSize;

                        if (this.furnaceItemStacks[1].stackSize == 0)
                        {
                            this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItemStack(furnaceItemStacks[1]);
                        }
                    }
                }
            }
            if(isBurning() && canSmelt())
            {
                furnaceCookTime++;
                if(furnaceCookTime >= 20)
                {
                    furnaceCookTime = 0;
					if(furnaceHeat <= 2560){
						furnaceHeat += 40;
					}
					if(furnaceHeat > 2520 && furnaceHeat < 2560){
						furnaceHeat = 2560;
					}
                    smeltItem();
                    flag1 = true;
                }
            } else
            {
                furnaceCookTime = 0;
				if(furnaceHeat > 0){
					furnaceHeat--;
				}
            }
            if(flag != (furnaceBurnTime > 0))
            {
                flag1 = true;
                BlockNukeFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
            }
			if(furnaceHeat >= 2560){
				BlockNukeFurnace.meltdown(worldObj, xCoord, yCoord, zCoord);
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
    	if(par1==1)
    		return par2ItemStack.itemID==Steamcraft.uranium.itemID;
    	else
    		return super.isItemValidForSlot(par1, par2ItemStack);
    }
}
