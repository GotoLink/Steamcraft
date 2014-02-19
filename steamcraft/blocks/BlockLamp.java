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
	private boolean isPowered;

	public BlockLamp(boolean flag) {
		super(Material.field_151575_d);
		isPowered = flag;
        func_149711_c(2.0F);
		func_149672_a(Block.field_149778_k);
		if (flag) {
            func_149715_a(1.0F);
		}
	}

	@Override
	public Item func_149650_a(int i, Random random, int j) {
		return Item.func_150898_a(getIdle());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item func_149694_d(World par1World, int par2, int par3, int par4) {
		return Item.func_150898_a(getIdle());
	}

	@Override
	public void func_149726_b(World world, int i, int j, int k) {
		if (!world.isRemote) {
			if (this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k)) {
				world.func_147464_a(i, j, k, this, 4);
			} else if (!this.isPowered && world.isBlockIndirectlyGettingPowered(i, j, k)) {
				world.func_147465_d(i, j, k, getActive(), 0, 2);
			}
		}
	}

	@Override
	public void func_149695_a(World world, int i, int j, int k, Block l) {
        func_149726_b(world, i, j, k);
	}

	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
		if (!world.isRemote && this.isPowered && !world.isBlockIndirectlyGettingPowered(i, j, k)) {
			world.func_147465_d(i, j, k, getIdle(), 0, 2);
		}
	}

	public static Block getActive() {
		return HandlerRegistry.getBlock("steamcraft:lampOn").get();
	}

	public static Block getIdle() {
		return HandlerRegistry.getBlock("steamcraft:lamp").get();
	}
}
