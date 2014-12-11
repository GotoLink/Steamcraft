package steamcraft.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import steamcraft.HandlerRegistry;

public final class SlotNukeFurnace extends SlotFurnace {
	private final EntityPlayer thePlayer;

	public SlotNukeFurnace(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k) {
		super(entityplayer, iinventory, i, j, k);
		this.thePlayer = entityplayer;
	}

	@Override
	protected void onCrafting(ItemStack par1ItemStack) {
		thePlayer.triggerAchievement(HandlerRegistry.getAchievement("fallout"));
		super.onCrafting(par1ItemStack);
	}
}