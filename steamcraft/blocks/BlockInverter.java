package steamcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import steamcraft.BlockHandler;
import steamcraft.HandlerRegistry;

import java.util.Random;

public class BlockInverter extends BlockRedstoneAccess {
	private final boolean torchActive;

	public BlockInverter(boolean flag) {
		super(flag);
		torchActive = flag;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return par1 == 0 ? (this.torchActive ? BlockDiode.iconInverterActive : BlockDiode.iconInverterIdle) : (par1 == 1 ? this.blockIcon : Blocks.double_stone_slab.getBlockTextureFromSide(1));
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Item.getItemFromBlock(getActive().get());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4) {
		return Item.getItemFromBlock(getActive().get());
	}

	@Override
	public boolean isAssociatedBlock(Block par1) {
		return par1 == getIdle().get() || par1 == getActive().get();
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (!torchActive) {
			return;
		}
		int l = world.getBlockMetadata(i, j, k);
		double d = i + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d1 = j + 0.7F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d2 = k + 0.5F + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;
		if (l == 1) {
			world.spawnParticle("reddust", d - d4, d1 + d3, d2, -1.0D, 0.7D, 1.0D);
		} else if (l == 2) {
			world.spawnParticle("reddust", d + d4, d1 + d3, d2, -1.0D, 0.7D, 1.0D);
		} else if (l == 3) {
			world.spawnParticle("reddust", d, d1 + d3, d2 - d4, -1.0D, 0.7D, 1.0D);
		} else if (l == 4) {
			world.spawnParticle("reddust", d, d1 + d3, d2 + d4, -1.0D, 0.7D, 1.0D);
		} else {
			world.spawnParticle("reddust", d, d1, d2, -1.0D, 0.7D, 1.0D);
		}
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		boolean flag = this.hasIndirectPower(par1World, par2, par3, par4);
        updateToggleTimes(par1World);
		if (this.torchActive) {
			if (flag) {
				par1World.setBlock(par2, par3, par4, getIdle().get(), par1World.getBlockMetadata(par2, par3, par4), 3);
				if (this.checkBurnout(par1World, par2, par3, par4, true)) {
					par1World.playSoundEffect(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);
					for (int l = 0; l < 5; ++l) {
						double d0 = par2 + par5Random.nextDouble() * 0.6D + 0.2D;
						double d1 = par3 + par5Random.nextDouble() * 0.6D + 0.2D;
						double d2 = par4 + par5Random.nextDouble() * 0.6D + 0.2D;
						par1World.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		} else if (!flag && !this.checkBurnout(par1World, par2, par3, par4, false)) {
			par1World.setBlock(par2, par3, par4, getActive().get(), par1World.getBlockMetadata(par2, par3, par4), 3);
		}
	}

	private static BlockHandler getActive() {
		return HandlerRegistry.getBlock("steamcraft:inverteractive");
	}

	private static BlockHandler getIdle() {
		return HandlerRegistry.getBlock("steamcraft:inverteridle");
	}
}
