package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

public class ItemSCAxe extends ItemSCTool
{
	private static Block blocksEffectiveAgainst[] = ItemAxe.blocksEffectiveAgainst;
    public ItemSCAxe(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, 3, enumtoolmaterial, blocksEffectiveAgainst);
    }
}
