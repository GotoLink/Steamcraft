package steamcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import steamcraft.blocks.BlockChemFurnace;
import steamcraft.blocks.BlockMainFurnace;

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
	public int func_145955_e(int i) {
		if (currentItemBurnTimea == 0) {
			currentItemBurnTimea = 100;
		}
		if (currentItemBurnTimeb == 0) {
			currentItemBurnTimeb = 100;
		}
		return (field_145956_a * i) / (currentItemBurnTimea + currentItemBurnTimeb);
	}

	@Override
	public int func_145953_d(int i) {
		return (field_145956_a * i) / 100;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		if (par1 == 3)
			return func_145954_b(par2ItemStack);
		else
			return super.isItemValidForSlot(par1, par2ItemStack);
	}

	@Override
	public void func_145839_a(NBTTagCompound nbttagcompound) {
		super.func_145839_a(nbttagcompound);
		currentItemBurnTimea = func_145952_a(getStackInSlot(1));
		currentItemBurnTimeb = func_145952_a(getStackInSlot(3));
	}

	@Override
	public void func_145845_h() {
		boolean flag = field_145956_a > 0;
		boolean flag1 = false;
		if (field_145956_a > 0) {
            field_145956_a--;
		}
		if (!field_145850_b.isRemote) {
			if (field_145956_a == 0 && isSmeltable()) {
				ItemStack stack1 = getStackInSlot(1).copy();
				ItemStack stack3 = getStackInSlot(3).copy();
				if (stack1 != null && stack3 != null) {
					if (!stack1.isItemEqual(stack3)) {
						currentItemBurnTimea = func_145952_a(stack1);
						currentItemBurnTimeb = func_145952_a(stack3);
						if (currentItemBurnTimea > 0 && currentItemBurnTimeb > 0) {
                            field_145956_a = currentItemBurnTimea + currentItemBurnTimeb;
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
                field_145961_j++;
				if (field_145961_j >= 100) {
                    field_145961_j = 0;
                    func_145949_j();
					flag1 = true;
				}
			} else {
                field_145961_j = 0;
			}
			if (flag != (field_145956_a > 0)) {
				flag1 = true;
				BlockMainFurnace.updateFurnaceBlockState(field_145956_a > 0, field_145850_b, field_145851_c, field_145848_d, field_145849_e, BlockChemFurnace.getActive(), BlockChemFurnace.getIdle(), false);
			}
		}
		if (flag1) {
			this.onInventoryChanged();
		}
	}
}
