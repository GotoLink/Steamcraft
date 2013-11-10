package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;

public class BlockSCOre extends BlockOre {
	public BlockSCOre(int i) {
		super(i);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		if (blockID == getVoluciteId()) {
			return Steamcraft.material.itemID;
		} else {
			return super.idDropped(i, random, j);
		}
	}

	private static int getVoluciteId() {
		return HandlerRegistry.getBlock("steamcraft:oreVolucite").getID();
	}
}
