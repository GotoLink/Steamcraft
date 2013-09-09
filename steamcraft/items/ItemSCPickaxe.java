package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import steamcraft.mod_Steamcraft;

public class ItemSCPickaxe extends ItemPickaxe
{
	 public ItemSCPickaxe(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, 2, enumtoolmaterial, xblocksEffectiveAgainst);
		xefficiencyOnProperMaterial = 4F;
        xefficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial();
		toolMaterial = enumtoolmaterial;
    }

    public boolean canHarvestBlock(Block block)
    {
       if(block == mod_Steamcraft.oreQuartz || block == mod_Steamcraft.oreQuartzActive ||  block == mod_Steamcraft.blockQuartz || block == mod_Steamcraft.decorQuartz){
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == mod_Steamcraft.oreVolucite || block == mod_Steamcraft.blockVolucite || block == mod_Steamcraft.decorVolucite){
            return toolMaterial.getHarvestLevel() == 5 || toolMaterial.getHarvestLevel() == 6;
        }
		if(block == mod_Steamcraft.lamp || block == mod_Steamcraft.leavesLamp){
            return true;
        }
		if(block == mod_Steamcraft.oreUranite || block == mod_Steamcraft.blockUranium || block == mod_Steamcraft.decorUranium){
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.obsidian)
        {
            return toolMaterial.getHarvestLevel() >= 3;
        }
        if(block == Block.blockDiamond || block == mod_Steamcraft.oreSCDiamond	|| block == mod_Steamcraft.decorDiamond)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.blockGold || block == Block.oreGold || block == mod_Steamcraft.decorDiamond)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.blockSteel || block == Block.oreIron || block == mod_Steamcraft.blockCastIron || block == mod_Steamcraft.decorIron || block == mod_Steamcraft.decorCastIron)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if(block == Block.blockLapis || block == Block.oreLapis || block == mod_Steamcraft.decorLapis)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if(block == mod_Steamcraft.borniteOre || block == mod_Steamcraft.blockBrass || block == mod_Steamcraft.woodBrass || block == mod_Steamcraft.decorBrass)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == mod_Steamcraft.brimstone)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == mod_Steamcraft.orePhosphate)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block.blockMaterial == Material.rock)
        {
            return true;
        }
        return block.blockMaterial == Material.iron;
    }

    //public static Block blocksEffectiveAgainst[];
	private static Block xblocksEffectiveAgainst[];
    private float xefficiencyOnProperMaterial;

    static
    {
        xblocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold,
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstoneGlowing, Block.oreRedstone, Block.obsidian
        });
    }
   
    public static void addSteamcraftBlocks ()
    {
        xblocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold,
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstoneGlowing, Block.oreRedstone, mod_Steamcraft.brimstone,
			mod_Steamcraft.borniteOre, mod_Steamcraft.orePhosphate, mod_Steamcraft.oreUranite, mod_Steamcraft.oreQuartz, mod_Steamcraft.oreQuartzActive, mod_Steamcraft.oreVolucite,
			mod_Steamcraft.roofTile, mod_Steamcraft.blockCastIron, mod_Steamcraft.blockVolucite, mod_Steamcraft.blockBrass, mod_Steamcraft.lamp, mod_Steamcraft.woodBrass, mod_Steamcraft.leavesLamp,
			mod_Steamcraft.railingCastIron, mod_Steamcraft.decorIron, mod_Steamcraft.decorGold, mod_Steamcraft.decorBrass, mod_Steamcraft.decorDiamond, mod_Steamcraft.decorCastIron,
			mod_Steamcraft.decorVolucite, mod_Steamcraft.decorLapis, mod_Steamcraft.carvedStone, mod_Steamcraft.blockQuartz, mod_Steamcraft.blockUranium, mod_Steamcraft.decorUranium,
			mod_Steamcraft.decorQuartz, Block.obsidian, mod_Steamcraft.oreSCDiamond, mod_Steamcraft.oreSCCoal, Block.stoneBrick
        });
    }
   
    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < xblocksEffectiveAgainst.length; i++)
        {
            if(xblocksEffectiveAgainst[i] == block)
            {
			if(toolMaterial == EnumToolSteamcraft.STEAM){
				return (xefficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
				}
                return xefficiencyOnProperMaterial;
            }
        }

        return 1.0F;
    }
   protected EnumToolSteamcraft toolMaterial;

}

   /* static 
    {
        blocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, 
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, mod_Steamcraft.brimstone, Block.orePhosphate, Block.blockCastIron,
			Block.oreVolucite, Block.obsidian, Block.blockVolucite, Block.oreUranite, Block.oreRedstone, Block.blockBrass, Block.woodBrass, Block.lamp, Block.leavesLamp, Block.decorIron, 
			Block.decorGold, Block.decorBrass, Block.decorDiamond, Block.decorCastIron, Block.decorVolucite, Block.decorLapis, Block.stoneBrick, Block.oreQuartz, Block.oreQuartzActive,
			Block.roofTile
        });
    }*/
//}