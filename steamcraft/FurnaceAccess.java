package steamcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class FurnaceAccess extends TileEntityFurnace {
	public FurnaceAccess(int size) {
		super();
		if (size != 3) {
            field_145957_n = new ItemStack[size];
        }
	}

	public boolean isSmeltable() {
        return func_145948_k();
	}

    public boolean isBurning(){
        return func_145950_i();
    }

    public int getCookTime(){
        return field_145961_j;
    }

    public void setCookTime(int i){
        field_145961_j = i;
    }

    public int getBurnTime(){
        return field_145956_a;
    }

    public void setBurnTime(int i){
        field_145956_a = i;
    }

    public int getItemBurnTime(){
        return field_145963_i;
    }

    public void setItemBurnTime(int i){
        field_145963_i = i;
    }

    public int getBurnTimeLeftScaled(int scaleFactor){
        return func_145955_e(scaleFactor);
    }

    public int getCookProgressScaled(int scaleFactor){
        return func_145953_d(scaleFactor);
    }
}
