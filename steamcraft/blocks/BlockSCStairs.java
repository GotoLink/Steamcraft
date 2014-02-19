package steamcraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSCStairs extends BlockStairs {
	private Block modelBlock;
	private Item blockDrop;
	private int dropQuantity;

	public BlockSCStairs(Block block, int meta, Item blockdrop, int quantity) {
		super(block, meta);
        func_149713_g(0);
		modelBlock = block;
		blockDrop = blockdrop;
		dropQuantity = quantity;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side != ForgeDirection.DOWN && side != ForgeDirection.UP;
	}

	@Override
	public void func_149719_a(IBlockAccess iblockaccess, int i, int j, int k) {
        func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void func_149743_a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity ent) {
		int l = world.getBlockMetadata(i, j, k);
		if (l == 0) {
            func_149676_a(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
            func_149676_a(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
		} else if (l == 1) {
            func_149676_a(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
            func_149676_a(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
		} else if (l == 2) {
            func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
            func_149676_a(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
		} else if (l == 3) {
            func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
            func_149676_a(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			super.func_149743_a(world, i, j, k, axisalignedbb, arraylist, ent);
		}
        func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public Item func_149650_a(int i, Random random, int j) {
		if (blockDrop == null) {
			blockDrop = modelBlock.func_149650_a(i, random, j);
		}
		return blockDrop;
	}

	@Override
	public int func_149745_a(Random random) {
		if (dropQuantity == -1) {
			dropQuantity = modelBlock.func_149745_a(random);
		}
		return dropQuantity;
	}

	@Override
	public IIcon func_149691_a(int i, int j) {
		return modelBlock.func_149691_a(i, 0);
	}
}