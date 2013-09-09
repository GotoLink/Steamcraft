package steamcraft.items;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import steamcraft.mod_Steamcraft;

public class ItemSCArmor extends ItemArmor
{
    public ItemSCArmor(int i, int j, int k, int l)
    {
        super(i, EnumArmorMaterial.values()[j], k, l);
        damageReduceAmount = damageReduceAmountArray[l];
		if(k == mod_Steamcraft.EtheriumRenderIndex){
		setMaxDamage(-1);
		}else{
        setMaxDamage((int) Math.round(maxDamageArray[l] * 2.5) << j);
		}
    }

    private static final int damageReduceAmountArray[] = {
        3, 8, 6, 3
    };
    private static final int maxDamageArray[] = {
        11, 16, 15, 13
    };
}
