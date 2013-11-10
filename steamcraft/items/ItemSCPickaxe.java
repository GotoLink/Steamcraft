package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import steamcraft.HandlerRegistry;
import com.google.common.collect.ObjectArrays;

public class ItemSCPickaxe extends ItemSCTool {
	public static Block[] blockEffectiveAdded = new Block[] { get("brimstone"), Block.blockNetherQuartz, Block.obsidian, Block.stoneBrick, get("oreBornite"), get("orePhosphate"), get("oreUranite"),
			get("oreVolucite"), get("roofTile"), get("decor"), get("lampOn"), get("logBrass"), get("leavesLamp"), get("railingCastIron") };
	public static Block[] xblocksEffectiveAgainst = ObjectArrays.concat(ItemPickaxe.blocksEffectiveAgainst, blockEffectiveAdded, Block.class);

	public ItemSCPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 2, enumtoolmaterial, xblocksEffectiveAgainst);
	}

	public static Block get(String name) {
		return HandlerRegistry.getBlock("steamcraft:" + name).get();
	}
}