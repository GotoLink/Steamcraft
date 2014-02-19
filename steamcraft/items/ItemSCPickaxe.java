package steamcraft.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import steamcraft.HandlerRegistry;

import java.util.Set;

public class ItemSCPickaxe extends ItemSCTool {
	public static Set<Block> blockEffectiveAdded = Sets.newHashSet(get("brimstone"), Blocks.quartz_block, Blocks.obsidian, Blocks.stonebrick, get("oreBornite"), get("orePhosphate"), get("oreUranite"),
            get("oreVolucite"), get("roofTile"), get("decor"), get("lampOn"), get("logBrass"), get("leavesLamp"), get("railingCastIron"));
    static{blockEffectiveAdded.addAll(ItemPickaxe.field_150915_c);}
	public ItemSCPickaxe(ToolMaterial enumtoolmaterial) {
		super(2, enumtoolmaterial, blockEffectiveAdded);
	}

	public static Block get(String name) {
		return HandlerRegistry.getBlock("steamcraft:" + name).get();
	}
}