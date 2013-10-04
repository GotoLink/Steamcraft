package steamcraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockSCStairs extends BlockStairs
{
    private Block modelBlock;
	private int blockDrop;
	private int dropQuantity;
    public BlockSCStairs(int i, Block block,int meta, int blockdrop, int quantity)
    {
        super(i, block, meta);
        setLightOpacity(0);
        modelBlock = block;
		blockDrop = blockdrop;
		dropQuantity = quantity;
    }
    @Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return side!=ForgeDirection.DOWN && side!=ForgeDirection.UP;
	}
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity ent)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l == 0)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
        } else
        if(l == 1)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
        } else
        if(l == 2)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
        } else
        if(l == 3)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
            super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
        }
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
		if(blockDrop == -1){
			blockDrop = modelBlock.idDropped(i, random, j);
		}
        return blockDrop;
    }
    @Override
    public int quantityDropped(Random random)
    {
		if(dropQuantity == -1){
			dropQuantity = modelBlock.quantityDropped(random);
		}
        return dropQuantity;
    }
    @Override
    public Icon getIcon(int i, int j)
    {
        return modelBlock.getIcon(i, 0);
    }
}