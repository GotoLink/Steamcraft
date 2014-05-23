package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLamp extends Block {
	private final boolean isPowered;

	public BlockLamp(boolean flag) {
		super(Material.wood);
		isPowered = flag;
        setHardness(2.0F);
		setStepSound(Block.soundTypeGlass);
		if (flag) {
            setLightLevel(1.0F);
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(getIdle());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4) {
		return Item.getItemFromBlock(getIdle());
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if (!world.isRemote) {
			if (this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k)) {
				world.scheduleBlockUpdate(i, j, k, this, 4);
			} else if (!this.isPowered && world.isBlockIndirectlyGettingPowered(i, j, k)) {
				world.setBlock(i, j, k, getActive(), 0, 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l) {
        onBlockAdded(world, i, j, k);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (!world.isRemote && this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k)) {
			world.setBlock(i, j, k, getIdle(), 0, 2);
		}
	}

	public static Block getActive() {
		return HandlerRegistry.getBlock("steamcraft:lampOn").get();
	}

	public static Block getIdle() {
		return HandlerRegistry.getBlock("steamcraft:lamp").get();
	}
}
