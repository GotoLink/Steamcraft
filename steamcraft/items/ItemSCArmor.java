package steamcraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import steamcraft.Steamcraft;

public class ItemSCArmor extends ItemArmor {
	public ItemSCArmor(int i, EnumArmorMaterial j, int k, int l) {
		super(i, j, k, l);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		ItemSCArmor item = (ItemSCArmor) stack.getItem();
		String s1 = String.format("steamcraft:textures/models/armor/%s_layer_%d.png", Steamcraft.armorMap.get(item.renderIndex), (slot == 2 ? 2 : 1));
		return s1;
	}
}
