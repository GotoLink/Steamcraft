package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;
import steamcraft.Steamcraft;

import java.util.Iterator;
import java.util.Set;

public class ItemSCTool extends ItemTool {
	public Set<Block> blocksEffectiveAgainst;
	public float baseDamage;

	protected ItemSCTool(float j, ToolMaterial enumtoolmaterial, Set<Block> blocks) {
		super(j, enumtoolmaterial, blocks);
		this.blocksEffectiveAgainst = blocks;
		this.baseDamage = j + enumtoolmaterial.getDamageVsEntity();
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, Block block, int meta) {
		if (toolMaterial == Steamcraft.TOOLSTEAM) {
            Iterator itr = blocksEffectiveAgainst.iterator();
			while (itr.hasNext()) {
				if (itr.next() == block) {
					return efficiencyOnProperMaterial - (((float) itemstack.getItemDamage()) * 11 / 320);
				}
			}
			if (ForgeHooks.isToolEffective(itemstack, block, meta)) {
				return efficiencyOnProperMaterial - (((float) itemstack.getItemDamage()) * 11 / 320);
			}
		}
		return super.getDigSpeed(itemstack, block, meta);
	}
}
