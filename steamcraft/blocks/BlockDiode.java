package steamcraft.blocks;

import java.util.Random;

import steamcraft.mod_Steamcraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDiode extends BlockRedstoneRepeater
{
    public BlockDiode(int i, boolean flag)
    {
        super(i, flag);
    }
    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(!world.isBlockNormalCube(i, j - 1, k))
        {
            return false;
        } else
        {
            return super.canPlaceBlockAt(world, i, j, k);
        }
    }
    @Override
    public boolean canBlockStay(World world, int i, int j, int k)
    {
        if(!world.isBlockNormalCube(i, j - 1, k))
        {
            return false;
        } else
        {
            return super.canBlockStay(world, i, j, k);
        }
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        int l = world.getBlockMetadata(i, j, k);
        boolean flag = ignoreTick(world, i, j, k, l);
        if(isRepeaterPowered && !flag)
        {
            world.setBlockAndMetadataWithNotify(i, j, k, Block.redstoneRepeaterIdle.blockID, l);
        } else
        if(!isRepeaterPowered)
        {
            world.setBlockAndMetadataWithNotify(i, j, k, Block.redstoneRepeaterActive.blockID, l);
            if(!flag)
            {
                int i1 = (l & 0xc) >> 2;
                world.scheduleBlockUpdate(i, j, k, Block.redstoneRepeaterActive.blockID, repeaterState[i1] * 2);
            }
        }
    }
    @Override
    public Icon getIcon(int i, int j)
    {
    	if(i == 0)
        {
            return !isRepeaterPowered ? mod_Steamcraft.InverterIdle : mod_Steamcraft.InverterActive;
        }
        if(i == 1)
        {
            return !isRepeaterPowered ? mod_Steamcraft.DiodeIdle : mod_Steamcraft.DiodeActive;
        } else
        {
            return 5;
        }
    }
    @Override
    public int getRenderType()
    {
        return 15;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
        return isPoweringTo(world, i, j, k, l);
    }

    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(!isRepeaterPowered)
        {
            return false;
        }
        int i1 = iblockaccess.getBlockMetadata(i, j, k) & 3;
        if(i1 == 0 && l == 3)
        {
            return true;
        }
        if(i1 == 1 && l == 4)
        {
            return true;
        }
        if(i1 == 2 && l == 2)
        {
            return true;
        }
        return i1 == 3 && l == 5;
    }
    @Override
    public boolean canProvidePower()
    {
        return false;
    }
    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
        world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
    }
}
