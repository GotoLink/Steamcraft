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
        func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        func_149713_g(0);
	}

	@Override
	public void func_149749_a(World world, int i, int j, int k, Block par5, int par6) {
		world.func_147459_d(i, j, k, this);
		super.func_149749_a(world, i, j, k, par5, par6);
	}

	@Override
	public boolean func_149744_f() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1) {
			return field_149761_L;
		}
		if (l == 0) {
			return field_149761_L;
		} else {
			return blockSide;
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side != ForgeDirection.DOWN && side != ForgeDirection.UP;
	}

	@Override
	public int func_149709_b(IBlockAccess par1IBlockAccess, int i, int j, int k, int l) {
		if (par1IBlockAccess.getBlockMetadata(i, j, k) <= 0 && l == 1)
			return 15;
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149734_b(World world, int i, int j, int k, Random random) {
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
	public void func_149651_a(IIconRegister par1IconRegister) {
        field_149761_L = par1IconRegister.registerIcon(func_149641_N() + "top");
		blockSide = par1IconRegister.registerIcon(func_149641_N() + "side");
	}

    @Override
    public boolean func_149686_d(){
        return false;
    }

    @Override
    public boolean func_149662_c(){
        return false;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_149646_a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1) {
			return true;
		}
		if (!super.func_149646_a(iblockaccess, i, j, k, l)) {
			return false;
		}
		if (l == 0) {
			return true;
		} else {
			return iblockaccess.func_147439_a(i, j, k) != this;
		}
	}

	@Override
	public int func_149738_a(World world) {
		return 1;
	}

	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
		world.setBlockMetadataWithNotify(i, j, k, 1, 3);
		world.func_147459_d(i, j, k, this);
		world.func_147459_d(i, j - 1, k, this);
		world.func_147471_g(i, j, k);
		world.func_147464_a(i, j, k, this, func_149738_a(world));
	}
}