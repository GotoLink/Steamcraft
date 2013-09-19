package steamcraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.RedstoneUpdateInfo;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class BlockElectricLamp extends BlockRedstoneTorch
{
    public boolean torchActive;
	private Class EntityClass;
	public BlockElectricLamp(int i, Class class1, boolean flag)
    {
        super(i, flag);
		EntityClass = class1;
        torchActive = flag;
		float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
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
	public boolean hasTileEntity(int meta)
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
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	boolean flag = this.isIndirectlyPowered(world, i, j, k);
        List list = (List)redstoneUpdateInfoCache.get(world);

        while (list != null && !list.isEmpty() && world.getTotalWorldTime() - ((RedstoneUpdateInfo)list.get(0)).updateTime > 60L)
        {
            list.remove(0);
        }
        if(!torchActive)
        {
            if(flag)
            {
                world.setBlock(i, j, k, getActiveBlock(), world.getBlockMetadata(i, j, k), 2);
                if (this.checkForBurnout(world, i, j, k, true))
                {
                    world.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                    for (int l = 0; l < 5; ++l)
                    {
                        double d0 = (double)i + random.nextDouble() * 0.6D + 0.2D;
                        double d1 = (double)j + random.nextDouble() * 0.6D + 0.2D;
                        double d2 = (double)k + random.nextDouble() * 0.6D + 0.2D;
                        world.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        } 
        else if(!flag)
		{
			world.setBlock(i, j, k, getIdleBlock(), world.getBlockMetadata(i, j, k), 2);
		}
    }
    protected int getActiveBlock() 
	{
		return Steamcraft.torchElectricActive.blockID;
	}
    protected int getIdleBlock() 
    {
		return Steamcraft.torchElectricIdle.blockID;
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
        return Steamcraft.electricLamp.itemID;
    }
	@Override
    public boolean canProvidePower()
    {
        return false;
    }
	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int i, int j, int k, int l)
    {
		if(!torchActive)
        {
            return 0;
        }
		return 15;
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
