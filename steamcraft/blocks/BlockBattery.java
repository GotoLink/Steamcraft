package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBattery extends Block {
	private IIcon blockSide;

	public BlockBattery() {
		super(Steamcraft.solidcircuit);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        setLightOpacity(0);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
		world.notifyBlocksOfNeighborChange(i, j, k, this);
		super.breakBlock(world, i, j, k, par5, par6);
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1) {
			return blockIcon;
		}
		if (l == 0) {
			return blockIcon;
		} else {
			return blockSide;
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side != ForgeDirection.DOWN && side != ForgeDirection.UP;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int i, int j, int k, int l) {
		if (par1IBlockAccess.getBlockMetadata(i, j, k) <= 0 && l == 1)
			return 15;
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		double d = i + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d1 = j + 0.2F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d2 = k + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d4 = 0.5D;
		world.spawnParticle("reddust", d - d4, d1, d2, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d + d4, d1, d2, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d, d1, d2 - d4, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d, d1, d2 + d4, -1.0D, 0.7D, 1.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(getTextureName() + "top");
		blockSide = par1IconRegister.registerIcon(getTextureName() + "side");
	}

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1) {
			return true;
		}
		if (!super.shouldSideBeRendered(iblockaccess, i, j, k, l)) {
			return false;
		}
		return l == 0 || iblockaccess.getBlock(i, j, k) != this;
	}

	@Override
	public int tickRate(World world) {
		return 1;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		world.setBlockMetadataWithNotify(i, j, k, 1, 3);
		world.notifyBlocksOfNeighborChange(i, j, k, this);
		world.notifyBlocksOfNeighborChange(i, j - 1, k, this);
		world.markBlockForUpdate(i, j, k);
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}
}