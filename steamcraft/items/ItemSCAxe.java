package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

public class ItemSCAxe extends ItemSCTool {
	public ItemSCAxe(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 3, enumtoolmaterial, ItemAxe.blocksEffectiveAgainst);
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block != null && (block.blockMaterial == Material.wood || block.blockMaterial == Material.plants || block.blockMaterial == Material.vine);
	}
}
