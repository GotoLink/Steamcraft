package steamcraft.items;

import steamcraft.mod_Steamcraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSCSword extends ItemSword
{
    public ItemSCSword(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, enumtoolmaterial);
        weaponDamage = 4 + enumtoolmaterial.getDamageVsEntity() * 2;
    }
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase1, EntityLivingBase livingBase2)
    {
		if(toolMaterial == mod_Steamcraft.STEAM){
			weaponDamage-= (int)Math.round(stack.getItemDamage()*10/320);
		}
		return super.hitEntity(stack, livingBase1, livingBase2);
    }
    @Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 0x11940;
    }
}
