package steamcraft.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import steamcraft.Steamcraft;

public class SlotNukeFurnace extends SlotFurnace
{
    private EntityPlayer thePlayer;
	public SlotNukeFurnace(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
    {
        super(entityplayer, iinventory, i, j, k);
        this.thePlayer = entityplayer;
    }
    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }
    @Override
    protected void onCrafting(ItemStack par1ItemStack)
    {
		thePlayer.triggerAchievement(Steamcraft.ach_Fallout);
        super.onCrafting(par1ItemStack);
    }
}