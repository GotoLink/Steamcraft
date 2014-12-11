package steamcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockSCStairs extends BlockStairs {
	private final Block modelBlock;
	private Item blockDrop;
	private int dropQuantity;

	public BlockSCStairs(Block block, int meta, Item blockdrop, int quantity) {
		super(block, meta);
        setLightOpacity(0);
		modelBlock = block;
		blockDrop = blockdrop;
		dropQuantity = quantity;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side != ForgeDirection.DOWN && side != ForgeDirection.UP;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity ent) {
		int l = world.getBlockMetadata(i, j, k);
		if (l == 0) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
		} else if (l == 1) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
		} else if (l == 2) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
		} else if (l == 3) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
            setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, ent);
		}
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		if (blockDrop == null) {
			blockDrop = modelBlock.getItemDropped(i, random, j);
		}
		return blockDrop;
	}

	@Override
	public int quantityDropped(Random random) {
		if (dropQuantity == -1) {
			dropQuantity = modelBlock.quantityDropped(random);
		}
		return dropQuantity;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		return modelBlock.getIcon(i, 0);
	}
}