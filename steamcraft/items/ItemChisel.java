package steamcraft.items;

import net.minecraft.item.Item;

public class ItemChisel extends Item
{
    public ItemChisel(int i,int j)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(j);
    }
	public boolean isFull3D()
    {
        return true;
    }
}
