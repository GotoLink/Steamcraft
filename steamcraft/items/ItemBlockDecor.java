package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import steamcraft.blocks.BlockDecor;

public class ItemBlockDecor extends ItemBlockWithMetadata {
	public ItemBlockDecor(int par1, Block par2Block) {
		super(par1, par2Block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + BlockDecor.names[itemstack.getItemDamage()];
	}
}
