package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class BlockSCFarmland extends BlockFarmland
{
    public BlockSCFarmland(int i)
    {
        super(i);
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(random.nextInt(5) == 0)
        {
            if(isWaterNearby(world, i, j, k) || world.canLightningStrikeAt(i, j + 1, k))
            {
                world.setBlockMetadataWithNotify(i, j, k, 7, 2);
            } else
            {
                int l = world.getBlockMetadata(i, j, k);
                if(l > 0)
                {
                    world.setBlockMetadataWithNotify(i, j, k, l - 1, 2);
                } else
                if(!isCropsNearby(world, i, j, k))
                {
                    world.setBlock(i, j, k, Block.dirt.blockID);
                }
            }
        }
    }

    private boolean isCropsNearby(World world, int i, int j, int k)
    {
        int l = 0;
        for(int i1 = i - l; i1 <= i + l; i1++)
        {
            for(int j1 = k - l; j1 <= k + l; j1++)
            {
                int k1 = world.getBlockId(i1, j + 1, j1);
                if(k1 == Block.crops.blockID || k1 == Block.melonStem.blockID || k1 == Block.pumpkinStem.blockID || k1 == mod_Steamcraft.teaPlant.blockID)
                {
                    return true;
                }
            }

        }

        return false;
    }
}
