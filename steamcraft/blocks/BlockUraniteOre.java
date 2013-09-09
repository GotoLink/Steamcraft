package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class BlockUraniteOre extends Block
{
    public BlockUraniteOre(int i)
    {
        super(i, Material.rock);
        setTickOnLoad(true);
    }

    public int tickRate()
    {
        return 30;
    }

   /* public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        func_320_h(world, i, j, k);
        super.onBlockClicked(world, i, j, k, entityplayer);
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
        func_320_h(world, i, j, k);
        super.onEntityWalking(world, i, j, k, entity);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        func_320_h(world, i, j, k);
        return super.blockActivated(world, i, j, k, entityplayer);
    }*/

    private void func_320_h(World world, int i, int j, int k)
    {
        func_319_i(world, i, j, k);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
    }

    public int idDropped(int i, Random random)
    {
        return mod_Steamcraft.oreUranite.blockID;
    }

    public int quantityDropped(Random random)
    {
        return 1;
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