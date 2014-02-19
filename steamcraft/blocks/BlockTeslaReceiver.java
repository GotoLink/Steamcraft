package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeslaReceiver extends Block {
	private IIcon sideBlock;

	public BlockTeslaReceiver() {
		super(Steamcraft.staticcircuit);
        func_149675_a(true);
        func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        func_149713_g(0);
	}

	@Override
	public boolean func_149696_a(World world, int i, int j, int k, int par5, int par6) {
		world.func_147459_d(i, j, k, this);
		return super.func_149696_a(world, i, j, k, par5, par6);
	}

	@Override
	public boolean func_149744_f() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1 || (l == 0)) {
			return field_149761_L;
		}
		return sideBlock;
	}

	@Override
	public Item func_149650_a(int i, Random random, int j) {
		return Item.func_150898_a(getIdle());
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side != ForgeDirection.DOWN && side != ForgeDirection.UP;
	}

	@Override
	public int func_149709_b(IBlockAccess par1IBlockAccess, int i, int j, int k, int l) {
		if (par1IBlockAccess.getBlockMetadata(i, j, k) == 2 && l == 0)
			return 15;
		return 0;
	}

	@Override
	public void func_149726_b(World world, int i, int j, int k) {
		for (int a = -9; a <= 9; a++) {
			for (int b = -9; b <= 9; b++) {
				for (int c = -9; c <= 9; c++) {
					if (world.func_147439_a(i + a, j + b, k + c) == BlockTeslaCoil.getTeslaIdle() || world.func_147439_a(i + a, j + b, k + c) == BlockTeslaCoil.getTeslaActive()) {
						world.func_147459_d(i + a, j + b, k + c, this);
						world.func_147464_a(i + a, j + b, k + c, this, func_149738_a(world));
					}
				}
			}
		}
		world.func_147459_d(i, j, k, this);
		world.func_147464_a(i, j, k, this, func_149738_a(world));
	}

	@Override
	public int func_149745_a(Random random) {
		return 1;
	}

	@Override
	public void func_149734_b(World world, int i, int j, int k, Random random) {
		int l = world.getBlockMetadata(i, j, k);
		if (l == 1) {
			return;
		}
		double d = i + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d1 = j + 0.2F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d2 = k + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d4 = 0.5D;
		world.spawnParticle("reddust", d - d4, d1, d2, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d + d4, d1, d2, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d, d1, d2 - d4, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d, d1, d2 + d4, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d, d1 + 1.1D, d2, -1.0D, 0.7D, 1.0D);
		world.spawnParticle("reddust", d, d1 - 0.2D, d2, -1.0D, 0.7D, 1.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
		super.func_149651_a(par1IconRegister);
		sideBlock = par1IconRegister.registerIcon("steamcraft:receiverside");
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
	public boolean func_149646_a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if (l == 1) {
			return true;
		}
		return super.func_149646_a(iblockaccess, i, j, k, l);
	}

	@Override
	public int func_149738_a(World world) {
		return 2;
	}

	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
		world.func_147459_d(i, j, k, this);
		world.func_147471_g(i, j, k);
		if (world.getBlockMetadata(i, j, k) == 2) {
			world.func_147465_d(i, j, k, getActive(), 2, 2);
		} else {
			world.func_147465_d(i, j, k, getIdle(), 1, 2);
		}
		world.func_147464_a(i, j, k, this, func_149738_a(world));
	}

	public static Block getActive() {
		return HandlerRegistry.getBlock("steamcraft:receiverOn").get();
	}

	public static Block getIdle() {
		return HandlerRegistry.getBlock("steamcraft:receiver").get();
	}
}