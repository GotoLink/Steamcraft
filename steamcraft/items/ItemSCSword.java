package steamcraft.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import steamcraft.Steamcraft;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemSCSword extends ItemSword
{
	private EnumToolMaterial toolMaterial;
	private double damage;
	public ItemSCSword(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, enumtoolmaterial);
        this.toolMaterial = enumtoolmaterial;
        damage=4 + enumtoolmaterial.getDamageVsEntity()*2;
    }
	@Override
	public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.damage, 0));
        return multimap;
    }
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase1, EntityLivingBase livingBase2)
    {
		if(toolMaterial == Steamcraft.TOOLSTEAM){
			damage=4 + func_82803_g()*2-(double)stack.getItemDamage()*10/320;
		}
		return super.hitEntity(stack, livingBase1, livingBase2);
    }
    @Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 0x11940;
    }
    @Override
	public boolean onBlockDestroyed(ItemStack stack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
    {
		if(toolMaterial == Steamcraft.TOOLSTEAM){
			damage=4 + func_82803_g()*2- (float)stack.getItemDamage()*10/320;
		}
        return super.onBlockDestroyed(stack, par2World, par3, par4, par5, par6, par7EntityLivingBase);
    }
}
