package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import steamcraft.Steamcraft;

public class ItemSCHoe extends ItemHoe
{
    public ItemSCHoe(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, enumtoolmaterial);
    }
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
    {
		if(!entityplayer.canPlayerEdit(i, j, k, l, itemstack))
        {
            return false;
        }
		else
        {
            UseHoeEvent event = new UseHoeEvent(entityplayer, itemstack, world, i, j, k);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return false;
            }
            if (event.getResult() == Result.ALLOW)
            {
                itemstack.damageItem(1, entityplayer);
                return true;
            }
	        int i1 = world.getBlockId(i, j, k);
	        int j1 = world.getBlockId(i, j + 1, k);
	        if(l != 0 && j1 == 0 && i1 == Block.grass.blockID || i1 == Block.dirt.blockID)
	        {
	            Block block = Block.tilledField;
	            world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
	            if(world.isRemote)
	            {
	                return true;
	            } else
	            {
	                world.setBlock(i, j, k, block.blockID);
					if(theToolMaterial == Steamcraft.TOOLSTEAM){
						itemstack.damageItem(1 +  (int)Math.round(itemstack.getItemDamage()*5/320), entityplayer);
						return true;
					}
	                itemstack.damageItem(1, entityplayer);
	                return true;
	            }
	        } else
	        {
	            return false;
	        }
        }
    }
}
