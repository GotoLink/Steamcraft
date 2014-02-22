package steamcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class FurnaceAccess extends TileEntityFurnace {
	public FurnaceAccess(int size) {
		super();
		if (size != 3) {
            furnaceItemStacks = new ItemStack[size];
        }
	}

	public boolean isSmeltable() {
        return canSmelt();
	}

    public int getCookTime(){
        return furnaceCookTime;
    }

    public void setCookTime(int i){
        furnaceCookTime = i;
    }

    public int getBurnTime(){
        return furnaceBurnTime;
    }

    public void setBurnTime(int i){
        furnaceBurnTime = i;
    }

    public int getItemBurnTime(){
        return currentItemBurnTime;
    }

    public void setItemBurnTime(int i){
        currentItemBurnTime = i;
    }
}
