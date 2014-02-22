package steamcraft.items;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import steamcraft.Steamcraft;

public class ItemSCHoe extends ItemHoe {
	public ItemSCHoe(ToolMaterial enumtoolmaterial) {
		super(enumtoolmaterial);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		} else {
			UseHoeEvent event = new UseHoeEvent(entityplayer, itemstack, world, i, j, k);
			if (MinecraftForge.EVENT_BUS.post(event)) {
				return false;
			}
			if (event.getResult() == Event.Result.ALLOW) {
				itemstack.damageItem(1, entityplayer);
				return true;
			}
			Block i1 = world.getBlock(i, j, k);
			Block j1 = world.getBlock(i, j + 1, k);
			if (l != 0 && j1 == Blocks.air && i1 == Blocks.grass || i1 == Blocks.dirt) {
				Block block = Blocks.farmland;
				world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, block.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F,
						block.stepSound.getPitch() * 0.8F);
				if (world.isRemote) {
					return true;
				} else {
					world.setBlock(i, j, k, block);
					if (theToolMaterial == Steamcraft.TOOLSTEAM) {
						itemstack.damageItem(1 + Math.round(itemstack.getItemDamage() * 5 / 320), entityplayer);
						return true;
					}
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			} else {
				return false;
			}
		}
	}
}
