package steamcraft.blocks;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.Steamcraft;
import steamcraft.TileEntityNukeFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNukeFurnace extends BlockMainFurnace {
	public BlockNukeFurnace(int i, boolean flag) {
		super(i, flag, "nukefurnaceside", "nukefurnacetop", "nukefurnace");
		setTickRandomly(true);
	}

	@Override
	public int tickRate(World world) {
		return 40;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Steamcraft.nukeOvenIdle.blockID;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (!isActive) {
			return;
		}
		int l = world.getBlockMetadata(i, j, k);
		float f = i + 0.5F;
		float f1 = j + 0.0F + (random.nextFloat() * 6F) / 16F;
		float f2 = k + 0.5F;
		float f3 = 0.52F;
		float f4 = random.nextFloat() * 0.6F - 0.3F;
		if (l == 4) {
			world.spawnParticle("reddust", f - f3, f1, f2 + f4, -1.0D, 1.0D, 0.0D);
		} else if (l == 5) {
			world.spawnParticle("reddust", f + f3, f1, f2 + f4, -1.0D, 1.0D, 0.0D);
		} else if (l == 2) {
			world.spawnParticle("reddust", f + f4, f1, f2 - f3, -1.0D, 1.0D, 0.0D);
		} else if (l == 3) {
			world.spawnParticle("reddust", f + f4, f1, f2 + f3, -1.0D, 1.0D, 0.0D);
		}
		world.spawnParticle("reddust", f, f1 + 0.6F, f2, 0.0D, 0.0D, 1.0D);
		world.spawnParticle("reddust", f, f1 + 0.6F, f2, 0.0D, 0.0D, 1.0D);
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (world.isRemote) {
			return true;
		}
		if (world.getBlockTileEntity(i, j, k) instanceof TileEntityNukeFurnace) {
			entityplayer.openGui(Steamcraft.instance, 2, world, i, j, k);
		}
		return true;
	}

	public static void meltdown(World world, int i, int j, int k) {
		//world.playSoundEffect((float)i, (float)j, (float)k, "ambient.weather.thunder", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.9F);
		world.getClosestPlayer(i, j, k, 35).triggerAchievement(Steamcraft.achs[5]);
		world.createExplosion(null, i, j, k, 25F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		double d = i + 0.5F + (0.5F) * 2.0000000000000001D;
		double d1 = j + 0.7F + (0.5F) * 2.0000000000000001D;
		double d2 = k + 0.5F + (0.5F) * 2.0000000000000001D;
		world.spawnParticle("reddust", d, d1, d2, -1.0D, 1.0D, 0.0D);
	}

	public void spawnSmoke(World world, int i, int j, int k, Random random) {
		double d = (double) (i + 0.5F) + (double) (random.nextFloat() - 0.5F);
		double d1 = (double) (j + 0.7F) + (double) (random.nextFloat() - 0.3F);
		double d2 = (double) (k + 0.5F) + (double) (random.nextFloat() - 0.5F);
		world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		TileEntityNukeFurnace tileentityfurnace = (TileEntityNukeFurnace) world.getBlockTileEntity(i, j, k);
		int i1 = world.getBlockId(i + 1, j, k);
		int i2 = world.getBlockId(i - 1, j, k);
		int j1 = world.getBlockId(i, j + 1, k);
		int j2 = world.getBlockId(i, j - 1, k);
		int k1 = world.getBlockId(i, j, k + 1);
		int k2 = world.getBlockId(i, j, k - 1);
		if (tileentityfurnace.getHeat() >= 1000) {
			absorbWater(world, i - 1, j, k, random);
			absorbWater(world, i + 1, j, k, random);
			absorbWater(world, i, j + 1, k, random);
			absorbWater(world, i, j - 1, k, random);
			absorbWater(world, i, j, k + 1, random);
			absorbWater(world, i, j, k - 1, random);
		}
		if (i1 == lavaStill.blockID || i1 == lavaMoving.blockID || i2 == lavaStill.blockID || i2 == lavaMoving.blockID || j1 == lavaStill.blockID || j1 == lavaMoving.blockID
				|| j2 == lavaStill.blockID || j2 == lavaMoving.blockID || k1 == lavaStill.blockID || k1 == lavaMoving.blockID || k2 == lavaStill.blockID || k2 == lavaMoving.blockID) {
			tileentityfurnace.addHeat(160);
		}
		if (i1 == fire.blockID || i2 == fire.blockID || j2 == fire.blockID || k1 == fire.blockID || k2 == fire.blockID) {
			tileentityfurnace.addHeat(80);
		}
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	private void absorbWater(World world, int i, int j, int k, Random random) {
		int id = world.getBlockId(i, j, k);
		boolean flag = false;
		if (id == waterMoving.blockID) {
			((TileEntityNukeFurnace) world.getBlockTileEntity(i, j, k)).addHeat(-5);
			flag = true;
		} else if (id == waterStill.blockID) {
			((TileEntityNukeFurnace) world.getBlockTileEntity(i, j, k)).addHeat(-10);
			flag = true;
		}
		if (flag) {
			world.setBlockToAir(i, j, k);
			world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
			for (int d = 0; d < 4; d++)
				spawnSmoke(world, i, j, k, random);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNukeFurnace();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return Steamcraft.nukeOvenIdle.blockID;
	}
}
