package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class ItemSCTool extends ItemTool
{
    private Block[] blocksEffectiveAgainst;
	protected ItemSCTool(int i, int j, EnumToolMaterial enumtoolmaterial, Block ablock[])
    {
        super(i, j, enumtoolmaterial, ablock);
        this.blocksEffectiveAgainst = ablock;
    }
    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
        {
            if(blocksEffectiveAgainst[i] == block)
            {
				if(toolMaterial == Steamcraft.STEAM){
					return (efficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
				}
				return efficiencyOnProperMaterial;
            }
        }
        return 1.0F;
    }
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase1, EntityLivingBase livingBase2)
	{
		if(toolMaterial == Steamcraft.STEAM){
			damageVsEntity-= (int)Math.round(stack.getItemDamage()*10/320);
		}
		return super.hitEntity(stack, livingBase1, livingBase2);
	}
}
