package steamcraft.items;

import com.google.common.collect.Multimap;

import steamcraft.Steamcraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSCSword extends ItemSword
{
    private EnumToolMaterial toolMaterial;
	public ItemSCSword(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, enumtoolmaterial);
        this.toolMaterial = enumtoolmaterial;
        setWeaponDamage(4 + enumtoolmaterial.getDamageVsEntity() * 2);
    }
	public void setWeaponDamage(float dmg){
		getItemAttributeModifiers().put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)dmg, 0));
	}
	public double getWeaponDamage(){
		return ((AttributeModifier)getItemAttributeModifiers().get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())).getAmount();
	}
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase1, EntityLivingBase livingBase2)
    {
		if(toolMaterial == Steamcraft.STEAM){
			setWeaponDamage((float) (getWeaponDamage()-Math.round(stack.getItemDamage()*10/320)));
		}
		return super.hitEntity(stack, livingBase1, livingBase2);
    }
    @Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 0x11940;
    }
}
