package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemElectricLamp extends Item
{
    public ItemElectricLamp(int i, Block block)
    {
        super(i);
		spawnID = block.blockID;
    }
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
    {
        if(world.getBlockId(i, j, k) != Block.snow.blockID)
        {
            if(l == 0)
            {
                j--;
            }
            if(l == 1)
            {
                j++;
            }
            if(l == 2)
            {
                k--;
            }
            if(l == 3)
            {
                k++;
            }
            if(l == 4)
            {
                i--;
            }
            if(l == 5)
            {
                i++;
            }
            if(!world.isAirBlock(i, j, k))
            {
                return false;
            }
        }
        if(!entityplayer.canPlayerEdit(i, j, k, l, itemstack))
        {
            return false;
        }
        if(itemstack.stackSize == 0)
        {
            return false;
        }
        Block block = Block.blocksList[spawnID];
        if(block.canPlaceBlockAt(world, i, j, k))
        {
            if(world.setBlock(i, j, k, spawnID))
            {
                if(world.getBlockId(i, j, k) == spawnID)
                {
                    Block.blocksList[spawnID].onBlockPlaced(world, i, j, k, l, par8, par9, par10, 0);
                    Block.blocksList[spawnID].onBlockPlacedBy(world, i, j, k, entityplayer, itemstack);
                }
                world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                itemstack.stackSize--;
            }
        }
        return true;
    }

    private int spawnID;
}
