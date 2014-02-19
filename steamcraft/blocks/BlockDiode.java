package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import steamcraft.BlockHandler;
import steamcraft.HandlerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiode extends BlockRedstoneRepeater {
	public static IIcon iconInverterIdle;
	public static IIcon iconInverterActive;
	private static IIcon iconDiodeIdle;
	private static IIcon iconDiodeActive;

	public BlockDiode(boolean flag) {
		super(flag);
        func_149649_H();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149691_a(int i, int j) {
		if (i == 0) {
			return !field_149914_a ? iconInverterIdle : iconInverterActive;
		}
		if (i == 1) {
			return !field_149914_a ? iconDiodeIdle : iconDiodeActive;
		}
		return super.func_149691_a(i, j);
	}

	@Override
	public Item func_149650_a(int par1, Random par2Random, int par3) {
		return Item.func_150898_a(getIdle().get());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item func_149694_d(World par1World, int par2, int par3, int par4) {
		return Item.func_150898_a(getIdle().get());
	}

	@Override
	public void func_149734_b(World world, int i, int j, int k, Random random) {
		if (!field_149914_a) {
			return;
		}
		int l = world.getBlockMetadata(i, j, k);
		int i1 = func_149895_l(l);
		double d = i + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d1 = j + 0.4F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d2 = k + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		if (random.nextInt(2) == 0) {
			switch (i1) {
			case 0:
				d4 = -0.3125D;
				break;
			case 1:
				d3 = 0.3125D;
				break;
			case 2:
				d4 = 0.3125D;
				break;
			case 3:
				d3 = -0.3125D;
			}
		} else {
			int j1 = (l & 12) >> 2;
			switch (i1) {
			case 0:
				d4 = field_149973_b[j1];
				break;
			case 1:
				d3 = -field_149973_b[j1];
				break;
			case 2:
				d4 = -field_149973_b[j1];
				break;
			case 3:
				d3 = field_149973_b[j1];
			}
		}
		world.spawnParticle("reddust", d + d3, d1, d2 + d4, -1.0D, 0.7D, 1.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
		super.func_149651_a(par1IconRegister);
		iconInverterIdle = par1IconRegister.registerIcon("steamcraft:inverteridle");
		iconInverterActive = par1IconRegister.registerIcon("steamcraft:inverteractive");
		iconDiodeIdle = par1IconRegister.registerIcon("steamcraft:diodeidle");
		iconDiodeActive = par1IconRegister.registerIcon("steamcraft:diodeactive");
	}

	@Override
	protected BlockRedstoneDiode func_149898_i() {
		return (BlockRedstoneDiode) getIdle().get();
	}

	@Override
	protected BlockRedstoneDiode func_149906_e() {
		return (BlockRedstoneDiode) getActive().get();
	}

	private static BlockHandler getActive() {
		return HandlerRegistry.getBlock("steamcraft:diodeactive");
	}

	private static BlockHandler getIdle() {
		return HandlerRegistry.getBlock("steamcraft:diodeidle");
	}
}
