package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;

public class ItemSCAxe extends ItemSCTool
{
    public ItemSCAxe(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, 3, enumtoolmaterial, blocksEffectiveAgainst);
    }

    private static Block blocksEffectiveAgainst[];

    static 
    {
        blocksEffectiveAgainst = (new Block[] {
            Block.planks, Block.bookShelf, Block.wood, Block.chest
        });
    }
}
