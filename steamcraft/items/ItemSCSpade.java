package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemSpade;

import java.util.Set;

public class ItemSCSpade extends ItemSCTool {
	public ItemSCSpade(ToolMaterial enumtoolmaterial) {
		super(1, enumtoolmaterial, ItemSpade.field_150916_c);
	}

	@Override
	public boolean func_150897_b(Block par1Block) {
		return par1Block.getMaterial() == Material.snow || par1Block.getMaterial() == Material.craftedSnow;
	}

    @Override
    Set<Block> getEffectivelyBrokenBlocks() {
        return ItemSpade.field_150916_c;
    }
}
