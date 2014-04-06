package steamcraft.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;

import java.util.Set;

public class ItemSCDrill extends ItemSCTool {
	public ItemSCDrill(ToolMaterial enumtoolmaterial) {
		super(3, enumtoolmaterial, null);
		efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial() * 1.5F;
	}

    @Override
    Set<Block> getEffectivelyBrokenBlocks() {
        Set<Block> blockEffectiveAdded = Sets.newHashSet(Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.grass, Blocks.quartz_block, Blocks.obsidian, Blocks.stonebrick, get("brimstone"), get("oreBornite"), get("orePhosphate"), get("oreUranite"),
                get("oreVolucite"), get("roofTile"), get("decor"), get("lampOn"), get("logBrass"), get("leavesLamp"), get("railingCastIron"));
        blockEffectiveAdded.addAll(ItemPickaxe.field_150915_c);
        return blockEffectiveAdded;
    }
}