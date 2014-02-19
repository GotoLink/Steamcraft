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
	public int func_145955_e(int i) {
		if (field_145963_i == 0) {
            field_145963_i = 20;
		}
		return (field_145956_a * i) / field_145963_i;
	}

	@Override
	public int func_145953_d(int i) {
		return (field_145961_j * i) / 20;
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
	public void func_145839_a(NBTTagCompound nbttagcompound) {
		super.func_145839_a(nbttagcompound);
		furnaceHeat = nbttagcompound.getShort("Heat");
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
			if (isBurning() && isSmeltable()) {
                field_145961_j++;
				if (field_145961_j >= 20) {
                    field_145961_j = 0;
					if (furnaceHeat <= 2560) {
						addHeat(40);
					}
					if (furnaceHeat > 2520 && furnaceHeat < 2560) {
						furnaceHeat = 2560;
					}
                    func_145949_j();
					flag1 = true;
				}
			} else {
                field_145961_j = 0;
				if (furnaceHeat > 0) {
					addHeat(-1);
				}
			}
			if (flag != (field_145956_a > 0)) {
				flag1 = true;
				BlockMainFurnace.updateFurnaceBlockState(field_145956_a > 0, field_145850_b, field_145851_c, field_145848_d, field_145849_e, BlockNukeFurnace.getActive(), BlockNukeFurnace.getIdle(), true);
			}
			if (furnaceHeat >= 2560) {
				BlockNukeFurnace.meltdown(field_145850_b, field_145851_c, field_145848_d, field_145849_e);
			}
		}
		if (flag1) {
			this.onInventoryChanged();
		}
	}

	@Override
	public void func_145841_b(NBTTagCompound nbttagcompound) {
		super.func_145841_b(nbttagcompound);
		nbttagcompound.setShort("Heat", (short) furnaceHeat);
	}
}
