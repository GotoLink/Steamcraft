package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import steamcraft.blocks.BlockSCStep;

public class ItemSCSlab extends ItemBlock
{
    public ItemSCSlab(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public int getIconFromDamage(int i)
    {
        return Block.stairSingle.getBlockTextureFromSideAndMetadata(2, i);
    }

    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        int i = itemstack.getItemDamage();
        if(i < 0 || i >= BlockSCStep.field_22037_a.length)
        {
            i = 0;
        }
        return (new StringBuilder()).append(super.getItemName()).append(".").append(BlockSCStep.field_22037_a[i]).toString();
    }
}
