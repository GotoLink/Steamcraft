package steamcraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.terraingen.TerrainGen;
import steamcraft.WorldGenNetherTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNetherSapling extends BlockSapling {
	public BlockNetherSapling(int id) {
		super(id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return this.blockIcon;
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Nether;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public void growTree(World world, int par2, int par3, int par4, Random par5Random) {
		if (!TerrainGen.saplingGrowTree(world, par5Random, par2, par3, par4))
			return;
		int l = world.getBlockMetadata(par2, par3, par4);
		WorldGenerator tree = new WorldGenNetherTrees();
		/*
		 * if (par5Random.nextInt(10) == 0) { object = new
		 * WorldGenBigTree(true); }
		 */
		world.setBlock(par2, par3, par4, 0, 0, 4);
		if (!tree.generate(world, par5Random, par2, par3, par4)) {
			world.setBlock(par2, par3, par4, this.blockID, l, 4);
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(this.getTextureName());
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		return par1 == Block.netherrack.blockID || par1 == Block.slowSand.blockID || super.canThisPlantGrowOnThisBlockID(par1);
	}
}
