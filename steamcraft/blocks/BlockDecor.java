package steamcraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends Block {
	private IIcon[] icons;
	private static final String[] DECORBLOCK_NAMES = { "Block of Cast Iron", "Block of Volucite", "Block of Brass", "Block of Uranium", "Engraved Iron Block", "Engraved Gold Block",
			"Engraved Diamond Block", "Engraved Cast Iron Block", "Engraved Volucite Block", "Engraved Brass Block", "Engraved Lapis Lazuli Block", "Carved Stone", "Engraved Uranium Block" };
	public static final String[] names = { "castironblock", "voluciteblock", "brassblock", "uraniumblock", "engriron", "engrgold", "engrdiamond", "engrcastiron", "engrvolucite", "engrbrass",
			"engrlapis", "carvedstone", "engruranium" };
	private static final float[] hardness = new float[] { 7, 50, 5, 10, 5, 3, 5, 7, 50, 5, 3, 2, 10 };

	public BlockDecor() {
		super(Material.field_151573_f);
		func_149672_a(Block.field_149777_j);
        func_149752_b(10F);
		String[] toolsets = { "pickaxe", "drill" };
		for (int ix = 2; ix < names.length; ix++) {
			for (String tool : toolsets)
				setHarvestLevel(tool, 2, ix);
		}
		for (String tool : toolsets) {
			setHarvestLevel(tool, 1, 0);
			setHarvestLevel(tool, 1, 4);
			setHarvestLevel(tool, 1, 7);
			setHarvestLevel(tool, 1, 10);
			setHarvestLevel(tool, 5, 1);
			setHarvestLevel(tool, 5, 8);
		}
	}

	@Override
	public int func_149692_a(int metadata) {
		return metadata;
	}

	@Override
	public float func_149712_f(World world, int x, int y, int z) {
		return hardness[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
		if (world.getBlockMetadata(i, j, k) == 3 || world.getBlockMetadata(i, j, k) == 12) {
			float f = 0.1F;
			return AxisAlignedBB.getAABBPool().getAABB(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
		} else
			return super.func_149668_a(world, i, j, k);
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		switch (world.getBlockMetadata(x, y, z)) {
		case 0:
		case 7:
			return 4;
		case 1:
		case 8:
			return 10000;
		case 3:
		case 12:
			return 6 / 5;
		case 10:
			return 1;
		default:
			return 2;
		}
	}

	@Override
	public IIcon func_149691_a(int i, int j) {
		return icons[j];
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if (world.getBlockMetadata(x, y, z) == 3 || world.getBlockMetadata(x, y, z) == 12)
			return (int) (15.0F * 0.625F);
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149666_a(Item par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < names.length; ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}

	@Override
	public void func_149724_b(World world, int i, int j, int k, Entity entity) {
		if (world.getBlockMetadata(i, j, k) == 3 || world.getBlockMetadata(i, j, k) == 12)
			entity.attackEntityFrom(DamageSource.magic, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149734_b(World world, int i, int j, int k, Random random) {
		if (world.getBlockMetadata(i, j, k) == 3 || world.getBlockMetadata(i, j, k) == 12)
			animate(world, i, j, k);
	}

	@Override
	public void func_149651_a(IIconRegister reg) {
		icons = new IIcon[names.length];
		for (int i = 0; i < names.length; i++) {
			icons[i] = reg.registerIcon("steamcraft:" + names[i]);
		}
	}

	private void animate(World world, int i, int j, int k) {
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
