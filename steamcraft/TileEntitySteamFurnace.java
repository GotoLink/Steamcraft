package steamcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import steamcraft.blocks.BlockMainFurnace;
import steamcraft.blocks.BlockSteamFurnace;

public class TileEntitySteamFurnace extends TileEntityFurnace {
	public int waterLevel;

	public TileEntitySteamFurnace() {
		furnaceItemStacks = new ItemStack[4];
		setGuiDisplayName("Steam Furnace");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		waterLevel = nbttagcompound.getShort("WaterLevel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("WaterLevel", (short) waterLevel);
	}

	@Override
	public int getCookProgressScaled(int i) {
		return (furnaceCookTime * i) / 600;
	}

	public int getWaterScaled(int i) {
		if (waterLevel > 0) {
			return (int) (Math.ceil((waterLevel * i) / 4096));
		} else {
			return -2;
		}
	}

	@Override
	public int getBurnTimeRemainingScaled(int i) {
		if (currentItemBurnTime == 0) {
			currentItemBurnTime = 600;
		}
		return (furnaceBurnTime * i) / currentItemBurnTime;
	}

	public int getWater() {
		return waterLevel;
	}

	@Override
	public void updateEntity() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;
		if (furnaceBurnTime > 0) {
			furnaceBurnTime--;
		}
		if (!worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.canSmelt()) {
				this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
				if (this.furnaceBurnTime > 0) {
					flag1 = true;
					if (this.furnaceItemStacks[1] != null) {
						--this.furnaceItemStacks[1].stackSize;
						if (this.furnaceItemStacks[1].stackSize == 0) {
							this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItemStack(furnaceItemStacks[1]);
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
			if (isBurning() && canSmelt()) {
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
				if (furnaceItemStacks[3] != null) {
					if (waterLevel <= 0 && furnaceItemStacks[3].itemID == Item.bucketWater.itemID) {
						furnaceItemStacks[3] = new ItemStack(Item.bucketEmpty);
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
				BlockMainFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord, Steamcraft.steamOvenActive.blockID, Steamcraft.steamOvenIdle.blockID, false);
			}
		}
		if (flag1) {
			this.onInventoryChanged();
		}
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		if (par1 == 3)
			return (par2ItemStack.itemID == Item.bucketWater.itemID);
		else
			return super.isItemValidForSlot(par1, par2ItemStack);
	}
}
