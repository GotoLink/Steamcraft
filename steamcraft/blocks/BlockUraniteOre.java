package steamcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Random;

public class BlockUraniteOre extends Block {
	public BlockUraniteOre() {
		super(Material.rock);
        setTickRandomly(true);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float a, float b, float c) {
		func_319_i(world, i, j, k);
		return super.onBlockActivated(world, i, j, k, entityplayer, l, a, b, c);
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		func_319_i(world, i, j, k);
		super.onBlockClicked(world, i, j, k, entityplayer);
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
		func_319_i(world, i, j, k);
		super.onEntityWalking(world, i, j, k, entity);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		func_319_i(world, i, j, k);
	}

	@Override
	public int tickRate(World world) {
		return 30;
	}

	private void func_319_i(World world, int i, int j, int k) {
		Random random = world.rand;
		double d = 0.0625D;
		for (int l = 0; l < 6; l++) {
			double d1 = i + random.nextFloat();
			double d2 = j + random.nextFloat();
			double d3 = k + random.nextFloat();
			if (l == 0 && !world.getBlock(i, j + 1, k).isOpaqueCube()) {
				d2 = j + 1 + d;
			}
			if (l == 1 && !world.getBlock(i, j - 1, k).isOpaqueCube()) {
				d2 = j + 0 - d;
			}
			if (l == 2 && !world.getBlock(i, j, k + 1).isOpaqueCube()) {
				d3 = k + 1 + d;
			}
			if (l == 3 && !world.getBlock(i, j, k - 1).isOpaqueCube()) {
				d3 = k + 0 - d;
			}
			if (l == 4 && !world.getBlock(i + 1, j, k).isOpaqueCube()) {
				d1 = i + 1 + d;
			}
			if (l == 5 && !world.getBlock(i - 1, j, k).isOpaqueCube()) {
				d1 = i + 0 - d;
			}
			if (d1 < i || d1 > i + 1 || d2 < 0.0D || d2 > j + 1 || d3 < k || d3 > k + 1) {
				world.spawnParticle("reddust", d1, d2, d3, -1.0D, 1.0D, -1.0D);
			}
		}
	}
}