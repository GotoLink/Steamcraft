package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockRedstoneLogic;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiode extends BlockRedstoneRepeater {
	public static Icon iconInverterIdle;
	public static Icon iconInverterActive;
	private static Icon iconDiodeIdle;
	private static Icon iconDiodeActive;

	public BlockDiode(int i, boolean flag) {
		super(i, flag);
		disableStats();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
		if (i == 0) {
			return !isRepeaterPowered ? iconInverterIdle : iconInverterActive;
		}
		if (i == 1) {
			return !isRepeaterPowered ? iconDiodeIdle : iconDiodeActive;
		}
		return super.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		iconInverterIdle = par1IconRegister.registerIcon("steamcraft:inverteridle");
		iconInverterActive = par1IconRegister.registerIcon("steamcraft:inverteractive");
		iconDiodeIdle = par1IconRegister.registerIcon("steamcraft:diodeidle");
		iconDiodeActive = par1IconRegister.registerIcon("steamcraft:diodeactive");
	}

	@Override
	protected BlockRedstoneLogic func_94485_e() {
		return (BlockRedstoneLogic) Steamcraft.redstoneRepeaterActive;
	}

	@Override
	protected BlockRedstoneLogic func_94484_i() {
		return (BlockRedstoneLogic) Steamcraft.redstoneRepeaterIdle;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Steamcraft.redstoneRepeaterIdle.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return Steamcraft.redstoneRepeaterIdle.blockID;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (!isRepeaterPowered) {
			return;
		}
		int l = world.getBlockMetadata(i, j, k);
		int i1 = getDirection(l);
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
				d4 = repeaterTorchOffset[j1];
				break;
			case 1:
				d3 = -repeaterTorchOffset[j1];
				break;
			case 2:
				d4 = -repeaterTorchOffset[j1];
				break;
			case 3:
				d3 = repeaterTorchOffset[j1];
			}
		}
		world.spawnParticle("reddust", d + d3, d1, d2 + d4, -1.0D, 0.7D, 1.0D);
	}
}
