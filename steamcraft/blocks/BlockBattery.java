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

public class BlockBattery extends Block
{
    private Icon blockSide;
	public BlockBattery(int i)
    {
        super(i, Steamcraft.solidcircuit);
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
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
		blockIcon = par1IconRegister.registerIcon(getTextureName()+"top");
        blockSide = par1IconRegister.registerIcon(getTextureName()+"side");
    }
	@Override
	public int tickRate(World world)
    {
        return 1;
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
		world.setBlockMetadataWithNotify(i, j, k, 1, 3);
	    world.notifyBlocksOfNeighborChange(i, j, k, blockID);
	    world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
	    world.markBlockForUpdate(i, j, k);
	    world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}
	@Override
	public void breakBlock(World world, int i, int j, int k, int par5, int par6)
    {
        world.notifyBlocksOfNeighborChange(i, j, k, blockID);
		world.notifyBlocksOfNeighborChange(i+1, j, k, blockID);
		world.notifyBlocksOfNeighborChange(i-1, j, k, blockID);
		world.notifyBlocksOfNeighborChange(i, j, k+1, blockID);
		world.notifyBlocksOfNeighborChange(i, j, k-1, blockID);
		world.notifyBlocksOfNeighborChange(i, j+1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        super.breakBlock(world, i, j, k, par6, par6);
    }
	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int i, int j, int k, int l)
    {
		if(par1IBlockAccess.getBlockMetadata(i, j, k) <= 0 && l == 1)
			return 15;
        return 0;
    }
    @Override
    public boolean canProvidePower()
    {
        return true;
    }
    @Override
    @SideOnly(Side.CLIENT)
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
    @Override
    public int quantityDropped(Random random)
    {
        return 1;
    }
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return Steamcraft.battery.blockID;
    }
    @Override
    @SideOnly(Side.CLIENT)
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