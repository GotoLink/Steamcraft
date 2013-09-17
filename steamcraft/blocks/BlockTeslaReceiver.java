package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeslaReceiver extends Block
{
    private Icon sideBlock;

	public BlockTeslaReceiver(int i)
    {
        super(i, Steamcraft.staticcircuit);
		setTickRandomly(true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		setLightOpacity(0);
    }
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return side!=ForgeDirection.DOWN && side!=ForgeDirection.UP;
	}
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(l == 1 || (l == 0))
        {
            return blockIcon;
        }
    	return sideBlock;
	}
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        sideBlock = par1IconRegister.registerIcon("steamcraft:receiverside");
    }
    @Override
	public int tickRate(World world)
    {
        return 2;
    }
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    @Override
	public void updateTick(World world, int i, int j, int k, Random random)
    {
	    world.notifyBlocksOfNeighborChange(i, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
	    world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
	    world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
	    world.markBlockForUpdate(i, j, k);
		if(world.getBlockMetadata(i, j, k) == 2){
			world.setBlock(i, j, k, Steamcraft.teslaReceiverActive.blockID, 2, 2);
		}else{
			world.setBlock(i, j, k, Steamcraft.teslaReceiver.blockID, 1, 2);
		}
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}
    @Override
	public void onBlockAdded(World world, int i, int j, int k)
    {
		for(int a = -9; a <= 9; a ++){
			for(int b = -9; b <= 9; b ++){
				for(int c = -9; c <= 9; c ++){
					if(world.getBlockId(i+a, j+b, k+c) == Steamcraft.torchTeslaIdle.blockID || world.getBlockId(i+a, j+b, k+c) == Steamcraft.torchTeslaActive.blockID){
						world.notifyBlocksOfNeighborChange(i+a, j+b, k+c, blockID);
						world.scheduleBlockUpdate(i+a, j+b, k+c, blockID, tickRate(world));
					}
				}
			}
		}
		world.notifyBlocksOfNeighborChange(i, j, k, blockID);
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}
	
    @Override
	public void breakBlock(World world, int i, int j, int k, int par5, int par6)
    {
	    world.notifyBlocksOfNeighborChange(i, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
	    world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
	    world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
        super.breakBlock(world, i, j, k, par5, par6);
    }
	
	public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return iblockaccess.getBlockMetadata(i, j, k) == 2;
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
    public boolean canProvidePower()
    {
        return true;
    }
    @Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
		int l = world.getBlockMetadata(i, j, k);
		if(l == 1)
        {
            return;
        }
        double d = (double)((float)i + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d1 = (double)((float)j+0.2F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d2 = (double)((float)k + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d4 = 0.5D;
        world.spawnParticle("reddust", d - d4, d1, d2, -1.0D, 0.7D, 1.0D);
        world.spawnParticle("reddust", d + d4, d1, d2, -1.0D, 0.7D, 1.0D);
        world.spawnParticle("reddust", d, d1, d2 - d4, -1.0D, 0.7D, 1.0D);
        world.spawnParticle("reddust", d, d1, d2 + d4, -1.0D, 0.7D, 1.0D);
        world.spawnParticle("reddust", d, d1+1.1D, d2, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d, d1-0.2D, d2, -1.0D, 0.7D, 1.0D);
    }
    @Override
    public int quantityDropped(Random random)
    {
        return 1;
    }
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return Steamcraft.teslaReceiver.blockID;
    }
    @Override
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
        return (true);
        }
    }
	
	public int sideIndexInTexture;
}



/*
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