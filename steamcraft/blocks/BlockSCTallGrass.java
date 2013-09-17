package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.item.Item;
import steamcraft.Steamcraft;

public class BlockSCTallGrass extends BlockTallGrass
{
    public BlockSCTallGrass(int i)
    {
        super(i);
    }
    @Override
    public int idDropped(int i, Random random, int j)
    {
        if(random.nextInt(8) == 0)
        {
            return Item.seeds.itemID;
        } else if(random.nextInt(8) == 1)
        {
            return Steamcraft.teaSeed.itemID;
        }
        {
            return -1;
        }
    }
}
