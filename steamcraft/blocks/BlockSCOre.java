package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class BlockSCOre extends BlockOre
{
    public BlockSCOre(int i)
    {
        super(i);
    }
	
	public void onBlockAdded(World world, int i, int j, int k)
    {
	if(blockID == mod_Steamcraft.oreVolucite.blockID){
		System.out.println("Volucite Generated At: " + i + ", " + j + ", " + k);
		}
	if(blockID == Block.oreDiamond.blockID){
		System.out.println("Diamond Generated At: " + i + ", " + j + ", " + k);
		}
	}

    public int idDropped(int i, Random random)
    {
        if(blockID == Block.oreCoal.blockID)
        {
            return Item.coal.itemID;
        }
        if(blockID == Block.oreDiamond.blockID)
        {
            return Item.diamond.itemID;
        }
        if(blockID == Block.oreLapis.blockID)
        {
            return Item.dyePowder.itemID;
        }
		if(blockID == mod_Steamcraft.oreVolucite.blockID)
        {
			return mod_Steamcraft.etherium.itemID;
		}		
		else
        {
            return blockID;
        }
    }

    public int quantityDropped(Random random)
    {
        if(blockID == Block.oreLapis.blockID)
        {
            return 4 + random.nextInt(5);
        }
		if(blockID == Block.oreCoal.blockID)
        {
            return 2 + random.nextInt(3);
        }
		if(blockID == Block.oreDiamond.blockID)
		{
			if(random.nextInt(10) == 0){
			return 2 + random.nextInt(2);
			}
			else
			{
			return 1;
			}
		} 
		else
        {
            return 1;
        }
    }
}
