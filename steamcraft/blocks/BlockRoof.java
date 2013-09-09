package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRoof extends Block
{
    public BlockRoof(int i)
    {
        super(i, Material.rock);
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
            return blockIndexInTexture;
    }

	public int idDropped(int i, Random random)
    {
        return Block.stairSingle.blockID;
    }
	
	public int quantityDropped(Random random)
    {
        return 2;
	}
	
	protected int damageDropped(int i)
    {
        return 6;
    }
	
}
