package steamcraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.terraingen.TerrainGen;
import steamcraft.WorldGenNetherTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNetherSapling extends BlockSapling {
	public BlockNetherSapling() {
		super();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149691_a(int par1, int par2) {
		return this.field_149761_L;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Nether;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149666_a(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public void func_149878_d(World world, int par2, int par3, int par4, Random par5Random) {
		if (!TerrainGen.saplingGrowTree(world, par5Random, par2, par3, par4))
			return;
		int l = world.getBlockMetadata(par2, par3, par4);
		WorldGenerator tree = new WorldGenNetherTrees();
		/*
		 * if (par5Random.nextInt(10) == 0) { object = new
		 * WorldGenBigTree(true); }
		 */
		world.func_147465_d(par2, par3, par4, Blocks.air, 0, 4);
		if (!tree.generate(world, par5Random, par2, par3, par4)) {
			world.func_147465_d(par2, par3, par4, this, l, 4);
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
		this.field_149761_L = par1IconRegister.registerIcon(this.func_149641_N());
	}

	@Override
	protected boolean func_149854_a(Block par1) {
		return par1 == Blocks.netherrack || par1 == Blocks.soul_sand || super.func_149854_a(par1);
	}
}
