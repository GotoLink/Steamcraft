package steamcraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class ItemTeacup extends ItemFood
{
    public ItemTeacup(int i, int j, float f)
    {
        super(i,j,f,false);
		maxStackSize = 1;
    }

    public ItemTeacup(int i, int j)
    {
        this(i, j, 0.0F);
    }
    @Override
    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        par1ItemStack = new ItemStack(mod_Steamcraft.emptyTeacup, 1);
    }
}
