package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;

public class BlockSCOre extends BlockOre {
	public BlockSCOre() {
		super();
	}

	@Override
	public Item func_149650_a(int i, Random random, int j) {
		if (this == getVoluciteId()) {
			return Steamcraft.material;
		} else {
			return super.func_149650_a(i, random, j);
		}
	}

	private static Block getVoluciteId() {
		return HandlerRegistry.getBlock("steamcraft:oreVolucite").get();
	}
}
