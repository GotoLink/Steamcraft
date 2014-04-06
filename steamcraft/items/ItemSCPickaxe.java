package steamcraft.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;

import java.util.Set;

public class ItemSCPickaxe extends ItemSCTool {
	public ItemSCPickaxe(ToolMaterial enumtoolmaterial) {
		super(2, enumtoolmaterial, null);
	}

    @Override
    Set<Block> getEffectivelyBrokenBlocks(){
        Set<Block> blockEffectiveAdded = Sets.newHashSet(Blocks.quartz_block, Blocks.obsidian, Blocks.stonebrick, get("brimstone"), get("oreBornite"), get("orePhosphate"), get("oreUranite"),
                get("oreVolucite"), get("roofTile"), get("decor"), get("lampOn"), get("logBrass"), get("leavesLamp"), get("railingCastIron"));
        blockEffectiveAdded.addAll(ItemPickaxe.field_150915_c);
        return blockEffectiveAdded;
    }
}