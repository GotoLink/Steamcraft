package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInverter extends BlockRedstoneTorch
{
    private boolean torchActive;
	public BlockInverter(int i, boolean flag)
    {
        super(i,flag);
        torchActive = flag;
        setTickRandomly(true);
    }
	@Override
    public Icon getIcon(int i, int j)
    {
        if(i == 1)
        {
            return Block.redstoneWire.getIcon(i, j);
        } else
        {
			return super.getIcon(i, j);
		}
    }

    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(!torchActive)
        {
            return false;
        }
        int i1 = iblockaccess.getBlockMetadata(i, j, k);
        if(i1 == 5 && l == 1)
        {
            return false;
        }
        if(i1 == 3 && l == 3)
        {
            return false;
        }
        if(i1 == 4 && l == 2)
        {
            return false;
        }
        if(i1 == 1 && l == 5)
        {
            return false;
        }
        return i1 != 2 || l != 4;
    }

    private boolean func_30002_h(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l == 5 && world.getIndirectPowerOutput(i, j - 1, k, 0))
        {
            return true;
        }
        if(l == 3 && world.getIndirectPowerOutput(i, j, k - 1, 2))
        {
            return true;
        }
        if(l == 4 && world.getIndirectPowerOutput(i, j, k + 1, 3))
        {
            return true;
        }
        if(l == 1 && world.getIndirectPowerOutput(i - 1, j, k, 4))
        {
            return true;
        }
        return l == 2 && world.getIndirectPowerOutput(i + 1, j, k, 5);
    }
    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        super.onNeighborBlockChange(world, i, j, k, l);
        world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            return isPoweringTo(world, i, j, k, l);
        } else
        {
            return false;
        }
    }
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(!torchActive)
        {
            return;
        }
        int l = world.getBlockMetadata(i, j, k);
        double d = (double)((float)i + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d1 = (double)((float)j + 0.7F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d2 = (double)((float)k + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;
        if(l == 1)
        {
            world.spawnParticle("reddust", d - d4, d1 + d3, d2, -1.0D, 0.7D, 1.0D);
        } else
        if(l == 2)
        {
            world.spawnParticle("reddust", d + d4, d1 + d3, d2, -1.0D, 0.7D, 1.0D);
        } else
        if(l == 3)
        {
            world.spawnParticle("reddust", d, d1 + d3, d2 - d4, -1.0D, 0.7D, 1.0D);
        } else
        if(l == 4)
        {
            world.spawnParticle("reddust", d, d1 + d3, d2 + d4, -1.0D, 0.7D, 1.0D);
        } else
        {
            world.spawnParticle("reddust", d, d1, d2, -1.0D, 0.7D, 1.0D);
        }
    }
}
