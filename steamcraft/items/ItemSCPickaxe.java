package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import steamcraft.Steamcraft;

public class ItemSCPickaxe extends ItemSCTool
{
	 public ItemSCPickaxe(int i, EnumToolMaterial enumtoolmaterial)
	 {
        super(i, 2, enumtoolmaterial, xblocksEffectiveAgainst);
	 }
	 @Override
	 public boolean canHarvestBlock(Block block)
	 {
		if(block == Steamcraft.oreVolucite){
            return toolMaterial.getHarvestLevel() == 5||toolMaterial.getHarvestLevel() == 6;
        }
		if(block == Steamcraft.lamp || block == Steamcraft.leavesLamp){
            return true;
        }
		if(block == Steamcraft.oreUranite || block == Steamcraft.decorBlock
				||block == Block.blockDiamond || block == Block.blockGold || block == Block.oreGold
				||block == Steamcraft.oreQuartz || block == Steamcraft.oreQuartzActive ||  block == Block.blockNetherQuartz){
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.obsidian)
        {
            return toolMaterial.getHarvestLevel() >= 3;
        }
        if(block == Block.blockIron || block == Block.oreIron 
        		||block == Block.blockLapis || block == Block.oreLapis)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if(block == Steamcraft.borniteOre || block == Steamcraft.woodBrass
        		||block == Steamcraft.brimstone ||block == Steamcraft.orePhosphate)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        return block.blockMaterial == Material.rock || block.blockMaterial == Material.iron;
    }

	private static Block xblocksEffectiveAgainst[];
    static
    {
        xblocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold,
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstoneGlowing, Block.oreRedstone, 
            Steamcraft.brimstone, Block.blockNetherQuartz, Block.obsidian, Block.stoneBrick,
			Steamcraft.borniteOre, Steamcraft.orePhosphate, Steamcraft.oreUranite, Steamcraft.oreQuartz,
			Steamcraft.oreQuartzActive, Steamcraft.oreVolucite,
			Steamcraft.roofTile, Steamcraft.decorBlock, Steamcraft.lamp, Steamcraft.woodBrass,
			Steamcraft.leavesLamp, Steamcraft.railingCastIron
        });
    }
}