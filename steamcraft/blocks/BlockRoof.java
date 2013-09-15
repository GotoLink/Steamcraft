package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;

public class BlockRoof extends Block
{
    public BlockRoof(int i)
    {
        super(i, Material.rock);
    }
    @Override
    public Icon getIcon(int i, int j)
    {
        return blockIndexInTexture;
    }
    @Override
	public int idDropped(int i, Random random, int j)
    {
        return Block.stairSingle.blockID;
    }
    @Override
	public int quantityDropped(Random random)
    {
        return 2;
	}
    @Override
	public int damageDropped(int i)
    {
        return 6;
    }
	
}
