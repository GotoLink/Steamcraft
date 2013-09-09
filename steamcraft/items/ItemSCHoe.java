package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.EnumToolSteamcraft;

public class ItemSCHoe extends ItemHoe
{
    public ItemSCHoe(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, enumtoolmaterial);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
		 if(!entityplayer.func_35190_e(i, j, k))
        {
            return false;
        }
        int i1 = world.getBlockId(i, j, k);
        int j1 = world.getBlockId(i, j + 1, k);
        if(l != 0 && j1 == 0 && i1 == Block.grass.blockID || i1 == Block.dirt.blockID)
        {
            Block block = Block.tilledField;
            world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, block.stepSound.stepSoundDir2(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
            if(world.isRemote)
            {
                return true;
            } else
            {
                world.setBlockWithNotify(i, j, k, block.blockID);
				if(toolMaterial == EnumToolSteamcraft.STEAM){
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
