package steamcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import steamcraft.blocks.BlockMainFurnace;
import steamcraft.blocks.BlockNukeFurnace;

public class TileEntityNukeFurnace extends FurnaceAccess {
	public int furnaceHeat;

	public TileEntityNukeFurnace() {
		super(3);
        func_145951_a("Nuclear Reactor");
	}

	public void addHeat(int i) {
		furnaceHeat += i;
	}

	@Override
	public int getBurnTimeRemainingScaled(int i) {
		if (currentItemBurnTime == 0) {
            currentItemBurnTime = 20;
		}
		return (furnaceBurnTime * i) / currentItemBurnTime;
	}

	@Override
	public int getCookProgressScaled(int i) {
		return (furnaceCookTime * i) / 20;
	}

	public int getHeat() {
		return furnaceHeat;
	}

	public int getHeatScaled(int i) {
		return (furnaceHeat * i) / 2560;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		if (par1 == 1)
			return par2ItemStack.isItemEqual(new ItemStack(Steamcraft.material, 1, 9));
		else
			return super.isItemValidForSlot(par1, par2ItemStack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		furnaceHeat = nbttagcompound.getShort("Heat");
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
							setInventorySlotContents(1, stack1.getItem().getContainerItem(stack1));
						}
					}
				}
			}
			if (isBurning() && isSmeltable()) {
                furnaceCookTime++;
				if (furnaceCookTime >= 20) {
                    furnaceCookTime = 0;
					if (furnaceHeat <= 2560) {
						addHeat(40);
					}
					if (furnaceHeat > 2520 && furnaceHeat < 2560) {
						furnaceHeat = 2560;
					}
                    smeltItem();
					flag1 = true;
				}
			} else {
                furnaceCookTime = 0;
				if (furnaceHeat > 0) {
					addHeat(-1);
				}
			}
			if (flag != (furnaceBurnTime > 0)) {
				flag1 = true;
				BlockMainFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord, BlockNukeFurnace.getActive(), BlockNukeFurnace.getIdle(), true);
			}
			if (furnaceHeat >= 2560) {
				BlockNukeFurnace.meltdown(worldObj, xCoord, yCoord, zCoord);
			}
		}
		if (flag1) {
			this.markDirty();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("Heat", (short) furnaceHeat);
	}
}
