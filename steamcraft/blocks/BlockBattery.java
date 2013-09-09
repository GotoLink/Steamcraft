package steamcraft.blocks;

import java.util.Random;

import steamcraft.mod_Steamcraft;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBattery extends Block
{
    public BlockBattery(int i)
    {
        super(i, mod_Steamcraft.solidcircuit);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        setLightOpacity(0);
    }
	
    public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(l == 1)
        {
            return blockIcon;
        }
        if(l == 0)
        {
            return blockIcon;
        }else
        {
            return blockSide;
        }
	}

	public int tickRate()
    {
        return 1;
    }
	
	public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	public void updateTick(World world, int i, int j, int k, Random random)
    {
		world.setBlockMetadataWithNotify(i, j, k, 1, 3);
	    world.notifyBlocksOfNeighborChange(i, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
	    world.markBlocksDirty(i, j, k, i, j, k);
	    world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
	}
	
	public void onBlockRemoval(World world, int i, int j, int k)
    {
        world.notifyBlocksOfNeighborChange(i, j, k, blockID);
		world.notifyBlocksOfNeighborChange(i+1, j, k, blockID);
		world.notifyBlocksOfNeighborChange(i-1, j, k, blockID);
		world.notifyBlocksOfNeighborChange(i, j, k+1, blockID);
		world.notifyBlocksOfNeighborChange(i, j, k-1, blockID);
		world.notifyBlocksOfNeighborChange(i, j+1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        super.onBlockRemoval(world, i, j, k);
    }
	
	public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return iblockaccess.getBlockMetadata(i, j, k) <= 0;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
            return l == 1;
    }

    public boolean canProvidePower()
    {
        return true;
    }
	
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
	   double d = (double)((float)i + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
       double d1 = (double)((float)j+0.2F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
       double d2 = (double)((float)k + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
       double d4 = 0.5D;
       world.spawnParticle("reddust", d - d4, d1, d2, -1.0D, 0.7D, 1.0D);
       world.spawnParticle("reddust", d + d4, d1, d2, -1.0D, 0.7D, 1.0D);
       world.spawnParticle("reddust", d, d1, d2 - d4, -1.0D, 0.7D, 1.0D);
       world.spawnParticle("reddust", d, d1, d2 + d4, -1.0D, 0.7D, 1.0D);
	}
	
    public int quantityDropped(Random random)
    {
        return 1;
    }

    public int idDropped(int i, Random random)
    {
        return mod_Steamcraft.battery.blockID;
    }
	
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(l == 1)
        {
            return true;
        }
        if(!super.shouldSideBeRendered(iblockaccess, i, j, k, l))
        {
            return false;
        }
        if(l == 0)
        {
            return true;
        } else
        {
            return iblockaccess.getBlockId(i, j, k) != blockID;
        }
    }
}



/*import java.util.List;
import java.util.Random;

public class BlockBattery extends Block
{

    protected BlockBattery(int i, int j)
    {
        super(i, j, Material.rock);
        triggerMobType =  EnumMobType.everything;
        setTickOnLoad(true);
    }

    public int tickRate()
    {
        return 1;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return true;
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        boolean flag = false;
        if(!world.isBlockOpaqueCube(i, j - 1, k))
        {
            flag = true;
        }
        if(flag)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
            setStateIfMobInteractsWithPlate(world, i, j, k);
            return;
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
	setStateIfMobInteractsWithPlate(world, i, j, k);
    return;
    }

    private void setStateIfMobInteractsWithPlate(World world, int i, int j, int k)
    {
            world.setBlockMetadataWithNotify(i, j, k, 1);
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.markBlocksDirty(i, j, k, i, j, k);
            world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l > 0)
        {
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        }
        super.onBlockRemoval(world, i, j, k);
    }

    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return iblockaccess.getBlockMetadata(i, j, k) > 0;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
        if(world.getBlockMetadata(i, j, k) == 0)
        {
            return false;
        } else
        {
            return l == 1;
        }
    }

    public boolean canProvidePower()
    {
        return true;
    }
	
    private EnumMobType triggerMobType;
}*/