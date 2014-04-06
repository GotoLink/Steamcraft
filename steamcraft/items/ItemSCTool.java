package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;
import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;

import java.util.Set;

public abstract class ItemSCTool extends ItemTool {
	public Set<Block> blocksEffectiveAgainst;
	public float baseDamage;

	protected ItemSCTool(float j, ToolMaterial enumtoolmaterial, Set<Block> blocks) {
		super(j, enumtoolmaterial, blocks);
		this.blocksEffectiveAgainst = blocks;
		this.baseDamage = j + enumtoolmaterial.getDamageVsEntity();
	}

    @Override
    public float func_150893_a(ItemStack itemStack, Block block){
        if(blocksEffectiveAgainst==null){
            blocksEffectiveAgainst = getEffectivelyBrokenBlocks();
        }
        return blocksEffectiveAgainst.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
    }

	@Override
	public float getDigSpeed(ItemStack itemstack, Block block, int meta) {
        if(blocksEffectiveAgainst==null){
            blocksEffectiveAgainst = getEffectivelyBrokenBlocks();
        }
        if (blocksEffectiveAgainst.contains(block) || ForgeHooks.isToolEffective(itemstack, block, meta)){
            if (toolMaterial == Steamcraft.TOOLSTEAM) {
                return efficiencyOnProperMaterial - (((float) itemstack.getItemDamage()) * 11 / 320);
            }else{
                return efficiencyOnProperMaterial;
            }
        }
		return 1.0F;
	}

    public static Block get(String name) {
        return HandlerRegistry.getBlock("steamcraft:" + name).get();
    }

    abstract Set<Block> getEffectivelyBrokenBlocks();
}
