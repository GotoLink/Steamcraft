package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import steamcraft.Steamcraft;

import java.util.Set;

public class ItemSCAxe extends ItemSCTool {
	public ItemSCAxe(ToolMaterial enumtoolmaterial) {
		super(3, enumtoolmaterial, ItemAxe.field_150917_c);
	}

	@Override
	public boolean func_150897_b(Block block) {
		return isCuttableMaterial(block.getMaterial());
	}

    public boolean isCuttableMaterial(Material material){
        return material == Material.wood || material == Material.plants || material == Material.vine;
    }

	@Override
	public float getDigSpeed(ItemStack itemstack, Block par2Block, int meta) {
        if (isCuttableMaterial(par2Block.getMaterial())) {
            if (toolMaterial == Steamcraft.TOOLSTEAM) {
                return efficiencyOnProperMaterial - (((float) itemstack.getItemDamage()) * 11 / 320);
            } else {
                return this.efficiencyOnProperMaterial;
            }
        }
		return super.getDigSpeed(itemstack, par2Block, meta);
	}

    @Override
    Set<Block> getEffectivelyBrokenBlocks(){
        return ItemAxe.field_150917_c;
    }

}
