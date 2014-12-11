package steamcraft;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import steamcraft.blocks.BlockChemFurnace;
import steamcraft.blocks.BlockMainFurnace;

import java.util.HashMap;
import java.util.Map;

public class TileEntityChemFurnace extends FurnaceAccess {
	public int currentItemBurnTimea;
	public int currentItemBurnTimeb;
	public static Map<Item, Integer> fuels = new HashMap<Item, Integer>();

	public TileEntityChemFurnace() {
		super(4);
        func_145951_a("Chemical Furnace");
	}

	static {
		fuels.put(Items.sugar, 20);
		fuels.put(Items.gunpowder, 100);
		fuels.put(Items.glowstone_dust, 3200);
	}

	@Override
	public int getBurnTimeRemainingScaled(int i) {
		if (currentItemBurnTimea == 0) {
			currentItemBurnTimea = 100;
		}
		if (currentItemBurnTimeb == 0) {
			currentItemBurnTimeb = 100;
		}
		return (furnaceBurnTime * i) / (currentItemBurnTimea + currentItemBurnTimeb);
	}

	@Override
	public int getCookProgressScaled(int i) {
		return (furnaceBurnTime * i) / 100;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		if (par1 == 3)
			return isItemFuel(par2ItemStack);
		else
			return super.isItemValidForSlot(par1, par2ItemStack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		currentItemBurnTimea = getItemBurnTime(getStackInSlot(1));
		currentItemBurnTimeb = getItemBurnTime(getStackInSlot(3));
	}

	@Override
	public void updateEntity() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;
		if (furnaceBurnTime > 0) {
            furnaceBurnTime--;
		}
		if (!worldObj.isRemote) {
			if (furnaceBurnTime == 0 && isSmeltable()) {
				ItemStack stack1 = getStackInSlot(1).copy();
				ItemStack stack3 = getStackInSlot(3).copy();
				if (stack1 != null && stack3 != null) {
					if (!stack1.isItemEqual(stack3)) {
						currentItemBurnTimea = getItemBurnTime(stack1);
						currentItemBurnTimeb = getItemBurnTime(stack3);
						if (currentItemBurnTimea > 0 && currentItemBurnTimeb > 0) {
                            furnaceBurnTime = currentItemBurnTimea + currentItemBurnTimeb;
							flag1 = true;
							decrStackSize(1,1);
							decrStackSize(3,1);
							if (getStackInSlot(1) == null) {
								setInventorySlotContents(1, stack1.getItem().getContainerItem(stack1));
							}
							if (getStackInSlot(3) == null) {
								setInventorySlotContents(3, stack3.getItem().getContainerItem(stack3));
							}
						}
					}
				}
			}
			if (isBurning() && isSmeltable()) {
                furnaceCookTime++;
				if (furnaceCookTime >= 100) {
                    furnaceCookTime = 0;
                    smeltItem();
					flag1 = true;
				}
			} else {
                furnaceCookTime = 0;
			}
			if (flag != (furnaceBurnTime > 0)) {
				flag1 = true;
				BlockMainFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord, BlockChemFurnace.getActive(), BlockChemFurnace.getIdle(), false);
			}
		}
		if (flag1) {
			this.markDirty();
		}
	}
}
