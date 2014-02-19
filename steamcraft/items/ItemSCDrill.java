package steamcraft.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Set;

public class ItemSCDrill extends ItemSCTool {
    public static Set<Block> blockEffectiveAdded = Sets.newHashSet(Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.grass);
    static{blockEffectiveAdded.addAll(ItemSCPickaxe.blockEffectiveAdded);}
	public ItemSCDrill(ToolMaterial enumtoolmaterial) {
		super(3, enumtoolmaterial, blockEffectiveAdded);
		efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial() * 1.5F;
	}
}