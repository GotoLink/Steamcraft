package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import steamcraft.Steamcraft;

public class BlockSCOre extends BlockOre {
	public BlockSCOre(int i) {
		super(i);
	}

	/*
	 * @Override public void onBlockAdded(World world, int i, int j, int k) {
	 * if(blockID == Steamcraft.oreVolucite.blockID){
	 * System.out.println("Volucite at " + i+","+j+","+k); } }
	 */
	@Override
	public int idDropped(int i, Random random, int j) {
		if (blockID == Steamcraft.oreVolucite.blockID) {
			return Steamcraft.material.itemID;
		} else {
			return super.idDropped(i, random, j);
		}
	}
}
