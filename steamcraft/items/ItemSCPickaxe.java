package steamcraft.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemSCPickaxe extends ItemSCTool {
	public ItemSCPickaxe(ToolMaterial enumtoolmaterial) {
		super(2, enumtoolmaterial, null);
	}

    @Override
    Set<Block> getEffectivelyBrokenBlocks(){
        Set<Block> blockEffectiveAdded = Sets.newHashSet(Blocks.quartz_block, Blocks.obsidian, Blocks.stonebrick, get("brimstone"), get("oreBornite"), get("orePhosphate"), get("oreUranite"),
                get("oreVolucite"), get("roofTile"), get("decor"), get("lampOn"), get("logBrass"), get("leavesLamp"), get("railingCastIron"));
        blockEffectiveAdded.addAll(ItemPickaxe.field_150915_c);
        return blockEffectiveAdded;
    }

    @Override
    public boolean func_150897_b(Block block){
        if(block == Blocks.obsidian){
            return this.toolMaterial.getHarvestLevel() >= 3;
        }
        else if(block == Blocks.diamond_block || block == Blocks.diamond_ore || block == Blocks.emerald_ore || block == Blocks.emerald_block || block == Blocks.gold_block || block == Blocks.gold_ore){
            return this.toolMaterial.getHarvestLevel() >= 2;
        }
        else if(block == Blocks.iron_block || block == Blocks.iron_ore || block == Blocks.lapis_block || block == Blocks.lapis_ore){
            return this.toolMaterial.getHarvestLevel() >= 1;
        }
        else if(block == Blocks.redstone_ore || block == Blocks.lit_redstone_ore){
            return this.toolMaterial.getHarvestLevel() >= 2;
        }
        else
            return isPickableMaterial(block.getMaterial());

    }

    @Override
    public float func_150893_a(ItemStack stack, Block block){
        return isPickableMaterial(block.getMaterial()) ? this.efficiencyOnProperMaterial : super.func_150893_a(stack, block);
    }

    public boolean isPickableMaterial(Material material){
        return material == Material.rock || material == Material.iron || material == Material.anvil;
    }
}