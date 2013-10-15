package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;

import com.google.common.collect.ObjectArrays;

public class ItemSCDrill extends ItemSCTool {
	public static Block[] xblocksEffectiveAgainst = ObjectArrays.concat(ItemSCPickaxe.xblocksEffectiveAgainst, new Block[] { Block.dirt, Block.sand, Block.gravel, Block.grass }, Block.class);

	public ItemSCDrill(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 3, enumtoolmaterial, xblocksEffectiveAgainst);
		efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial() * 1.5F;
	}
}