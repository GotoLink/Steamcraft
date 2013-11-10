package steamcraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.HandlerRegistry;

public class ItemTeacup extends ItemFood {
	public ItemTeacup(int i, int j) {
		super(i, j, 0.0F, false);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
		return new ItemStack(getEmpty(), 1);
	}

	public static Item getEmpty() {
		return HandlerRegistry.getItem("steamcraft:teacupEmpty").get();
	}

	public static Item getFull() {
		return HandlerRegistry.getItem("steamcraft:teacup").get();
	}
}
