package steamcraft.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import steamcraft.mod_Steamcraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockElectricLamp extends BlockContainer
{

    public BlockElectricLamp(int i, Class class1, boolean flag)
    {
        super(i, Material.circuits);
		EntityClass = class1;
        torchActive = flag;
        setTickRandomly(true);
		float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }
	
	public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        if(i == 1)
        {
            return Block.redstoneWire.getBlockTextureFromSideAndMetadata(i, j);
        } else
        {
            return super.getBlockTextureFromSideAndMetadata(i, j);
        }
    }

    public int tickRate()
    {
        return 2;
    }
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        setBlockBoundsBasedOnState(world, i, j, k);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }
	
	public int getRenderType()
    {
        return -1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public TileEntity getBlockEntity()
    {
        
    }
	
	 private boolean canPlaceTop(World world, int i, int j, int k)
    {
        return (world.isBlockNormalCube(i, j, k) || world.getBlockId(i, j, k) == Block.fence.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.railingCastIron.blockID
		|| world.getBlockId(i, j, k) == Block.glass.blockID);
    }
	
	private boolean canPlaceSide(World world, int i, int j, int k)
    {
        return (world.isBlockNormalCube(i, j, k) || world.getBlockId(i, j, k) == Block.glass.blockID
		|| world.getBlockId(i, j, k) == Block.stairSingle.blockID
		|| world.getBlockId(i, j, k) == Block.stairCompactCobblestone.blockID
		|| world.getBlockId(i, j, k) == Block.stairCompactPlanks.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.stairCompactStone.blockID
		|| world.getBlockId(i, j, k) == Block.stairsStoneBrickSmooth.blockID
		|| world.getBlockId(i, j, k) == Block.stairsBrick.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.stairRoof.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.teslaReceiver.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.teslaReceiverActive.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.battery.blockID);
    }
	
	private boolean canPlaceBottom(World world, int i, int j, int k)
    {
        return (world.isBlockNormalCube(i, j, k) || world.getBlockId(i, j, k) == Block.glass.blockID
		|| world.getBlockId(i, j, k) == Block.stairSingle.blockID
		|| world.getBlockId(i, j, k) == Block.stairCompactCobblestone.blockID
		|| world.getBlockId(i, j, k) == Block.stairCompactPlanks.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.stairCompactStone.blockID
		|| world.getBlockId(i, j, k) == Block.stairsStoneBrickSmooth.blockID
		|| world.getBlockId(i, j, k) == Block.stairsBrick.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.stairRoof.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.teslaReceiver.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.teslaReceiverActive.blockID
		|| world.getBlockId(i, j, k) == mod_Steamcraft.battery.blockID);
	}
	
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(canPlaceSide(world, i - 1, j , k))
        {
            return true;
        }
        if(canPlaceSide(world, i + 1, j , k))
        {
            return true;
        }
        if(canPlaceSide(world, i, j , k - 1))
        {
            return true;
        }
        if(canPlaceSide(world, i, j , k + 1))
        {
            return true;
        }
		if(canPlaceBottom(world, i, j + 1 , k))
        {
            return true;
        }
        return canPlaceTop(world, i, j - 1, k);
    }
	
	public void onBlockPlaced(World world, int i, int j, int k, int l)
    {
		int i1 = world.getBlockMetadata(i, j, k);
		if(l == 0 && canPlaceBottom(world, i, j + 1 , k))
		{
              i1 = 6;
        }
        if(l == 1 && canPlaceTop(world, i, j - 1, k))
        {
              i1 = 5;
        }
        if(l == 2 && canPlaceSide(world, i, j , k + 1))
		{
               i1 = 4;
        }
        if(l == 3 && canPlaceSide(world, i, j , k - 1))
		{
               i1 = 3;
        }
        if(l == 4 && canPlaceSide(world, i + 1, j , k))
		{
               i1 = 2;
        }
        if(l == 5 && canPlaceSide(world, i - 1, j , k))
		{
               i1 = 1;
        }
		 world.setBlockMetadataWithNotify(i, j, k, i1);
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
		if(world.getBlockMetadata(i, j, k) == 0)
      {
           super.onBlockAdded(world, i, j, k);
	  }
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
			world.notifyBlocksOfNeighborChange(i, j, k, blockID);
			
		if(world.getBlockMetadata(i, j, k) == 0){
		if(canPlaceBottom(world, i, j + 1, k))
		{
            world.setBlockMetadataWithNotify(i, j, k, 6);
        } else	
        if(canPlaceSide(world, i - 1, j , k))
		{
            world.setBlockMetadataWithNotify(i, j, k, 1);
        } else
        if(canPlaceSide(world, i + 1, j , k))
		{
            world.setBlockMetadataWithNotify(i, j, k, 2);
        } else
        if(canPlaceSide(world, i, j , k - 1))
		{
            world.setBlockMetadataWithNotify(i, j, k, 3);
        } else
        if(canPlaceSide(world, i, j , k + 1))
		{
            world.setBlockMetadataWithNotify(i, j, k, 4);
        } else
        if(canPlaceTop(world, i, j - 1, k))
        {
            world.setBlockMetadataWithNotify(i, j, k, 5);
		}
		}
        dropTorchIfCantStay(world, i, j, k);
	}

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        if(torchActive)
        {
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
        }
    }

    private boolean func_22026_h(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l == 5 && world.isBlockIndirectlyProvidingPowerTo(i, j - 1, k, 0))
        {
            return true;
        }
        if(l == 3 && world.isBlockIndirectlyProvidingPowerTo(i, j, k - 1, 2))
        {
            return true;
        }
        if(l == 4 && world.isBlockIndirectlyProvidingPowerTo(i, j, k + 1, 3))
        {
            return true;
        }
        if(l == 1 && world.isBlockIndirectlyProvidingPowerTo(i - 1, j, k, 4))
        {
            return true;
        }
		 if(l == 6 && world.isBlockIndirectlyProvidingPowerTo(i, j + 1, k, 1))
        {
            return true;
        }
        return l == 2 && world.isBlockIndirectlyProvidingPowerTo(i + 1, j, k, 5);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
	
		super.updateTick(world, i, j, k, random);
        if(world.getBlockMetadata(i, j, k) == 0)
        {
            onBlockAdded(world, i, j, k);
        }
        boolean flag = func_22026_h(world, i, j, k);
        for(; torchUpdates.size() > 0 && world.getWorldTime() - ((RedstoneUpdateInfo)torchUpdates.get(0)).updateTime > 100L; torchUpdates.remove(0)) { }
        if(!torchActive)
        {
            if(flag)
            {
                world.setBlockAndMetadataWithNotify(i, j, k, mod_Steamcraft.torchElectricActive.blockID, world.getBlockMetadata(i, j, k));
            }
        } else
			if(!flag)
			{
				world.setBlockAndMetadataWithNotify(i, j, k, mod_Steamcraft.torchElectricIdle.blockID, world.getBlockMetadata(i, j, k));
			}
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
			if(dropTorchIfCantStay(world, i, j, k))
        {
            int i1 = world.getBlockMetadata(i, j, k);
            boolean flag = false;
            if(!canPlaceSide(world, i - 1, j , k) && i1 == 1)
            {
                flag = true;
            }
            if(!canPlaceSide(world, i + 1, j , k) && i1 == 2)
            {
                flag = true;
            }
            if(!canPlaceSide(world, i, j , k - 1) && i1 == 3)
            {
                flag = true;
            }
            if(!canPlaceSide(world, i, j , k + 1) && i1 == 4)
            {
                flag = true;
            }
            if(!canPlaceTop(world, i, j - 1, k) && i1 == 5)
            {
                flag = true;
            }
            if(flag)
            {
                dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
                world.setBlockWithNotify(i, j, k, 0);
            }
        }
		super.onNeighborBlockChange(world, i, j, k, l);
        world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
    }
	
	private boolean dropTorchIfCantStay(World world, int i, int j, int k)
    {
        if(!canPlaceBlockAt(world, i, j, k))
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        } else
        {
            return true;
        }
    }

    public int idDropped(int i, Random random)
    {
        return mod_Steamcraft.electricLamp.shiftedIndex;
    }
	

    public boolean canProvidePower()
    {
        return false;
    }

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

    private boolean torchActive;
	private Class EntityClass;
    private static List torchUpdates = new ArrayList();
	@Override
	public TileEntity createNewTileEntity(World world) {
		try
        {
            return (TileEntity)EntityClass.newInstance();
        }
        catch(Exception exception)
        {
            throw new RuntimeException(exception);
        }
	}

}
