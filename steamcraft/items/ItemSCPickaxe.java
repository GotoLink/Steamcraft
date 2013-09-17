package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import steamcraft.Steamcraft;

public class ItemSCPickaxe extends ItemPickaxe
{
	 public ItemSCPickaxe(int i, EnumToolMaterial enumtoolmaterial)
	 {
        super(i, enumtoolmaterial);
		xefficiencyOnProperMaterial = 4F;
        xefficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial();
		toolMaterial = enumtoolmaterial;
	 }
	 @Override
	 public boolean canHarvestBlock(Block block)
	 {
       if(block == Steamcraft.oreQuartz || block == Steamcraft.oreQuartzActive ||  block == Block.blockNetherQuartz){
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == Steamcraft.oreVolucite || block == Steamcraft.blockVolucite || block == Steamcraft.decorVolucite){
            return toolMaterial.getHarvestLevel() == 5 || toolMaterial.getHarvestLevel() == 6;
        }
		if(block == Steamcraft.lamp || block == Steamcraft.leavesLamp){
            return true;
        }
		if(block == Steamcraft.oreUranite || block == Steamcraft.blockUranium || block == Steamcraft.decorUranium){
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.obsidian)
        {
            return toolMaterial.getHarvestLevel() >= 3;
        }
        if(block == Block.blockDiamond	|| block == Steamcraft.decorDiamond)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.blockGold || block == Block.oreGold || block == Steamcraft.decorDiamond)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block == Block.blockIron || block == Block.oreIron || block == Steamcraft.blockCastIron || block == Steamcraft.decorIron || block == Steamcraft.decorCastIron)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if(block == Block.blockLapis || block == Block.oreLapis || block == Steamcraft.decorLapis)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if(block == Steamcraft.borniteOre || block == Steamcraft.blockBrass || block == Steamcraft.woodBrass || block == Steamcraft.decorBrass)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == Steamcraft.brimstone)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
		if(block == Steamcraft.orePhosphate)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if(block.blockMaterial == Material.rock)
        {
            return true;
        }
        return block.blockMaterial == Material.iron;
    }

	private static Block xblocksEffectiveAgainst[];
    private float xefficiencyOnProperMaterial;

    static
    {
        xblocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold,
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstoneGlowing, Block.oreRedstone, Block.obsidian
        });
    }
   
    public static void addSteamcraftBlocks ()
    {
        xblocksEffectiveAgainst = (new Block[] {
            Block.cobblestone, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold,
            Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstoneGlowing, Block.oreRedstone, Steamcraft.brimstone,
			Steamcraft.borniteOre, Steamcraft.orePhosphate, Steamcraft.oreUranite, Steamcraft.oreQuartz, Steamcraft.oreQuartzActive, Steamcraft.oreVolucite,
			Steamcraft.roofTile, Steamcraft.blockCastIron, Steamcraft.blockVolucite, Steamcraft.blockBrass, Steamcraft.lamp, Steamcraft.woodBrass, Steamcraft.leavesLamp,
			Steamcraft.railingCastIron, Steamcraft.decorIron, Steamcraft.decorGold, Steamcraft.decorBrass, Steamcraft.decorDiamond, Steamcraft.decorCastIron,
			Steamcraft.decorVolucite, Steamcraft.decorLapis, Steamcraft.carvedStone, Block.blockNetherQuartz, Steamcraft.blockUranium, Steamcraft.decorUranium,
			Block.obsidian, Block.stoneBrick
        });
    }
    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < xblocksEffectiveAgainst.length; i++)
        {
            if(xblocksEffectiveAgainst[i] == block)
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