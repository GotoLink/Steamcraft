package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class BlockSCOre extends BlockOre
{
    public BlockSCOre(int i)
    {
        super(i);
    }
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
    {
		if(blockID == Steamcraft.oreVolucite.blockID){
			//System.out.println("Volucite Generated At: " + i + ", " + j + ", " + k);
		}
	}
	@Override
    public int idDropped(int i, Random random, int j)
    {
		if(blockID == Steamcraft.oreVolucite.blockID)
        {
			return Steamcraft.etherium.itemID;
		}		
		else
        {
            return super.idDropped(i, random, j);
        }
    }
}
