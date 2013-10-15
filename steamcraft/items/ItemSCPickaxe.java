package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import steamcraft.Steamcraft;

import com.google.common.collect.ObjectArrays;

public class ItemSCPickaxe extends ItemSCTool {
	public static Block[] blockEffectiveAdded = new Block[] { Steamcraft.brimstone, Block.blockNetherQuartz, Block.obsidian,
		Block.stoneBrick, Steamcraft.borniteOre, Steamcraft.orePhosphate, Steamcraft.oreUranite, Steamcraft.oreVolucite, Steamcraft.roofTile,
		Steamcraft.decorBlock, Steamcraft.lamp, Steamcraft.woodBrass, Steamcraft.leavesLamp, Steamcraft.railingCastIron };
	public static Block[] xblocksEffectiveAgainst = ObjectArrays.concat(ItemPickaxe.blocksEffectiveAgainst, blockEffectiveAdded, Block.class);

	public ItemSCPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 2, enumtoolmaterial, xblocksEffectiveAgainst);
	}
}