package steamcraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends Block {
	private Icon[] icons;
	private static final String[] DECORBLOCK_NAMES = { "Block of Cast Iron", "Block of Volucite", "Block of Brass", "Block of Uranium", "Engraved Iron Block", "Engraved Gold Block",
			"Engraved Diamond Block", "Engraved Cast Iron Block", "Engraved Volucite Block", "Engraved Brass Block", "Engraved Lapis Lazuli Block", "Carved Stone", "Engraved Uranium Block" };
	public static final String[] names = { "castironblock", "voluciteblock", "brassblock", "uraniumblock", "engriron", "engrgold", "engrdiamond", "engrcastiron", "engrvolucite", "engrbrass",
			"engrlapis", "carvedstone", "engruranium" };
	private static final float[] hardness = new float[] { 7, 50, 5, 10, 5, 3, 5, 7, 50, 5, 3, 2, 10 };

	public BlockDecor(int id) {
		super(id, Material.iron);
		setStepSound(Block.soundMetalFootstep);
		setResistance(10F);
		String[] toolsets = { "pickaxe", "drill" };
		for (int ix = 2; ix < names.length; ix++) {
			for (String tool : toolsets)
				MinecraftForge.setBlockHarvestLevel(this, ix, tool, 2);
		}
		for (String tool : toolsets) {
			MinecraftForge.setBlockHarvestLevel(this, 0, tool, 1);
			MinecraftForge.setBlockHarvestLevel(this, 4, tool, 1);
			MinecraftForge.setBlockHarvestLevel(this, 7, tool, 1);
			MinecraftForge.setBlockHarvestLevel(this, 10, tool, 1);
			MinecraftForge.setBlockHarvestLevel(this, 1, tool, 5);
			MinecraftForge.setBlockHarvestLevel(this, 8, tool, 5);
		}
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return hardness[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		if (world.getBlockMetadata(i, j, k) == 3 || world.getBlockMetadata(i, j, k) == 12) {
			float f = 0.1F;
			return AxisAlignedBB.getBoundingBox(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
		} else
			return super.getCollisionBoundingBoxFromPool(world, i, j, k);
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
	public Icon getIcon(int i, int j) {
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
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < names.length; ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (world.getBlockMetadata(i, j, k) == 3 || world.getBlockMetadata(i, j, k) == 12)
			entity.attackEntityFrom(DamageSource.magic, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.getBlockMetadata(i, j, k) == 3 || world.getBlockMetadata(i, j, k) == 12)
			animate(world, i, j, k);
	}

	@Override
	public void registerIcons(IconRegister reg) {
		icons = new Icon[names.length];
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
			if (l == 0 && !world.isBlockOpaqueCube(i, j + 1, k)) {
				d2 = j + 1 + d;
			}
			if (l == 1 && !world.isBlockOpaqueCube(i, j - 1, k)) {
				d2 = j + 0 - d;
			}
			if (l == 2 && !world.isBlockOpaqueCube(i, j, k + 1)) {
				d3 = k + 1 + d;
			}
			if (l == 3 && !world.isBlockOpaqueCube(i, j, k - 1)) {
				d3 = k + 0 - d;
			}
			if (l == 4 && !world.isBlockOpaqueCube(i + 1, j, k)) {
				d1 = i + 1 + d;
			}
			if (l == 5 && !world.isBlockOpaqueCube(i - 1, j, k)) {
				d1 = i + 0 - d;
			}
			if (d1 < i || d1 > i + 1 || d2 < 0.0D || d2 > j + 1 || d3 < k || d3 > k + 1) {
				world.spawnParticle("reddust", d1, d2, d3, -1.0D, 1.0D, -1.0D);
			}
		}
	}
}
