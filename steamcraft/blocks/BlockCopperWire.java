package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCopperWire extends BlockRedstoneWire {
	public static int modelID;

	public BlockCopperWire() {
		super();
        func_149649_H();
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return side != -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int func_149720_d(IBlockAccess iblockaccess, int i, int j, int k) {
		return 0x800000;
	}

	@Override
	public int func_149645_b() {
		return modelID;
	}

	@Override
	public Item func_149650_a(int par1, Random par2Random, int par3) {
		return Item.func_150898_a(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item func_149694_d(World par1World, int par2, int par3, int par4) {
		return Item.func_150898_a(this);
	}

	@Override
	public void func_149734_b(World world, int i, int j, int k, Random random) {
		int l = world.getBlockMetadata(i, j, k);
		if (l > 0) {
			double d = i + 0.5D + (random.nextFloat() - 0.5D) * 0.20000000000000001D;
			double d1 = j + 0.0625F;
			double d2 = k + 0.5D + (random.nextFloat() - 0.5D) * 0.20000000000000001D;
			float f = 1 / 15F;
			float f1 = -1;
			float f2 = f * f * 0.7F - 0.5F;
			float f3 = f * f * 0.6F - 0.7F;
			if (f2 < 0.0F) {
				f2 = 0.7F;
			}
			if (f3 < 0.0F) {
				f3 = 1.0F;
			}
			world.spawnParticle("reddust", d, d1, d2, f1, f2, f3);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
		super.func_149651_a(par1IconRegister);
		this.field_149761_L = par1IconRegister.registerIcon("steamcraft:copperwire");
	}
}
