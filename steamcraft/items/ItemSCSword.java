package steamcraft.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;
import steamcraft.EnumToolSteamcraft;

public class ItemSCSword extends ItemSword
{
    public ItemSCSword(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, enumtoolmaterial);
        weaponDamage = 4 + enumtoolmaterial.getDamageVsEntity() * 2;
		itemDamage = 0;
    }

    public int getDamageVsEntity(Entity entity)
    {
		if(toolMaterial == EnumToolSteamcraft.STEAM){
		return weaponDamage - (int)Math.round(itemDamage*10/320);
		}
		return weaponDamage;
    }

    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 0x11940;
    }
	
	private int itemDamage;
}
