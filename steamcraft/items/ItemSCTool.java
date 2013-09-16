package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class ItemSCTool extends ItemTool
{
    protected ItemSCTool(int i, int j, EnumToolMaterial enumtoolmaterial, Block ablock[])
    {
        super(i, j, enumtoolmaterial, ablock);
        blocksEffectiveAgainst = ablock;
        efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial();
    }
    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
        {
            if(blocksEffectiveAgainst[i] == block)
            {
				if(toolMaterial == mod_Steamcraft.STEAM){
					return (efficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
				}
				return efficiencyOnProperMaterial;
            }
        }
        return 1.0F;
    }
	@Override
    public boolean onBlockDestroyed(ItemStack itemstack,World world, int i, int j, int k, int l, EntityLivingBase entityliving)
    {
		if(toolMaterial == mod_Steamcraft.STEAM){
    	   //System.out.println(efficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
		}
		itemstack.damageItem(1, entityliving);
        return true;
    }
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase1, EntityLivingBase livingBase2)
	{
		if(toolMaterial == mod_Steamcraft.STEAM){
			damageVsEntity-= (int)Math.round(stack.getItemDamage()*10/320);
		}
		return super.hitEntity(stack, livingBase1, livingBase2);
	}

    private Block blocksEffectiveAgainst[];
}
