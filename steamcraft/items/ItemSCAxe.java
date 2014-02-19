package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import steamcraft.Steamcraft;

public class ItemSCAxe extends ItemSCTool {
	public ItemSCAxe(ToolMaterial enumtoolmaterial) {
		super(3, enumtoolmaterial, ItemAxe.field_150917_c);
	}

	@Override
	public boolean func_150897_b(Block block) {
		return block != null && (block.func_149688_o() == Material.field_151575_d || block.func_149688_o() == Material.field_151585_k || block.func_149688_o() == Material.field_151582_l);
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, Block par2Block, int meta) {
		if (par2Block != null) {
			if (par2Block.func_149688_o() == Material.field_151575_d || par2Block.func_149688_o() == Material.field_151585_k || par2Block.func_149688_o() == Material.field_151582_l) {
				if (toolMaterial == Steamcraft.TOOLSTEAM) {
					return efficiencyOnProperMaterial - (((float) itemstack.getItemDamage()) * 11 / 320);
				} else {
					return this.efficiencyOnProperMaterial;
				}
			}
		}
		return super.getDigSpeed(itemstack, par2Block, meta);
	}
}
