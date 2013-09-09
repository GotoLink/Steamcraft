package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockUranium extends Block
{
    public BlockUranium(int i)
    {
        super(i, Material.rock);
    }
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        float f = 0.1F;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f, (float)(i + 1) - f, (float)(j + 1) - f, (float)(k + 1) - f);
    }
	
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        entity.attackEntityFrom(DamageSource.magic, 1);
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
            func_319_i(world, i, j, k);
    }

    private void func_319_i(World world, int i, int j, int k)
    {
        Random random = world.rand;
        double d = 0.0625D;
        for(int l = 0; l < 6; l++)
        {
            double d1 = (float)i + random.nextFloat();
            double d2 = (float)j + random.nextFloat();
            double d3 = (float)k + random.nextFloat();
            if(l == 0 && !world.isBlockOpaqueCube(i, j + 1, k))
            {
                d2 = (double)(j + 1) + d;
            }
            if(l == 1 && !world.isBlockOpaqueCube(i, j - 1, k))
            {
                d2 = (double)(j + 0) - d;
            }
            if(l == 2 && !world.isBlockOpaqueCube(i, j, k + 1))
            {
                d3 = (double)(k + 1) + d;
            }
            if(l == 3 && !world.isBlockOpaqueCube(i, j, k - 1))
            {
                d3 = (double)(k + 0) - d;
            }
            if(l == 4 && !world.isBlockOpaqueCube(i + 1, j, k))
            {
                d1 = (double)(i + 1) + d;
            }
            if(l == 5 && !world.isBlockOpaqueCube(i - 1, j, k))
            {
                d1 = (double)(i + 0) - d;
            }
            if(d1 < (double)i || d1 > (double)(i + 1) || d2 < 0.0D || d2 > (double)(j + 1) || d3 < (double)k || d3 > (double)(k + 1))
            {
                world.spawnParticle("reddust", d1, d2, d3, -1.0D, 1.0D, -1.0D);
            }
        }

    }

    private boolean glowing;
}
