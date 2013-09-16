package steamcraft.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import steamcraft.mod_Steamcraft;

public class SlotNukeFurnace extends SlotFurnace
{
    public SlotNukeFurnace(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
    {
        super(entityplayer, iinventory, i, j, k);
    }
    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }
    @Override
    protected void onCrafting(ItemStack par1ItemStack)
    {
		thePlayer.triggerAchievement(mod_Steamcraft.ach_Fallout);
        super.onCrafting(par1ItemStack);
    }
}