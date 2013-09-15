package steamcraft.blocks;

import java.util.Random;

import net.minecraft.world.World;

public class BlockTorchPhosphorus extends BlockSCTorch
{
    public BlockTorchPhosphorus(int i)
    {
		super(i);
    }
    
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        int l = world.getBlockMetadata(i, j, k);
        double d = (double)((float)i + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d1 = (double)((float)j + 0.7F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d2 = (double)((float)k + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;
        if(l == 1)
        {
            world.spawnParticle("reddust", d - d4, d1 + d3, d2, -1.0D, 1.0D, 0.0D);
        } else
        if(l == 2)
        {
            world.spawnParticle("reddust", d + d4, d1 + d3, d2, -1.0D, 1.0D, 0.0D);
        } else
        if(l == 3)
        {
            world.spawnParticle("reddust", d, d1 + d3, d2 - d4, -1.0D, 1.0D, 0.0D);
        } else
        if(l == 4)
        {
            world.spawnParticle("reddust", d, d1 + d3, d2 + d4, -1.0D, 1.0D, 0.0D);
        } else
        {
            world.spawnParticle("reddust", d, d1, d2, -1.0D, 1.0D, 0.0D);
        }
    }
}