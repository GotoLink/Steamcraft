package steamcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSCFence extends BlockFence {
	public BlockSCFence(Material material, Block blockgate, boolean flag) {
		super("steamcraft:castironblock", material);
		blockGate = blockgate;
		doesJoinBlocks = flag;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		if (world.getBlock(i, j - 1, k) == this) {
			return true;
		}
		if (!world.getBlock(i, j - 1, k).getMaterial().isSolid()) {
			return false;
		} else {
			return super.canPlaceBlockAt(world, i, j, k);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getAABBPool().getAABB(i, j, k, i + 1, j + 1.5F, k + 1);
	}

	@Override
	public boolean canConnectFenceTo(IBlockAccess iblockaccess, int i, int j, int k) {
		Block l = iblockaccess.getBlock(i, j, k);
		if (doesJoinBlocks && l != Blocks.air) {
			return l.getMaterial().isOpaque() || l == this || l == blockGate;
		}
		return l == this || l == blockGate;
	}

	public Block blockGate;
	public boolean doesJoinBlocks;
}
