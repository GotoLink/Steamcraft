package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import steamcraft.Steamcraft;

public class ItemSCDrill extends ItemSCTool
{
	public ItemSCDrill(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, 3, enumtoolmaterial, xblocksEffectiveAgainst);
		xefficiencyOnProperMaterial = 4F;
        xefficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial()*1.5F;
		toolMaterial = enumtoolmaterial;
    }
	@Override
    public boolean canHarvestBlock(Block block)
    {
		if(block == Steamcraft.oreQuartz || block == Steamcraft.oreQuartzActive){
            return false;
        }
		if(block == Block.blockNetherQuartz){
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == Steamcraft.oreVolucite){
            return false;
        }
		if(block == Steamcraft.lamp || block == Steamcraft.leavesLamp){
            return true;
        }
		if(block == Steamcraft.blockVolucite || block == Steamcraft.decorVolucite){
            return toolMaterial.getHarvestLevel() == 5 || toolMaterial.getHarvestLevel() == 6;
        }
		if(block == Steamcraft.oreUranite){
            return false;
        }
		if(block == Steamcraft.blockUranium || block == Steamcraft.decorUranium){
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.obsidian)
        {
            return false;
        }
        if(block == Block.blockDiamond || block == Steamcraft.decorDiamond)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.oreGold)
        {
            return false;
        }
		if(block == Block.blockGold || block == Steamcraft.decorGold){
			return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.blockIron || block == Block.oreIron || block == Steamcraft.blockCastIron || block == Steamcraft.decorIron || block == Steamcraft.decorCastIron)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if(block == Block.oreLapis)
        {
            return false;
        }
		if(block == Block.blockLapis || block == Steamcraft.decorLapis){
			return toolMaterial.getHarvestLevel() >= 1;
        }
     	if(block == Block.oreRedstone || block == Block.oreRedstoneGlowing || block == Steamcraft.blockBrass || block == Steamcraft.woodBrass || block == Steamcraft.decorBrass)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == Steamcraft.brimstone)
        {
            return false;
        }
		if(block == Steamcraft.orePhosphate)
        {
            return false;
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
            Block.cobblestone, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold,
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstoneGlowing, Block.oreRedstone,
			Block.dirt, Block.sand, Block.gravel, Block.grass, Block.obsidian
        });
    }
   
    public static void addSteamcraftBlocks ()
    {
        xblocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold,
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstoneGlowing, Block.oreRedstone, Steamcraft.brimstone,
			Steamcraft.borniteOre, Steamcraft.orePhosphate, Steamcraft.oreUranite, Steamcraft.oreQuartz, Steamcraft.oreQuartzActive, Steamcraft.oreVolucite,
			Block.dirt, Block.sand, Block.gravel, Block.grass, Block.obsidian, Steamcraft.roofTile, Steamcraft.blockCastIron, Steamcraft.blockVolucite, Steamcraft.blockBrass,
			Steamcraft.lamp, Steamcraft.woodBrass, Steamcraft.leavesLamp, Steamcraft.railingCastIron, Steamcraft.decorIron, Steamcraft.decorGold, Steamcraft.decorBrass,
			Steamcraft.decorDiamond, Steamcraft.decorCastIron, Steamcraft.decorVolucite, Steamcraft.decorLapis, Steamcraft.carvedStone, Block.blockNetherQuartz, 
			Steamcraft.blockUranium, Steamcraft.decorUranium, Block.obsidian
        });
    }
    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < xblocksEffectiveAgainst.length; i++)
        {
        	if(block.blockID==xblocksEffectiveAgainst[i].blockID)
        	{
        		if(toolMaterial == Steamcraft.STEAM){
					return (xefficiencyOnProperMaterial - (((float)itemstack.getItemDamage())*11/320));
				}
	            return xefficiencyOnProperMaterial;
        	}
        }
        return 1.0F;
    }
	
   protected EnumToolMaterial toolMaterial;

}