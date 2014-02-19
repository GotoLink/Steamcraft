package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockUraniteOre extends Block {
	public BlockUraniteOre() {
		super(Material.field_151576_e);
        func_149675_a(true);
	}

	@Override
	public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float a, float b, float c) {
		func_319_i(world, i, j, k);
		return super.func_149727_a(world, i, j, k, entityplayer, l, a, b, c);
	}

	@Override
	public void func_149699_a(World world, int i, int j, int k, EntityPlayer entityplayer) {
		func_319_i(world, i, j, k);
		super.func_149699_a(world, i, j, k, entityplayer);
	}

	@Override
	public void func_149724_b(World world, int i, int j, int k, Entity entity) {
		func_319_i(world, i, j, k);
		super.func_149724_b(world, i, j, k, entity);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149734_b(World world, int i, int j, int k, Random random) {
		func_319_i(world, i, j, k);
	}

	@Override
	public int func_149738_a(World world) {
		return 30;
	}

	private void func_319_i(World world, int i, int j, int k) {
		Random random = world.rand;
		double d = 0.0625D;
		for (int l = 0; l < 6; l++) {
			double d1 = i + random.nextFloat();
			double d2 = j + random.nextFloat();
			double d3 = k + random.nextFloat();
			if (l == 0 && !world.func_147439_a(i, j + 1, k).func_149662_c()) {
				d2 = j + 1 + d;
			}
			if (l == 1 && !world.func_147439_a(i, j - 1, k).func_149662_c()) {
				d2 = j + 0 - d;
			}
			if (l == 2 && !world.func_147439_a(i, j, k + 1).func_149662_c()) {
				d3 = k + 1 + d;
			}
			if (l == 3 && !world.func_147439_a(i, j, k - 1).func_149662_c()) {
				d3 = k + 0 - d;
			}
			if (l == 4 && !world.func_147439_a(i + 1, j, k).func_149662_c()) {
				d1 = i + 1 + d;
			}
			if (l == 5 && !world.func_147439_a(i - 1, j, k).func_149662_c()) {
				d1 = i + 0 - d;
			}
			if (d1 < i || d1 > i + 1 || d2 < 0.0D || d2 > j + 1 || d3 < k || d3 > k + 1) {
				world.spawnParticle("reddust", d1, d2, d3, -1.0D, 1.0D, -1.0D);
			}
		}
	}
}