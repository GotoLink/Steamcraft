package steamcraft.blocks;

import java.util.Random;

import steamcraft.mod_Steamcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockLamp extends Block
{
    public BlockLamp(int i, Material material)
    {
        super(i, material);
		isPowered = false;
    }
    @Override
	public int tickRate(World world)
    {
        return 1;
    }
    @Override
	public boolean isOpaqueCube()
    {
        return true;
    }
    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
        world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
        world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
		isPowered = func_30002_h(world, i, j, k);
		if(isPowered){
			world.setBlockAndMetadataWithNotify(i, j, k, mod_Steamcraft.lampoff.blockID, world.getBlockMetadata(i, j, k));
		}else{
			world.setBlockAndMetadataWithNotify(i, j, k, mod_Steamcraft.lamp.blockID, world.getBlockMetadata(i, j, k));
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
        return mod_Steamcraft.lamp.blockID;
    }
	
	private boolean func_30002_h(World world, int i, int j, int k)
    {
      if(world.isBlockProvidingPowerTo(i, j - 1, k, 0))
        {
            return true;
        }
        if(world.isBlockProvidingPowerTo(i, j + 1, k, 1))
        {
            return true;
        }
        if(world.isBlockProvidingPowerTo(i, j, k - 1, 2))
        {
            return true;
        }
        if(world.isBlockProvidingPowerTo(i, j, k + 1, 3))
        {
            return true;
        }
        if(world.isBlockProvidingPowerTo(i - 1, j, k, 4))
        {
            return true;
        }
        return world.isBlockProvidingPowerTo(i + 1, j, k, 5);
    }
	@Override
	public boolean canProvidePower()
    {
        return false;
    }
	
	private boolean isPowered;
}
