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
	private float baseDamage;
	protected ItemSCTool(int i, int j, EnumToolMaterial enumtoolmaterial, Block ablock[])
    {
        super(i, j, enumtoolmaterial, ablock);
        this.blocksEffectiveAgainst = ablock;
        this.baseDamage = j + enumtoolmaterial.getDamageVsEntity();
    }
    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
    	if(toolMaterial == Steamcraft.TOOLSTEAM){
	        for(int i = 0; i < blocksEffectiveAgainst.length; i++){
	            if(blocksEffectiveAgainst[i] == block){
					return (efficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
	            }
	        }
    	}
        return super.getStrVsBlock(itemstack, block);
    }
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase1, EntityLivingBase livingBase2)
	{
		if(toolMaterial == Steamcraft.TOOLSTEAM){
			damageVsEntity=baseDamage- (float)stack.getItemDamage()*10/320;
		}
		return super.hitEntity(stack, livingBase1, livingBase2);
	}
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
    {
		if(toolMaterial == Steamcraft.TOOLSTEAM){
			damageVsEntity=baseDamage- (float)stack.getItemDamage()*10/320;
		}
        return super.onBlockDestroyed(stack, par2World, par3, par4, par5, par6, par7EntityLivingBase);
    }
}
