package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemSpade;

public class ItemSCSpade extends ItemSCTool {
	public ItemSCSpade(ToolMaterial enumtoolmaterial) {
		super(1, enumtoolmaterial, ItemSpade.field_150916_c);
	}

	@Override
	public boolean func_150897_b(Block par1Block) {
		return par1Block.func_149688_o() == Material.field_151597_y || par1Block.func_149688_o() == Material.field_151596_z;
	}
}
