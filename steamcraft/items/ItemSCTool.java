package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import steamcraft.EnumToolSteamcraft;

public class ItemSCTool extends ItemTool
{
    protected ItemSCTool(int i, int j, EnumToolMaterial enumtoolmaterial, Block ablock[])
    {
        super(i, j, enumtoolmaterial, ablock);
        blocksEffectiveAgainst = ablock;
        efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial();
    }

    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
        {
            if(blocksEffectiveAgainst[i] == block)
            {
				if(toolMaterial == EnumToolSteamcraft.STEAM){
				return (efficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
				}
				return efficiencyOnProperMaterial;
            }
        }

        return 1.0F;
    }

    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving)
    {
       if(toolMaterial == EnumToolSteamcraft.STEAM){
	   System.out.println(efficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
		}
		itemstack.damageItem(1, entityliving);
        return true;
    }

    public int getDamageVsEntity(Entity entity)
    {
		if(toolMaterial == EnumToolSteamcraft.STEAM){
		return (int) (damageVsEntity - (int)Math.round(itemDamage*5/320));
		}
		return (int) damageVsEntity;
    }

    private Block blocksEffectiveAgainst[];
}
