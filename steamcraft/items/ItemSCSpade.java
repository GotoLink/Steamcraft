package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class ItemSCSpade extends ItemSCTool{

	public ItemSCSpade(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, 1, enumtoolmaterial, ItemSpade.blocksEffectiveAgainst);
	}
	@Override
	public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockMaterial == Material.snow || par1Block.blockMaterial == Material.craftedSnow;
    }
}
