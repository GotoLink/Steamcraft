package steamcraft;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import steamcraft.blocks.BlockMainFurnace;
import steamcraft.blocks.BlockSteamFurnace;

public class TileEntitySteamFurnace extends FurnaceAccess {
	public int waterLevel;

	public TileEntitySteamFurnace() {
		super(4);
        func_145951_a("Steam Furnace");
	}

	@Override
	public int func_145955_e(int i) {
		if (field_145963_i == 0) {
            field_145963_i = 600;
		}
		return (field_145956_a * i) / field_145963_i;
	}

	@Override
	public int func_145953_d(int i) {
		return (field_145961_j * i) / 600;
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
			return (par2ItemStack.getItem() == Items.water_bucket);
		else
			return super.isItemValidForSlot(par1, par2ItemStack);
	}

	@Override
	public void func_145839_a(NBTTagCompound nbttagcompound) {
		super.func_145839_a(nbttagcompound);
		waterLevel = nbttagcompound.getShort("WaterLevel");
	}

	@Override
	public void func_145845_h() {
		boolean flag = field_145956_a > 0;
		boolean flag1 = false;
		if (field_145956_a > 0) {
            field_145956_a--;
		}
		if (!field_145850_b.isRemote) {
			if (this.field_145956_a == 0 && this.isSmeltable()) {
				ItemStack stack1 = getStackInSlot(1).copy();
				this.field_145963_i = this.field_145956_a = func_145952_a(stack1);
				if (this.field_145956_a > 0) {
					flag1 = true;
					if (stack1 != null) {
						decrStackSize(1,1);
						if (this.getStackInSlot(1) == null) {
							setInventorySlotContents(1, stack1.getItem().getContainerItem(stack1));
						}
					}
				}
			}
			if (isBurning()) {
				if (waterLevel == 1) {
					BlockSteamFurnace.playSound(field_145850_b, field_145851_c, field_145848_d, field_145849_e, "random.fizz");
				}
				waterLevel--;
			}
			if (isBurning() && isSmeltable()) {
				if (waterLevel > 0) {
                    field_145961_j += 4;
				} else {
                    field_145961_j += 3;
				}
				if (waterLevel > 4096) {
					waterLevel = 4096;
				}
				if (waterLevel < 0) {
					waterLevel = 0;
				}
				if (getStackInSlot(3) != null) {
					if (waterLevel <= 0 && getStackInSlot(3).getItem() == Items.water_bucket) {
						setInventorySlotContents(3, new ItemStack(Items.bucket));
						waterLevel = 4096;
						BlockSteamFurnace.playSound(field_145850_b, field_145851_c, field_145848_d, field_145849_e, "random.fizz");
					}
				}
				if (field_145961_j >= 600) {
                    field_145961_j = 0;
                    func_145949_j();
					flag1 = true;
				}
			} else {
                field_145961_j = 0;
			}
			if (flag != (field_145956_a > 0)) {
				flag1 = true;
				BlockMainFurnace.updateFurnaceBlockState(field_145956_a > 0, field_145850_b, field_145851_c, field_145848_d, field_145849_e, BlockSteamFurnace.getActive(), BlockSteamFurnace.getIdle(), false);
			}
		}
		if (flag1) {
			this.onInventoryChanged();
		}
	}

	@Override
	public void func_145841_b(NBTTagCompound nbttagcompound) {
		super.func_145841_b(nbttagcompound);
		nbttagcompound.setShort("WaterLevel", (short) waterLevel);
	}
}
