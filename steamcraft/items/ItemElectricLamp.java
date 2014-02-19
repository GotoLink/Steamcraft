package steamcraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemElectricLamp extends Item {
	private Block spawnID;

	public ItemElectricLamp(Block spawn) {
		super();
		spawnID = spawn;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		if (world.func_147439_a(i, j, k) != Blocks.snow) {
			if (l == 0) {
				j--;
			}
			if (l == 1) {
				j++;
			}
			if (l == 2) {
				k--;
			}
			if (l == 3) {
				k++;
			}
			if (l == 4) {
				i--;
			}
			if (l == 5) {
				i++;
			}
			if (!world.func_147437_c(i, j, k)) {
				return false;
			}
		}
		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}
		if (itemstack.stackSize == 0) {
			return false;
		}
		if (world.func_147472_a(spawnID, i, j, k, false, l, null, itemstack)) {
            int meta = spawnID.func_149660_a(world, i, j, k, l, par8, par9, par10, 0);
			if (world.func_147465_d(i, j, k, spawnID, meta, 3)) {
				if (world.func_147439_a(i, j, k) == spawnID) {
					spawnID.func_149689_a(world, i, j, k, entityplayer, itemstack);
                    spawnID.func_149714_e(world, i, j, k, meta);
				}
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, spawnID.field_149762_H.func_150496_b(), (spawnID.field_149762_H.func_150497_c() + 1.0F) / 2.0F, spawnID.field_149762_H.func_150494_d() * 0.8F);
				itemstack.stackSize--;
			}
		}
		return true;
	}
}
