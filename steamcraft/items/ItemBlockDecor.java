package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import steamcraft.blocks.BlockDecor;

public class ItemBlockDecor extends ItemBlockWithMetadata {
	public ItemBlockDecor(Block par2Block) {
		super(par2Block, par2Block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + BlockDecor.names[itemstack.getItemDamage()];
	}
}
