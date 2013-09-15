package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStep;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import steamcraft.blocks.BlockSCStep;

public class ItemSCSlab extends ItemBlock
{
    public ItemSCSlab(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    @Override
    public Icon getIconFromDamage(int i)
    {
        return Block.stairsWoodJungle.getIcon(2, i);
    }
    @Override
    public int getMetadata(int i)
    {
        return i;
    }
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        int i = itemstack.getItemDamage();
        if(i < 0 || i >= BlockStep.blockStepTypes.length)
        {
            i = 0;
        }
        return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(BlockStep.blockStepTypes[i]).toString();
    }
}
