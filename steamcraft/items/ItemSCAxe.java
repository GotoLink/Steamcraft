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
		return block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine);
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, Block par2Block, int meta) {
		if (par2Block != null) {
			if (par2Block.getMaterial() == Material.wood || par2Block.getMaterial() == Material.plants || par2Block.getMaterial() == Material.vine) {
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
