package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class BlockWirelessLamp extends BlockRedstoneTorch
{
    public boolean torchActive;
	private Class EntityClass;
	public BlockWirelessLamp(int i, Class class1, boolean flag)
    {
        super(i, flag);
		EntityClass = class1;
        torchActive = flag;
		float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
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
	@Override
    public int tickRate(World world)
    {
        return 1;
    }
	@Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        setBlockBoundsBasedOnState(world, i, j, k);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }
	@Override
	public int getRenderType()
    {
        return -1;
    }
	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
	@Override
    public TileEntity createTileEntity(World world,int meta)
    {
        try
        {
            return (TileEntity)EntityClass.newInstance();
        }
        catch(Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }

    private boolean isPowering(World world, int i, int j, int k)
    {
		int i1;
		int i2;
		int j1;
		int k1;
		int k2;
		for(int l = 0; l < 9; l ++){
        i1 = world.getBlockMetadata(i+l, j, k);
		i2 = world.getBlockMetadata(i-l, j, k);
		j1 = world.getBlockMetadata(i, j-l, k);
		k1 = world.getBlockMetadata(i, j, k+l);
		k2 = world.getBlockMetadata(i, j, k-l);
        if(j1 == 5 && world.getBlockId(i,j-l,k) == Steamcraft.torchTeslaActive.blockID)
        {
            return true;
        }
        if(k2 == 3 && world.getBlockId(i,j,k-l) == Steamcraft.torchTeslaActive.blockID)
        {
            return true;
        }
        if(k1 == 4 && world.getBlockId(i,j,k+l) == Steamcraft.torchTeslaActive.blockID)
        {
            return true;
        }
        if(i2 == 1 && world.getBlockId(i-l,j,k) == Steamcraft.torchTeslaActive.blockID)
        {
            return true;
        }
        if(i1 == 2 && world.getBlockId(i+l,j,k) == Steamcraft.torchTeslaActive.blockID)
		{
			return true;
		}
		}
		return false;
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
		super.updateTick(world, i, j, k, random);
        if(world.getBlockMetadata(i, j, k) == 0)
        {
            onBlockAdded(world, i, j, k);
        }
        boolean flag = isPowering(world, i, j, k);
        if(!torchActive)
        {
            if(flag)
            {
                world.setBlock(i, j, k, Steamcraft.wirelessLampActive.blockID, world.getBlockMetadata(i, j, k), 2);
            }
        } else
			if(!flag)
			{
				world.setBlock(i, j, k, Steamcraft.wirelessLampIdle.blockID, world.getBlockMetadata(i, j, k), 2);
			}
    }
    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
		super.onNeighborBlockChange(world, i, j, k, l);
        world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
    }
	@Override
    public int idDropped(int i, Random random, int j)
    {
        return Steamcraft.wirelessLamp.itemID;
    }
	@Override
    public boolean canProvidePower()
    {
        return false;
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
        double d1 = (double)((float)j + 1.0F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
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
