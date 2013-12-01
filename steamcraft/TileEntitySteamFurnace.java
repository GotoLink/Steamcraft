package steamcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import steamcraft.blocks.BlockMainFurnace;
import steamcraft.blocks.BlockSteamFurnace;

public class TileEntitySteamFurnace extends FurnaceAccess {
	public int waterLevel;

	public TileEntitySteamFurnace() {
		super(4);
		setGuiDisplayName("Steam Furnace");
	}

	@Override
	public int getBurnTimeRemainingScaled(int i) {
		if (currentItemBurnTime == 0) {
			currentItemBurnTime = 600;
		}
		return (furnaceBurnTime * i) / currentItemBurnTime;
	}

	@Override
	public int getCookProgressScaled(int i) {
		return (furnaceCookTime * i) / 600;
	}

	public int getWater() {
		return waterLevel;
	}

	public int getWaterScaled(int i) {
		if (waterLevel > 0) {
			return (int) (Math.ceil((waterLevel * i) / 4096));
		} else {
			return -2;
		}
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		if (par1 == 3)
			return (par2ItemStack.itemID == Item.bucketWater.itemID);
		else
			return super.isItemValidForSlot(par1, par2ItemStack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		waterLevel = nbttagcompound.getShort("WaterLevel");
	}

	@Override
	public void updateEntity() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;
		if (furnaceBurnTime > 0) {
			furnaceBurnTime--;
		}
		if (!worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.isSmeltable()) {
				ItemStack stack1 = getStackInSlot(1).copy();
				this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(stack1);
				if (this.furnaceBurnTime > 0) {
					flag1 = true;
					if (stack1 != null) {
						decrStackSize(1,1);
						if (this.getStackInSlot(1) == null) {
							setInventorySlotContents(1, stack1.getItem().getContainerItemStack(stack1));
						}
					}
				}
			}
			if (isBurning()) {
				if (waterLevel == 1) {
					BlockSteamFurnace.playSound(worldObj, xCoord, yCoord, zCoord, "random.fizz");
				}
				waterLevel--;
			}
			if (isBurning() && isSmeltable()) {
				if (waterLevel > 0) {
					furnaceCookTime += 4;
				} else {
					furnaceCookTime += 3;
				}
				if (waterLevel > 4096) {
					waterLevel = 4096;
				}
				if (waterLevel < 0) {
					waterLevel = 0;
				}
				if (getStackInSlot(3) != null) {
					if (waterLevel <= 0 && getStackInSlot(3).itemID == Item.bucketWater.itemID) {
						setInventorySlotContents(3, new ItemStack(Item.bucketEmpty));
						waterLevel = 4096;
						BlockSteamFurnace.playSound(worldObj, xCoord, yCoord, zCoord, "random.fizz");
					}
				}
				if (furnaceCookTime >= 600) {
					furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else {
				furnaceCookTime = 0;
			}
			if (flag != (furnaceBurnTime > 0)) {
				flag1 = true;
				BlockMainFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord, BlockSteamFurnace.getActive(), BlockSteamFurnace.getIdle(), false);
			}
		}
		if (flag1) {
			this.onInventoryChanged();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("WaterLevel", (short) waterLevel);
	}
}
