package steamcraft.blocks;

import java.util.Random;

import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class BlockElectricLamp extends BlockWirelessLamp
{
    public BlockElectricLamp(int i, Class class1, boolean flag)
    {
        super(i, class1,flag);
        setTickRandomly(true);
    }
    @Override
    public int tickRate(World world)
    {
        return 2;
    }

    private boolean isPowering(World world, int i, int j, int k)
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
		 if(l == 6 && world.getIndirectPowerOutput(i, j + 1, k, 1))
        {
            return true;
        }
        return l == 2 && world.getIndirectPowerOutput(i + 1, j, k, 5);
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
		super.updateTick(world, i, j, k, random);
        boolean flag = isPowering(world, i, j, k);
        if(!torchActive)
        {
            if(flag)
            {
                world.setBlock(i, j, k, Steamcraft.torchElectricActive.blockID, world.getBlockMetadata(i, j, k), 2);
            }
        } 
        else if(!flag)
		{
			world.setBlock(i, j, k, Steamcraft.torchElectricIdle.blockID, world.getBlockMetadata(i, j, k), 2);
		}
    }
	
	@Override
    public int idDropped(int i, Random random, int j)
    {
        return Steamcraft.electricLamp.itemID;
    }
}
