package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;
import steamcraft.TileEntityNukeFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNukeFurnace extends BlockMainFurnace {
	public BlockNukeFurnace(boolean flag) {
		super(flag, "nukefurnaceside", "nukefurnacetop", "nukefurnace");
        setTickRandomly(true);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityNukeFurnace();
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(getIdle());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4) {
		return Item.getItemFromBlock(getIdle());
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (world.isRemote) {
			return true;
		}
		if (world.getTileEntity(i, j, k) instanceof TileEntityNukeFurnace) {
			entityplayer.openGui(Steamcraft.instance, 2, world, i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	@Override
    @SideOnly(Side.CLIENT)
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
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	public void spawnSmoke(World world, int i, int j, int k, Random random) {
		double d = (double) (i + 0.5F) + (double) (random.nextFloat() - 0.5F);
		double d1 = (double) (j + 0.7F) + (double) (random.nextFloat() - 0.3F);
		double d2 = (double) (k + 0.5F) + (double) (random.nextFloat() - 0.5F);
		world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public int tickRate(World world) {
		return 40;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		TileEntityNukeFurnace tileentityfurnace = (TileEntityNukeFurnace) world.getTileEntity(i, j, k);
		Block i1 = world.getBlock(i + 1, j, k);
		Block i2 = world.getBlock(i - 1, j, k);
		Block j1 = world.getBlock(i, j + 1, k);
		Block j2 = world.getBlock(i, j - 1, k);
		Block k1 = world.getBlock(i, j, k + 1);
		Block k2 = world.getBlock(i, j, k - 1);
		if (tileentityfurnace.getHeat() >= 1000) {
			absorbWater(world, i - 1, j, k, random);
			absorbWater(world, i + 1, j, k, random);
			absorbWater(world, i, j + 1, k, random);
			absorbWater(world, i, j - 1, k, random);
			absorbWater(world, i, j, k + 1, random);
			absorbWater(world, i, j, k - 1, random);
		}
		if (i1 == Blocks.lava || i1 == Blocks.flowing_lava || i2 == Blocks.lava  || i2 == Blocks.flowing_lava || j1 == Blocks.lava  || j1 == Blocks.flowing_lava
				|| j2 == Blocks.lava  || j2 == Blocks.flowing_lava || k1 == Blocks.lava  || k1 == Blocks.flowing_lava || k2 == Blocks.lava  || k2 == Blocks.flowing_lava) {
			tileentityfurnace.addHeat(160);
		}
		if (i1 == Blocks.fire || i2 == Blocks.fire || j2 == Blocks.fire || k1 == Blocks.fire || k2 == Blocks.fire) {
			tileentityfurnace.addHeat(80);
		}
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	private void absorbWater(World world, int i, int j, int k, Random random) {
		Block id = world.getBlock(i, j, k);
		boolean flag = false;
		if (id == Blocks.flowing_water) {
			((TileEntityNukeFurnace) world.getTileEntity(i, j, k)).addHeat(-5);
			flag = true;
		} else if (id == Blocks.water) {
			((TileEntityNukeFurnace) world.getTileEntity(i, j, k)).addHeat(-10);
			flag = true;
		}
		if (flag) {
			world.setBlockToAir(i, j, k);
			world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
			for (int d = 0; d < 4; d++)
				spawnSmoke(world, i, j, k, random);
		}
	}

	public static Block getActive() {
		return HandlerRegistry.getBlock("steamcraft:nukeFurnaceOn").get();
	}

	public static Block getIdle() {
		return HandlerRegistry.getBlock("steamcraft:nukeFurnace").get();
	}

	public static void meltdown(World world, int i, int j, int k) {
		//world.playSoundEffect((float)i, (float)j, (float)k, "ambient.weather.thunder", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.9F);
		EntityPlayer player = world.getClosestPlayer(i, j, k, 35);
        if(player!=null){
            player.triggerAchievement(Steamcraft.achs.get("ruinedeverything"));
        }
		world.createExplosion(null, i, j, k, 25F, world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		double d = i + 0.5F + (0.5F) * 2.0000000000000001D;
		double d1 = j + 0.7F + (0.5F) * 2.0000000000000001D;
		double d2 = k + 0.5F + (0.5F) * 2.0000000000000001D;
		world.spawnParticle("reddust", d, d1, d2, -1.0D, 1.0D, 0.0D);
	}
}
