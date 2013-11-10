package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLamp extends Block {
	private boolean isPowered;

	public BlockLamp(int i, boolean flag) {
		super(i, Material.wood);
		isPowered = flag;
		setHardness(2.0F);
		setStepSound(Block.soundGlassFootstep);
		if (flag) {
			setLightValue(1.0F);
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return getIdle();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return getIdle();
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if (!world.isRemote) {
			if (this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k)) {
				world.scheduleBlockUpdate(i, j, k, this.blockID, 4);
			} else if (!this.isPowered && world.isBlockIndirectlyGettingPowered(i, j, k)) {
				world.setBlock(i, j, k, getActive(), 0, 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		onBlockAdded(world, i, j, k);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (!world.isRemote && this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k)) {
			world.setBlock(i, j, k, getIdle(), 0, 2);
		}
	}

	public static int getActive() {
		return HandlerRegistry.getBlock("steamcraft:lampOn").getID();
	}

	public static int getIdle() {
		return HandlerRegistry.getBlock("steamcraft:lamp").getID();
	}
}
