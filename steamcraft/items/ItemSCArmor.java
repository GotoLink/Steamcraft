package steamcraft.items;

import java.lang.reflect.Field;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import steamcraft.Steamcraft;

public class ItemSCArmor extends ItemArmor
{
    public ItemSCArmor(int i, int j, int k, int l)
    {
        super(i, EnumArmorMaterial.values()[j], k, l);
        setDamageReduceAmount(damageReduceAmountArray[l]);
		if(k == Steamcraft.armorIndexes[0]){
			setMaxDamage(-1);
		}else{
			setMaxDamage((int) Math.round(maxDamageArray[l] * 2.5) << j);
		}
    }

    private void setDamageReduceAmount(int amount){
    	Field f;
		try {
			f = ItemArmor.class.getDeclaredFields()[5];//damagereduceamount
	        if(!f.isAccessible())
	        	f.setAccessible(true);
	        f.set(this,amount);
		}
        catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
    }
    private static final int damageReduceAmountArray[] = {
        3, 8, 6, 3
    };
    private static final int maxDamageArray[] = {
        11, 16, 15, 13
    };
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
    	ItemSCArmor item = (ItemSCArmor)stack.getItem();
    	String s1 = String.format("steamcraft:textures/models/armor/%s_layer_%d.png",
    			Steamcraft.armorMap.get(item.renderIndex), (slot == 2 ? 2 : 1));
		return s1;
    }
}
