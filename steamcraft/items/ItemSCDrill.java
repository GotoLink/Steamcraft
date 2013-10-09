package steamcraft.items;

import com.google.common.collect.ObjectArrays;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import steamcraft.Steamcraft;

public class ItemSCDrill extends ItemSCTool
{
	public ItemSCDrill(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, 3, enumtoolmaterial, xblocksEffectiveAgainst);
        efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial()*1.5F;
    }
	@Override
    public boolean canHarvestBlock(Block block)
    {
		if(block == Steamcraft.oreQuartz || block == Steamcraft.oreQuartzActive
				||block == Steamcraft.oreVolucite||block == Steamcraft.oreUranite
				||block == Block.obsidian||block == Block.oreGold||block == Block.oreLapis
				||block == Steamcraft.brimstone || block == Steamcraft.orePhosphate){
            return false;
        }
        if(block == Block.blockIron || block == Block.oreIron|| block == Block.blockLapis){
			return toolMaterial.getHarvestLevel() >= 1;
        }
		if(block == Block.blockNetherQuartz ||block == Steamcraft.decorBlock
				||block == Block.blockDiamond ||block == Block.blockGold
        		||block == Block.oreRedstone || block == Block.oreRedstoneGlowing 
     			|| block == Steamcraft.woodBrass)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == Steamcraft.lamp || block == Steamcraft.leavesLamp){
            return true;
        }
        return block.blockMaterial == Material.rock||block.blockMaterial == Material.iron;
    }
	public static Block[] xblocksEffectiveAgainst = ObjectArrays.concat(ItemSCPickaxe.xblocksEffectiveAgainst,
			new Block[] { Block.dirt, Block.sand, Block.gravel, Block.grass},Block.class);
}