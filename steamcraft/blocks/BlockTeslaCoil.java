package steamcraft.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class BlockTeslaCoil extends BlockSCTorch
{
	public BlockTeslaCoil(int i, boolean flag)
    {
        super(i);
        torchActive = flag;
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

    private boolean checkForBurnout(World world, int i, int j, int k, boolean flag)
    {
        if(flag)
        {
            torchUpdates.add(new RedstoneUpdateInfo(i, j, k, world.getWorldTime()));
        }
        int l = 0;
        for(int i1 = 0; i1 < torchUpdates.size(); i1++)
        {
            RedstoneUpdateInfo redstoneupdateinfo = (RedstoneUpdateInfo)torchUpdates.get(i1);
            if(redstoneupdateinfo.x == i && redstoneupdateinfo.y == j && redstoneupdateinfo.z == k && ++l >= 8)
            {
                return true;
            }
        }

        return false;
    }
    @Override
    public int tickRate(World world)
    {
        return 1;
    }
    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(world.getBlockMetadata(i, j, k) == 0)
        {
            super.onBlockAdded(world, i, j, k);
        }
        if(torchActive)
        {
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
        }
		world.notifyBlocksOfNeighborChange(i, j, k, blockID);
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
    	int l2 = world.getBlockMetadata(i, j, k);
        int f = 0;
        int f1 = 0;
		int f2 = 0;
        if(l2 == 3)
        {
			 f = 0;
            f1 = 1;
			f2 = 0;
        } else
        if(l2 == 2)
        {
            f = -1;
           f1 = 0;
		   f2 = 0;
        } else
        if(l2 == 1)
        {
            f = 1;
           f1 = 0;
		   f2 = 0;
        } else
        if(l2 == 4)
        {
            f = 0;
            f1 = -1;
			f2 = 0;
        } else
        {
             f = 0;
            f1 = 0;
			f2 = 1;
        }

		int nn = 0;
		while (nn <= 8) {
		nn=nn+1;
		int t1 = (i + f * nn);
		int t2 = (k + f1 * nn);
		int t3 = (j + f2 * nn);

           if((world.getBlockId(t1, t3, t2) == mod_Steamcraft.teslaReceiver.blockID || world.getBlockId(t1, t3, t2) == mod_Steamcraft.teslaReceiverActive.blockID) && nn >= 1) {
            world.setBlockMetadataWithNotify(t1, t3, t2, 1, 2);

            world.notifyBlocksOfNeighborChange(t1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1 - 1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1 + 1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3, t2 - 1, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3, t2 + 1, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3 - 1, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3 + 1, t2, blockID);
			}
			if((world.getBlockId(t1, t3, t2) == mod_Steamcraft.wirelessLampIdle.blockID || world.getBlockId(t1, t3, t2) == mod_Steamcraft.wirelessLampActive.blockID) && nn >= 1) {
			world.notifyBlocksOfNeighborChange(t1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1 - 1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1 + 1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3, t2 - 1, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3, t2 + 1, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3 - 1, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3 + 1, t2, blockID);
			}
			}
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
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        byte nnum = 1;

        if(torchActive)
		{
    	  nnum = 2;
		}
		else	
		{
			nnum = 1;
		}
		
        int l2 = world.getBlockMetadata(i, j, k);
        int f = 0;
        int f1 = 0;
		int f2 = 0;
        if(l2 == 3)
        {
			 f = 0;
            f1 = 1;
			f2 = 0;
        } else
        if(l2 == 2)
        {
            f = -1;
           f1 = 0;
		   f2 = 0;
        } else
        if(l2 == 1)
        {
            f = 1;
           f1 = 0;
		   f2 = 0;
        } else
        if(l2 == 4)
        {
            f = 0;
            f1 = -1;
			f2 = 0;
        } else
        {
             f = 0;
            f1 = 0;
			f2 = 1;
        }

		int nn = 0;
		while (nn <= 8) {
		nn=nn+1;
		int t1 = (i + f * nn);
		int t2 = (k + f1 * nn);
		int t3 = (j + f2 * nn);

       if((world.getBlockId(t1, t3, t2) == mod_Steamcraft.teslaReceiver.blockID || world.getBlockId(t1, t3, t2) == mod_Steamcraft.teslaReceiverActive.blockID) && nn >= 1) {
    	   world.setBlockMetadataWithNotify(t1, t3, t2, nnum, 2);
	        world.notifyBlocksOfNeighborChange(t1, t3, t2, blockID);
	        world.notifyBlocksOfNeighborChange(t1 - 1, t3, t2, blockID);
	        world.notifyBlocksOfNeighborChange(t1 + 1, t3, t2, blockID);
	        world.notifyBlocksOfNeighborChange(t1, t3, t2 - 1, blockID);
	        world.notifyBlocksOfNeighborChange(t1, t3, t2 + 1, blockID);
	        world.notifyBlocksOfNeighborChange(t1, t3 - 1, t2, blockID);
	        world.notifyBlocksOfNeighborChange(t1, t3 + 1, t2, blockID);
       }
	   	if((world.getBlockId(t1, t3, t2) == mod_Steamcraft.wirelessLampIdle.blockID || world.getBlockId(t1, t3, t2) == mod_Steamcraft.wirelessLampActive.blockID) && nn >= 1) {
			world.notifyBlocksOfNeighborChange(t1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1 - 1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1 + 1, t3, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3, t2 - 1, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3, t2 + 1, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3 - 1, t2, blockID);
            world.notifyBlocksOfNeighborChange(t1, t3 + 1, t2, blockID);
			}
		}
	
	
        boolean flag = func_22026_h(world, i, j, k);
        for(; torchUpdates.size() > 0 && world.getWorldTime() - ((RedstoneUpdateInfo)torchUpdates.get(0)).updateTime > 100L; torchUpdates.remove(0)) { }
        if(!torchActive)
        {
            if(flag)
            {
                world.setBlock(i, j, k, mod_Steamcraft.torchTeslaActive.blockID, world.getBlockMetadata(i, j, k), 2);
                if(checkForBurnout(world, i, j, k, true))
                {
                    world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                    for(int l = 0; l < 5; l++)
                    {
                        double d = (double)i + random.nextDouble() * 0.59999999999999998D + 0.20000000000000001D;
                        double d1 = (double)j + random.nextDouble() * 0.59999999999999998D + 0.20000000000000001D;
                        double d2 = (double)k + random.nextDouble() * 0.59999999999999998D + 0.20000000000000001D;
                        world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
                    }

                }
            }
        } else
        if(!flag && !checkForBurnout(world, i, j, k, false))
        {
            world.setBlock(i, j, k, mod_Steamcraft.torchTeslaIdle.blockID, world.getBlockMetadata(i, j, k), 2);
        }
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
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
        return mod_Steamcraft.torchTeslaIdle.blockID;
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
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
    }

    private boolean torchActive;
    private static List torchUpdates = new ArrayList();

}
