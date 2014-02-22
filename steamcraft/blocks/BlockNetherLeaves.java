package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import steamcraft.HandlerRegistry;

public class BlockNetherLeaves extends Block {
	public BlockNetherLeaves(Material mat) {
		super(mat);
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (!par1World.isRemote) {
			int j1 = 20;
			if (par7 > 0) {
				j1 -= 2 << par7;
				if (j1 < 10) {
					j1 = 10;
				}
			}
			if (par1World.rand.nextInt(j1) == 0) {
				Item k1 = this.getItemDropped(par5, par1World.rand, par7);
				this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(k1, 1, this.damageDropped(par5)));
			}
			j1 = 200;
			if (par7 > 0) {
				j1 -= 10 << par7;
				if (j1 < 40) {
					j1 = 40;
				}
			}
			if (par1World.rand.nextInt(j1) == 0) {
				this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(HandlerRegistry.getBlock("steamcraft.nethersapling").get(), 1, 0));
			}
		}
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Items.blaze_powder;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return false;
	}

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return par1Random.nextInt(20) == 0 ? 1 : 0;
	}
}
