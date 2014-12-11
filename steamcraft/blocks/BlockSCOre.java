package steamcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;

import java.util.Random;

public class BlockSCOre extends BlockOre {
	public BlockSCOre() {
		super();
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		if (this == getVoluciteId()) {
			return Steamcraft.material;
		} else {
			return super.getItemDropped(i, random, j);
		}
	}

	private static Block getVoluciteId() {
		return HandlerRegistry.getBlock("steamcraft:oreVolucite").get();
	}
}
